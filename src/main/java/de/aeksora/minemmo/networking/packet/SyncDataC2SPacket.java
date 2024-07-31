package de.aeksora.minemmo.networking.packet;

import de.aeksora.minemmo.util.StatModifier;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class SyncDataC2SPacket {
    @SuppressWarnings("unused")
    public static void addGAD2S(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        StatModifier.addStrength(player, 1);
    }

    @SuppressWarnings("unused")
    public static void addGMH2S(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        StatModifier.addHealth(player, 1);
    }

    @SuppressWarnings("unused")
    public static void addGMS2S(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        StatModifier.addSpeed(player, 1);
    }

    @SuppressWarnings("unused")
    public static void addRegen2S(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        StatModifier.addRegen(player, 1);
    }
}
