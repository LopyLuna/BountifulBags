package uwu.lopyluna.bbags;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import uwu.lopyluna.bbags.foundation.BountifulBagsRegistrate;
import uwu.lopyluna.bbags.registry.BlocksBB;
import uwu.lopyluna.bbags.registry.CreativeTabsBB;
import uwu.lopyluna.bbags.registry.ItemsBB;

@SuppressWarnings({"unused"})
@Mod(BountifulBags.MOD_ID)
public class BountifulBags {
    public static final String MOD_ID = "bbags";
    public static final String NAME = "BountifulBags";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final BountifulBagsRegistrate REGISTRATION = BountifulBagsRegistrate.create(MOD_ID);
    static IEventBus bus;

    public BountifulBags() {
        bus = FMLJavaModLoadingContext.get().getModEventBus();

        ItemsBB.register();
        BlocksBB.register();

        CreativeTabsBB.register(bus);

        MinecraftForge.EVENT_BUS.register(this);
        finalizeRegistrate();
    }

    public static void finalizeRegistrate() {
        registrate().registerEventListeners(bus);
    }

    public static ResourceLocation asResource(String name) {
        return new ResourceLocation(MOD_ID, name);
    }

    public static BountifulBagsRegistrate registrate() {
        return REGISTRATION;
    }
}
