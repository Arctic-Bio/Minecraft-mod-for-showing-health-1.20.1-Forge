package com.moddedhealth;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = ModdedHealthMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class KeyBindings {
    
    public static final KeyMapping TOGGLE_HEALTH_DISPLAY = new KeyMapping(
            "key.moddedhealth.toggle_health_display", 
            InputConstants.Type.KEYSYM, 
            GLFW.GLFW_KEY_H, 
            "key.categories.moddedhealth"
    );
    
    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event) {
        event.register(TOGGLE_HEALTH_DISPLAY);
    }
}