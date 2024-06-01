package de.aeksora.minemmo.util;

import de.aeksora.minemmo.networking.MineMMONetworkingConstants;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class XpData {
    public static int addXp(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        int xp = nbt.getInt("xp");
        xp += amount;



        nbt.putInt("xp", xp);
        syncXp(xp, (ServerPlayerEntity) player);
        return xp;
    }

    public static int setXp(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        nbt.putInt("xp", amount);

        syncXp(amount, (ServerPlayerEntity) player);
        return amount;
    }

    public static boolean removeXp(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        int xp = nbt.getInt("xp");

        if (xp < amount) {
            return false;
        } else {
            xp -= amount;
            nbt.putInt("xp", xp);

            syncXp(xp, (ServerPlayerEntity) player);
            return true;
        }
    }

    public static void syncXp(int xp, ServerPlayerEntity player) {
        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeInt(xp);
        ServerPlayNetworking.send(player, MineMMONetworkingConstants.XP_PACKET_ID, buffer);
    }
}
