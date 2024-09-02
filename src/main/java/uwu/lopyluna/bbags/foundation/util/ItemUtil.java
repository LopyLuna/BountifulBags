package uwu.lopyluna.bbags.foundation.util;

import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.minecraft.world.item.Item;
import uwu.lopyluna.bbags.foundation.BountifulBagsRegistrate;
import static uwu.lopyluna.bbags.registry.ItemsBB.*;

@SuppressWarnings({"unused"})
public class ItemUtil {


    public static <T extends Item> ItemBuilder<T, BountifulBagsRegistrate> regItem(String name, NonNullFunction<Item.Properties, T> factory) {
        return REGISTRATION.item(name, factory);
    }
    public static <T extends Item> ItemBuilder<T, BountifulBagsRegistrate> regItemHoliday(String name, String rarity, String lang, NonNullFunction<Item.Properties, T> factory) {
        REGISTRATION.addRawLang("item.bbags." + name + "_easter", rarity + lang + " Easter Egg");
        REGISTRATION.addRawLang("item.bbags." + name + "_xmas", rarity + lang + " Present");
        return REGISTRATION.item(name, factory);
    }
}
