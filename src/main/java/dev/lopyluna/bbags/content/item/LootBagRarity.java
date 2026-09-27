package dev.lopyluna.bbags.content.item;

import dev.lopyluna.bbags.BBags;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.function.Function;

public enum LootBagRarity {
    COMMON(64, s -> s),
    UNCOMMON(256, s -> s.withColor(0x9CFF2B)),
    RARE(1024, s -> s.withColor(0x70D4FF)),
    EPIC(4096, s -> s.withColor(0xFF60E2)),
    LEGENDARY(16384, s -> s.withColor(0xFFE649));

    public final ResourceKey<LootTable> loot;
    public final Style style;
    public final int odds;

    LootBagRarity(int odds, Function<Style, Style> style) {
        this.odds = odds;
        this.loot = ResourceKey.create(Registries.LOOT_TABLE, BBags.loc("gameplay/lootbags/" + id()));
        this.style = style.apply(Style.EMPTY);
    }

    public String id() {
        return name().toLowerCase(Locale.ROOT);
    }

    @Nullable
    public LootBagRarity lower() {
        return ordinal() == 0 ? null : values()[ordinal() - 1];
    }
}
