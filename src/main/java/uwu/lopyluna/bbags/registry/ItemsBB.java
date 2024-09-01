package uwu.lopyluna.bbags.registry;


import com.tterrag.registrate.util.entry.ItemEntry;
import uwu.lopyluna.bbags.BountifulBags;
import uwu.lopyluna.bbags.content.item.BagItem;
import uwu.lopyluna.bbags.foundation.BountifulBagsRegistrate;

import static uwu.lopyluna.bbags.foundation.util.ItemUtil.regItem;

@SuppressWarnings({"unused"})
public class ItemsBB {
    public static final BountifulBagsRegistrate REGISTRATION = BountifulBags.registrate();

    public static final ItemEntry<BagItem> BAG = regItem("bag", BagItem::new).register();



    public static void register() {}
}