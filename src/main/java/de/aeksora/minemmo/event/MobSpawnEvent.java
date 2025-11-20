package de.aeksora.minemmo.event;

import de.aeksora.minemmo.MineMMO;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MobSpawnEvent {
    @SuppressWarnings("unused")
    public static final Logger LOGGER = LoggerFactory.getLogger(MineMMO.MOD_ID);
    private static final Random RANDOM = new Random();

    public static void register() {
        ServerEntityEvents.ENTITY_LOAD.register(((entity, world) -> {
            if (world instanceof ServerWorld && entity instanceof HostileEntity) {
                modifyAttributes((HostileEntity) entity, world);
            }
        }));
    }

    private static void modifyAttributes(HostileEntity mob, ServerWorld world) {
        BlockPos spawnPoint = world.getSpawnPos();
        BlockPos mobPos = mob.getBlockPos();
        double distance = mobPos.getManhattanDistance(spawnPoint);

        double baseMultiplier = 0.175 * Math.pow(distance, 0.5) + 1;
        double noiseFactor = 0.8 + (RANDOM.nextDouble() * 1.2);

        boolean isElite = RANDOM.nextDouble() < 0.05;
        if (isElite) {
            baseMultiplier *= 3;
        }

        double finalMultiplier = baseMultiplier * noiseFactor;

        EntityAttributeInstance healthAttribute = mob.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (healthAttribute != null) {
            healthAttribute.setBaseValue((int)(healthAttribute.getBaseValue() * finalMultiplier));
        }

        EntityAttributeInstance damageAttribute = mob.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        if (damageAttribute != null) {
            damageAttribute.setBaseValue((int)(damageAttribute.getBaseValue() * finalMultiplier));
        }

        mob.setHealth(mob.getMaxHealth());

        setName(mob, isElite);

        Set<HostileEntity> entitiesToUpdate = new HashSet<>();

        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {
            if (entity instanceof HostileEntity) {
                entitiesToUpdate.add((HostileEntity) entity);
            }
            return true;
        });

        // Register the event listener for the server tick
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (HostileEntity entity : entitiesToUpdate) {
                if (entity.isAlive()) {
                    setName(entity, isElite);
                }
            }
            entitiesToUpdate.clear();
        });
    }

    private static void setName(HostileEntity mob, boolean isElite) {
        String name = String.format("Health: %d, Damage: %d", (int) mob.getHealth(), (int) mob.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE));

        if (isElite) {
            name = "ELITE " + name;
        }

        mob.setCustomName(Text.of(name));
        mob.setCustomNameVisible(true);
    }
}
