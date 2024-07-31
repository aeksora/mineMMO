package de.aeksora.minemmo.util;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import de.aeksora.minemmo.MineMMO;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Objects;

public class XpCommand {

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
                        .then(CommandManager.literal("reset").executes(XpCommand::reset))
        )));
    }

    private static int getStats(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();

        if (source.getEntity() instanceof ServerPlayerEntity player) {
            player.sendMessage(Text.literal(
                    "Strength: " + Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE)).getBaseValue()
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
            StatModifier.addStrength(player, amount);
        }

        return Command.SINGLE_SUCCESS;
    }

    private static int resetStrength(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();

        if (source.getEntity() instanceof ServerPlayerEntity player) {
            double strength = Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE)).getBaseValue();
            int statLevel = (int) Math.round((strength - 1) / MineMMO.STRENGTH_PER_LEVEL);

            Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE)).setBaseValue(1);

            LevelData.setLevel(player, LevelData.getLevel(player) - statLevel);

            StatModifier.syncAtributes(player);
        }

        return Command.SINGLE_SUCCESS;
    }

    private static int addHealth(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        int amount = IntegerArgumentType.getInteger(context, "amount");

        if (source.getEntity() instanceof ServerPlayerEntity player) {
            StatModifier.addHealth(player, amount);
        }

        return Command.SINGLE_SUCCESS;
    }

    private static int resetHealth(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();

        if (source.getEntity() instanceof ServerPlayerEntity player) {
            double health = Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).getBaseValue();
            int statLevel = (int) Math.round((health - 20) / MineMMO.HEALTH_PER_LEVEL);

            Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(20);

            LevelData.setLevel(player, LevelData.getLevel(player) - statLevel);

            StatModifier.syncAtributes(player);
        }

        return Command.SINGLE_SUCCESS;
    }

    private static int addSpeed(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        int amount = IntegerArgumentType.getInteger(context, "amount");

        if (source.getEntity() instanceof ServerPlayerEntity player) {
            StatModifier.addSpeed(player, amount);
        }

        return Command.SINGLE_SUCCESS;
    }

    private static int resetSpeed(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();

        if (source.getEntity() instanceof ServerPlayerEntity player) {
            double speed = Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)).getBaseValue();
            int statLevel = (int) Math.round((speed - 0.1) / MineMMO.SPEED_PER_LEVEL);
            Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)).setBaseValue(0.1);

            LevelData.setLevel(player, LevelData.getLevel(player) - statLevel);

            StatModifier.syncAtributes(player);
        }

        return Command.SINGLE_SUCCESS;
    }

    private static int reset(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();

        if (source.getEntity() instanceof ServerPlayerEntity player) {
            Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE)).setBaseValue(1);
            Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(20);
            Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)).setBaseValue(0.1);
            ((IEntityDataSaver) player).getPersistentData().putFloat("regen", 0.0f);

            ((IEntityDataSaver) player).getPersistentData().putInt("level", 0);

            StatModifier.syncAtributes(player);
        }

        return Command.SINGLE_SUCCESS;
    }
}
