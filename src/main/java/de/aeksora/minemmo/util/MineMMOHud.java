package de.aeksora.minemmo.util;

import de.aeksora.minemmo.MineMMO;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MineMMOHud implements HudRenderCallback {
    public static final Logger LOGGER = LoggerFactory.getLogger(MineMMO.MOD_ID);

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
