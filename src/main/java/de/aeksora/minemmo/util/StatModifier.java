package de.aeksora.minemmo.util;

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
                strengthAttribute.setBaseValue(strengthAttribute.getBaseValue() + 1);
                LevelData.addLevel(player, 1);
            }
        }

        PacketByteBuf buffer = PacketByteBufs.create();
        assert strengthAttribute != null;
        buffer.writeDouble(strengthAttribute.getBaseValue());
        ServerPlayNetworking.send(player, MineMMONetworkingConstants.GAD_PACKET_2C_ID, buffer);
    }

    public static void addHealth(ServerPlayerEntity player, int amountToBeAdded) {
        EntityAttributeInstance healthAttribute = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);

        for (int i = 0; i < amountToBeAdded; i++) {
            if (XpData.removeXp((IEntityDataSaver) player, LevelData.getXpNeeded(LevelData.getLevel(player)))) {
                assert healthAttribute != null;
                healthAttribute.setBaseValue(healthAttribute.getBaseValue() + 1);
                LevelData.addLevel(player, 1);
            }
        }

        PacketByteBuf buffer = PacketByteBufs.create();
        assert healthAttribute != null;
        buffer.writeDouble(healthAttribute.getBaseValue());
        ServerPlayNetworking.send(player, MineMMONetworkingConstants.GMH_PACKET_2C_ID, buffer);
    }

    public static void addSpeed(ServerPlayerEntity player, int amountToBeAdded) {
        EntityAttributeInstance speedAttribute = player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);

        for (int i = 0; i < amountToBeAdded; i++) {
            if (XpData.removeXp((IEntityDataSaver) player, LevelData.getXpNeeded(LevelData.getLevel(player)))) {
                assert speedAttribute != null;
                speedAttribute.setBaseValue(speedAttribute.getBaseValue() + 0.001);
                LevelData.addLevel(player, 1);
            }
        }

        PacketByteBuf buffer = PacketByteBufs.create();
        assert speedAttribute != null;
        buffer.writeDouble(speedAttribute.getBaseValue());
        ServerPlayNetworking.send(player, MineMMONetworkingConstants.GMS_PACKET_2C_ID, buffer);
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
    }
}
