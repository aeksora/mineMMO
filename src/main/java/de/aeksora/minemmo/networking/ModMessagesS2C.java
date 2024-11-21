package de.aeksora.minemmo.networking;

import de.aeksora.minemmo.networking.packet.SyncDataS2CPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

@Environment(EnvType.CLIENT)
public class ModMessagesS2C {
    public static void registerS2CPackages() {
        // S2C
        ClientPlayNetworking.registerGlobalReceiver(MineMMONetworkingConstants.XP_PACKET_ID, SyncDataS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(MineMMONetworkingConstants.LEVEL_PACKET_ID, SyncDataS2CPacket::receiveLevel);
        ClientPlayNetworking.registerGlobalReceiver(MineMMONetworkingConstants.REGEN_PACKET_ID, SyncDataS2CPacket::receiveRegen);
        ClientPlayNetworking.registerGlobalReceiver(MineMMONetworkingConstants.STRENGTH_PACKET_ID, SyncDataS2CPacket::receiveStrength);
        ClientPlayNetworking.registerGlobalReceiver(MineMMONetworkingConstants.HEALTH_PACKET_ID, SyncDataS2CPacket::receiveHealth);
        ClientPlayNetworking.registerGlobalReceiver(MineMMONetworkingConstants.SPEED_PACKET_ID, SyncDataS2CPacket::receiveSpeed);
        ClientPlayNetworking.registerGlobalReceiver(MineMMONetworkingConstants.MININGSPEED_PACKET_ID, SyncDataS2CPacket::receiveMiningSpeed);
    }
}
