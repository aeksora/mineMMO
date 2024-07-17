package de.aeksora.minemmo.networking;

import de.aeksora.minemmo.networking.packet.SyncDataC2SPacket;
import de.aeksora.minemmo.networking.packet.SyncDataS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class ModMessages {
    public static void registerS2CPackages() {
        // S2C
        ClientPlayNetworking.registerGlobalReceiver(MineMMONetworkingConstants.XP_PACKET_ID, SyncDataS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(MineMMONetworkingConstants.GAD_PACKET_2C_ID, SyncDataS2CPacket::setGAD);
        ClientPlayNetworking.registerGlobalReceiver(MineMMONetworkingConstants.GMH_PACKET_2C_ID, SyncDataS2CPacket::setGMH);
        ClientPlayNetworking.registerGlobalReceiver(MineMMONetworkingConstants.GMS_PACKET_2C_ID, SyncDataS2CPacket::setGMS);
    }

    public static void registerC2SPackages() {
        // C2S
        ServerPlayNetworking.registerGlobalReceiver(MineMMONetworkingConstants.GAD_PACKET_2S_ID, SyncDataC2SPacket::addGAD2S);
        ServerPlayNetworking.registerGlobalReceiver(MineMMONetworkingConstants.GMH_PACKET_2S_ID, SyncDataC2SPacket::addGMH2S);
        ServerPlayNetworking.registerGlobalReceiver(MineMMONetworkingConstants.GMS_PACKET_2S_ID, SyncDataC2SPacket::addGMS2S);
    }
}
