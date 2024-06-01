package de.aeksora.minecraftmmo.util;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Text;

public class MineMMOHud implements HudRenderCallback {
    public static final Logger LOGGER = LoggerFactory.getLogger("MinecraftMMO");

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
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

            drawContext.drawText(client.textRenderer, hudText, x, y, 0x32a86f, false);
        }
    }
}
