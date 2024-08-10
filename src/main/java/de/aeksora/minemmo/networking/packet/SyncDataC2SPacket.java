package de.aeksora.minemmo.networking.packet;

import de.aeksora.minemmo.MineMMO;
import de.aeksora.minemmo.util.IEntityDataSaver;
import de.aeksora.minemmo.util.StatModifier;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class SyncDataC2SPacket {

    public static final Logger LOGGER = LoggerFactory.getLogger(MineMMO.MOD_ID);

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

    @SuppressWarnings("unused")
    public static void setGAD2S(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        // Retain the buffer to prevent it from being prematurely released
        buf.retain();

        server.execute(() -> {
            try {
                if (player != null) {
                    // Check if there are enough readable bytes before attempting to read
                    if (buf.readableBytes() >= 4) {
                        double strength = buf.readDouble();
                        Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE)).setBaseValue(strength);
                    } else {
                        LOGGER.error("Not enough readable bytes in PacketByteBuf");
                    }
                } else {
                    LOGGER.error("Client player is null when receiving packet");
                }
            } catch (Exception e) {
                LOGGER.error("Error processing PacketByteBuf", e);
            } finally {
                // Ensure the buffer is released to prevent memory leaks
                buf.release();
            }
        });
    }

    @SuppressWarnings("unused")
    public static void setGMH2S(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        // Retain the buffer to prevent it from being prematurely released
        buf.retain();

        server.execute(() -> {
            try {
                if (player != null) {
                    // Check if there are enough readable bytes before attempting to read
                    if (buf.readableBytes() >= 4) {
                        double health = buf.readDouble();
                        Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(health);
                    } else {
                        LOGGER.error("Not enough readable bytes in PacketByteBuf");
                    }
                } else {
                    LOGGER.error("Client player is null when receiving packet");
                }
            } catch (Exception e) {
                LOGGER.error("Error processing PacketByteBuf", e);
            } finally {
                // Ensure the buffer is released to prevent memory leaks
                buf.release();
            }
        });
    }

    @SuppressWarnings("unused")
    public static void setGMS2S(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        // Retain the buffer to prevent it from being prematurely released
        buf.retain();

        server.execute(() -> {
            try {
                if (player != null) {
                    // Check if there are enough readable bytes before attempting to read
                    if (buf.readableBytes() >= 4) {
                        double speed = buf.readDouble();
                        Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)).setBaseValue(speed);
                    } else {
                        LOGGER.error("Not enough readable bytes in PacketByteBuf");
                    }
                } else {
                    LOGGER.error("Client player is null when receiving packet");
                }
            } catch (Exception e) {
                LOGGER.error("Error processing PacketByteBuf", e);
            } finally {
                // Ensure the buffer is released to prevent memory leaks
                buf.release();
            }
        });
    }

    @SuppressWarnings("unused")
    public static void setRegen2S(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        // Retain the buffer to prevent it from being prematurely released
        buf.retain();

        server.execute(() -> {
            try {
                if (player != null) {
                    // Check if there are enough readable bytes before attempting to read
                    if (buf.readableBytes() >= 4) {
                        float regen = buf.readFloat();
                        ((IEntityDataSaver) player).getPersistentData().putFloat("regen", regen);
                    } else {
                        LOGGER.error("Not enough readable bytes in PacketByteBuf");
                    }
                } else {
                    LOGGER.error("Client player is null when receiving packet");
                }
            } catch (Exception e) {
                LOGGER.error("Error processing PacketByteBuf", e);
            } finally {
                // Ensure the buffer is released to prevent memory leaks
                buf.release();
            }
        });
    }
}
