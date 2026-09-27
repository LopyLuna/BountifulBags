package dev.lopyluna.bbags;

import dev.lopyluna.bbags.content.item.LootBagRarity;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.StringUtils;

import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class BBagsConfig {
    public static final Map<String, String> LANG = new LinkedHashMap<>();
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final Map<LootBagRarity, ModConfigSpec.IntValue> ODDS = new EnumMap<>(LootBagRarity.class);
    public static final ModConfigSpec.DoubleValue LOOTING_BONUS;
    public static final ModConfigSpec.IntValue HEALTH_TIER_BASE;
    public static final ModConfigSpec.DoubleValue HEALTH_TIER_BONUS;

    public static final ModConfigSpec.DoubleValue MIN_ITEM_DAMAGE;
    public static final ModConfigSpec.DoubleValue MAX_ITEM_DAMAGE;

    public static final ModConfigSpec.BooleanValue SKIP_SPAWNER_MOBS;
    public static final ModConfigSpec.DoubleValue MAX_ENVIRONMENT_DAMAGE;
    public static final ModConfigSpec.BooleanValue DIMINISHING_RETURNS;
    public static final ModConfigSpec.IntValue KILL_LIMIT;
    public static final ModConfigSpec.DoubleValue KILLS_FORGOTTEN_PER_MINUTE;

    public static final ModConfigSpec SPEC;

    static {
        push("drops", "Drops", "Loot Bag drop chances when a player kills a hostile mob");
        for (var rarity : LootBagRarity.values()) {
            var name = StringUtils.capitalize(rarity.id());
            ODDS.put(rarity, entry(rarity.id() + "Odds", name + " Odds", "Chance of " + name + " Loot Bags dropping, as 1 in X kills (0 disables it)")
                    .defineInRange(rarity.id() + "Odds", rarity.odds, 0, Integer.MAX_VALUE));
        }
        LOOTING_BONUS = entry("lootingBonus", "Looting Bonus", "Extra drop chance per Looting level (1.0 makes Looting III 4x more likely)")
                .defineInRange("lootingBonus", 1.0, 0.0, 16.0);
        HEALTH_TIER_BASE = entry("healthTierBase", "Health Tier Base", "Health tiers are this x 2^n + this (20 gives 40, 60, 100, 180, 340, 660...), every tier a mob's max health reaches upgrades the Loot Bag by one rarity (0 disables it)")
                .defineInRange("healthTierBase", 20, 0, 100000);
        HEALTH_TIER_BONUS = entry("healthTierBonus", "Health Tier Bonus", "Drop chance multiplier for each health tier a mob reaches (2.0 doubles it every tier)")
                .defineInRange("healthTierBonus", 2.0, 1.0, 16.0);
        BUILDER.pop();

        push("loot", "Loot", "What comes out of Loot Bags when opened");
        MIN_ITEM_DAMAGE = entry("minItemDamage", "Min Item Damage", "Least durability tools and armor from Loot Bags can be missing (0.5 = 50% damaged)")
                .defineInRange("minItemDamage", 0.25, 0.0, 1.0);
        MAX_ITEM_DAMAGE = entry("maxItemDamage", "Max Item Damage", "Most durability tools and armor from Loot Bags can be missing (0.75 = 75% damaged)")
                .defineInRange("maxItemDamage", 0.5, 0.0, 1.0);
        BUILDER.pop();

        push("farming", "Farming", "Limits that stop mob farms from generating Loot Bags");
        SKIP_SPAWNER_MOBS = entry("skipSpawnerMobs", "Skip Spawner Mobs", "Mobs from monster spawners never drop Loot Bags")
                .define("skipSpawnerMobs", true);
        MAX_ENVIRONMENT_DAMAGE = entry("maxEnvironmentDamage", "Max Environment Damage", "Mobs that took more than this share of their max health from non-player damage (falling, drowning, cramming...) drop no Loot Bags (1.0 disables it)")
                .defineInRange("maxEnvironmentDamage", 0.5, 0.0, 1.0);
        DIMINISHING_RETURNS = entry("diminishingReturns", "Diminishing Returns", "Lower drop chances for players who kill lots of mobs in a short time")
                .define("diminishingReturns", true);
        KILL_LIMIT = entry("killLimit", "Kill Limit", "Recent kills a player can have before their drop chances go down (at twice this many, chances are halved)")
                .defineInRange("killLimit", 60, 1, 100000);
        KILLS_FORGOTTEN_PER_MINUTE = entry("killsForgottenPerMinute", "Kills Forgotten Per Minute", "How much a player's recent kill count goes down each minute")
                .defineInRange("killsForgottenPerMinute", 5.0, 0.0, 1000.0);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }

    private static void push(String key, String name, String comment) {
        entry(key, name, comment).push(key);
    }

    private static ModConfigSpec.Builder entry(String key, String name, String comment) {
        LANG.put(key, name);
        LANG.put(key + ".tooltip", comment);
        return BUILDER.comment(comment);
    }
}
