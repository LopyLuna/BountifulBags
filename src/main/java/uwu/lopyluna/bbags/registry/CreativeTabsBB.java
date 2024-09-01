package uwu.lopyluna.bbags.registry;

import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import uwu.lopyluna.bbags.BountifulBags;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static uwu.lopyluna.bbags.BountifulBags.MOD_ID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
@SuppressWarnings({"SameParameterValue", "unused"})
public class CreativeTabsBB {
    private static final DeferredRegister<CreativeModeTab> REGISTER =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static final Supplier<CreativeModeTab> BASE_TAB = registerTabSearchBar("base", Items.CHERRY_LOG, output -> {
        for (RegistryEntry<Item> item : BountifulBags.registrate().getAll(Registries.ITEM)) {
            output.accept(new ItemStack(item.get()));
        }
    });

    private static Supplier<CreativeModeTab> registerTabSearchBar(String name, ItemLike icon, Consumer<CreativeModeTab.Output> displayItems) {
        return REGISTER.register(name, () -> { final CreativeModeTab.Builder builder = CreativeModeTab.builder(); builder
                .title(Component.translatable("itemGroup." + MOD_ID + "." + name + "_tab"))
                .icon(() -> new ItemStack(icon))
                .withSearchBar()
                .displayItems((pParameters, pOutput) -> displayItems.accept(pOutput));
            return builder.build();
        });
    }

    public static void register(IEventBus modEventBus) {
        REGISTER.register(modEventBus);
    }


}
