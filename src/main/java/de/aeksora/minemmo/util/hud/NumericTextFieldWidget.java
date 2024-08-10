package de.aeksora.minemmo.util.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class NumericTextFieldWidget extends TextFieldWidget {

    private Runnable onEnterPressed;

    public NumericTextFieldWidget(int x, int y, int width, int height, Text text) {
        super(MinecraftClient.getInstance().textRenderer, x, y, width, height, text);
    }

    @Override
    public boolean charTyped(char chr, int keyCode) {
        if (Character.isDigit(chr)) {
            return super.charTyped(chr, keyCode);
        }
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 257 || keyCode == 335) { // Enter and Numpad Enter
            if (onEnterPressed != null) {
                onEnterPressed.run();
                return true;
            }
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public void setOnEnterPressed(Runnable onEnterPressed) {
        this.onEnterPressed = onEnterPressed;
    }
}

