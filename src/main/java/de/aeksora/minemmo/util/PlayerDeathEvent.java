package de.aeksora.minemmo.util;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;

import javax.swing.text.html.parser.Entity;

public class PlayerDeathEvent {
    public static void register() {
        ServerPlayerEvents.COPY_FROM.register(PlayerDeathEvent::copyAttributes);
    }

    private static void copyAttributes(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
        newPlayer.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(oldPlayer.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).getBaseValue());
        newPlayer.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(oldPlayer.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).getBaseValue());

    }
}
