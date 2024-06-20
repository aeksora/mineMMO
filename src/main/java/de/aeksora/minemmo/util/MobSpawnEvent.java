package de.aeksora.minemmo.util;

import de.aeksora.minemmo.MineMMO;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class MobSpawnEvent {
    public static final Logger LOGGER = LoggerFactory.getLogger(MineMMO.MOD_ID);
    private static final Random RANDOM = new Random();

    public static void register() {
        ServerEntityEvents.ENTITY_LOAD.register(((entity, world) -> {
            if (world instanceof ServerWorld && entity instanceof HostileEntity) {
                modifyAttributes((HostileEntity) entity, (ServerWorld) world);
            }
        }));
    }

    private static void modifyAttributes(HostileEntity mob, ServerWorld world) {
        BlockPos spawnPoint = world.getSpawnPos();
        BlockPos mobPos = mob.getBlockPos();
        double distance = mobPos.getManhattanDistance(spawnPoint);

        double baseMultiplier = Math.pow(2, distance/400.0);
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

        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {
            if (entity instanceof HostileEntity) {
                System.out.println("Damage event called on: " + entity.getName());
                setName(mob, isElite);
            }
            return true;
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
