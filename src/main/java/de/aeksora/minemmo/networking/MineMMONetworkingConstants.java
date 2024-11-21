package de.aeksora.minemmo.networking;

import net.minecraft.util.Identifier;

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
    public static final Identifier STRENGTH_PACKET_ID = new Identifier("minemmo", "gad_sync");

    /**
     * Used to sync health from server to client
     */
    public static final Identifier HEALTH_PACKET_ID = new Identifier("minemmo", "gmh_sync");

    /**
     * Used to sync speed from server to client
     */
    public static final Identifier SPEED_PACKET_ID = new Identifier("minemmo", "gms_sync");

    public static final Identifier STRENGTH_LIMIT_ID = new Identifier("minemmo", "gad_limit_2s");
    public static final Identifier HEALTH_LIMIT_ID = new Identifier("minemmo", "gmh_limit_2s");
    public static final Identifier SPEED_LIMIT_ID = new Identifier("minemmo", "gms_limit_2s");
    public static final Identifier REGEN_LIMIT_ID = new Identifier("minemmo", "regen_limit_2s");
    public static final Identifier MININGSPEED_LIMIT_ID = new Identifier("minemmo", "miningspeed_limit_2s");
}
