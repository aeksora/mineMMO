package de.aeksora.minemmo.networking.packet;

import de.aeksora.minemmo.util.IEntityDataSaver;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.network.PacketByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

import static de.aeksora.minemmo.MineMMO.MOD_ID;

public class SyncDataS2CPacket {

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @SuppressWarnings("unused")
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        // Retain the buffer to prevent it from being prematurely released
        buf.retain();

        client.execute(() -> {
            try {
                if (client.player != null) {
                    // Check if there are enough readable bytes before attempting to read
                    if (buf.readableBytes() >= 4) {
                        int xp = buf.readInt();
                        ((IEntityDataSaver) client.player).getPersistentData().putInt("xp", xp);
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
    public static void receiveLevel(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        // Retain the buffer to prevent it from being prematurely released
        buf.retain();

        client.execute(() -> {
            try {
                if (client.player != null) {
                    // Check if there are enough readable bytes before attempting to read
                    if (buf.readableBytes() >= 4) {
                        int level = buf.readInt();
                        ((IEntityDataSaver) client.player).getPersistentData().putInt("level", level);
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
    public static void receiveRegen(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        // Retain the buffer to prevent it from being prematurely released
        buf.retain();

        client.execute(() -> {
            try {
                if (client.player != null) {
                    // Check if there are enough readable bytes before attempting to read
                    if (buf.readableBytes() >= 4) {
                        float regen = buf.readFloat();
                        ((IEntityDataSaver) client.player).getPersistentData().putFloat("regen", regen);
                        ((IEntityDataSaver) client.player).getPersistentData().putFloat("maxRegen", regen);
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
    public static void receiveStrength(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        // Retain the buffer to prevent it from being prematurely released
        buf.retain();

        client.execute(() -> {
            try {
                if (client.player != null) {
                    // Check if there are enough readable bytes before attempting to read
                    if (buf.readableBytes() >= 4) {
                        double strength = buf.readDouble();
                        assert client.player != null;
                        Objects.requireNonNull(client.player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE)).setBaseValue(strength);
                        ((IEntityDataSaver) client.player).getPersistentData().putDouble("maxStrength", strength);
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
    public static void receiveHealth(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        // Retain the buffer to prevent it from being prematurely released
        buf.retain();

        client.execute(() -> {
            try {
                if (client.player != null) {
                    // Check if there are enough readable bytes before attempting to read
                    if (buf.readableBytes() >= 4) {
                        double health = buf.readDouble();
                        assert client.player != null;
                        Objects.requireNonNull(client.player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(health);
                        ((IEntityDataSaver) client.player).getPersistentData().putDouble("maxHealth", health);
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
    public static void receiveSpeed(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        // Retain the buffer to prevent it from being prematurely released
        buf.retain();

        client.execute(() -> {
            try {
                if (client.player != null) {
                    // Check if there are enough readable bytes before attempting to read
                    if (buf.readableBytes() >= 4) {
                        double speed = buf.readDouble();
                        assert client.player != null;
                        Objects.requireNonNull(client.player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)).setBaseValue(speed);
                        ((IEntityDataSaver) client.player).getPersistentData().putDouble("maxSpeed", speed);
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
