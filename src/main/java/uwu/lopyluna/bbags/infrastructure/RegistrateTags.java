package uwu.lopyluna.bbags.infrastructure;

import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.minecraft.core.Holder;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collections;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import static uwu.lopyluna.bbags.BountifulBags.MOD_ID;
import static uwu.lopyluna.bbags.BountifulBags.REGISTRATION;

@SuppressWarnings("deprecation")
public class RegistrateTags {
    public static void addGenerators() {
        REGISTRATION.addDataGenerator(ProviderType.ITEM_TAGS, RegistrateTags::genItemTags);
    }

    private static void genItemTags(RegistrateTagsProvider<Item> p) {
        CreateTagsProvider<Item> prov = new CreateTagsProvider<>(p, Item::builtInRegistryHolder);

        prov.tag(modTag("loot_bags/common/low"))   //Very Rare Loot
                .add(Items.GRAVEL)
                .add(Items.SAND)
                .add(Items.IRON_NUGGET)
                .add(Items.RAW_IRON)
        ;prov.tag(modTag("loot_bags/common/med"))  //Uncommon Loot
                .add(Items.ACACIA_SAPLING)
                .add(Items.BIRCH_SAPLING)
                .add(Items.SPRUCE_SAPLING)
                .add(Items.CHERRY_SAPLING)
                .add(Items.OAK_SAPLING)
                .add(Items.DARK_OAK_SAPLING)
                .add(Items.JUNGLE_SAPLING)
                .add(Items.RAW_COPPER)
                .add(Items.STONE)
        ;prov.tag(modTag("loot_bags/common/high")) //Very Common loot
                .add(Items.ACACIA_PLANKS)
                .add(Items.BIRCH_PLANKS)
                .add(Items.SPRUCE_PLANKS)
                .add(Items.CHERRY_PLANKS)
                .add(Items.OAK_PLANKS)
                .add(Items.DARK_OAK_PLANKS)
                .add(Items.JUNGLE_PLANKS)
                .add(Items.TORCH)
                .add(Items.BONE_MEAL)
                .add(Items.BEETROOT_SEEDS)
                .add(Items.MELON_SEEDS)
                .add(Items.PUMPKIN_SEEDS)
                .add(Items.WHEAT_SEEDS)
                .add(Items.STICK)
                .add(Items.POTATO)
                .add(Items.CARROT)
                .add(Items.WHEAT)
                .add(Items.COBBLESTONE)
        ;
        prov.tag(modTag("loot_bags/uncommon/low"))   //Very Rare Loot
                .add(Items.ARROW)
                .add(Items.GOLD_NUGGET)
                .add(Items.RAW_COPPER_BLOCK)
                .add(Items.FIRE_CHARGE)
                .add(Items.AMETHYST_SHARD)
                .add(Items.MUSIC_DISC_11)
                .add(Items.MUSIC_DISC_13)
        ;prov.tag(modTag("loot_bags/uncommon/med"))  //Uncommon Loot
                .add(Items.AMETHYST_SHARD)
                .add(Items.PAPER)
                .add(Items.CACTUS)
                .add(Items.GUNPOWDER)
                .add(Items.MANGROVE_PROPAGULE)
                .add(Items.IRON_INGOT)
                .add(Items.COPPER_BLOCK)
                .add(Items.RAW_COPPER_BLOCK)
                .add(Items.MAP)
        ;prov.tag(modTag("loot_bags/uncommon/high")) //Very Common loot
                .add(Items.ACACIA_LOG)
                .add(Items.BIRCH_LOG)
                .add(Items.SPRUCE_LOG)
                .add(Items.CHERRY_LOG)
                .add(Items.OAK_LOG)
                .add(Items.DARK_OAK_LOG)
                .add(Items.JUNGLE_LOG)
                .add(Items.MANGROVE_LOG)
                .add(Items.TROPICAL_FISH)
                .add(Items.SALMON)
                .add(Items.COD)
                .add(Items.BRICK)
                .add(Items.GRAVEL)
                .add(Items.SAND)
                .add(Items.CHARCOAL)
                .add(Items.MANGROVE_PLANKS)
                .add(Items.STONE)
                .add(Items.COAL)
                .add(Items.STRING)
                .add(Items.BONE)
                .add(Items.FEATHER)
                .add(Items.LEATHER)
                .add(Items.APPLE)
                .add(Items.COPPER_INGOT)
                .add(Items.COCOA_BEANS)
        ;
        prov.tag(modTag("loot_bags/rare/low"))   //Very Rare Loot
                .add(Items.GOLDEN_APPLE)
                .add(Items.GLOWSTONE)
                .add(Items.ENDER_PEARL)
                .add(Items.QUARTZ)
                .add(Items.ARROW)
                .add(Items.RAW_COPPER_BLOCK)
                .add(Items.LEATHER_HORSE_ARMOR)
                .add(Items.MUSIC_DISC_CAT)
                .add(Items.MUSIC_DISC_BLOCKS)
                .add(Items.MUSIC_DISC_FAR)
                .add(Items.MUSIC_DISC_CHIRP)
                .add(Items.MUSIC_DISC_MALL)
                .add(Items.MUSIC_DISC_MELLOHI)
                .add(Items.MUSIC_DISC_STAL)
                .add(Items.MUSIC_DISC_STRAD)
                .add(Items.MUSIC_DISC_WARD)
                .add(Items.MUSIC_DISC_WAIT)
        ;prov.tag(modTag("loot_bags/rare/med"))  //Uncommon Loot
                .add(Items.CRIMSON_STEM)
                .add(Items.WARPED_STEM)
                .add(Items.BAMBOO)
                .add(Items.BRICK)
                .add(Items.TUFF)
                .add(Items.SLIME_BALL)
                .add(Items.GOLD_INGOT)
                .add(Items.COBBLED_DEEPSLATE)
                .add(Items.GRANITE)
                .add(Items.ANDESITE)
                .add(Items.DIORITE)
                .add(Items.COPPER_BLOCK)
                .add(Items.COOKED_BEEF)
                .add(Items.COOKED_PORKCHOP)
                .add(Items.MELON)
                .add(Items.GLOW_BERRIES)
                .add(Items.COMPASS)
                .add(Items.CLOCK)
        ;prov.tag(modTag("loot_bags/rare/high")) //Very Common loot
                .add(Items.INK_SAC)
                .add(Items.REDSTONE)
                .add(Items.LAPIS_LAZULI)
                .add(Items.GOLD_NUGGET)
                .add(Items.IRON_NUGGET)
                .add(Items.FLINT)
                .add(Items.FIRE_CHARGE)
                .add(Items.WARPED_PLANKS)
                .add(Items.CRIMSON_PLANKS)
                .add(Items.GUNPOWDER)
                .add(Items.IRON_INGOT)
                .add(Items.COPPER_BLOCK)
                .add(Items.BREAD)
                .add(Items.RED_MUSHROOM)
                .add(Items.BROWN_MUSHROOM)
                .add(Items.COOKED_CHICKEN)
                .add(Items.COOKED_COD)
                .add(Items.COOKED_MUTTON)
                .add(Items.MELON_SLICE)
                .add(Items.COCOA_BEANS)
        ;
        prov.tag(modTag("loot_bags/epic/low"))   //Very Rare Loot
                .add(Items.TOTEM_OF_UNDYING)
                .add(Items.GOLDEN_APPLE)
                .add(Items.EMERALD)
                .add(Items.DIAMOND)
                .add(Items.ARROW)
                .add(Items.SADDLE)
                .add(Items.GOLDEN_HORSE_ARMOR)
                .add(Items.IRON_HORSE_ARMOR)
        ;prov.tag(modTag("loot_bags/epic/med"))  //Uncommon Loot
                .add(Items.OBSIDIAN)
                .add(Items.EXPERIENCE_BOTTLE)
                .add(Items.GOLDEN_APPLE)
                .add(Items.GLOWSTONE)
                .add(Items.TORCHFLOWER_SEEDS)
                .add(Items.ENDER_PEARL)
                .add(Items.RAW_GOLD_BLOCK)
                .add(Items.RAW_IRON_BLOCK)
                .add(Items.QUARTZ)
                .add(Items.COOKED_CHICKEN)
                .add(Items.COOKED_COD)
                .add(Items.COOKED_MUTTON)
                .add(Items.MELON)
                .add(Items.INK_SAC)
                .add(Items.GLOW_INK_SAC)
                .add(Items.GOLDEN_CARROT)
                .add(Items.COMPASS)
                .add(Items.CLOCK)
                .add(Items.BELL)
        ;prov.tag(modTag("loot_bags/epic/high")) //Very Common loot
                .add(Items.GOLDEN_CARROT)
                .add(Items.SPECTRAL_ARROW)
                .add(Items.REDSTONE)
                .add(Items.LAPIS_LAZULI)
                .add(Items.IRON_INGOT)
                .add(Items.GOLD_INGOT)
                .add(Items.IRON_BLOCK)
                .add(Items.GOLD_BLOCK)
                .add(Items.COOKED_BEEF)
                .add(Items.COOKED_PORKCHOP)
                .add(Items.PUMPKIN_PIE)
                .add(Items.COCOA_BEANS)
        ;
        prov.tag(modTag("loot_bags/legendary/low"))   //Very Rare Loot
                .add(Items.NETHERITE_SCRAP)
                .add(Items.TOTEM_OF_UNDYING)
                .add(Items.EMERALD_BLOCK)
                .add(Items.DIAMOND_BLOCK)
                .add(Items.ENCHANTED_GOLDEN_APPLE)
                .add(Items.SADDLE)
                .add(Items.DIAMOND_HORSE_ARMOR)
        ;prov.tag(modTag("loot_bags/legendary/med"))  //Uncommon Loot
                .add(Items.BELL)
                .add(Items.GOLDEN_CARROT)
                .add(Items.TOTEM_OF_UNDYING)
                .add(Items.GOLDEN_APPLE)
                .add(Items.GLOWSTONE)
                .add(Items.EMERALD)
                .add(Items.DIAMOND)
                .add(Items.EGG)
                .add(Items.NAME_TAG)
                .add(Items.NAUTILUS_SHELL)
                .add(Items.GLOW_INK_SAC)
        ;prov.tag(modTag("loot_bags/legendary/high")) //Very Common loot
                .add(Items.GOLDEN_CARROT)
                .add(Items.REDSTONE_BLOCK)
                .add(Items.LAPIS_BLOCK)
                .add(Items.OBSIDIAN)
                .add(Items.EXPERIENCE_BOTTLE)
                .add(Items.ENDER_PEARL)
        ;
        prov.tag(modTag("loot_bags/xmas/ultra"))   //Ultra Rare Loot
                .add(Items.ENCHANTED_GOLDEN_APPLE)
                .add(Items.DIAMOND_BLOCK)
                .add(Items.NETHERITE_INGOT)
        ;prov.tag(modTag("loot_bags/xmas/low"))   //Very Rare Loot
                .add(Items.BLUE_ICE)
                .add(Items.GOLDEN_APPLE)
                .add(Items.DIAMOND)
        ;prov.tag(modTag("loot_bags/xmas/med"))  //Uncommon Loot
                .add(Items.CAKE)
                .add(Items.DIAMOND)
                .add(Items.SPRUCE_SAPLING)
                .add(Items.SPRUCE_LOG)
                .add(Items.COAL)
                .add(Items.MILK_BUCKET)
                .add(Items.SNOWBALL)
                .add(Items.SNOW_BLOCK)
                .add(Items.NAME_TAG)
        ;prov.tag(modTag("loot_bags/xmas/high")) //Very Common loot
                .add(Items.COCOA_BEANS)
                .add(Items.SNOWBALL)
                .add(Items.COOKIE)
        ;
        prov.tag(modTag("loot_bags/halloween/low"))   //Very Rare Loot
                .add(Items.GOLDEN_APPLE)
        ;prov.tag(modTag("loot_bags/halloween/med"))  //Uncommon Loot
                .add(Items.PUMPKIN_SEEDS)
                .add(Items.PUMPKIN)
                .add(Items.GOLDEN_CARROT)
                .add(Items.SPIDER_EYE)
                .add(Items.PUFFERFISH)
                .add(Items.EGG)
                .add(Items.HONEY_BOTTLE)
                .add(Items.COOKIE)
        ;prov.tag(modTag("loot_bags/halloween/high")) //Very Common loot
                .add(Items.PUMPKIN_PIE)
                .add(Items.CARVED_PUMPKIN)
                .add(Items.ROTTEN_FLESH)
                .add(Items.COAL)
        ;
    }













    public static TagKey<Item> modTag(String path) {
        return Objects.requireNonNull(ForgeRegistries.ITEMS.tags()).createOptionalTagKey(new ResourceLocation(MOD_ID, path), Collections.emptySet());
    }

    @SuppressWarnings("unused")
    public static class CreateTagsProvider<T> {

        private final RegistrateTagsProvider<T> provider;
        private final Function<T, ResourceKey<T>> keyExtractor;

        public CreateTagsProvider(RegistrateTagsProvider<T> provider, Function<T, Holder.Reference<T>> refExtractor) {
            this.provider = provider;
            this.keyExtractor = refExtractor.andThen(Holder.Reference::key);
        }

        public CreateTagAppender<T> tag(TagKey<T> tag) {
            TagBuilder tagbuilder = getOrCreateRawBuilder(tag);
            return new CreateTagAppender<>(tagbuilder, keyExtractor, MOD_ID);
        }

        public TagBuilder getOrCreateRawBuilder(TagKey<T> tag) {
            return provider.addTag(tag).getInternalBuilder();
        }

    }

    @SuppressWarnings("unused")
    public static class CreateTagAppender<T> extends TagsProvider.TagAppender<T> {

        private final Function<T, ResourceKey<T>> keyExtractor;

        public CreateTagAppender(TagBuilder pBuilder, Function<T, ResourceKey<T>> pKeyExtractor, String modId) {
            super(pBuilder, modId);
            this.keyExtractor = pKeyExtractor;
        }

        public CreateTagAppender<T> add(T entry) {
            this.add(this.keyExtractor.apply(entry));
            return this;
        }

        @SafeVarargs
        public final CreateTagAppender<T> add(T... entries) {
            Stream.of(entries)
                    .map(this.keyExtractor)
                    .forEach(this::add);
            return this;
        }

    }
}
