package com.moddedhealth;

import com.moddedhealth.config.HealthDisplayConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ModdedHealthMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
@OnlyIn(Dist.CLIENT)
public class ClientEventHandler {
    
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (KeyBindings.TOGGLE_HEALTH_DISPLAY.consumeClick()) {
            // Toggle the health display
            boolean currentState = HealthDisplayConfig.ENABLED.get();
            HealthDisplayConfig.ENABLED.set(!currentState);
            
            // Show a message to the player
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.player != null) {
                String message = !currentState ? "Health Display Enabled" : "Health Display Disabled";
                minecraft.player.displayClientMessage(Component.literal(message), true);
            }
        }
    }
}