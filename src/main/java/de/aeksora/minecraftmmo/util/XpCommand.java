package de.aeksora.minecraftmmo.util;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class XpCommand {
    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> {
            dispatcher.register(
                    CommandManager.literal("getxp")
                            .executes(XpCommand::getXp)
            );
        }));

        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("addxp")
                    .then(
                            CommandManager
                                    .argument("amount", IntegerArgumentType.integer())
                                    .executes(XpCommand::addXp)
                    )
            );
        }));
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
}
