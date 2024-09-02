package uwu.lopyluna.bbags;

import com.mojang.logging.LogUtils;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import uwu.lopyluna.bbags.content.item.BagItem;
import uwu.lopyluna.bbags.event.CommonEventBags;
import uwu.lopyluna.bbags.foundation.BountifulBagsRegistrate;
import uwu.lopyluna.bbags.registry.BlocksBB;
import uwu.lopyluna.bbags.registry.CreativeTabsBB;
import uwu.lopyluna.bbags.registry.ItemsBB;

import java.time.LocalDate;
import java.time.Month;

import static uwu.lopyluna.bbags.registry.ItemsBB.holidayTextureItems;

@SuppressWarnings({"unused"})
@Mod(BountifulBags.MOD_ID)
public class BountifulBags {
    public static final String MOD_ID = "bbags";
    public static final String NAME = "BountifulBags";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final BountifulBagsRegistrate REGISTRATION = BountifulBagsRegistrate.create(MOD_ID);
    static IEventBus bus;

    public static boolean isNewYearMonth = LocalDate.now().getMonth() == Month.JANUARY;
    public static boolean isValentineMonth = LocalDate.now().getMonth() == Month.FEBRUARY;
    public static boolean isSaintPatrickMonth = LocalDate.now().getMonth() == Month.MARCH;
    public static boolean isEasterMonth = LocalDate.now().getMonth() == Month.APRIL;
    //MAY
    public static boolean isPrideMonth = LocalDate.now().getMonth() == Month.JUNE;
    //JULY
    //AUGUST
    //SEPTEMBER
    public static boolean isHalloweenMonth = LocalDate.now().getMonth() == Month.OCTOBER;
    public static boolean isThanksgivingMonth = LocalDate.now().getMonth() == Month.NOVEMBER;
    public static boolean isXmasMonth = LocalDate.now().getMonth() == Month.DECEMBER;

    public BountifulBags() {
        bus = FMLJavaModLoadingContext.get().getModEventBus();

        ItemsBB.register();
        BlocksBB.register();
        CreativeTabsBB.register(bus);

        MinecraftForge.EVENT_BUS.register(new CommonEventBags());
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

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                for (ItemEntry<BagItem> item : holidayTextureItems) {
                    ItemProperties.register(item.get(), new ResourceLocation(MOD_ID, "xmas_textures"), (pStack, pLevel, pEntity, pSeed) ->
                            isXmasMonth ? 1 : 0);
                    ItemProperties.register(item.get(), new ResourceLocation(MOD_ID, "easter_textures"), (pStack, pLevel, pEntity, pSeed) ->
                            isEasterMonth ? 1 : 0);
                }
            });
        }
    }
}
