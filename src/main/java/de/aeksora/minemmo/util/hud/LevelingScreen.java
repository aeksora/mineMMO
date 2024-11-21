package de.aeksora.minemmo.util.hud;

import de.aeksora.minemmo.MineMMO;
import de.aeksora.minemmo.networking.MineMMONetworkingConstants;
import de.aeksora.minemmo.util.IEntityDataSaver;
import de.aeksora.minemmo.util.LevelData;
import de.aeksora.minemmo.util.Stat;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LevelingScreen extends Screen {

    private static final Logger LOGGER = LoggerFactory.getLogger(MineMMO.MOD_ID);

    public LevelingScreen() {
        super(Text.literal("Leveling Screen"));
    }

    public ButtonWidget button1;
    public ButtonWidget button2;
    public ButtonWidget button3;
    public ButtonWidget button4;
    public ButtonWidget button5;

    private NumericTextFieldWidget inputField1;
    private NumericTextFieldWidget inputField2;
    private NumericTextFieldWidget inputField3;
    private NumericTextFieldWidget inputField4;
    private NumericTextFieldWidget inputField5;

    @Override
    public void init() {
        // Calculate positions
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int buttonWidth = 80;
        int buttonHeight = 20;
        int buttonYSpacing = 30;
        int inputFieldWidth = 50;

        // Initialize input fields
        inputField1 = new NumericTextFieldWidget(centerX - 160, centerY - buttonYSpacing, inputFieldWidth, buttonHeight, Text.literal(""));
        inputField2 = new NumericTextFieldWidget(centerX - 160, centerY, inputFieldWidth, buttonHeight, Text.literal(""));
        inputField3 = new NumericTextFieldWidget(centerX - 160, centerY + buttonYSpacing, inputFieldWidth, buttonHeight, Text.literal(""));
        inputField4 = new NumericTextFieldWidget(centerX - 160, centerY + 2 * buttonYSpacing, inputFieldWidth, buttonHeight, Text.literal(""));
        inputField5 = new NumericTextFieldWidget(centerX - 160, centerY + 3 * buttonYSpacing, inputFieldWidth, buttonHeight, Text.literal(""));

        // Add input fields to screen
        this.addSelectableChild(inputField1);
        this.addSelectableChild(inputField2);
        this.addSelectableChild(inputField3);
        this.addSelectableChild(inputField4);
        this.addSelectableChild(inputField5);

        // Set input field callbacks
        inputField1.setOnEnterPressed(() -> handleInput(inputField1, Stat.STRENGTH));
        inputField2.setOnEnterPressed(() -> handleInput(inputField2, Stat.HEALTH));
        inputField3.setOnEnterPressed(() -> handleInput(inputField3, Stat.SPEED));
        inputField4.setOnEnterPressed(() -> handleInput(inputField4, Stat.REGEN));
        inputField5.setOnEnterPressed(() -> handleInput(inputField5, Stat.MINESPEED));

        // Button 1
        button1 = ButtonWidget.builder(Text.literal("Strength"), button -> {
                    PacketByteBuf buffer = PacketByteBufs.create();
                    ClientPlayNetworking.send(MineMMONetworkingConstants.STRENGTH_PACKET_ID, buffer);

                    assert this.client != null;
                    this.client.setScreen(new LevelingScreen());
                })
                .dimensions(centerX - 105, centerY - buttonYSpacing, buttonWidth, buttonHeight)
                .tooltip(Tooltip.of(Text.literal("Click to put a level into strength")))
                .build();

        // Button 2
        button2 = ButtonWidget.builder(Text.literal("Health"), button -> {
                    PacketByteBuf buffer = PacketByteBufs.create();
                    ClientPlayNetworking.send(MineMMONetworkingConstants.HEALTH_PACKET_ID, buffer);

                    assert this.client != null;
                    this.client.setScreen(new LevelingScreen());
                })
                .dimensions(centerX - 105, centerY, buttonWidth, buttonHeight)
                .tooltip(Tooltip.of(Text.literal("Click to put a level into health")))
                .build();

        // Button 3
        button3 = ButtonWidget.builder(Text.literal("Speed"), button -> {
                    PacketByteBuf buffer = PacketByteBufs.create();
                    ClientPlayNetworking.send(MineMMONetworkingConstants.SPEED_PACKET_ID, buffer);
                })
                .dimensions(centerX - 105, centerY + buttonYSpacing, buttonWidth, buttonHeight)
                .tooltip(Tooltip.of(Text.literal("Click to put a level into speed")))
                .build();

        // Button 4
        button4 = ButtonWidget.builder(Text.literal("Regeneration"), button -> {
                    PacketByteBuf buffer = PacketByteBufs.create();
                    ClientPlayNetworking.send(MineMMONetworkingConstants.REGEN_PACKET_ID, buffer);
                })
                .dimensions(centerX - 105, centerY + 2 * buttonYSpacing, buttonWidth, buttonHeight)
                .tooltip(Tooltip.of(Text.literal("Click to put a level into regeneration")))
                .build();

        // Button 5
        button5 = ButtonWidget.builder(Text.literal("Mining Speed"), button -> {
                    PacketByteBuf buffer = PacketByteBufs.create();
                    ClientPlayNetworking.send(MineMMONetworkingConstants.MININGSPEED_PACKET_ID, buffer);
                })
                .dimensions(centerX - 105, centerY + 3 * buttonYSpacing, buttonWidth, buttonHeight)
                .tooltip(Tooltip.of(Text.literal("Click to put a level into mining speed")))
                .build();

        // Add buttons to screen
        this.addDrawableChild(button1);
        this.addDrawableChild(button2);
        this.addDrawableChild(button3);
        this.addDrawableChild(button4);
        this.addDrawableChild(button5);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        // Render background
        this.renderBackground(matrices);

        // Render the screen's title
        drawCenteredTextWithShadow(matrices, textRenderer, this.title, this.width / 2, 15, 0xFFFFFF);

        // Render input fields
        inputField1.render(matrices, mouseX, mouseY, delta);
        inputField2.render(matrices, mouseX, mouseY, delta);
        inputField3.render(matrices, mouseX, mouseY, delta);
        inputField4.render(matrices, mouseX, mouseY, delta);
        inputField5.render(matrices, mouseX, mouseY, delta);

        // Render buttons and labels
        super.render(matrices, mouseX, mouseY, delta);

        // Render level
        assert this.client != null;
        ClientPlayerEntity player = this.client.player;
        assert player != null;
        int level = ((IEntityDataSaver) player).getPersistentData().getInt("level");
        int y = ((this.height/2-25)/2) - 15/2;
        drawCenteredTextWithShadow(matrices, textRenderer, Text.literal("Level: " + level), this.width / 2, y, 0xFFFFFF);
        drawCenteredTextWithShadow(matrices, textRenderer, Text.literal("Next Level: " + LevelData.getXpNeeded(level) + "XP"), this.width / 2, y + 10, 0xFFFFFF);

        Map<String, Double> strengthMap = getLevelAndStat(Stat.STRENGTH);
        Map<String, Double> healthMap = getLevelAndStat(Stat.HEALTH);
        Map<String, Double> speedMap = getLevelAndStat(Stat.SPEED);
        Map<String, Double> regenMap = getLevelAndStat(Stat.REGEN);
        Map<String, Double> miningMap = getLevelAndStat(Stat.MINESPEED);

        // Render text labels next to the buttons
        drawTextWithShadow(matrices, textRenderer, Text.literal("Lv. " + strengthMap.get("level").intValue() + "/" + strengthMap.get("maxLevel").intValue() + " -> " + strengthMap.get("stat")), this.width / 2 - 20, this.height / 2 - 25, 0xFFFFFF);
        drawTextWithShadow(matrices, textRenderer, Text.literal("Lv. " + healthMap.get("level").intValue() + "/" + healthMap.get("maxLevel").intValue() + " -> " + healthMap.get("stat").intValue()), this.width / 2 - 20, this.height / 2 + 5, 0xFFFFFF);
        drawTextWithShadow(matrices, textRenderer, Text.literal("Lv. " + speedMap.get("level").intValue() + "/" + speedMap.get("maxLevel").intValue() + " -> " + speedMap.get("stat")), this.width / 2 - 20, this.height / 2 + 35, 0xFFFFFF);
        drawTextWithShadow(matrices, textRenderer, Text.literal("Lv. " + regenMap.get("level").intValue() + "/" + regenMap.get("maxLevel").intValue() + " -> " + regenMap.get("stat")), this.width / 2 - 20, this.height / 2 + 65, 0xFFFFFF);
        drawTextWithShadow(matrices, textRenderer, Text.literal("Lv. " + miningMap.get("level").intValue() + "/" + miningMap.get("maxLevel").intValue() + " -> " + miningMap.get("stat")), this.width / 2 - 20, this.height / 2 + 95, 0xFFFFFF);
    }

    public Map<String, Double> getLevelAndStat(Stat statId) {
        assert this.client != null;
        ClientPlayerEntity player = this.client.player;

        double level;
        double maxLevel = 0;
        double stat;


        switch (statId) {
            case STRENGTH -> {
                double strength = Objects.requireNonNull(Objects.requireNonNull(player).getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE)).getBaseValue();
                float maxStrength = ((IEntityDataSaver) Objects.requireNonNull(player)).getPersistentData().getFloat("maxStrength");
                level = (int) (strength - 1.0);
                maxLevel = (int) (maxStrength - 1.0);
                stat = strength;
            }
            case HEALTH -> {
                double health = Objects.requireNonNull(Objects.requireNonNull(player).getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).getValue();
                float maxHealth = ((IEntityDataSaver) Objects.requireNonNull(player)).getPersistentData().getFloat("maxHealth");
                level = (int) (health - 20.0);
                maxLevel = (int) (maxHealth - 20.0);
                stat = health;
            }
            case SPEED -> {
                double speed = Objects.requireNonNull(Objects.requireNonNull(player).getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)).getValue();
                float maxSpeed = ((IEntityDataSaver) Objects.requireNonNull(player)).getPersistentData().getFloat("maxSpeed");
                level = (int) Math.round((speed - 0.1) / MineMMO.SPEED_PER_LEVEL);
                maxLevel = (int) Math.round((maxSpeed - 0.1) / MineMMO.SPEED_PER_LEVEL);
                stat = Math.round(speed * 10000.0) / 10000.0;
            }
            case REGEN -> {
                float regen = ((IEntityDataSaver) Objects.requireNonNull(player)).getPersistentData().getFloat("regen");
                float maxRegen = ((IEntityDataSaver) Objects.requireNonNull(player)).getPersistentData().getFloat("maxRegen");
                level = (int) Math.round((regen - 0.25) / MineMMO.REGEN_PER_LEVEL);
                maxLevel = (int) Math.round((maxRegen - 0.25) / MineMMO.REGEN_PER_LEVEL);
                stat = Math.round(regen * 100.0) / 100.0;
            }
            case MINESPEED -> {
                float miningSpeed = ((IEntityDataSaver) Objects.requireNonNull(player)).getPersistentData().getFloat("miningSpeed");
                float maxMiningSpeed = ((IEntityDataSaver) Objects.requireNonNull(player)).getPersistentData().getFloat("maxMiningSpeed");
                level = Math.round((miningSpeed - 1.0f) / MineMMO.MINESPEED_PER_LEVEL);
                maxLevel = Math.round((maxMiningSpeed - 1.0f) / MineMMO.MINESPEED_PER_LEVEL);
                stat = Math.round(miningSpeed * 100.0) / 100.0;
            }
            default -> {
                level = 0;
                stat = 0.0;
            }
        }

        Map<String, Double> map = new HashMap<>();
        map.put("level", level);
        map.put("maxLevel", maxLevel);
        map.put("stat", stat);
        return map;
    }

    private void handleInput(NumericTextFieldWidget inputField, Stat stat) {
        String input = inputField.getText();
        if (input.isEmpty()) return; // Ignore empty input
        int value = Integer.parseInt(input);

        LOGGER.info("Entered value: " + value);

        int maxStatLevel = getLevelAndStat(stat).get("maxLevel").intValue();

        if (value > maxStatLevel) {
            value = maxStatLevel;
        } else if (value < 0) {
            value = 0;
        }

        PacketByteBuf buffer = PacketByteBufs.create();

        double statVal = 0.0;

        switch (stat) {
            case STRENGTH -> {
                statVal = MineMMO.STRENGTH_PER_LEVEL * value + 1;
                Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(this.client).player).getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE)).setBaseValue(statVal);
                buffer.writeDouble(statVal);
                ClientPlayNetworking.send(MineMMONetworkingConstants.STRENGTH_LIMIT_ID, buffer);
            }
            case HEALTH -> {
                statVal = MineMMO.HEALTH_PER_LEVEL * value + 20.0;
                Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(this.client).player).getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(statVal);
                buffer.writeDouble(statVal);
                ClientPlayNetworking.send(MineMMONetworkingConstants.HEALTH_LIMIT_ID, buffer);
            }
            case SPEED -> {
                statVal = MineMMO.SPEED_PER_LEVEL * value + 0.1;
                Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(this.client).player).getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)).setBaseValue(statVal);
                buffer.writeDouble(statVal);
                ClientPlayNetworking.send(MineMMONetworkingConstants.SPEED_LIMIT_ID, buffer);
            }
            case REGEN -> {
                statVal = MineMMO.REGEN_PER_LEVEL * value + 0.25f;
                ((IEntityDataSaver) Objects.requireNonNull(Objects.requireNonNull(this.client).player)).getPersistentData().putFloat("regen", (float) statVal);
                buffer.writeFloat((float) statVal);
                ClientPlayNetworking.send(MineMMONetworkingConstants.REGEN_LIMIT_ID, buffer);
            }
            case MINESPEED -> {
                statVal = MineMMO.MINESPEED_PER_LEVEL * value + 1.0f;
                ((IEntityDataSaver) Objects.requireNonNull(Objects.requireNonNull(this.client).player)).getPersistentData().putFloat("miningSpeed", (float) statVal);
                buffer.writeFloat((float) statVal);
                ClientPlayNetworking.send(MineMMONetworkingConstants.MININGSPEED_LIMIT_ID, buffer);
            }
        }

        LOGGER.info("Stat level set to: " + value);
        LOGGER.info("Stat value set to: " + statVal);

        assert this.client != null;
        this.client.setScreen(new LevelingScreen());
    }
}
