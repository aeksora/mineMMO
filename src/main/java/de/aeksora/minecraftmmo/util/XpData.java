package de.aeksora.minecraftmmo.util;

import net.minecraft.nbt.NbtCompound;

public class XpData {
    public static int addXp(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        int xp = nbt.getInt("xp");
        xp += amount;

        nbt.putInt("xp", xp);

        return xp;
    }

    public static int removeXp(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        int xp = nbt.getInt("xp");
        xp = Math.max(0, xp - amount);

        nbt.putInt("xp", xp);

        return xp;
    }
}
