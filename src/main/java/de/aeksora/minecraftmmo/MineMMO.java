package de.aeksora.minecraftmmo;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MineMMO implements ModInitializer {
    public static final String MOD_ID = "MinecraftMMO";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Mod initialized");
    }
}
