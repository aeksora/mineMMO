package de.aeksora.minemmo.util;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import de.aeksora.minemmo.MineMMO;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class XpCommand {
    public static final Logger LOGGER = LoggerFactory.getLogger(MineMMO.MOD_ID);

    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> dispatcher.register(
                CommandManager.literal("minemmoxp")
                        .then(CommandManager.literal("get").executes(XpCommand::getXp))
                        .then(CommandManager.literal("add")
                                .then(CommandManager.argument("amount", IntegerArgumentType.integer()).executes(XpCommand::addXp))
                        )
                        .then(CommandManager.literal("set")
                                .then(CommandManager.argument("amount", IntegerArgumentType.integer()).executes(XpCommand::setXp))
                        )
                        .then(CommandManager.literal("stats").executes(XpCommand::getStats)
                                .then(CommandManager.literal("strength")
                                        .then(CommandManager.argument("amount", IntegerArgumentType.integer()).executes(XpCommand::addStrength))
                                )
                                .then(CommandManager.literal("strengthreset").executes(XpCommand::resetStrength))
                                .then(CommandManager.literal("health")
                                        .then(CommandManager.argument("amount", IntegerArgumentType.integer()).executes(XpCommand::addHealth))
                                )
                                .then(CommandManager.literal("healthreset").executes(XpCommand::resetHealth))
                                .then(CommandManager.literal("speed")
                                        .then(CommandManager.argument("amount", IntegerArgumentType.integer()).executes(XpCommand::addSpeed))
                                )
                                .then(CommandManager.literal("speedreset").executes(XpCommand::resetSpeed))
                        )
        )));
    }

    private static int getStats(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();

        if (source.getEntity() instanceof ServerPlayerEntity player) {
            player.sendMessage(Text.literal(
                    "Strength: " + Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE)).getValue()
                            + "\nHealth: " + Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).getValue()
                            + "\nSpeed: " + Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)).getValue()
                    )
            );
        }

        return Command.SINGLE_SUCCESS;
    }

    private static int getXp(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();

        if (source.getEntity() instanceof ServerPlayerEntity player) {
            player.sendMessage(
                    Text.literal(String.valueOf(((IEntityDataSaver) player).getPersistentData().getInt("xp")))
            );
        }

        return Command.SINGLE_SUCCESS;
    }

    private static int addXp(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        int amount = IntegerArgumentType.getInteger(context, "amount");

        if (source.getEntity() instanceof ServerPlayerEntity player) {
            XpData.addXp((IEntityDataSaver) player, amount);
        }

        return Command.SINGLE_SUCCESS;
    }

    private static int setXp(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        int amount = IntegerArgumentType.getInteger(context, "amount");

        if (source.getEntity() instanceof ServerPlayerEntity player) {
            XpData.setXp((IEntityDataSaver) player, amount);
        }

        return Command.SINGLE_SUCCESS;
    }

    private static int addStrength(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        int amount = IntegerArgumentType.getInteger(context, "amount");

        if (source.getEntity() instanceof ServerPlayerEntity player) {
            EntityAttributeInstance strengthAttribute = player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);

            double strength = strengthAttribute != null ? strengthAttribute.getValue() : 0;
            LOGGER.info("Player strength start: " + strength);

            for (int i = 0; i < amount; i++) {
                if (XpData.removeXp((IEntityDataSaver) player, 1000)) {
                    Objects.requireNonNull(strengthAttribute).setBaseValue(strength + 1);
                    strength = strengthAttribute.getValue();
                    LOGGER.info("Player strength: " + strength);
                }
            }

            LOGGER.info("Player strength end: " + strength);

        }

        return Command.SINGLE_SUCCESS;
    }

    private static int resetStrength(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();

        if (source.getEntity() instanceof ServerPlayerEntity player) {
            Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE)).setBaseValue(1);
        }

        return Command.SINGLE_SUCCESS;
    }

    private static int addHealth(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        int amount = IntegerArgumentType.getInteger(context, "amount");

        if (source.getEntity() instanceof ServerPlayerEntity player) {
            EntityAttributeInstance healthAttribute = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);

            double health = healthAttribute != null ? healthAttribute.getValue() : 0;
            LOGGER.info("Player health start: " + health);

            for (int i = 0; i < amount; i++) {
                if (XpData.removeXp((IEntityDataSaver) player, 1000)) {
                    Objects.requireNonNull(healthAttribute).setBaseValue(health + 1);
                    health = healthAttribute.getValue();
                    LOGGER.info("Player health: " + health);
                }
            }

            player.setHealth(player.getMaxHealth());

            LOGGER.info("Player health end: " + health);

        }

        return Command.SINGLE_SUCCESS;
    }

    private static int resetHealth(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();

        if (source.getEntity() instanceof ServerPlayerEntity player) {
            Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(1);
        }

        return Command.SINGLE_SUCCESS;
    }

    private static int addSpeed(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        int amount = IntegerArgumentType.getInteger(context, "amount");

        if (source.getEntity() instanceof ServerPlayerEntity player) {
            EntityAttributeInstance speedAttribute = player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);

            double speed = speedAttribute != null ? speedAttribute.getValue() : 0;
            LOGGER.info("Player speed start: " + speed);


            for (int i = 0; i < amount; i++) {
                if (XpData.removeXp((IEntityDataSaver) player, 1000)) {
                    Objects.requireNonNull(speedAttribute).setBaseValue(speed + 0.001);
                    speed = speedAttribute.getValue();
                    LOGGER.info("Player speed: " + speed);
                }
            }

            LOGGER.info("Player speed end: " + speed);

        }

        return Command.SINGLE_SUCCESS;
    }

    private static int resetSpeed(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();

        if (source.getEntity() instanceof ServerPlayerEntity player) {
            Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)).setBaseValue(0.1);
        }

        return Command.SINGLE_SUCCESS;
    }
}
