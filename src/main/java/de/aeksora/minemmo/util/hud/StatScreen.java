package de.aeksora.minemmo.util.hud;

import de.aeksora.minemmo.MineMMO;
import de.aeksora.minemmo.util.IEntityDataSaver;
import de.aeksora.minemmo.util.Stat;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class StatScreen extends Screen {
    private static final Logger LOGGER = LoggerFactory.getLogger(MineMMO.MOD_ID);

    private double strength;
    private double health;
    private double speed;
    private float regeneration;
    private float miningSpeed;

    private int xp;

    private ButtonWidget strengthPlus;
    private TextFieldWidget strengthField;

    public StatScreen() {
        super(Text.literal("Stat Allocation"));
    }

    @Override
    protected void init() {
        loadStats();

        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int y = centerY - 60;

        // Strength
        strengthField = new TextFieldWidget(this.textRenderer, centerX - 50, y, 40, 20, Text.literal(""));
        strengthField.setText(String.valueOf(strength));
        this.addDrawableChild(strengthField);

        strengthPlus = addDrawableChild(ButtonWidget.builder(Text.literal("+"), b -> changeStat("strength", 1))
                .position(centerX - 5, y)
                .size(20, 20)
                .build());

        y += 30;

        // Agility (repeat)
        TextFieldWidget healthField = new TextFieldWidget(this.textRenderer, centerX - 50, y, 40, 20, Text.literal(""));
        healthField.setText(String.valueOf(health));
        this.addDrawableChild(healthField);

        addDrawableChild(ButtonWidget.builder(Text.literal("+"), b -> changeStat("health", 1))
                .position(centerX - 5, y)
                .size(20, 20)
                .build());

        y += 30;

        // Speed
        TextFieldWidget speedField = new TextFieldWidget(this.textRenderer, centerX - 50, y, 40, 20, Text.literal(""));
        speedField.setText(String.valueOf(speed));
        this.addDrawableChild(speedField);

        addDrawableChild(ButtonWidget.builder(Text.literal("+"), b -> changeStat("speed", 1))
                .position(centerX - 5, y)
                .size(20, 20)
                .build());

        y += 30;

        // Regeneration
        TextFieldWidget regenerationField = new TextFieldWidget(this.textRenderer, centerX - 50, y, 40, 20, Text.literal(""));
        regenerationField.setText(String.valueOf(speed));
        this.addDrawableChild(regenerationField);

        addDrawableChild(ButtonWidget.builder(Text.literal("+"), b -> changeStat("regeneration", 1))
                .position(centerX - 5, y)
                .size(20, 20)
                .build());

        // Confirm & Reset
        ButtonWidget confirmButton = addDrawableChild(ButtonWidget.builder(Text.literal("Confirm"), b -> confirmChanges())
                .position(centerX - 60, y + 50)
                .size(50, 20)
                .build());

        ButtonWidget resetButton = addDrawableChild(ButtonWidget.builder(Text.literal("Reset"), b -> resetChanges())
                .position(centerX + 10, y + 50)
                .size(50, 20)
                .build());
    }

    private void loadStats() {
        assert this.client != null;
        ClientPlayerEntity player = this.client.player;
        assert player != null;
        xp = ((IEntityDataSaver) player).getPersistentData().getInt("xp");

        strength = Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE)).getBaseValue();
        health = Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).getBaseValue();
        speed = Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)).getBaseValue();
        regeneration = ((IEntityDataSaver) player).getPersistentData().getFloat("regen");
        miningSpeed = ((IEntityDataSaver) player).getPersistentData().getFloat("miningSpeed");

        LOGGER.info("Loaded stats: {strength: {}, health: {}, speed: {}, regeneration: {}, miningSpeed: {}}", strength, health, speed, regeneration, miningSpeed);
    }

    private void changeStat(String stat, int amount) {
//        if (availablePoints - amount < 0 && amount > 0) return;
//
//        switch (stat) {
//            case "strength":
//                if (strength + amount <= strengthMax && strength + amount >= 0) {
//                    strength += amount;
//                    availablePoints -= amount;
//                    strengthField.setText(String.valueOf(strength));
//                }
//                break;
//            case "health":
//                if (health + amount <= agilityMax && health + amount >= 0) {
//                    health += amount;
//                    availablePoints -= amount;
//                }
//                break;
//            case "speed":
//                if (speed + amount <= defenseMax && speed + amount >= 0) {
//                    speed += amount;
//                    availablePoints -= amount;
//                }
//                break;
//            case "regeneration":
//                if (regeneration + amount <= defenseMax && regeneration + amount >= 0) {
//                    regeneration += amount;
//                    availablePoints -= amount;
//                }
//                break;
//            case "miningSpeed":
//                if (miningSpeed + amount <= defenseMax && miningSpeed + amount >= 0) {
//                    miningSpeed += amount;
//                    availablePoints -= amount;
//                }
//                break;
//        }
    }

    private void confirmChanges() {
        // Sync to server or save values
        this.close();
    }

    private void resetChanges() {
        // Reset to original values
//        strength = 10;
//        agility = 8;
//        defense = 12;
//        availablePoints = 5;
//        strengthField.setText(String.valueOf(strength));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        String label = "Current XP: " + xp;
        int textWidth = textRenderer.getWidth(label);
        textRenderer.drawWithShadow(matrices, label, (this.width - textWidth) / 2f, 20, 0xFFFFFF);
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
