package de.aeksora.minemmo.functions;

import de.aeksora.minemmo.util.IEntityDataSaver;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameRules;

public class CustomHealthRegen {

    private static final int TICKS_PER_SECOND = 20;

    public static void register() {
        // Register the server tick callback
        ServerTickEvents.START_WORLD_TICK.register(CustomHealthRegen::onWorldTick);
        ServerTickEvents.START_SERVER_TICK.register(CustomHealthRegen::onServerStart);
    }

    private static void onServerStart(MinecraftServer minecraftServer) {
        minecraftServer.getGameRules().get(GameRules.NATURAL_REGENERATION).set(false, minecraftServer);
    }

    private static void onWorldTick(ServerWorld world) {
        // Iterate over all players in the world
        for (PlayerEntity player : world.getPlayers()) {
            // Ensure the player is a server-side player
            if (player instanceof ServerPlayerEntity) {
                // Check if the player is not dead and is not at full health
                if (player.isAlive() && player.getHealth() < player.getMaxHealth()) {

                    // Get the player's maximum health
                    float maxHealth = player.getMaxHealth();

                    // Base regeneration rate per second (e.g., 1 health point per second)
                    float baseRegenRatePerSecond = ((IEntityDataSaver) player).getPersistentData().getFloat("regen");

                    // Calculate regeneration per tick
                    float regenRatePerTick = baseRegenRatePerSecond / TICKS_PER_SECOND;

                    // Apply the regeneration
                    float currentHealth = player.getHealth();
                    float newHealth = Math.min(currentHealth + regenRatePerTick, maxHealth); // Ensure it doesn't exceed max health
                    player.setHealth(newHealth);
                }
            }
        }
    }
}
