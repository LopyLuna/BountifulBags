package uwu.lopyluna.bbags.foundation;

import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.Builder;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@SuppressWarnings({"unused"})
public class BountifulBagsRegistrate extends AbstractRegistrate<BountifulBagsRegistrate> {

    private static final Map<RegistryEntry<?>, RegistryObject<CreativeModeTab>> TAB_LOOKUP = new IdentityHashMap<>();

    @Nullable
    protected RegistryObject<CreativeModeTab> currentTab;


    public static BountifulBagsRegistrate create(String modid) {
        return new BountifulBagsRegistrate(modid);
    }

    protected BountifulBagsRegistrate(String modid) {
        super(modid);
    }

    public static boolean isInCreativeTab(RegistryEntry<?> entry, RegistryObject<CreativeModeTab> tab) {
        return TAB_LOOKUP.get(entry) == tab;
    }
    @Nullable
    public BountifulBagsRegistrate setCreativeTab(RegistryObject<CreativeModeTab> tab) {
        currentTab = tab;
        return self();
    }

    public RegistryObject<CreativeModeTab> getCreativeTab() {
        assert currentTab != null;
        return currentTab;
    }

    @Override
    public BountifulBagsRegistrate registerEventListeners(IEventBus bus) {
        return super.registerEventListeners(bus);
    }

    @Override
    protected <R, T extends R> RegistryEntry<T> accept(String name, ResourceKey<? extends Registry<R>> type,
                                                       Builder<R, T, ?, ?> builder, NonNullSupplier<? extends T> creator,
                                                       NonNullFunction<RegistryObject<T>, ? extends RegistryEntry<T>> entryFactory) {
        RegistryEntry<T> entry = super.accept(name, type, builder, creator, entryFactory);
        if (currentTab != null) {
            TAB_LOOKUP.put(entry, currentTab);
        }
        return entry;
    }

    protected static void onClient(Supplier<Runnable> toRun) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, toRun);
    }

}
