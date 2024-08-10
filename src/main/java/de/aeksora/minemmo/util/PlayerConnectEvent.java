package de.aeksora.minemmo.util;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerConnectEvent {
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

        // sync attributes
        StatModifier.syncAtributes(player);

        // Initialize mining speed if not already initialized
//        System.out.println("Mining speed was " + persistentData.getFloat("miningSpeed") + " on player join");
        if (persistentData.getFloat("miningSpeed") == 0.0f) {
            persistentData.putFloat("miningSpeed", 1.0f);
//            System.out.println("Set mining speed to 1.0 on player join");
        }
    }
}
