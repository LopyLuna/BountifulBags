package uwu.lopyluna.bbags.content.item;

import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import static uwu.lopyluna.bbags.BountifulBags.*;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BagItem extends Item {

    ResourceLocation COMMON = new ResourceLocation(MOD_ID, "gameplay/lootbags/common");
    ResourceLocation UNCOMMON = new ResourceLocation(MOD_ID, "gameplay/lootbags/uncommon");
    ResourceLocation RARE = new ResourceLocation(MOD_ID, "gameplay/lootbags/rare");
    ResourceLocation EPIC = new ResourceLocation(MOD_ID, "gameplay/lootbags/epic");
    ResourceLocation LEGENDARY = new ResourceLocation(MOD_ID, "gameplay/lootbags/legendary");
    ResourceLocation PRESENT = new ResourceLocation(MOD_ID, "gameplay/lootbags/xmas");
    ResourceLocation HALLOWEEN = new ResourceLocation(MOD_ID, "gameplay/lootbags/halloween");

    ResourceLocation providedLootTable;
    BagType TYPE;

    public static BagItem create(Properties pProperties, BagType pType) {
        return new BagItem(pProperties, pType, BuiltInLootTables.EMPTY);
    }
    public static BagItem create(Properties pProperties, BagType pType, ResourceLocation pProvidedLootTable) {
        return new BagItem(pProperties, pType, pProvidedLootTable);
    }
    public static BagItem create(Properties pProperties, ResourceLocation pProvidedLootTable) {
        return new BagItem(pProperties, BagType.NORMAL, pProvidedLootTable);
    }

    public BagItem(Properties pProperties, BagType pType, ResourceLocation pProvidedLootTable) {
        super(pProperties.stacksTo(16));
        TYPE = pType;
        providedLootTable = pProvidedLootTable;
    }

    public boolean isXmasValid() {
        return (isXmasMonth) && isLootbagToHolidayValid();
    }
    public boolean isEasterValid() {
        return (isEasterMonth) && isLootbagToHolidayValid();
    }

    @Override
    public Component getName(ItemStack pStack) {
        return isXmasValid() ? Component.translatable(this.getDescriptionId(pStack) + "_xmas") : isEasterValid() ? Component.translatable(this.getDescriptionId(pStack) + "_easter") : super.getName(pStack);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);

        if (!pLevel.isClientSide && pPlayer instanceof ServerPlayer pServerPlayer) {
            ServerLevel pServerLevel = pServerPlayer.serverLevel();
            MinecraftServer pServer = pServerPlayer.level().getServer();
            if (pServer == null) return super.use(pLevel, pServerPlayer, pUsedHand);


            LootParams lootParams = (new LootParams.Builder(pServerLevel))
                    .withParameter(LootContextParams.THIS_ENTITY, pServerPlayer)
                    .withParameter(LootContextParams.ORIGIN, pServerPlayer.position())
                    .withLuck(pServerPlayer.getLuck())
                    .create(LootContextParamSets.GIFT);

            List<ItemStack> list = pServer.getLootData().getLootTable(getLootTable()).getRandomItems(lootParams);

            for (ItemStack stack : list) {
                if (!pServerPlayer.getInventory().add(stack)) {
                    Objects.requireNonNull(pServerPlayer.drop(stack, true)).setPickUpDelay(0);
                }
            }
        }

        this.playDropContentsSound(pPlayer);
        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        if (TYPE == BagType.DEBUG) {
            pPlayer.displayClientMessage(Component.literal("success").withStyle(ChatFormatting.GREEN), false);
        }
        if (!pPlayer.getAbilities().instabuild || TYPE == BagType.DEBUG) {
            itemStack.shrink(1);
        }
        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide());
    }

    private void playDropContentsSound(Entity pEntity) {
        if (TYPE == BagType.PRESENT || isXmasValid()) {
            pEntity.playSound(SoundEvents.PAINTING_PLACE, 0.6F, 0.50F + pEntity.level().getRandom().nextFloat() * 0.4F);
            pEntity.playSound(SoundEvents.PAINTING_BREAK, 0.8F, 0.65F + pEntity.level().getRandom().nextFloat() * 0.4F);
        } else if (TYPE == BagType.NORMAL_CRATE) {
            pEntity.playSound(SoundEvents.ZOMBIE_BREAK_WOODEN_DOOR, 0.3F, 0.8F + pEntity.level().getRandom().nextFloat() * 0.4F);
        } else if (isEasterValid()) {
            pEntity.playSound(SoundEvents.SNIFFER_EGG_HATCH, 0.6F, 0.8F + pEntity.level().getRandom().nextFloat() * 0.4F);
        } else if (TYPE == BagType.NORMAL_CHAMBER) {
            pEntity.playSound(SoundEvents.GILDED_BLACKSTONE_BREAK, 1.25F, 0.9F + pEntity.level().getRandom().nextFloat() * 0.4F);
            pEntity.playSound(SoundEvents.ZOMBIE_ATTACK_IRON_DOOR, 0.6F, 0.3F + pEntity.level().getRandom().nextFloat() * 0.4F);
        } else {
            pEntity.playSound(SoundEvents.BUNDLE_DROP_CONTENTS, 0.8F, 0.8F + pEntity.level().getRandom().nextFloat() * 0.4F);
        }
        if (isHalloweenMonth) {
            if (TYPE == BagType.HALLOWEEN) {
                if (pEntity.level().getRandom().nextIntBetweenInclusive(1, 50) == 1) {
                    pEntity.playSound(halloweenScarySounds().get(pEntity.level().getRandom().nextIntBetweenInclusive(1, halloweenScarySounds().size())), 0.6F + pEntity.level().getRandom().nextFloat() * 0.4F, 0.6F + pEntity.level().getRandom().nextFloat() * 0.4F);
                }
            } else {
                if (pEntity.level().getRandom().nextIntBetweenInclusive(1, 100) == 1) {
                    pEntity.playSound(halloweenScarySounds().get(pEntity.level().getRandom().nextIntBetweenInclusive(1, halloweenScarySounds().size())), 0.6F + pEntity.level().getRandom().nextFloat() * 0.4F, 0.6F + pEntity.level().getRandom().nextFloat() * 0.4F);
                }
            }
            pEntity.playSound(SoundEvents.SOUL_ESCAPE, 1.25F, 0.8F + pEntity.level().getRandom().nextFloat() * 0.4F);
        }
        pEntity.playSound(SoundEvents.ITEM_PICKUP, 0.4F, 0.6F + pEntity.level().getRandom().nextFloat() * 0.4F);
    }

    public boolean isLootbagToHolidayValid() {
        return switch (TYPE) {
            case COMMON, UNCOMMON, RARE, EPIC, LEGENDARY -> true;
            case PRESENT, HALLOWEEN, DEBUG, NORMAL, NORMAL_CRATE, NORMAL_CHAMBER -> false;
        };
    }

    public List<SoundEvent> halloweenScarySounds() {
        return new ArrayList<>(List.of(SoundEvents.RAVAGER_ROAR, SoundEvents.RAVAGER_CELEBRATE, SoundEvents.RAVAGER_STUNNED, SoundEvents.TRIDENT_THUNDER, SoundEvents.GOAT_HORN_SOUND_VARIANTS.get(7).get(), SoundEvents.GOAT_HORN_SOUND_VARIANTS.get(6).get(), SoundEvents.GOAT_HORN_SOUND_VARIANTS.get(4).get(), SoundEvents.RAID_HORN.get(), SoundEvents.TOTEM_USE, SoundEvents.ENDER_DRAGON_GROWL, SoundEvents.END_GATEWAY_SPAWN, SoundEvents.END_PORTAL_SPAWN, SoundEvents.WANDERING_TRADER_DISAPPEARED, SoundEvents.WANDERING_TRADER_AMBIENT, SoundEvents.WANDERING_TRADER_REAPPEARED, SoundEvents.LLAMA_ANGRY, SoundEvents.GHAST_WARN, SoundEvents.GHAST_SCREAM, SoundEvents.ILLUSIONER_PREPARE_BLINDNESS, SoundEvents.EVOKER_PREPARE_ATTACK, SoundEvents.EVOKER_PREPARE_WOLOLO, SoundEvents.ENDERMAN_STARE, SoundEvents.ENDERMAN_SCREAM, SoundEvents.WITHER_SPAWN, SoundEvents.ZOMBIE_BREAK_WOODEN_DOOR, SoundEvents.ZOMBIE_VILLAGER_CURE, SoundEvents.WITCH_CELEBRATE, SoundEvents.WARDEN_EMERGE, SoundEvents.CREEPER_PRIMED, SoundEvents.LIGHTNING_BOLT_THUNDER, SoundEvents.GHAST_AMBIENT, SoundEvents.SOUL_ESCAPE, SoundEvents.AMBIENT_CAVE.get(), SoundEvents.AMBIENT_UNDERWATER_LOOP_ADDITIONS_RARE, SoundEvents.AMBIENT_UNDERWATER_LOOP_ADDITIONS_ULTRA_RARE));
    }

    public ResourceLocation getLootTable() {
        return switch (TYPE) {
            case COMMON -> COMMON;
            case UNCOMMON -> UNCOMMON;
            case RARE -> RARE;
            case EPIC -> EPIC;
            case LEGENDARY -> LEGENDARY;
            case PRESENT -> PRESENT;
            case HALLOWEEN -> HALLOWEEN;
            case DEBUG -> BuiltInLootTables.EMPTY;
            case NORMAL, NORMAL_CRATE, NORMAL_CHAMBER -> providedLootTable;
        };
    }
}
