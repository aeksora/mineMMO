package de.aeksora.minemmo.util;

import de.aeksora.minemmo.networking.MineMMONetworkingConstants;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class XpData {
    /**
     * Adds XP to the ServerPlayerEntity and syncs them to the corresponding ClientPlayerEntity
     * @param player the player the XP should be added to
     * @param amount the amount of XP that should be added
     * @return the players XP after adding
     */
    public static int addXp(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        int xp = nbt.getInt("xp");
        xp += amount;

        nbt.putInt("xp", xp);
        syncXp(xp, (ServerPlayerEntity) player);
        return xp;
    }

    /**
     * Sets XP of the ServerPlayerEntity and syncs them to the corresponding ClientPlayerEntity
     * @param player the player whose XP should be set
     * @param amount the value the XP should be set to
     * @return the players XP after modifying
     */
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

    /**
     * Syncs a ServerPlayerEntity's XP to the corresponding ClientPlayerEntity
     * @param xp the xp that should be written to the ClientPlayerEntity
     * @param player the ServerPlayerEntity
     */
    public static void syncXp(int xp, ServerPlayerEntity player) {
        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeInt(xp);
        ServerPlayNetworking.send(player, MineMMONetworkingConstants.XP_PACKET_ID, buffer);
    }
}
