package de.aeksora.minemmo.util;

import de.aeksora.minemmo.networking.MineMMONetworkingConstants;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class LevelData {
    public static int getLevel(ServerPlayerEntity player) {
        NbtCompound nbt = ((IEntityDataSaver) player).getPersistentData();
        return nbt.getInt("level");
    }

    public static int getXpNeeded(int level) {
        // return (int) (Math.sqrt((xp - 1000.0) / 2.0));
        return (int) (0.02 * Math.pow(level, 3) + 3.61 * Math.pow(level , 2) + 64.81 * level + 255.89);
    }

    public static void syncLevel(int level, ServerPlayerEntity player) {
        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeInt(level);
        ServerPlayNetworking.send(player, MineMMONetworkingConstants.LEVEL_PACKET_ID, buffer);
    }

    public static void addLevel(ServerPlayerEntity player, int amount) {
        NbtCompound nbt = ((IEntityDataSaver) player).getPersistentData();
        int level = nbt.getInt("level");
        level += amount;



        nbt.putInt("level", level);
        syncLevel(level, player);
    }
}
