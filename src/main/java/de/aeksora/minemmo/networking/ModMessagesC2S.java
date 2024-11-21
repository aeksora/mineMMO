package de.aeksora.minemmo.networking;

import de.aeksora.minemmo.networking.packet.SyncDataC2SPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class ModMessagesC2S {

    public static void registerC2SPackages() {
        // C2S
        ServerPlayNetworking.registerGlobalReceiver(MineMMONetworkingConstants.STRENGTH_PACKET_ID, SyncDataC2SPacket::addGAD2S);
        ServerPlayNetworking.registerGlobalReceiver(MineMMONetworkingConstants.STRENGTH_LIMIT_ID, SyncDataC2SPacket::setGAD2S);
        ServerPlayNetworking.registerGlobalReceiver(MineMMONetworkingConstants.HEALTH_PACKET_ID, SyncDataC2SPacket::addGMH2S);
        ServerPlayNetworking.registerGlobalReceiver(MineMMONetworkingConstants.HEALTH_LIMIT_ID, SyncDataC2SPacket::setGMH2S);
        ServerPlayNetworking.registerGlobalReceiver(MineMMONetworkingConstants.SPEED_PACKET_ID, SyncDataC2SPacket::addGMS2S);
        ServerPlayNetworking.registerGlobalReceiver(MineMMONetworkingConstants.SPEED_LIMIT_ID, SyncDataC2SPacket::setGMS2S);
        ServerPlayNetworking.registerGlobalReceiver(MineMMONetworkingConstants.REGEN_PACKET_ID, SyncDataC2SPacket::addRegen2S);
        ServerPlayNetworking.registerGlobalReceiver(MineMMONetworkingConstants.REGEN_LIMIT_ID, SyncDataC2SPacket::setRegen2S);
        ServerPlayNetworking.registerGlobalReceiver(MineMMONetworkingConstants.MININGSPEED_PACKET_ID, SyncDataC2SPacket::addMining2S);
        ServerPlayNetworking.registerGlobalReceiver(MineMMONetworkingConstants.MININGSPEED_LIMIT_ID, SyncDataC2SPacket::setMining2S);
    }

}
