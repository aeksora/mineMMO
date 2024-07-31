package de.aeksora.minemmo;

import de.aeksora.minemmo.networking.ModMessagesC2S;
import de.aeksora.minemmo.util.*;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MineMMO implements ModInitializer {
	public static final String MOD_ID = "MineMMO";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Mod initialized");
		MobSpawnEvent.register();
		MobKilledEvent.register();
		PlayerDeathEvent.register();
		XpCommand.registerCommands();
		ModMessagesC2S.registerC2SPackages();
		PlayerConnectEvent.register();
	}

}