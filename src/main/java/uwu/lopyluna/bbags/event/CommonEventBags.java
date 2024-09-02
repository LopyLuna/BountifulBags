package uwu.lopyluna.bbags.event;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import uwu.lopyluna.bbags.registry.ItemsBB;

import java.util.Collection;

@SuppressWarnings("unused")
public final class CommonEventBags {

    @SubscribeEvent
    public void onLivingDrops(LivingDropsEvent event) {
        Entity entity = event.getEntity();
        Level level = entity.getCommandSenderWorld();

        if (!level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT))
            return;

        Collection<ItemEntity> drops = event.getDrops();
        Entity attackerEntity = event.getSource().getEntity();
        ItemStack itemHand = attackerEntity instanceof Player attackerPlayer ? attackerPlayer.getItemInHand(InteractionHand.MAIN_HAND) : ItemStack.EMPTY;
        int lootLevel = itemHand == ItemStack.EMPTY ? 0 : itemHand.getEnchantmentLevel(Enchantments.MOB_LOOTING);
        double chance1 = (lootLevel * 0.01) >= 1 ? 1.0 : 0.001 + (lootLevel * 0.01);
        double chance2 = (lootLevel * 0.02) >= 1 ? 1.0 : 0.008 + (lootLevel * 0.02);
        double chance3 = (lootLevel * 0.04) >= 1 ? 1.0 : 0.012 + (lootLevel * 0.04);
        double chance4 = (lootLevel * 0.06) >= 1 ? 1.0 : 0.034 + (lootLevel * 0.06);
        double chance5 = (lootLevel * 0.08) >= 1 ? 1.0 : 0.072 + (lootLevel * 0.08);
        double chance6 = (lootLevel * 0.1) >= 1 ? 1.0 : 0.1 + (lootLevel * 0.1);
        double chance7 = (lootLevel * 0.12) >= 1 ? 1.0 : 0.2 + (lootLevel * 0.12);

        if (entity instanceof PiglinBrute) {
            if (Math.random() < chance1) {
                drops.add(newItemDrop(level, entity, ItemsBB.BASTION_TREASURE_CHAMBER.get()));
            } else if (Math.random() < chance4) {
                drops.add(newItemDrop(level, entity, ItemsBB.BASTION_STABLE_CHAMBER.get()));
            } else if (Math.random() < chance5) {
                drops.add(newItemDrop(level, entity, ItemsBB.BASTION_BRIDGE_CHAMBER.get()));
            } else if (Math.random() < chance7) {
                drops.add(newItemDrop(level, entity, ItemsBB.BASTION_CHAMBER.get()));
            }
        } else if (entity instanceof Monster) {
            if (Math.random() < chance1) {
                drops.add(newItemDrop(level, entity, ItemsBB.LEGENDARY_LOOTBAG.get()));
            } else if (Math.random() < chance2) {
                drops.add(newItemDrop(level, entity, ItemsBB.EPIC_LOOTBAG.get()));
            } else if (Math.random() < chance3) {
                drops.add(newItemDrop(level, entity, ItemsBB.RARE_LOOTBAG.get()));
            } else if (Math.random() < chance4) {
                drops.add(newItemDrop(level, entity, ItemsBB.UNCOMMON_LOOTBAG.get()));
            } else if (Math.random() < chance5) {
                drops.add(newItemDrop(level, entity, ItemsBB.COMMON_LOOTBAG.get()));
            }
        }
    }
    public static ItemEntity newItemDrop(Level level, Entity entity, ItemLike item) {
        return new ItemEntity(level, entity.getX(), entity.getY(), entity.getZ(), new ItemStack(item));
    }
}
