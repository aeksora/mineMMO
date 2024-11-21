package de.aeksora.minemmo.util;

import de.aeksora.minemmo.networking.MineMMONetworkingConstants;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Getters and Setters for the stats of the supplied PlayerEntity
 */
public class StatHelper {
    private final PlayerEntity player;
    private NbtCompound persistentData;

    private Map<String, Identifier> serverToClientIdentifiers = new HashMap<>();
    private Map<String, Identifier> clientToServerIdentifiers = new HashMap<>();

    private final Identifier xpId = MineMMONetworkingConstants.XP_PACKET_ID;
    private final Identifier levelId = MineMMONetworkingConstants.LEVEL_PACKET_ID;
    private final Identifier regenId = MineMMONetworkingConstants.REGEN_PACKET_ID;
    private final Identifier miningId = MineMMONetworkingConstants.MININGSPEED_PACKET_ID;

    public StatHelper(PlayerEntity player) {
        this.player = player;
        this.persistentData = ((IEntityDataSaver) player).getPersistentData();
    }

    public int getXp() {
        int xp = persistentData.getInt("xp");
        sync(xp, player, xpId);
        return xp;
    }

    public void setXp(int xp) {
        persistentData.putInt("xp", xp);
        sync(xp, player, xpId);
    }

    public int getLevel() {
        return persistentData.getInt("level");
    }

    public float getMiningSpeed() {
        float miningSpeed = persistentData.getFloat("miningSpeed");
        sync(miningSpeed, player, miningId);
        return miningSpeed;
    }

    public void setMiningSpeed(float miningSpeed) {
        persistentData.putFloat("miningSpeed", miningSpeed);
        sync(miningSpeed, player, miningId);
    }

    public float getRegen() {
        float regen = persistentData.getFloat("regen");
        sync(regen, player, regenId);
        return regen;
    }

    public void setRegen(float regen) {
        persistentData.putFloat("regen", regen);
        sync(regen, player, regenId);
    }

    private static void sync(int data, PlayerEntity player, Identifier identifier) {
        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeInt(data);
        if (player instanceof ServerPlayerEntity) {
            ServerPlayNetworking.send((ServerPlayerEntity) player, identifier, buffer);
        } else if (player instanceof ClientPlayerEntity) {
            ClientPlayNetworking.send(identifier, buffer);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private static void sync(float data, PlayerEntity player, Identifier identifier) {
        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeFloat(data);
        if (player instanceof ServerPlayerEntity) {
            ServerPlayNetworking.send((ServerPlayerEntity) player, identifier, buffer);
        } else if (player instanceof ClientPlayerEntity) {
            ClientPlayNetworking.send(identifier, buffer);
        } else {
            throw new IllegalArgumentException();
        }
    }
}