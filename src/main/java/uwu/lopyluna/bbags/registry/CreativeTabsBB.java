package uwu.lopyluna.bbags.registry;

import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static uwu.lopyluna.bbags.BountifulBags.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
@SuppressWarnings({"SameParameterValue", "unused"})
public class CreativeTabsBB {
    private static final DeferredRegister<CreativeModeTab> REGISTER =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static ItemEntry<? extends Item> icon = isXmasMonth ? ItemsBB.PRESENT_BOX : isHalloweenMonth ? ItemsBB.HALLOWEEN_LOOTBAG : isEasterMonth ? ItemsBB.COMMON_LOOTBAG : ItemsBB.UNCOMMON_LOOTBAG;

    public static final RegistryObject<CreativeModeTab> BASE_TAB = register("base");

    private static RegistryObject<CreativeModeTab> register(String name) {
        return REGISTER.register(name,
                () -> CreativeModeTab.builder()
                        .title(REGISTRATION.addRawLang("itemGroup."+ MOD_ID +"."+ name, NAME))
                        .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                        .icon(() -> icon.isPresent() ? new ItemStack(icon.get()) : new ItemStack(Items.BUNDLE))
                        .displayItems((p, o) -> {for (RegistryEntry<Item> item : REGISTRATION.getAll(Registries.ITEM)) { o.accept(new ItemStack(item.get())); }})
                        .build());
    }

    public static void register(IEventBus modEventBus) {
        REGISTER.register(modEventBus);
    }


}
