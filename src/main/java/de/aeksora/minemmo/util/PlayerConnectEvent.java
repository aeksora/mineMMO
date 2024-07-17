package de.aeksora.minemmo.util;

import de.aeksora.minemmo.networking.MineMMONetworkingConstants;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Objects;

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

        //sync xp
        ServerPlayerEntity player = serverPlayNetworkHandler.getPlayer();
        int xp = ((IEntityDataSaver) player).getPersistentData().getInt("xp");
        XpData.syncXp(xp, player);

        // sync attributes
        PacketByteBuf buffer1 = PacketByteBufs.create();
        buffer1.writeDouble(Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE)).getBaseValue());
        ServerPlayNetworking.send(player, MineMMONetworkingConstants.GAD_PACKET_2C_ID, buffer1);

        PacketByteBuf buffer2 = PacketByteBufs.create();
        buffer2.writeDouble(Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).getValue());
        ServerPlayNetworking.send(player, MineMMONetworkingConstants.GMH_PACKET_2C_ID, buffer2);

        PacketByteBuf buffer3 = PacketByteBufs.create();
        buffer3.writeDouble(Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)).getValue());
        ServerPlayNetworking.send(player, MineMMONetworkingConstants.GMS_PACKET_2C_ID, buffer3);
    }
}
