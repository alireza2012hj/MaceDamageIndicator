package org.alirezahj.maceDamageIndicator;

import com.mojang.brigadier.Command;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.List;

public class CommandManager {
    public static void registerCommands(MaceDamageIndicator plugin) {
        plugin.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,event -> {

            final Commands commands = event.registrar();
            commands.register(
                    Commands.literal("macedamageindicator")
                        .then(
                            Commands.literal("reload")
                                    .requires(source -> source.getSender().hasPermission("macedamageindicator"))
                                    .executes(ctx -> {
                                        plugin.reloadConfig();
                                        ctx.getSource().getSender().sendMessage(
                                                LegacyComponentSerializer.legacySection().deserialize("§7[§6MaceDamageIndicator§7] §r§aConfig Reloaded!!")
                                        );
                                        return Command.SINGLE_SUCCESS;
                                    })
                            )

                        .then(
                           Commands.literal("toggle")
                                   .requires(source -> source.getSender().hasPermission("macedamageindicator"))
                                   .executes(ctx -> {
                                       boolean enabled = !plugin.getConfig().getBoolean("enabled", true);
                                       String status = enabled? "§7[§6MaceDamageIndicator§7] §r§aEnabled!" : "§7[§6MaceDamageIndicator§7] §r§cDisabled :(";

                                       Helpers.setEnabledPersisted(enabled, plugin);
                                       ctx.getSource().getSender().sendMessage(
                                               LegacyComponentSerializer.legacySection().deserialize(status)
                                       );

                                       return Command.SINGLE_SUCCESS;
                                   })
                        )


                            .build(),
                "Manage MaceDamageIndicator stuff",
                List.of("mdi")
            );

        });
    }
}