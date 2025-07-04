package com.moddedhealth;

import com.moddedhealth.config.HealthDisplayConfig;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class HealthDisplayRenderer {
    
    private static final int TEXT_COLOR = 0xFFFFFF; // White text
    private static final int BACKGROUND_COLOR = 0x80000000; // Semi-transparent black
    
    @SubscribeEvent
    public void onRenderLevelStage(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_ENTITIES) {
            return;
        }
        
        // Check if health display is enabled
        if (!HealthDisplayConfig.ENABLED.get()) {
            return;
        }
        
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null || minecraft.player == null) {
            return;
        }
        
        PoseStack poseStack = event.getPoseStack();
        MultiBufferSource.BufferSource bufferSource = minecraft.renderBuffers().bufferSource();
        
        // Get camera position
        Vec3 cameraPos = minecraft.gameRenderer.getMainCamera().getPosition();
        
        // Iterate through all entities in the world
        for (Entity entity : minecraft.level.entitiesForRendering()) {
            if (entity instanceof LivingEntity livingEntity && shouldShowHealthFor(livingEntity, minecraft.player)) {
                renderHealthAboveEntity(livingEntity, poseStack, bufferSource, cameraPos, minecraft);
            }
        }
    }
    
    private boolean shouldShowHealthFor(LivingEntity entity, Player player) {
        // Don't show health for the player themselves
        if (entity == player) {
            return false;
        }
        
        // Check configuration settings
        if (entity instanceof Player) {
            return HealthDisplayConfig.SHOW_PLAYERS.get();
        } else if (entity instanceof Monster) {
            return HealthDisplayConfig.SHOW_HOSTILE_MOBS.get();
        } else {
            return HealthDisplayConfig.SHOW_PASSIVE_MOBS.get();
        }
    }
    
    private void renderHealthAboveEntity(LivingEntity entity, PoseStack poseStack, 
                                       MultiBufferSource bufferSource, Vec3 cameraPos, Minecraft minecraft) {
        
        // Skip if entity is too far away
        double distance = entity.distanceToSqr(cameraPos.x, cameraPos.y, cameraPos.z);
        double maxDistance = HealthDisplayConfig.MAX_DISTANCE.get();
        if (distance > maxDistance * maxDistance) {
            return;
        }
        
        // Get entity position
        Vec3 entityPos = entity.position();
        double entityX = entityPos.x - cameraPos.x;
        double entityY = entityPos.y - cameraPos.y + entity.getBbHeight() + 0.5; // Above entity
        double entityZ = entityPos.z - cameraPos.z;
        
        // Create health text
        float health = entity.getHealth();
        float maxHealth = entity.getMaxHealth();
        String healthText;
        
        if (HealthDisplayConfig.SHOW_PERCENTAGE.get()) {
            float percentage = (health / maxHealth) * 100;
            healthText = String.format("%.0f%%", percentage);
        } else {
            healthText = String.format("%.1f/%.1f", health, maxHealth);
        }
        
        // Choose color based on health percentage
        int textColor = getHealthColor(health / maxHealth);
        
        poseStack.pushPose();
        poseStack.translate(entityX, entityY, entityZ);
        
        // Make text face the camera
        EntityRenderDispatcher dispatcher = minecraft.getEntityRenderDispatcher();
        poseStack.mulPose(dispatcher.cameraOrientation());
        
        // Scale text based on distance
        float scale = (float) Math.max(0.02f, Math.min(0.05f, 0.1f / Math.sqrt(distance)));
        poseStack.scale(-scale, -scale, scale);
        
        Font font = minecraft.font;
        float textWidth = font.width(healthText);
        float textHeight = font.lineHeight;
        
        // Draw background
        Matrix4f matrix = poseStack.last().pose();
        drawBackground(matrix, bufferSource, textWidth, textHeight);
        
        // Draw text
        font.drawInBatch(healthText, -textWidth / 2, -textHeight / 2, textColor, false, 
                        matrix, bufferSource, Font.DisplayMode.NORMAL, 0, 15728880);
        
        poseStack.popPose();
    }
    
    private void drawBackground(Matrix4f matrix, MultiBufferSource bufferSource, float textWidth, float textHeight) {
        // Add some padding
        float padding = 2;
        float x1 = -textWidth / 2 - padding;
        float y1 = -textHeight / 2 - padding;
        float x2 = textWidth / 2 + padding;
        float y2 = textHeight / 2 + padding;
        
        // Draw background rectangle
        // Note: This is a simplified background. For a more sophisticated background,
        // you would need to use vertex buffers and proper rendering
    }
    
    private int getHealthColor(float healthPercent) {
        // Red to yellow to green gradient based on health percentage
        if (healthPercent > 0.66f) {
            // Green to yellow
            float factor = (healthPercent - 0.66f) / 0.34f;
            int red = (int) (255 * (1 - factor));
            return 0xFF000000 | (red << 16) | (255 << 8) | 0;
        } else if (healthPercent > 0.33f) {
            // Yellow to orange
            float factor = (healthPercent - 0.33f) / 0.33f;
            int green = (int) (255 * factor);
            return 0xFF000000 | (255 << 16) | (green << 8) | 0;
        } else {
            // Orange to red
            float factor = healthPercent / 0.33f;
            int green = (int) (128 * factor);
            return 0xFF000000 | (255 << 16) | (green << 8) | 0;
        }
    }
}