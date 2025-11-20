package de.aeksora.minemmo.event;

import de.aeksora.minemmo.MineMMO;
import de.aeksora.minemmo.util.LevelData;
import de.aeksora.minemmo.util.StatHelper;
import de.aeksora.minemmo.util.StatModifier;
import de.aeksora.minemmo.util.XpData;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class PlayerConnectEvent {
    public static final Logger LOGGER = LoggerFactory.getLogger(MineMMO.MOD_ID);

    public static void register() {
        ServerPlayConnectionEvents.JOIN.register(PlayerConnectEvent::onPlayerJoin);
    }

    private static void onPlayerJoin(ServerPlayNetworkHandler serverPlayNetworkHandler, PacketSender packetSender, MinecraftServer minecraftServer) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //sync xp and level
        ServerPlayerEntity player = serverPlayNetworkHandler.getPlayer();
        StatHelper helper = new StatHelper(player);

        XpData.syncXp(helper.getXp(), player);
        LevelData.syncLevel(helper.getLevel(), player);

        // Initialize mining speed if not already initialized
        if (helper.getMiningSpeed() < 1.0f) {
            helper.setMiningSpeed(1.0f);
            LOGGER.info("Set mining speed to 1.0");
        }

        // Initialize regen if not already initialized
        if (helper.getRegen() == 0.0f) {
            helper.setRegen(0.25f);
            LOGGER.info("Set regen to 0.25");
        }

        // sync attributes
        StatModifier.syncAtributes(player);
    }
}
