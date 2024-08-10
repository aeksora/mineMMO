package de.aeksora.minemmo;

import de.aeksora.minemmo.networking.ModMessagesS2C;
import de.aeksora.minemmo.util.hud.LevelingScreen;
import de.aeksora.minemmo.util.hud.MineMMOHud;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MineMMOClient implements ClientModInitializer {
	@SuppressWarnings("unused")
	public static final Logger LOGGER = LoggerFactory.getLogger(MineMMO.MOD_ID);

	private static KeyBinding keyBinding;

	@Override
	public void onInitializeClient() {
		HudRenderCallback.EVENT.register(new MineMMOHud());
		initKeybinds();
		ModMessagesS2C.registerS2CPackages();
	}

	public void initKeybinds() {
		keyBinding = KeyBindingHelper.registerKeyBinding(
				new KeyBinding(
						"Open Leveling Screen",
						InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_J,
						"MineMMO"
				)
		);

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (keyBinding.wasPressed()) {
				System.out.println("Key pressed");
				client.setScreen(new LevelingScreen());
			}
		});
	}
}