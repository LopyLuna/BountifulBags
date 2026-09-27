package dev.lopyluna.bbags.content.item;

import dev.lopyluna.bbags.BBagsConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@ParametersAreNonnullByDefault
public class LootBagItem extends Item {
    public static boolean isNewYearMonth = LocalDate.now().getMonth() == Month.JANUARY;
    public static boolean isValentineMonth = LocalDate.now().getMonth() == Month.FEBRUARY;
    public static boolean isSaintPatrickMonth = LocalDate.now().getMonth() == Month.MARCH;
    public static boolean isEasterMonth = LocalDate.now().getMonth() == Month.APRIL;
    //MAY
    public static boolean isPrideMonth = LocalDate.now().getMonth() == Month.JUNE;
    //JULY
    //AUGUST
    //SEPTEMBER
    public static boolean isHalloweenMonth = LocalDate.now().getMonth() == Month.OCTOBER;
    public static boolean isThanksgivingMonth = LocalDate.now().getMonth() == Month.NOVEMBER;
    public static boolean isXmasMonth = LocalDate.now().getMonth() == Month.DECEMBER;

    public LootBagRarity rarity;

    public LootBagItem(Properties properties, LootBagRarity rarity) {
        super(properties.stacksTo(16));
        this.rarity = rarity;
    }


    @Override
    public @NotNull Component getName(ItemStack stack) {
        return super.getName(stack).copy().withStyle(rarity.style);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
        var stack = entity.getItemInHand(hand);
        entity.awardStat(Stats.ITEM_USED.get(this));
        if (!(world instanceof ServerLevel level && entity instanceof ServerPlayer player)) {
            playOpenSound(entity, world);
            return InteractionResultHolder.consume(stack);
        }

        var shift = player.isShiftKeyDown();
        var params = getLootParams(level, player);
        var infinite = player.hasInfiniteMaterials();

        var loot = level.getServer().reloadableRegistries().getLootTable(rarity.loot);

        if (shift) {
            var count = stack.getCount();
            for (int i = 0; i < count; i++) {
                for (var itemStack : getLoot(i == 0, level, params, loot)) ItemHandlerHelper.giveItemToPlayer(player, itemStack);
                if (!infinite) stack.shrink(1);
            }
        } else {
            for (var itemStack : getLoot(false, level, params, loot)) ItemHandlerHelper.giveItemToPlayer(player, itemStack);
            if (!infinite) stack.shrink(1);
        }
        //ItemHandlerHelper.giveItemToPlayer();
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.NONE;
    }

    public static LootParams getLootParams(ServerLevel level, ServerPlayer player) {
        return new LootParams.Builder(level)
                .withParameter(LootContextParams.THIS_ENTITY, player)
                .withParameter(LootContextParams.ORIGIN, player.position())
                .withLuck(player.getLuck())
                .create(LootContextParamSets.GIFT);
    }

    public List<ItemStack> getLoot(boolean flag, ServerLevel level, LootParams params, LootTable loot) {
        var i = 0;
        var rand = loot.getRandomItems(params, level.random);
        if (flag) while (rand.isEmpty() && i++ < 10) rand = loot.getRandomItems(params, level.random);
        for (var item : rand) if (item.isDamageableItem()) damage(item, level);
        return rand;
    }

    public static void damage(ItemStack stack, ServerLevel level) {
        double min = BBagsConfig.MIN_ITEM_DAMAGE.get();
        double max = BBagsConfig.MAX_ITEM_DAMAGE.get();
        var amount = Math.min(min, max) + level.random.nextDouble() * Math.abs(max - min);
        stack.setDamageValue(Math.min(stack.getMaxDamage() - 1, Mth.floor(stack.getMaxDamage() * amount)));
    }


    private void playOpenSound(Entity entity, Level level) {
        if (isXmasMonth) {
            entity.playSound(SoundEvents.PAINTING_PLACE, 0.6F, 0.50F + level.random.nextFloat() * 0.4F);
            entity.playSound(SoundEvents.PAINTING_BREAK, 0.8F, 0.65F + level.random.nextFloat() * 0.4F);
        }
        entity.playSound(SoundEvents.BUNDLE_DROP_CONTENTS, isXmasMonth ? 0.6F : 0.8F, 0.8F + level.random.nextFloat() * 0.4F);
    }
}
