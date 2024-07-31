package de.aeksora.minemmo.util;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
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
        int xp = ((IEntityDataSaver) player).getPersistentData().getInt("xp");
        XpData.syncXp(xp, player);
        int level = ((IEntityDataSaver) player).getPersistentData().getInt("level");
        LevelData.syncLevel(level, player);

        // sync attributes
        StatModifier.syncAtributes(player);
    }
}
