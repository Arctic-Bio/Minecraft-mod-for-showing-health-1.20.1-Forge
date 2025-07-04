package com.moddedhealth;

import com.moddedhealth.config.HealthDisplayConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(ModdedHealthMod.MODID)
public class ModdedHealthMod {
    public static final String MODID = "moddedhealth";
    private static final Logger LOGGER = LoggerFactory.getLogger(ModdedHealthMod.class);

    public ModdedHealthMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        
        // Register the client setup method
        modEventBus.addListener(this::clientSetup);
        
        // Register configuration
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, HealthDisplayConfig.SPEC);
        
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        
        LOGGER.info("Modded Health mod initialized!");
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        // Register our client-side event handler
        MinecraftForge.EVENT_BUS.register(new HealthDisplayRenderer());
        LOGGER.info("Client setup complete!");
    }
}