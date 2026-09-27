package dev.lopyluna.bbags;

import dev.lopyluna.bbags.content.item.LootBagRarity;
import dev.lopyluna.bbags.register.AllItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import static dev.lopyluna.bbags.BBags.MOD_ID;
import static dev.lopyluna.bbags.BBags.NAME;

public class BBagsDatagen {
    public static final TagKey<Item> LOOT_BAGS = item("loot_bags");

    public static final TagKey<Item> LEATHER_ARMORS = item("leather_armors");
    public static final TagKey<Item> CHAINMAIL_ARMORS = item("chainmail_armors");
    public static final TagKey<Item> IRON_ARMORS = item("iron_armors");
    public static final TagKey<Item> GOLD_ARMORS = item("gold_armors");
    public static final TagKey<Item> DIAMOND_ARMORS = item("diamond_armors");
    public static final TagKey<Item> NETHERITE_ARMORS = item("netherite_armors");

    public static final TagKey<Item> WEAK_ARMORS = item("weak_armor");

    public static final TagKey<Item> OTHER_ARMORS = item("other_armors");
    public static final TagKey<Item> RARE_ARMORS = item("rare_armors");

    public static final TagKey<Item> WOODEN_TOOLS = item("wooden_tools");
    public static final TagKey<Item> STONE_TOOLS = item("stone_tools");
    public static final TagKey<Item> IRON_TOOLS = item("iron_tools");
    public static final TagKey<Item> GOLD_TOOLS = item("gold_tools");
    public static final TagKey<Item> DIAMOND_TOOLS = item("diamond_tools");
    public static final TagKey<Item> NETHERITE_TOOLS = item("netherite_tools");

    public static final TagKey<Item> WEAK_TOOLS = item("weak_tools");

    public static final TagKey<Item> OTHER_TOOLS = item("other_tools");
    public static final TagKey<Item> RARE_TOOLS = item("rare_tools");

    public static final TagKey<Item> OVERWORLD_SAPLINGS = item("overworld_saplings");
    public static final TagKey<Item> NETHER_SAPLINGS = item("nether_saplings");

    public static final TagKey<Item> OVERWORLD_LOGS = item("overworld_logs");
    public static final TagKey<Item> NETHER_LOGS = item("nether_logs");

    public static final TagKey<Item> OVERWORLD_FISHES = item("overworld_fishes");

    public static final TagKey<Item> OVERWORLD_CROPS = item("overworld_crops");
    public static final TagKey<Item> NETHER_CROPS = item("nether_crops");

    public static final TagKey<Item> OVERWORLD_MUSHROOMS = item("overworld_mushrooms");

    public static final TagKey<Item> WEAK_GENERAL_FOOD = item("weak_food");
    public static final TagKey<Item> NORMAL_GENERAL_FOOD = item("normal_food");
    public static final TagKey<Item> STRONG_GENERAL_FOOD = item("strong_food");

    public static final TagKey<Item> GOLDEN_FOOD = item("golden_food");
    public static final TagKey<Item> SPECIAL_FOOD = item("special_food");
    public static final TagKey<Item> UNSAFE_FOOD = item("unsafe_food");

    public static final TagKey<Item> NETHER_MOB_DROPS = item("nether_mob_drops");

    public static final TagKey<Item> COMMON_MOB_DROPS = item("common_mob_drops");
    public static final TagKey<Item> RARE_MOB_DROPS = item("rare_mob_drops");

    public static final TagKey<Item> COMMON_ORES = item("common_ores");
    public static final TagKey<Item> RARE_ORES = item("rare_ores");

    public static final TagKey<Item> COMMON_MATERIALS = item("common_materials");
    public static final TagKey<Item> RARE_MATERIALS = item("rare_materials");

    public static TagKey<Item> item(String name) { return TagKey.create(Registries.ITEM, BBags.loc(name)); }

    public static void gatherData(GatherDataEvent event) {
        if (!event.getMods().contains(MOD_ID)) return;
        var gen = event.getGenerator();
        var output = gen.getPackOutput();
        var provider = event.getLookupProvider();
        var helper = event.getExistingFileHelper();

        gen.addProvider(event.includeClient(), new Models(output, helper));
        gen.addProvider(event.includeClient(), new Lang(output));

        gen.addProvider(event.includeServer(), new Recipes(output, provider));
        gen.addProvider(event.includeServer(), new ItemTags(output, provider, helper));
        gen.addProvider(event.includeServer(), new LootTableProvider(output, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(Loot::new, LootContextParamSets.GIFT)
        ), provider));
    }

    public static class Models extends ItemModelProvider {
        public Models(PackOutput output, ExistingFileHelper helper) {
            super(output, MOD_ID, helper);
        }

        @Override
        protected void registerModels() {
            for (var bag : AllItems.LOOT_BAGS.values()) basicItem(bag.get())
                    .override().model(basicItem(BBags.loc(bag.getId().getPath() + "_xmas"))).predicate(BBags.loc("xmas"), 1.0f).end();
        }
    }

    public static class Lang extends LanguageProvider {
        public Lang(PackOutput output) {
            super(output, MOD_ID, "en_us");
        }

        @Override
        protected void addTranslations() {
            add("itemGroup." + MOD_ID + ".base", NAME);
            AllItems.LOOT_BAGS.forEach((rarity, bag) -> addItem(bag, StringUtils.capitalize(rarity.id()) + " Loot Bag"));
            BBagsConfig.LANG.forEach((key, name) -> add(MOD_ID + ".configuration." + key, name));
        }
    }

    public static class Recipes extends RecipeProvider {
        public Recipes(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
            super(output, provider);
        }

        @Override
        protected void buildRecipes(@NotNull RecipeOutput output) {
            for (var rarity : LootBagRarity.values()) {
                var lower = rarity.lower();
                if (lower == null) continue;
                var bag = AllItems.getLootBag(rarity);
                var lowerBag = AllItems.getLootBag(lower);
                var name = "crafting/" + rarity.id() + "_loot_bag_";

                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, lowerBag, 4)
                        .requires(bag).unlockedBy("has_" + rarity.id(), has(bag))
                        .save(output, BBags.loc(name + "to_" + lower.id()));
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, bag)
                        .requires(lowerBag, 4).unlockedBy("has_" + lower.id(), has(lowerBag))
                        .save(output, BBags.loc(name + "from_" + lower.id()));
            }
        }
    }

    public static class ItemTags extends ItemTagsProvider {
        public ItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, ExistingFileHelper helper) {
            super(output, provider, CompletableFuture.completedFuture(TagsProvider.TagLookup.empty()), MOD_ID, helper);
        }

        @Override
        protected void addTags(@NotNull HolderLookup.Provider provider) {
            var bags = tag(LOOT_BAGS);
            for (var bag : AllItems.LOOT_BAGS.values()) bags.add(bag.get());

            tag(LEATHER_ARMORS)
                    .add(Items.LEATHER_HELMET)
                    .add(Items.LEATHER_CHESTPLATE)
                    .add(Items.LEATHER_LEGGINGS)
                    .add(Items.LEATHER_BOOTS)
                    .add(Items.LEATHER_HORSE_ARMOR);
            tag(CHAINMAIL_ARMORS)
                    .add(Items.CHAINMAIL_HELMET)
                    .add(Items.CHAINMAIL_CHESTPLATE)
                    .add(Items.CHAINMAIL_LEGGINGS)
                    .add(Items.CHAINMAIL_BOOTS);
            tag(IRON_ARMORS)
                    .add(Items.IRON_HELMET)
                    .add(Items.IRON_CHESTPLATE)
                    .add(Items.IRON_LEGGINGS)
                    .add(Items.IRON_BOOTS)
                    .add(Items.IRON_HORSE_ARMOR);
            tag(GOLD_ARMORS)
                    .add(Items.GOLDEN_HELMET)
                    .add(Items.GOLDEN_CHESTPLATE)
                    .add(Items.GOLDEN_LEGGINGS)
                    .add(Items.GOLDEN_BOOTS)
                    .add(Items.GOLDEN_HORSE_ARMOR);
            tag(DIAMOND_ARMORS)
                    .add(Items.DIAMOND_HELMET)
                    .add(Items.DIAMOND_CHESTPLATE)
                    .add(Items.DIAMOND_LEGGINGS)
                    .add(Items.DIAMOND_BOOTS)
                    .add(Items.DIAMOND_HORSE_ARMOR);
            tag(NETHERITE_ARMORS)
                    .add(Items.NETHERITE_HELMET)
                    .add(Items.NETHERITE_CHESTPLATE)
                    .add(Items.NETHERITE_LEGGINGS)
                    .add(Items.NETHERITE_BOOTS);

            tag(WEAK_ARMORS)
                    .addTag(GOLD_ARMORS)
                    .addTag(CHAINMAIL_ARMORS);

            tag(OTHER_ARMORS)
                    .add(Items.WOLF_ARMOR)
                    .add(Items.TURTLE_HELMET);

            tag(RARE_ARMORS)
                    .add(Items.ELYTRA);

            tag(WOODEN_TOOLS)
                    .add(Items.WOODEN_AXE)
                    .add(Items.WOODEN_HOE)
                    .add(Items.WOODEN_PICKAXE)
                    .add(Items.WOODEN_SHOVEL)
                    .add(Items.WOODEN_SWORD);
            tag(STONE_TOOLS)
                    .add(Items.STONE_AXE)
                    .add(Items.STONE_HOE)
                    .add(Items.STONE_PICKAXE)
                    .add(Items.STONE_SHOVEL)
                    .add(Items.STONE_SWORD);
            tag(IRON_TOOLS)
                    .add(Items.IRON_AXE)
                    .add(Items.IRON_HOE)
                    .add(Items.IRON_PICKAXE)
                    .add(Items.IRON_SHOVEL)
                    .add(Items.IRON_SWORD);
            tag(GOLD_TOOLS)
                    .add(Items.GOLDEN_AXE)
                    .add(Items.GOLDEN_HOE)
                    .add(Items.GOLDEN_PICKAXE)
                    .add(Items.GOLDEN_SHOVEL)
                    .add(Items.GOLDEN_SWORD);
            tag(DIAMOND_TOOLS)
                    .add(Items.DIAMOND_AXE)
                    .add(Items.DIAMOND_HOE)
                    .add(Items.DIAMOND_PICKAXE)
                    .add(Items.DIAMOND_SHOVEL)
                    .add(Items.DIAMOND_SWORD);
            tag(NETHERITE_TOOLS)
                    .add(Items.NETHERITE_AXE)
                    .add(Items.NETHERITE_HOE)
                    .add(Items.NETHERITE_PICKAXE)
                    .add(Items.NETHERITE_SHOVEL)
                    .add(Items.NETHERITE_SWORD);

            tag(WEAK_TOOLS)
                    .addTag(STONE_TOOLS)
                    .addTag(GOLD_TOOLS);

            tag(OTHER_TOOLS)
                    .add(Items.FISHING_ROD)
                    .add(Items.SHEARS)
                    .add(Items.BOW)
                    .add(Items.CROSSBOW)
                    .add(Items.SHIELD);

            tag(RARE_TOOLS)
                    .add(Items.TRIDENT);

            tag(OVERWORLD_SAPLINGS)
                    .add(Items.OAK_SAPLING)
                    .add(Items.SPRUCE_SAPLING)
                    .add(Items.BIRCH_SAPLING)
                    .add(Items.JUNGLE_SAPLING)
                    .add(Items.ACACIA_SAPLING)
                    .add(Items.DARK_OAK_SAPLING)
                    .add(Items.MANGROVE_PROPAGULE)
                    .add(Items.CHERRY_SAPLING)
                    .add(Items.AZALEA)
                    .add(Items.FLOWERING_AZALEA);

            tag(NETHER_SAPLINGS)
                    .add(Items.WARPED_FUNGUS)
                    .add(Items.CRIMSON_FUNGUS);

            tag(OVERWORLD_LOGS)
                    .add(Items.OAK_LOG)
                    .add(Items.SPRUCE_LOG)
                    .add(Items.BIRCH_LOG)
                    .add(Items.JUNGLE_LOG)
                    .add(Items.ACACIA_LOG)
                    .add(Items.DARK_OAK_LOG)
                    .add(Items.MANGROVE_LOG)
                    .add(Items.CHERRY_LOG);

            tag(NETHER_LOGS)
                    .add(Items.WARPED_STEM)
                    .add(Items.CRIMSON_STEM);

            tag(OVERWORLD_FISHES)
                    .add(Items.COD)
                    .add(Items.SALMON)
                    .add(Items.TROPICAL_FISH)
                    .add(Items.PUFFERFISH);

            tag(OVERWORLD_CROPS)
                    .add(Items.WHEAT)
                    .add(Items.CARROT)
                    .add(Items.POTATO)
                    .add(Items.BEETROOT)
                    .add(Items.PUMPKIN)
                    .add(Items.MELON)
                    .add(Items.GLOW_BERRIES)
                    .add(Items.SWEET_BERRIES)
                    .add(Items.COCOA_BEANS);

            tag(NETHER_CROPS)
                    .add(Items.NETHER_WART);


            tag(OVERWORLD_MUSHROOMS)
                    .add(Items.RED_MUSHROOM)
                    .add(Items.BROWN_MUSHROOM);



            tag(WEAK_GENERAL_FOOD)
                    .add(Items.TROPICAL_FISH)
                    .add(Items.DRIED_KELP)
                    .add(Items.POTATO)
                    .add(Items.BEETROOT)
                    .add(Items.COOKIE)
                    .add(Items.GLOW_BERRIES)
                    .add(Items.COD)
                    .add(Items.SALMON)
                    .add(Items.SWEET_BERRIES)
                    .add(Items.MELON_SLICE)
                    .add(Items.MUTTON)
                    .add(Items.BEEF)
                    .add(Items.PORKCHOP)
                    .add(Items.RABBIT)
                    .add(Items.CARROT)
            ;
            tag(NORMAL_GENERAL_FOOD)
                    .add(Items.APPLE)
                    .add(Items.BAKED_POTATO)
                    .add(Items.BREAD)
                    .add(Items.COOKED_COD)
                    .add(Items.COOKED_RABBIT)
                    .add(Items.HONEY_BOTTLE)
                    .add(Items.BEETROOT_SOUP)
                    .add(Items.COOKED_CHICKEN)
                    .add(Items.MUSHROOM_STEW)
                    .add(Items.COOKED_MUTTON)
                    .add(Items.COOKED_SALMON)
            ;
            tag(STRONG_GENERAL_FOOD)
                    .add(Items.GOLDEN_CARROT)
                    .add(Items.GOLDEN_APPLE)
                    .add(Items.PUMPKIN_PIE)
                    .add(Items.COOKED_PORKCHOP)
                    .add(Items.COOKED_BEEF)
                    .add(Items.RABBIT_STEW)
            ;

            tag(GOLDEN_FOOD)
                    .add(Items.GOLDEN_APPLE)
                    .add(Items.GOLDEN_CARROT);

            tag(SPECIAL_FOOD)
                    .add(Items.ENCHANTED_GOLDEN_APPLE);

            tag(UNSAFE_FOOD)
                    .add(Items.SPIDER_EYE)
                    .add(Items.ROTTEN_FLESH)
                    .add(Items.POISONOUS_POTATO)
                    .add(Items.PUFFERFISH)
                    .add(Items.CHICKEN);


            tag(NETHER_MOB_DROPS)
                    .add(Items.BLAZE_ROD)
                    .add(Items.GHAST_TEAR)
                    .add(Items.MAGMA_CREAM);


            tag(COMMON_MOB_DROPS)
                    .add(Items.FEATHER)
                    .add(Items.INK_SAC)
                    .add(Items.LEATHER)
                    .add(Items.STRING)
                    .add(Items.BONE)
                    .add(Items.SLIME_BALL)
                    .add(Items.RABBIT_HIDE)
                    .add(Items.GUNPOWDER)
                    .add(Items.ROTTEN_FLESH)
                    .add(Items.BREEZE_ROD)
                    .add(Items.SPIDER_EYE);

            tag(RARE_MOB_DROPS)
                    .add(Items.EMERALD)
                    .add(Items.RABBIT_FOOT)
                    .add(Items.PHANTOM_MEMBRANE)
                    .add(Items.BLAZE_ROD)
                    .add(Items.GHAST_TEAR)
                    .add(Items.MAGMA_CREAM)
                    .add(Items.ENDER_PEARL);

            tag(COMMON_ORES)
                    .add(Items.COAL)
                    .add(Items.RAW_COPPER)
                    .add(Items.RAW_IRON)
                    .add(Items.RAW_GOLD)
                    .add(Items.LAPIS_LAZULI)
            ;

            tag(RARE_ORES)
                    .add(Items.AMETHYST_SHARD)
                    .add(Items.REDSTONE)
                    .add(Items.RAW_GOLD)
                    .add(Items.DIAMOND)
                    .add(Items.EMERALD)
                    .add(Items.QUARTZ)
                    .add(Items.GLOWSTONE_DUST)
            ;

            tag(COMMON_MATERIALS)
                    .add(Items.CHARCOAL)
                    .add(Items.COBBLESTONE)
                    .add(Items.STONE)
                    .add(Items.COPPER_INGOT)
                    .add(Items.IRON_INGOT)
                    .add(Items.GOLD_INGOT)
                    .add(Items.FLINT)
                    .add(Items.STRING)
            ;

            tag(RARE_MATERIALS)
                    .add(Items.ECHO_SHARD)
                    .add(Items.PRISMARINE_CRYSTALS)
                    .add(Items.PRISMARINE_SHARD)
                    .add(Items.POPPED_CHORUS_FRUIT)
            ;

            optional(COMMON_MATERIALS, "create", "andesite_alloy", "zinc_ingot", "copper_sheet", "iron_sheet");
            optional(COMMON_ORES, "create", "raw_zinc");
            optional(RARE_MATERIALS, "create", "brass_ingot", "precision_mechanism", "electron_tube", "polished_rose_quartz", "experience_nugget");
            optional(RARE_ORES, "create", "rose_quartz");
            optional(NORMAL_GENERAL_FOOD, "create", "bar_of_chocolate", "honeyed_apple");
            optional(STRONG_GENERAL_FOOD, "create", "sweet_roll", "chocolate_glazed_berries");

            optional(STONE_TOOLS, "farmersdelight", "flint_knife");
            optional(GOLD_TOOLS, "farmersdelight", "golden_knife");
            optional(IRON_TOOLS, "farmersdelight", "iron_knife");
            optional(DIAMOND_TOOLS, "farmersdelight", "diamond_knife");
            optional(NETHERITE_TOOLS, "farmersdelight", "netherite_knife");
            optional(OVERWORLD_CROPS, "farmersdelight", "cabbage", "tomato", "onion", "rice");
            optional(WEAK_GENERAL_FOOD, "farmersdelight", "fried_egg", "sweet_berry_cookie", "honey_cookie");
            optional(NORMAL_GENERAL_FOOD, "farmersdelight", "barbecue_stick", "hamburger", "bacon_sandwich", "chicken_sandwich", "mutton_wrap", "dumplings");
            optional(STRONG_GENERAL_FOOD, "farmersdelight", "beef_stew", "bacon_and_eggs", "pasta_with_meatballs", "steak_and_potatoes");

            optionalTags(COMMON_ORES, "raw_materials/tin", "raw_materials/lead", "raw_materials/zinc", "raw_materials/nickel", "raw_materials/aluminum");
            optionalTags(RARE_ORES, "raw_materials/silver", "raw_materials/osmium", "raw_materials/platinum", "raw_materials/tungsten", "raw_materials/uranium", "raw_materials/thorium", "gems/ruby", "gems/sapphire", "gems/peridot");
            optionalTags(COMMON_MATERIALS, "ingots/tin", "ingots/lead", "ingots/zinc", "ingots/nickel", "ingots/aluminum", "ingots/bronze", "ingots/cast_iron", "ingots/wrought_iron", "ingots/tarnished_gold");
            optionalTags(RARE_MATERIALS, "ingots/silver", "ingots/osmium", "ingots/steel", "ingots/electrum", "ingots/invar", "ingots/brass", "ingots/platinum", "ingots/tungsten", "ingots/uranium", "ingots/thorium", "ingots/rose_gold", "ingots/constantan");
        }

        private void optional(TagKey<Item> tag, String modId, String... paths) {
            var appender = tag(tag);
            for (var path : paths) appender.addOptional(ResourceLocation.fromNamespaceAndPath(modId, path));
        }

        private void optionalTags(TagKey<Item> tag, String... paths) {
            var appender = tag(tag);
            for (var path : paths) appender.addOptionalTag(ResourceLocation.fromNamespaceAndPath("c", path));
        }
    }

    public static class Loot implements LootTableSubProvider {
        private final HolderLookup.Provider registries;

        public Loot(HolderLookup.Provider registries) {
            this.registries = registries;
        }

        @Override
        public void generate(@NotNull BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
            for (var rarity : LootBagRarity.values()) output.accept(rarity.loot, LootTable.lootTable().withPool(switch (rarity) {
                case COMMON -> common();
                case UNCOMMON -> uncommon();
                case RARE -> rare();
                case EPIC -> epic();
                case LEGENDARY -> legendary();
            }));
        }

        public LootPool.Builder common() {
            var pool = pool(1, 2);

            pool.add(tag(LEATHER_ARMORS, 10));
            pool.add(tag(WOODEN_TOOLS, 10));
            pool.add(tag(OVERWORLD_SAPLINGS, 10));
            pool.add(tag(OVERWORLD_FISHES, 10, 1, 4));
            pool.add(tag(OVERWORLD_MUSHROOMS, 10, 1, 4));
            pool.add(tag(WEAK_GENERAL_FOOD, 40, 1, 4));
            pool.add(tag(UNSAFE_FOOD, 20, 1, 4));
            pool.add(tag(Tags.Items.DYES, 5, 1, 4));

            pool.add(book(4, 10));

            return pool;
        }

        public LootPool.Builder uncommon() {
            var pool = pool(1, 3);

            pool.add(tag(WEAK_ARMORS, 8));
            pool.add(tag(WEAK_TOOLS, 8));

            pool.add(tag(OVERWORLD_LOGS, 40, 2, 8));
            pool.add(tag(OVERWORLD_CROPS, 30, 1, 4));
            pool.add(tag(NORMAL_GENERAL_FOOD, 50, 2, 6));
            pool.add(tag(COMMON_MOB_DROPS, 30, 2, 6));
            pool.add(tag(OVERWORLD_MUSHROOMS, 10, 2, 4));
            pool.add(tag(COMMON_ORES, 40, 1, 6));
            pool.add(tag(Tags.Items.DYES, 5, 1, 4));

            for (var item : List.of(
                    Items.BUCKET,
                    Items.EGG,
                    Items.HONEYCOMB,
                    Items.NAME_TAG,
                    Items.LEAD,
                    Items.BOOK,
                    Items.STICK,
                    Items.BONE_MEAL
            )) pool.add(item(item, 10, 1, item == Items.BUCKET ? 1 : 2));

            pool.add(book(8, 10));

            return pool;
        }

        public LootPool.Builder rare() {
            var pool = pool(1, 4);

            pool.add(tag(IRON_ARMORS, 8));
            pool.add(tag(IRON_TOOLS, 8));

            pool.add(tag(OTHER_ARMORS, 8));
            pool.add(tag(OTHER_TOOLS, 8));

            pool.add(tag(NETHER_SAPLINGS, 20, 1, 2));
            pool.add(tag(NETHER_CROPS, 10, 1, 2));

            pool.add(tag(OVERWORLD_LOGS, 30, 4, 16));
            pool.add(tag(NORMAL_GENERAL_FOOD, 40, 2, 8));
            pool.add(tag(RARE_MOB_DROPS, 20, 2, 8));
            pool.add(tag(COMMON_MOB_DROPS, 50, 2, 8));
            pool.add(tag(COMMON_MATERIALS, 50, 2, 6));
            pool.add(tag(COMMON_ORES, 50, 4, 12));
            pool.add(tag(OVERWORLD_MUSHROOMS, 10, 1, 4));

            for (var item : List.of(
                    Items.BUCKET,
                    Items.HONEYCOMB,
                    Items.NAME_TAG,
                    Items.LEAD,
                    Items.BOOK,
                    Items.ARMADILLO_SCUTE,
                    Items.TURTLE_SCUTE,
                    Items.NAUTILUS_SHELL
            )) pool.add(item(item, 20, 1, 4));

            pool.add(book(15, 20));

            return pool;
        }

        public LootPool.Builder epic() {
            var pool = pool(1, 5);

            pool.add(tag(DIAMOND_ARMORS, 6));
            pool.add(tag(DIAMOND_TOOLS, 6));

            pool.add(tag(RARE_TOOLS, 8));

            pool.add(tag(OVERWORLD_LOGS, 40, 8, 24));
            pool.add(tag(NETHER_LOGS, 40, 2, 8));

            pool.add(tag(RARE_MOB_DROPS, 50, 2, 8));
            pool.add(tag(NETHER_MOB_DROPS, 40, 2, 8));

            pool.add(tag(COMMON_MATERIALS, 50, 4, 12));
            pool.add(tag(RARE_ORES, 50, 2, 8));

            pool.add(tag(NORMAL_GENERAL_FOOD, 40, 1, 8));
            pool.add(tag(STRONG_GENERAL_FOOD, 10, 1, 4));
            pool.add(tag(GOLDEN_FOOD, 10, 1, 4));

            for (var item : List.of(
                    Items.HONEYCOMB,
                    Items.ARMADILLO_SCUTE,
                    Items.TURTLE_SCUTE,
                    Items.BOOK,
                    Items.NAUTILUS_SHELL,
                    Items.TOTEM_OF_UNDYING,
                    Items.HEART_OF_THE_SEA
            )) pool.add(item(item, 10, 1, item == Items.HEART_OF_THE_SEA || item == Items.TOTEM_OF_UNDYING ? 1 : 4));

            pool.add(book(30, 10));

            return pool;
        }

        public LootPool.Builder legendary() {
            var pool = pool(1, 5);

            pool.add(tag(NETHERITE_ARMORS, 5));
            pool.add(tag(NETHERITE_TOOLS, 5));

            pool.add(tag(RARE_ARMORS, 1));
            pool.add(tag(RARE_TOOLS, 1));

            pool.add(tag(RARE_MOB_DROPS, 80, 1, 8));

            pool.add(tag(RARE_ORES, 80, 4, 16));
            pool.add(tag(RARE_MATERIALS, 80, 2, 12));

            pool.add(tag(STRONG_GENERAL_FOOD, 40, 2, 8));
            pool.add(tag(GOLDEN_FOOD, 20, 1, 4));
            pool.add(tag(SPECIAL_FOOD, 5, 1, 4));

            for (var item : List.of(
                    Items.NETHERITE_SCRAP,
                    Items.ANCIENT_DEBRIS,
                    Items.SHULKER_SHELL,
                    Items.DRAGON_BREATH,
                    Items.BOOK,
                    Items.TOTEM_OF_UNDYING,
                    Items.HEART_OF_THE_SEA
            )) pool.add(item(item, 10, 1, item == Items.HEART_OF_THE_SEA || item == Items.TOTEM_OF_UNDYING || item == Items.SHULKER_SHELL ? 1 : 4));

            pool.add(book(45, 10));

            return pool;
        }

        public LootPool.Builder pool(int rollsMin, int rollsMax) {
            return LootPool.lootPool().setRolls(UniformGenerator.between(rollsMin, rollsMax));
        }
        public LootPoolSingletonContainer.Builder<?> item(ItemLike item, int weight, int countMin, int countMax) {
            return LootItem.lootTableItem(item).setWeight(weight).apply(SetItemCountFunction.setCount(UniformGenerator.between(countMin, countMax)));
        }
        public LootPoolSingletonContainer.Builder<?> book(int levels, int weight) {
            return LootItem.lootTableItem(Items.BOOK).setWeight(weight).apply(EnchantWithLevelsFunction.enchantWithLevels(registries, ConstantValue.exactly(levels)));
        }
        public LootPoolSingletonContainer.Builder<?> tag(TagKey<Item> item, int weight) {
            return TagEntry.expandTag(item).setWeight(weight);
        }
        public LootPoolSingletonContainer.Builder<?> tag(TagKey<Item> item, int weight, int countMin, int countMax) {
            return tag(item, weight).apply(SetItemCountFunction.setCount(UniformGenerator.between(countMin, countMax)));
        }
    }
}
