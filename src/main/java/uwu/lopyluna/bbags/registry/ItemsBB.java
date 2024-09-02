package uwu.lopyluna.bbags.registry;


import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import uwu.lopyluna.bbags.BountifulBags;
import uwu.lopyluna.bbags.content.item.BagItem;
import uwu.lopyluna.bbags.content.item.BagType;
import uwu.lopyluna.bbags.foundation.BountifulBagsRegistrate;

import java.util.ArrayList;
import java.util.List;

import static uwu.lopyluna.bbags.foundation.util.ItemUtil.*;

@SuppressWarnings({"unused"})
public class ItemsBB {
    public static final BountifulBagsRegistrate REGISTRATION = BountifulBags.registrate();

    public static final ItemEntry<BagItem> COMMON_LOOTBAG = regItemHoliday("common_lootbag", ChatFormatting.WHITE.toString(), "Common", p -> BagItem.create(p, BagType.COMMON))
            .model((c, p) -> {})
            .lang(ChatFormatting.WHITE + "Common Loot Bag")
            .register();
    public static final ItemEntry<BagItem> UNCOMMON_LOOTBAG = regItemHoliday("uncommon_lootbag", ChatFormatting.GREEN.toString(), "Uncommon", p -> BagItem.create(p, BagType.UNCOMMON))
            .model((c, p) -> {})
            .lang(ChatFormatting.GREEN + "Uncommon Loot Bag")
            .register();
    public static final ItemEntry<BagItem> RARE_LOOTBAG = regItemHoliday("rare_lootbag", ChatFormatting.AQUA.toString(), "Rare", p -> BagItem.create(p, BagType.RARE))
            .model((c, p) -> {})
            .lang(ChatFormatting.AQUA + "Rare Loot Bag")
            .register();
    public static final ItemEntry<BagItem> EPIC_LOOTBAG = regItemHoliday("epic_lootbag", ChatFormatting.LIGHT_PURPLE.toString(), "Epic", p -> BagItem.create(p, BagType.EPIC))
            .model((c, p) -> {})
            .lang(ChatFormatting.LIGHT_PURPLE + "Epic Loot Bag")
            .register();
    public static final ItemEntry<BagItem> LEGENDARY_LOOTBAG = regItemHoliday("legendary_lootbag", ChatFormatting.GOLD.toString(), "Legendary", p -> BagItem.create(p, BagType.LEGENDARY))
            .model((c, p) -> {})
            .lang(ChatFormatting.GOLD + "Legendary Loot Bag")
            .register();
    public static final ItemEntry<BagItem> PRESENT_BOX = regItem("present_box", p -> BagItem.create(p, BagType.PRESENT))
            .lang(ChatFormatting.DARK_GREEN + "Present")
            .register();
    public static final ItemEntry<BagItem> HALLOWEEN_LOOTBAG = regItem("halloween_lootbag", p -> BagItem.create(p, BagType.HALLOWEEN))
            .lang(ChatFormatting.DARK_PURPLE + "Halloween Lootbag")
            .register();

    public static List<ItemEntry<BagItem>> holidayTextureItems = new ArrayList<>(List.of(COMMON_LOOTBAG, UNCOMMON_LOOTBAG, RARE_LOOTBAG, EPIC_LOOTBAG, LEGENDARY_LOOTBAG));

    //BASTION
    public static final ItemEntry<BagItem> BASTION_TREASURE_CHAMBER = regItem("bastion_treasure_chamber", p -> BagItem.create(p, BagType.NORMAL_CHAMBER, BuiltInLootTables.BASTION_TREASURE))
            .model(itemModelExist("bastion_chamber_base"))
            .lang(ChatFormatting.GOLD + "Bastion Treasure Chamber")
            .register();
    public static final ItemEntry<BagItem> BASTION_BRIDGE_CHAMBER = regItem("bastion_bridge_chamber", p -> BagItem.create(p, BagType.NORMAL_CHAMBER, BuiltInLootTables.BASTION_BRIDGE))
            .model(itemModelExist("bastion_chamber_base"))
            .lang(ChatFormatting.LIGHT_PURPLE + "Bastion Bridge Chamber")
            .register();
    public static final ItemEntry<BagItem> BASTION_STABLE_CHAMBER = regItem("bastion_stable_chamber", p -> BagItem.create(p, BagType.NORMAL_CHAMBER, BuiltInLootTables.BASTION_HOGLIN_STABLE))
            .model(itemModelExist("bastion_chamber_base"))
            .lang(ChatFormatting.LIGHT_PURPLE + "Bastion Stable Chamber")
            .register();
    public static final ItemEntry<BagItem> BASTION_CHAMBER= regItem("bastion_chamber", p -> BagItem.create(p, BagType.NORMAL_CHAMBER, BuiltInLootTables.BASTION_OTHER))
            .model(itemModelExist("bastion_chamber_base"))
            .lang(ChatFormatting.LIGHT_PURPLE + "Bastion Chamber")
            .register();
    //END
    public static final ItemEntry<BagItem> END_CITY_TREASURE_LOOTBAG = regItem("end_city_treasure_lootbag", p -> BagItem.create(p, BuiltInLootTables.END_CITY_TREASURE))
            .model(itemModelExist("legendary_lootbag"))
            .lang(ChatFormatting.GOLD + "End City Treasure Lootbag")
            .register();
    //SHIPWRECK
    public static final ItemEntry<BagItem> SHIPWRECK_TREASURE_LOOTBAG = regItem("shipwreck_treasure_lootbag", p -> BagItem.create(p, BuiltInLootTables.SHIPWRECK_TREASURE))
            .model(itemModelExist("rare_lootbag"))
            .lang(ChatFormatting.AQUA + "Shipwreck Treasure Lootbag")
            .register();
    public static final ItemEntry<BagItem> SHIPWRECK_SUPPLY_LOOTBAG = regItem("shipwreck_supply_lootbag", p -> BagItem.create(p, BuiltInLootTables.SHIPWRECK_SUPPLY))
            .model(itemModelExist("uncommon_lootbag"))
            .lang(ChatFormatting.GREEN + "Shipwreck Supply Lootbag")
            .register();
    //FISHING
    public static final ItemEntry<BagItem> FISHING_TREASURE_CRATE = regItem("fishing_treasure_crate", p -> BagItem.create(p, BagType.NORMAL_CRATE, BuiltInLootTables.FISHING_TREASURE))
            .model(itemModelExist("crate"))
            .lang(ChatFormatting.AQUA + "Fishing Treasure Crate")
            .register();
    public static final ItemEntry<BagItem> FISHING_JUNK_CRATE = regItem("fishing_junk_crate", p -> BagItem.create(p, BagType.NORMAL_CRATE, BuiltInLootTables.FISHING_JUNK))
            .model(itemModelExist("crate"))
            .lang(ChatFormatting.GRAY + "Fishing Junk Crate")
            .register();
    //BEACH
    public static final ItemEntry<BagItem> BURIED_TREASURE_CRATE = regItem("buried_treasure_crate", p -> BagItem.create(p, BagType.NORMAL_CRATE, BuiltInLootTables.BURIED_TREASURE))
            .model(itemModelExist("crate"))
            .lang(ChatFormatting.LIGHT_PURPLE + "Buried Treasure Crate")
            .register();


    public static <T extends Item> NonNullBiConsumer<DataGenContext<Item, T>, RegistrateItemModelProvider> itemModelExist() {
        return (c, p) -> p.withExistingParent("item/" + c.getName(), p.modLoc("item/" + c.getName()));
    }
    public static <T extends Item> NonNullBiConsumer<DataGenContext<Item, T>, RegistrateItemModelProvider> itemModelExist(String name) {
        return (c, p) -> p.withExistingParent(c.getId().getPath(), new ResourceLocation(BountifulBags.MOD_ID,"item/" + name));
    }
    
    public static void register() {}
}