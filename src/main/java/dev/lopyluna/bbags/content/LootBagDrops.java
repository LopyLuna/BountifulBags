package dev.lopyluna.bbags.content;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.lopyluna.bbags.BBagsConfig;
import dev.lopyluna.bbags.content.item.LootBagRarity;
import dev.lopyluna.bbags.register.AllItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import static dev.lopyluna.bbags.BBags.ATTACHMENT_TYPES;

public class LootBagDrops {
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Float>> ENVIRONMENT_DAMAGE = ATTACHMENT_TYPES.register("environment_damage", () -> AttachmentType
            .builder(() -> 0f)
            .build());

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<RecentKills>> RECENT_KILLS = ATTACHMENT_TYPES.register("recent_kills", () -> AttachmentType
            .builder(() -> RecentKills.EMPTY)
            .serialize(RecentKills.CODEC)
            .copyOnDeath()
            .build());

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        var entity = event.getEntity();
        if (!(entity instanceof Enemy) || entity.level().isClientSide || isPlayer(event.getSource())) return;
        entity.setData(ENVIRONMENT_DAMAGE, entity.getData(ENVIRONMENT_DAMAGE) + event.getNewDamage());
    }

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        var entity = event.getEntity();
        var level = entity.level();

        if (!(entity instanceof Enemy) || !isPlayer(event.getSource()) || !(event.getSource().getEntity() instanceof Player player)) return;
        if (!level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) return;
        if (BBagsConfig.SKIP_SPAWNER_MOBS.get() && entity instanceof Mob mob && mob.getSpawnType() == MobSpawnType.SPAWNER) return;
        double maxEnvironment = BBagsConfig.MAX_ENVIRONMENT_DAMAGE.get();
        if (maxEnvironment < 1 && entity.getData(ENVIRONMENT_DAMAGE) > entity.getMaxHealth() * maxEnvironment) return;

        var stack = player.getMainHandItem();
        int lootLevel = stack.isEmpty() ? 0 : stack.getEnchantmentLevel(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.LOOTING));

        var healthTiers = healthTiers(entity.getMaxHealth());
        var multiplier = (1 + lootLevel * BBagsConfig.LOOTING_BONUS.get()) * Math.pow(BBagsConfig.HEALTH_TIER_BONUS.get(), healthTiers) * diminishing(player, level.getGameTime());
        var roll = level.random.nextDouble();
        var rarities = LootBagRarity.values();
        for (int i = rarities.length - 1; i >= 0; i--) {
            int odds = BBagsConfig.ODDS.get(rarities[i]).get();
            if (odds <= 0) continue;
            roll -= multiplier / odds;
            if (roll >= 0) continue;
            event.getDrops().add(new ItemEntity(level, entity.getX(), entity.getY(), entity.getZ(), new ItemStack(AllItems.getLootBag(rarities[Math.min(rarities.length - 1, i + healthTiers)]))));
            return;
        }
    }

    public static int healthTiers(float health) {
        double base = BBagsConfig.HEALTH_TIER_BASE.get();
        if (base <= 0) return 0;
        var tiers = 0;
        while (health >= base * Math.pow(2, tiers) + base) tiers++;
        return tiers;
    }

    public static double diminishing(Player player, long time) {
        if (!BBagsConfig.DIMINISHING_RETURNS.get()) return 1;
        var recent = player.getData(RECENT_KILLS);
        var kills = Math.max(0, recent.kills() - Math.max(0, time - recent.time()) / 1200.0 * BBagsConfig.KILLS_FORGOTTEN_PER_MINUTE.get());
        player.setData(RECENT_KILLS, new RecentKills(kills + 1, time));
        double limit = BBagsConfig.KILL_LIMIT.get();
        return kills <= limit ? 1 : limit / kills;
    }

    public static boolean isPlayer(DamageSource source) {
        return source.getEntity() instanceof Player player && !(player instanceof FakePlayer);
    }

    public static void register() {}

    public record RecentKills(double kills, long time) {
        public static final RecentKills EMPTY = new RecentKills(0, 0);
        public static final Codec<RecentKills> CODEC = RecordCodecBuilder.create(i -> i.group(
                Codec.DOUBLE.fieldOf("kills").forGetter(RecentKills::kills),
                Codec.LONG.fieldOf("time").forGetter(RecentKills::time)
        ).apply(i, RecentKills::new));
    }
}
