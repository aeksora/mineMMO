package de.aeksora.minemmo.util;

import de.aeksora.minemmo.MineMMO;
import de.aeksora.minemmo.networking.MineMMONetworkingConstants;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Objects;

public class StatModifier {
    public static void addStrength(ServerPlayerEntity player, int amountToBeAdded) {
        EntityAttributeInstance strengthAttribute = player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);

        for (int i = 0; i < amountToBeAdded; i++) {
            if (XpData.removeXp((IEntityDataSaver) player, LevelData.getXpNeeded(LevelData.getLevel(player)))) {
                assert strengthAttribute != null;
                strengthAttribute.setBaseValue(strengthAttribute.getBaseValue() + MineMMO.STRENGTH_PER_LEVEL);
                LevelData.addLevel(player, 1);
            }
        }

        PacketByteBuf buffer = PacketByteBufs.create();
        assert strengthAttribute != null;
        buffer.writeDouble(strengthAttribute.getBaseValue());
        ServerPlayNetworking.send(player, MineMMONetworkingConstants.GAD_PACKET_2C_ID, buffer);
    }

    public static void setStrength(ServerPlayerEntity player, int amount) {
        EntityAttributeInstance strengthAttribute = player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);

        double trueAmount = amount * MineMMO.STRENGTH_PER_LEVEL + 1.0;

        assert strengthAttribute != null;
        strengthAttribute.setBaseValue(trueAmount);
        LevelData.addLevel(player, amount);

        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeDouble(strengthAttribute.getBaseValue());
        ServerPlayNetworking.send(player, MineMMONetworkingConstants.GAD_PACKET_2C_ID, buffer);
    }

    public static void addHealth(ServerPlayerEntity player, int amountToBeAdded) {
        EntityAttributeInstance healthAttribute = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);

        for (int i = 0; i < amountToBeAdded; i++) {
            if (XpData.removeXp((IEntityDataSaver) player, LevelData.getXpNeeded(LevelData.getLevel(player)))) {
                assert healthAttribute != null;
                healthAttribute.setBaseValue(healthAttribute.getBaseValue() + MineMMO.HEALTH_PER_LEVEL);
                LevelData.addLevel(player, 1);
            }
        }

        PacketByteBuf buffer = PacketByteBufs.create();
        assert healthAttribute != null;
        buffer.writeDouble(healthAttribute.getBaseValue());
        ServerPlayNetworking.send(player, MineMMONetworkingConstants.GMH_PACKET_2C_ID, buffer);
    }

    public static void setHealth(ServerPlayerEntity player, int amount) {
        EntityAttributeInstance healthAttribute = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);

        double trueAmount = amount * MineMMO.HEALTH_PER_LEVEL + 20.0;

        assert healthAttribute != null;
        healthAttribute.setBaseValue(trueAmount);
        LevelData.addLevel(player, amount);

        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeDouble(healthAttribute.getBaseValue());
        ServerPlayNetworking.send(player, MineMMONetworkingConstants.GMH_PACKET_2C_ID, buffer);
    }

    public static void addSpeed(ServerPlayerEntity player, int amountToBeAdded) {
        EntityAttributeInstance speedAttribute = player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);

        for (int i = 0; i < amountToBeAdded; i++) {
            if (XpData.removeXp((IEntityDataSaver) player, LevelData.getXpNeeded(LevelData.getLevel(player)))) {
                assert speedAttribute != null;
                speedAttribute.setBaseValue(speedAttribute.getBaseValue() + MineMMO.SPEED_PER_LEVEL);
                LevelData.addLevel(player, 1);
            }
        }

        PacketByteBuf buffer = PacketByteBufs.create();
        assert speedAttribute != null;
        buffer.writeDouble(speedAttribute.getBaseValue());
        ServerPlayNetworking.send(player, MineMMONetworkingConstants.GMS_PACKET_2C_ID, buffer);
    }

    public static void setSpeed(ServerPlayerEntity player, int amount) {
        EntityAttributeInstance speedAttribute = player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);

        double trueAmount = amount * MineMMO.SPEED_PER_LEVEL + 0.1f;

        assert speedAttribute != null;
        speedAttribute.setBaseValue(trueAmount);
        LevelData.addLevel(player, amount);

        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeDouble(speedAttribute.getBaseValue());
        ServerPlayNetworking.send(player, MineMMONetworkingConstants.GMS_PACKET_2C_ID, buffer);
    }

    public static void addRegen(ServerPlayerEntity player, int amountToBeAdded) {
        float regen = ((IEntityDataSaver) player).getPersistentData().getFloat("regen");

        for (int i = 0; i < amountToBeAdded; i++) {
            if (XpData.removeXp((IEntityDataSaver) player, LevelData.getXpNeeded(LevelData.getLevel(player)))) {
                regen += MineMMO.REGEN_PER_LEVEL;
                ((IEntityDataSaver) player).getPersistentData().putFloat("regen", regen);
                ((IEntityDataSaver) player).getPersistentData().putFloat("maxRegen", regen);
                LevelData.addLevel(player, 1);
            }
        }

        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeFloat(regen);
        ServerPlayNetworking.send(player, MineMMONetworkingConstants.REGEN_PACKET_ID, buffer);
    }

    public static void addMining(ServerPlayerEntity player, int amountToBeAdded) {
        float miningSpeed = ((IEntityDataSaver) player).getPersistentData().getFloat("miningSpeed");

        for (int i = 0; i < amountToBeAdded; i++) {
            if (XpData.removeXp((IEntityDataSaver) player, LevelData.getXpNeeded(LevelData.getLevel(player)))) {
                miningSpeed += MineMMO.MINESPEED_PER_LEVEL;
                ((IEntityDataSaver) player).getPersistentData().putFloat("miningSpeed", miningSpeed);
                ((IEntityDataSaver) player).getPersistentData().putFloat("maxMiningSpeed", miningSpeed);
                LevelData.addLevel(player, 1);
            }
        }

        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeFloat(miningSpeed);
        ServerPlayNetworking.send(player, MineMMONetworkingConstants.MININGSPEED_PACKET_ID, buffer);
    }

    public static void setRegen(ServerPlayerEntity player, int amount) {
        float regen = amount * MineMMO.REGEN_PER_LEVEL;

        ((IEntityDataSaver) player).getPersistentData().putFloat("regen", regen);
        LevelData.addLevel(player, amount);

        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeFloat(regen);
        ServerPlayNetworking.send(player, MineMMONetworkingConstants.REGEN_PACKET_ID, buffer);
    }

    public static void syncAtributes(ServerPlayerEntity player) {
        PacketByteBuf buffer1 = PacketByteBufs.create();
        buffer1.writeDouble(Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE)).getBaseValue());
        ServerPlayNetworking.send(player, MineMMONetworkingConstants.GAD_PACKET_2C_ID, buffer1);

        PacketByteBuf buffer2 = PacketByteBufs.create();
        buffer2.writeDouble(Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).getValue());
        ServerPlayNetworking.send(player, MineMMONetworkingConstants.GMH_PACKET_2C_ID, buffer2);

        PacketByteBuf buffer3 = PacketByteBufs.create();
        buffer3.writeDouble(Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)).getValue());
        ServerPlayNetworking.send(player, MineMMONetworkingConstants.GMS_PACKET_2C_ID, buffer3);

        PacketByteBuf buffer4 = PacketByteBufs.create();
        buffer4.writeFloat(((IEntityDataSaver) player).getPersistentData().getFloat("regen"));
        ServerPlayNetworking.send(player, MineMMONetworkingConstants.REGEN_PACKET_ID, buffer4);

        PacketByteBuf buffer5 = PacketByteBufs.create();
        buffer5.writeFloat(((IEntityDataSaver) player).getPersistentData().getFloat("miningSpeed"));
        ServerPlayNetworking.send(player, MineMMONetworkingConstants.MININGSPEED_PACKET_ID, buffer5);
    }
}
