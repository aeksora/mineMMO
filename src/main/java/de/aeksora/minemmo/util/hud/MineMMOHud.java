package de.aeksora.minemmo.util.hud;

import de.aeksora.minemmo.util.IEntityDataSaver;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

public class MineMMOHud implements HudRenderCallback {

    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player != null && client.world != null) {
            int xp = ((IEntityDataSaver) client.player).getPersistentData().getInt("xp");

            String hudText = "XP: " + xp;
            int screenWidth = client.getWindow().getScaledWidth();
            int screenHeight = client.getWindow().getScaledHeight();
            int textWidth = client.textRenderer.getWidth(hudText);
            int textHeight = client.textRenderer.fontHeight + 2;

            // Position with 5px padding
            int x = screenWidth - textWidth - 5;
            int y = screenHeight - textHeight - 5;

            client.textRenderer.draw(matrixStack, hudText, x, y, 0x32a86f);
        }
    }
}
