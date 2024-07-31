package de.aeksora.minemmo.networking;

import net.minecraft.util.Identifier;

public class MineMMONetworkingConstants {

    // S2C
    public static final Identifier XP_PACKET_ID = new Identifier("minemmo", "xp_sync");
    public static final Identifier LEVEL_PACKET_ID = new Identifier("minemmo", "level");
    public static final Identifier GAD_PACKET_2C_ID = new Identifier("minemmo", "gad_sync");
    public static final Identifier GMH_PACKET_2C_ID = new Identifier("minemmo", "gmh_sync");
    public static final Identifier GMS_PACKET_2C_ID = new Identifier("minemmo", "gms_sync");

    // C2S
    public static final Identifier GAD_PACKET_2S_ID = new Identifier("minemmo", "gad_2s");
    public static final Identifier GMH_PACKET_2S_ID = new Identifier("minemmo", "gmh_2s");
    public static final Identifier GMS_PACKET_2S_ID = new Identifier("minemmo", "gms_2s");
}
