package dev.lopyluna.bbags;

import com.mojang.logging.LogUtils;
import dev.lopyluna.bbags.content.LootBagDrops;
import dev.lopyluna.bbags.register.AllItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.slf4j.Logger;

@Mod(BBags.MOD_ID)
public class BBags {
    public static final String NAME = "Bountiful Bags";
    public static final String MOD_ID = "bbags";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, MOD_ID);

    public BBags(IEventBus bus, ModContainer mod) {
        AllItems.register();
        LootBagDrops.register();
        NeoForge.EVENT_BUS.register(LootBagDrops.class);
        ITEMS.register(bus);
        CREATIVE_MODE_TABS.register(bus);
        ATTACHMENT_TYPES.register(bus);

        mod.registerConfig(ModConfig.Type.SERVER, BBagsConfig.SPEC);
        bus.addListener(BBagsDatagen::gatherData);
    }

    public static ResourceLocation loc(String loc) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, loc);
    }
    public static ResourceLocation loc(String modID, String loc) {
        return ResourceLocation.fromNamespaceAndPath(modID, loc);
    }
    public static ResourceLocation locMC(String loc) {
        return ResourceLocation.withDefaultNamespace(loc);
    }
    public static ResourceLocation empty() {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, "empty");
    }
}
