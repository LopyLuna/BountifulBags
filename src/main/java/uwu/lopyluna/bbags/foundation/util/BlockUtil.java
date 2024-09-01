package uwu.lopyluna.bbags.foundation.util;

import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import uwu.lopyluna.bbags.foundation.BountifulBagsRegistrate;
import static uwu.lopyluna.bbags.registry.BlocksBB.*;

@SuppressWarnings({"unused"})
public class BlockUtil {

    public static <T extends Block> BlockBuilder<T, BountifulBagsRegistrate> regBlock(String name, NonNullFunction<Block.Properties, T> factory) {
        return REGISTRATION.block(name, factory).simpleItem();
    }
    public static <T extends Block> BlockBuilder<T, BountifulBagsRegistrate> regBlockLang(String name, String lang, NonNullFunction<Block.Properties, T> factory) {
        return REGISTRATION.block(name, factory).simpleItem().lang(lang);
    }
    public static BlockBuilder<Block, BountifulBagsRegistrate> simpleBlock(String name) {
        return REGISTRATION.block(name, Block::new).simpleItem();
    }
    public static BlockBuilder<Block, BountifulBagsRegistrate> simpleBlock(String name, String lang) {
        return REGISTRATION.block(name, Block::new).simpleItem().lang(lang);
    }


    @SafeVarargs
    public static <T extends Block> BlockBuilder<T, BountifulBagsRegistrate> regBlockTag(String name, NonNullFunction<Block.Properties, T> factory, TagKey<Block>... tags) {
        return REGISTRATION.block(name, factory).tag(tags).simpleItem();
    }
    @SafeVarargs
    public static <T extends Block> BlockBuilder<T, BountifulBagsRegistrate> regBlockTag(String name, String lang, NonNullFunction<Block.Properties, T> factory, TagKey<Block>... tags) {
        return REGISTRATION.block(name, factory).tag(tags).simpleItem().lang(lang);
    }
    @SafeVarargs
    public static BlockBuilder<Block, BountifulBagsRegistrate> simpleBlockTag(String name, TagKey<Block>... tags) {
        return REGISTRATION.block(name, Block::new).tag(tags).simpleItem();
    }
    @SafeVarargs
    public static BlockBuilder<Block, BountifulBagsRegistrate> simpleBlockTag(String name, String lang, TagKey<Block>... tags) {
        return REGISTRATION.block(name, Block::new).tag(tags).simpleItem().lang(lang);
    }
}
