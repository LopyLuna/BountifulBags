package uwu.lopyluna.bbags.content.item;

import net.minecraft.world.item.Item;

public class BagItem extends Item {
    public BagItem(Properties pProperties) {
        super(pProperties.stacksTo(16));
    }
}
