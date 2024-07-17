package de.aeksora.minemmo.networking.packet;

import de.aeksora.minemmo.util.IEntityDataSaver;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.network.PacketByteBuf;

import java.util.Objects;

public class SyncDataS2CPacket {
    @SuppressWarnings("unused")
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        assert client.player != null;
        ((IEntityDataSaver) client.player).getPersistentData().putInt("xp", buf.readInt());
        buf.clear();
    }

    @SuppressWarnings("unused")
    public static void setGAD(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        double strength = buf.readDouble();
        assert client.player != null;
        Objects.requireNonNull(client.player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE)).setBaseValue(strength);
    }

    @SuppressWarnings("unused")
    public static void setGMH(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        double health = buf.readDouble();
        assert client.player != null;
        Objects.requireNonNull(client.player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(health);
    }

    @SuppressWarnings("unused")
    public static void setGMS(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        double speed = buf.readDouble();
        assert client.player != null;
        Objects.requireNonNull(client.player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)).setBaseValue(speed);
    }
}
