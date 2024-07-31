package de.aeksora.minemmo.util;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Objects;

public class PlayerDeathEvent {
    public static void register() {
        ServerPlayerEvents.COPY_FROM.register(PlayerDeathEvent::copyAttributes);
        ServerPlayerEvents.AFTER_RESPAWN.register(PlayerDeathEvent::afterRespawn);
    }

    private static void copyAttributes(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
        Objects.requireNonNull(newPlayer.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(Objects.requireNonNull(oldPlayer.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).getBaseValue());
        Objects.requireNonNull(newPlayer.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE)).setBaseValue(Objects.requireNonNull(oldPlayer.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE)).getBaseValue());
        Objects.requireNonNull(newPlayer.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)).setBaseValue(Objects.requireNonNull(oldPlayer.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)).getBaseValue());
    }

    private static void afterRespawn(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
        if (!alive) {
            int level = ((IEntityDataSaver) oldPlayer).getPersistentData().getInt("level");
            LevelData.setLevel(newPlayer, level);

            StatModifier.syncAtributes(newPlayer);
        }
    }
}
