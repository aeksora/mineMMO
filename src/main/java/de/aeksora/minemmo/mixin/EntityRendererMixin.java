package de.aeksora.minemmo.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.util.math.Vec3d;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin<T extends Entity> {

    @Shadow
    @Final
    protected EntityRenderDispatcher dispatcher;

    @Inject(method = "renderLabelIfPresent", at = @At("HEAD"), cancellable = true)
    private void renderLabelIfPresent(T entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        if (entity instanceof HostileEntity) {
            // Debug print to check if the method is being called
            System.out.println("Rendering label for entity: " + entity.getName().getString());

            // Add code to draw the custom HUD
            matrices.push();
            matrices.translate(0.0D, entity.getHeight() + 0.5D, 0.0D);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-entity.getYaw(0.0F)));
            matrices.scale(-0.025F, -0.025F, 0.025F);
            MatrixStack.Entry entry = matrices.peek();
            RenderSystem.disableDepthTest();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
            String healthText = "HP: " + ((HostileEntity) entity).getHealth() + " / " + ((HostileEntity) entity).getMaxHealth();
            float x = -textRenderer.getWidth(healthText) / 2;
            Matrix4f matrix4f = entry.getPositionMatrix();
            textRenderer.draw(
                    healthText,
                    x,
                    0,
                    553648127,
                    false,
                    matrix4f,
                    vertexConsumers,
                    TextRenderer.TextLayerType.NORMAL,
                    0,
                    light,
                    false
            );
            RenderSystem.enableDepthTest();
            RenderSystem.disableBlend();
            matrices.pop();
            ci.cancel(); // Cancel the original method to avoid drawing the default label
        }
    }

    @Unique
    protected EntityRenderDispatcher getDispatcher() {
        return this.dispatcher;
    }
}
