package de.aeksora.minecraftmmo;

import de.aeksora.minecraftmmo.util.IEntityDataSaver;
import de.aeksora.minecraftmmo.util.XpData;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MobKilledEvent {
    public static final Logger LOGGER = LoggerFactory.getLogger("MincraftMMO");
    public static void register() {
        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register(((world, entity, killedEntity) -> {
            if (killedEntity instanceof MobEntity mob && entity instanceof ServerPlayerEntity player) {
                LOGGER.info("Player killed mob");

                int xpDropped = mobXpDrop(mob);

                XpData.addXp((IEntityDataSaver) player, xpDropped);
                // print current xp
//                entity.sendMessage(
//                        Text.literal("XP: " + ((IEntityDataSaver) player).getPersistentData().getInt("xp"))
//                );
                entity.sendMessage(
                        Text.literal("XP received: " + xpDropped)
                );
            }
        }));
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
                "\nMob Stats:\nmobName: " + killedMob.getDisplayName() + "\nmaxHealth: " + maxHealth + "\nattackDamage: " + attackDamage + "\nxp: " + xp
        );

        return xp;
    }
}
