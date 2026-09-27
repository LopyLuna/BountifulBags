package dev.lopyluna.bbags.register;

import dev.lopyluna.bbags.content.item.LootBagItem;
import dev.lopyluna.bbags.content.item.LootBagRarity;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.EnumMap;
import java.util.Map;

import static dev.lopyluna.bbags.BBags.*;

public class AllItems {
    public static final Map<LootBagRarity, DeferredItem<LootBagItem>> LOOT_BAGS = new EnumMap<>(LootBagRarity.class);

    static {
        for (var rarity : LootBagRarity.values()) LOOT_BAGS.put(rarity, ITEMS.registerItem(rarity.id() + "_loot_bag", p -> new LootBagItem(p, rarity)));
    }

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BASE_TAB = CREATIVE_MODE_TABS.register("base_tab", () -> CreativeModeTab.builder()
            .title(Component.translatableWithFallback("itemGroup." + MOD_ID + ".base", NAME))
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .icon(() -> getLootBag(LootBagRarity.LEGENDARY).getDefaultInstance())
            .displayItems((params, output) -> LOOT_BAGS.values().forEach(output::accept))
            .build());

    public static LootBagItem getLootBag(LootBagRarity rarity) {
        return LOOT_BAGS.get(rarity).get();
    }
    public static void register() {}
}
