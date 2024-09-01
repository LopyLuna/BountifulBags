package uwu.lopyluna.bbags.foundation.util;

import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import uwu.lopyluna.bbags.foundation.BountifulBagsRegistrate;
import static uwu.lopyluna.bbags.registry.ItemsBB.*;

@SuppressWarnings({"unused"})
public class ItemUtil {


    public static <T extends Item> ItemBuilder<T, BountifulBagsRegistrate> regItem(String name, NonNullFunction<Item.Properties, T> factory) {
        return REGISTRATION.item(name, factory);
    }
    public static <T extends Item> ItemBuilder<T, BountifulBagsRegistrate> regItemLang(String name, String lang, NonNullFunction<Item.Properties, T> factory) {
        return REGISTRATION.item(name, factory).lang(lang);
    }
    public static ItemBuilder<Item, BountifulBagsRegistrate> simpleItem(String name) {
        return REGISTRATION.item(name, Item::new);
    }
    public static ItemBuilder<Item, BountifulBagsRegistrate> simpleItem(String name, String lang) {
        return REGISTRATION.item(name, Item::new).lang(lang);
    }
    @SafeVarargs
    public static ItemBuilder<Item, BountifulBagsRegistrate> simpleItemTag(String name, TagKey<Item>... tags) {
        return REGISTRATION.item(name, Item::new).tag(tags);
    }
    @SafeVarargs
    public static ItemBuilder<Item, BountifulBagsRegistrate> simpleItemTagLang(String name, String lang, TagKey<Item>... tags) {
        return REGISTRATION.item(name, Item::new).tag(tags).lang(lang);
    }
}
