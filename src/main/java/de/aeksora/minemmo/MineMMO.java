package de.aeksora.minemmo;

import de.aeksora.minemmo.event.MobKilledEvent;
import de.aeksora.minemmo.event.MobSpawnEvent;
import de.aeksora.minemmo.event.PlayerConnectEvent;
import de.aeksora.minemmo.event.PlayerDeathEvent;
import de.aeksora.minemmo.functions.CustomHealthRegen;
import de.aeksora.minemmo.functions.EnchantmentRegisterer;
import de.aeksora.minemmo.functions.XpCommand;
import de.aeksora.minemmo.networking.ModMessagesC2S;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MineMMO implements ModInitializer {
	public static final String MOD_ID = "MineMMO";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final int HEALTH_PER_LEVEL = 1;
	public static final int STRENGTH_PER_LEVEL = 1;
	public static final double SPEED_PER_LEVEL = 0.0015;
	public static final float REGEN_PER_LEVEL = 0.02f;
	public static final float MINESPEED_PER_LEVEL = 0.0125f;

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
		CustomHealthRegen.register();
		EnchantmentRegisterer.register();
	}

}