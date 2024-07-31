package de.aeksora.minemmo.util;

import de.aeksora.minemmo.networking.MineMMONetworkingConstants;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;

import java.util.Objects;

public class LevelingScreen extends Screen {

    public LevelingScreen() {
        super(Text.literal("Leveling Screen"));
    }

    public ButtonWidget button1;
    public ButtonWidget button2;
    public ButtonWidget button3;

    @Override
    public void init() {
        // Calculate positions
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int buttonWidth = 80;
        int buttonHeight = 20;
        int buttonYSpacing = 30;

        // Button 1
        button1 = ButtonWidget.builder(Text.literal("Strength"), button -> {
                    PacketByteBuf buffer = PacketByteBufs.create();
                    ClientPlayNetworking.send(MineMMONetworkingConstants.GAD_PACKET_2S_ID, buffer);

                    assert this.client != null;
                    this.client.setScreen(new LevelingScreen());
                })
                .dimensions(centerX - 105, centerY - buttonYSpacing, buttonWidth, buttonHeight)
                .tooltip(Tooltip.of(Text.literal("Click to put a level into strength")))
                .build();

        // Button 2
        button2 = ButtonWidget.builder(Text.literal("Health"), button -> {
                    PacketByteBuf buffer = PacketByteBufs.create();
                    ClientPlayNetworking.send(MineMMONetworkingConstants.GMH_PACKET_2S_ID, buffer);

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    assert this.client != null;
                    this.client.setScreen(new LevelingScreen());
                })
                .dimensions(centerX - 105, centerY, buttonWidth, buttonHeight)
                .tooltip(Tooltip.of(Text.literal("Click to put a level into health")))
                .build();

        // Button 3
        button3 = ButtonWidget.builder(Text.literal("Speed"), button -> {
                    PacketByteBuf buffer = PacketByteBufs.create();
                    ClientPlayNetworking.send(MineMMONetworkingConstants.GMS_PACKET_2S_ID, buffer);
                })
                .dimensions(centerX - 105, centerY + buttonYSpacing, buttonWidth, buttonHeight)
                .tooltip(Tooltip.of(Text.literal("Click to put a level into speed")))
                .build();

        // Add buttons to screen
        this.addDrawableChild(button1);
        this.addDrawableChild(button2);
        this.addDrawableChild(button3);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        // Render background
        this.renderBackground(matrices);

        // Render the screen's title
        drawCenteredTextWithShadow(matrices, textRenderer, this.title, this.width / 2, 15, 0xFFFFFF);

        // Render buttons and labels
        super.render(matrices, mouseX, mouseY, delta);

        // Render level
        assert this.client != null;
        ClientPlayerEntity player = this.client.player;
        int level = ((IEntityDataSaver) player).getPersistentData().getInt("level");
        int y = ((this.height/2-25)/2) - 15/2;
        drawCenteredTextWithShadow(matrices, textRenderer, Text.literal("Level: " + level), this.width / 2, y, 0xFFFFFF);
        drawCenteredTextWithShadow(matrices, textRenderer, Text.literal("Next Level: " + LevelData.getXpNeeded(level) + "XP"), this.width / 2, y + 10, 0xFFFFFF);

        // Render text labels next to the buttons
        drawTextWithShadow(matrices, textRenderer, Text.literal("Lv. " + getLabelText("strength")), this.width / 2 - 20, this.height / 2 - 25, 0xFFFFFF);
        drawTextWithShadow(matrices, textRenderer, Text.literal("Lv. " + getLabelText("health")), this.width / 2 - 20, this.height / 2 + 5, 0xFFFFFF);
        drawTextWithShadow(matrices, textRenderer, Text.literal("Lv. " + getLabelText("speed")), this.width / 2 - 20, this.height / 2 + 35, 0xFFFFFF);
    }

    public String getLabelText(String statId) {
        assert this.client != null;
        ClientPlayerEntity player = this.client.player;

        int level;
        double stat;


        switch (statId) {
            case "strength" -> {
                double strength = Objects.requireNonNull(Objects.requireNonNull(player).getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE)).getBaseValue();
                level = (int) (strength - 1.0);
                stat = strength;
            }
            case "health" -> {
                double health = Objects.requireNonNull(Objects.requireNonNull(player).getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).getValue();
                level = (int) (health - 20.0);
                stat = health;
            }
            case "speed" -> {
                double speed = Objects.requireNonNull(Objects.requireNonNull(player).getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)).getValue();
                level = (int) Math.round((speed - 0.1) / 0.001);
                stat = Math.round(speed * 1000.0) / 1000.0;
            }
            default -> {
                level = 0;
                stat = 0.0;
            }
        }

        return level + " -> " + stat;
    }
}
