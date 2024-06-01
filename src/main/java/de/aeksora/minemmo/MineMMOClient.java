package de.aeksora.minemmo;

import de.aeksora.minemmo.util.MineMMOHud;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MineMMOClient implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger(MineMMO.MOD_ID);
	@Override
	public void onInitializeClient() {
		HudRenderCallback.EVENT.register(new MineMMOHud());
	}
}