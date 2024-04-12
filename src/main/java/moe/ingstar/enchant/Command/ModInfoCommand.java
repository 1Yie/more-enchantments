package moe.ingstar.enchant.Command;

import com.google.common.collect.Lists;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.Formatting;

import java.awt.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class ModInfoCommand {

    public static void initialize() {

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            CompletableFuture.runAsync(() -> {
                try {
                    CompletableFuture.delayedExecutor(2000, TimeUnit.MILLISECONDS).execute(() -> {
                    server.getPlayerManager().getPlayerList().forEach(ModInfoCommand::sendWelcomeMessage);
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });

        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            register(server.getCommandManager().getDispatcher());
        });
    }

    private static void sendWelcomeMessage(ServerPlayerEntity player) {
        Text send_1 = Text.translatable("command.more_enchantment.me.welcome.title").copy().formatted(Formatting.BOLD).formatted(Formatting.YELLOW);
        Text send_2 = Text.translatable("command.more_enchantment.me.welcome.context");
        Text combinedText = Texts.join(Lists.newArrayList(send_1, send_2), Texts.toText(Text.of(" ")));
        player.sendMessage(combinedText);
    }

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("me:info")
                .executes(context -> {
                    sendInfo(context.getSource());
                    return 1;
                }));

        dispatcher.register(CommandManager.literal("me:enchant")
                .executes(context -> {
                    sendInfoToEnchant(context.getSource());
                    return 1;
                }));
    }

    private static void sendInfo(ServerCommandSource source) {
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.title").copy().formatted(Formatting.BOLD).formatted(Formatting.BLUE));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.context").copy().formatted(Formatting.BOLD).formatted(Formatting.ITALIC));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.context.me.info").copy().formatted(Formatting.BOLD));
    }

    private static void sendInfoToEnchant(ServerCommandSource source) {
        source.sendMessage(Text.translatable("command.more_enchantment.enchant.context").formatted(Formatting.BOLD).formatted(Formatting.BLUE));
        source.sendMessage(Text.translatable("command.more_enchantment.website").setStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,"https://wiki.ingstar.moe/mods/me.html")).withBold(true)));
    }
}
