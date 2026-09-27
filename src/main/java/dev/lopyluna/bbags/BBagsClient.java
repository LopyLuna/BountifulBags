package dev.lopyluna.bbags;

import dev.lopyluna.bbags.content.item.LootBagRarity;
import dev.lopyluna.bbags.register.AllItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import static dev.lopyluna.bbags.BBags.MOD_ID;
import static dev.lopyluna.bbags.content.item.LootBagItem.isXmasMonth;

@Mod(value = MOD_ID, dist = Dist.CLIENT)
public class BBagsClient {
    public BBagsClient(IEventBus modEventBus, ModContainer container) {
        modEventBus.addListener(BBagsClient::clientInit);
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    public static void clientInit(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {

            for (var rarity : LootBagRarity.values()) {
                var item = AllItems.getLootBag(rarity);
                ItemProperties.register(item, BBags.loc("xmas"),
                        (pStack, pLevel, pEntity, pSeed) -> isXmasMonth ? 1 : 0);
                //ItemProperties.register(item.get(), new ResourceLocation(MOD_ID, "easter_textures"), (pStack, pLevel, pEntity, pSeed) ->
                //        isEasterMonth ? 1 : 0);
            }
        });
    }
}
