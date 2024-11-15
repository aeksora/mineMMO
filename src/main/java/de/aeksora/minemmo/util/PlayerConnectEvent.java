package de.aeksora.minemmo.util;

import de.aeksora.minemmo.MineMMO;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class PlayerConnectEvent {
    public static final Logger LOGGER = LoggerFactory.getLogger(MineMMO.MOD_ID);

    public static void register() {
        ServerPlayConnectionEvents.JOIN.register(PlayerConnectEvent::onPlayerJoin);
    }

    private static void onPlayerJoin(ServerPlayNetworkHandler serverPlayNetworkHandler, PacketSender packetSender, MinecraftServer minecraftServer) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //sync xp and level
        ServerPlayerEntity player = serverPlayNetworkHandler.getPlayer();
        NbtCompound persistentData = ((IEntityDataSaver) player).getPersistentData();

        int xp = persistentData.getInt("xp");
        XpData.syncXp(xp, player);
        int level = persistentData.getInt("level");
        LevelData.syncLevel(level, player);

        // Initialize mining speed if not already initialized
        if (persistentData.getFloat("miningSpeed") == 0.0f) {
            persistentData.putFloat("miningSpeed", 1.0f);
            LOGGER.info("Set mining speed to 1.0");
        }

        // Initialize regen if not already initialized
        if (persistentData.getFloat("regen") == 0.0f) {
            persistentData.putFloat("regen", 0.25f);
            LOGGER.info("Set regen to 0.25");
        }

        // sync attributes
        StatModifier.syncAtributes(player);
    }
}
