package de.aeksora.minemmo.util;

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageRecord;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MobKilledEvent {
    public static final Logger LOGGER = LoggerFactory.getLogger("MinecraftMMO");
    private static final Map<UUID, Map<UUID, Float>> mobDamageMap = new HashMap<>();

    public static void register() {
        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register(((world, entity, killedEntity) -> {
            if (killedEntity instanceof MobEntity mob && entity instanceof ServerPlayerEntity player) {
//                LOGGER.info("Player killed mob");
//
//                int xpDropped = mobXpDrop(mob);
//
//                XpData.addXp((IEntityDataSaver) player, xpDropped);

                UUID mobUUID = mob.getUuid();
                Map<UUID, Float> damageMap = mobDamageMap.getOrDefault(mobUUID, new HashMap<>());

                int xpDropped = mobXpDrop(mob);

                for (Map.Entry<UUID, Float> entry : damageMap.entrySet()) {
                    ServerPlayerEntity player1 = world.getServer().getPlayerManager().getPlayer(entry.getKey());
                    if (player1 != null) {
                        float damageDealt = entry.getValue();
                        int playerXp = (int) (xpDropped * (damageDealt / mob.getMaxHealth()));
                        XpData.addXp((IEntityDataSaver) player1, playerXp);
                        LOGGER.info("Player " + player.getName().getString() + " received " + playerXp + "/" + xpDropped + " XP");
                    }
                }

                mobDamageMap.remove(mobUUID);
            }
        }));

        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {
            if (source.getAttacker() instanceof ServerPlayerEntity && entity instanceof MobEntity) {
                UUID mobUUID = entity.getUuid();
                UUID playerUUID = source.getAttacker().getUuid();

                float damage = Math.min(amount, (int) entity.getHealth());

                Map<UUID, Float> damageMap = mobDamageMap.getOrDefault(mobUUID, new HashMap<>());
                damageMap.put(playerUUID, damageMap.getOrDefault(playerUUID, 0f) + damage);
                mobDamageMap.put(mobUUID, damageMap);
                LOGGER.info("Player " + source.getAttacker().getName().getString() + " dealt damage: " + damage + " to mob: " + entity.getName().getString());
            }
            return true;
        });
    }

    public static int mobXpDrop(MobEntity killedMob) {
        final float maxHealth = killedMob.getMaxHealth();
        double attackDamage;

        try {
            attackDamage = killedMob.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        } catch (IllegalArgumentException e) {
            attackDamage = 1;
        }

        final int xp = (int) (maxHealth * attackDamage);

        LOGGER.info(
                "\nMob Stats:\nmobName: " + killedMob.getName() + "\nmaxHealth: " + maxHealth + "\nattackDamage: " + attackDamage + "\nxp: " + xp
        );

        return xp;
    }
}
