package de.aeksora.minemmo.networking;

import de.aeksora.minemmo.networking.packet.XpSyncDataS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class ModMessages {
    public static void registerS2CPackages() {
        ClientPlayNetworking.registerGlobalReceiver(MineMMONetworkingConstants.XP_PACKET_ID, XpSyncDataS2CPacket::receive);
    }
}
