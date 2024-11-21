package de.aeksora.minemmo.networking;

import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class MineMMONetworkingConstants {

    // S2C
    /**
     * Used to sync xp from server to client
     */
    public static final Identifier XP_PACKET_ID = new Identifier("minemmo", "xp_sync");

    /**
     * Used to sync level from server to client
     */
    public static final Identifier LEVEL_PACKET_ID = new Identifier("minemmo", "level");

    /**
     * Used to sync regen from server to client
     */
    public static final Identifier REGEN_PACKET_ID = new Identifier("minemmo", "regen");

    /**
     * Used to sync mining speed from server to client
     */
    public static final Identifier MININGSPEED_PACKET_ID = new Identifier("minemmo", "miningspeed");

    /**
     * Used to sync strength from server to client
     */
    public static final Identifier GAD_PACKET_2C_ID = new Identifier("minemmo", "gad_sync");

    /**
     * Used to sync health from server to client
     */
    public static final Identifier GMH_PACKET_2C_ID = new Identifier("minemmo", "gmh_sync");

    /**
     * Used to sync speed from server to client
     */
    public static final Identifier GMS_PACKET_2C_ID = new Identifier("minemmo", "gms_sync");

    // C2S
    public static final Identifier GAD_PACKET_2S_ID = new Identifier("minemmo", "gad_2s");
    public static final Identifier GAD_PACKET_2S_LIMIT_ID = new Identifier("minemmo", "gad_limit_2s");
    public static final Identifier GMH_PACKET_2S_ID = new Identifier("minemmo", "gmh_2s");
    public static final Identifier GMH_PACKET_2S_LIMIT_ID = new Identifier("minemmo", "gmh_limit_2s");
    public static final Identifier GMS_PACKET_2S_ID = new Identifier("minemmo", "gms_2s");
    public static final Identifier GMS_PACKET_2S_LIMIT_ID = new Identifier("minemmo", "gms_limit_2s");
    public static final Identifier REGEN_PACKET_2S_ID = new Identifier("minemmo", "regen_2s");
    public static final Identifier REGEN_PACKET_2S_LIMIT_ID = new Identifier("minemmo", "regen_limit_2s");
    public static final Identifier MININGSPEED_PACKET_2S_ID = new Identifier("minemmo", "miningspeed_2s");
    public static final Identifier MININGSPEED_PACKET_2S_LIMIT_ID = new Identifier("minemmo", "miningspeed_limit_2s");
}
