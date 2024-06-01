package de.aeksora.minecraftmmo;

import de.aeksora.minecraftmmo.util.MineMMOHud;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class MineMMOClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		HudRenderCallback.EVENT.register(new MineMMOHud());
	}
}