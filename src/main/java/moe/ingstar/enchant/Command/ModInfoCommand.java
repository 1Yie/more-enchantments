package moe.ingstar.enchant.Command;

import com.google.common.collect.Lists;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.Formatting;

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
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.life_steal.title").copy().formatted(Formatting.BOLD));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.life_steal.context"));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.none"));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.thump.title").copy().formatted(Formatting.BOLD));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.thump.context"));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.none"));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.saturation.title").copy().formatted(Formatting.BOLD));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.saturation.context"));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.none"));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.soul_penetration.title").copy().formatted(Formatting.BOLD));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.soul_penetration.context"));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.none"));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.disarm.title").copy().formatted(Formatting.BOLD));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.disarm.context"));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.none"));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.leech.title").copy().formatted(Formatting.BOLD));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.leech.context"));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.none"));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.diamond_luck.title").copy().formatted(Formatting.BOLD));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.diamond_luck.context"));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.none"));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.health_boost_armor.title").copy().formatted(Formatting.BOLD));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.health_boost_armor.context"));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.none"));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.midas_touch.title").copy().formatted(Formatting.BOLD));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.midas_touch.context"));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.none"));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.arrow_shield.title").copy().formatted(Formatting.BOLD));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.arrow_shield.context"));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.none"));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.kiss_of_death.title").copy().formatted(Formatting.BOLD));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.kiss_of_death.context"));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.none"));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.container_of_heart.title").copy().formatted(Formatting.BOLD));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.container_of_heart.context"));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.none"));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.benevolence.title").copy().formatted(Formatting.BOLD));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.benevolence.context"));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.none"));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.benevolence.title").copy().formatted(Formatting.BOLD));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.benevolence.context"));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.none"));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.absolute_immunity.title").copy().formatted(Formatting.BOLD));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.absolute_immunity.context"));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.none"));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.death_as_home.title").copy().formatted(Formatting.BOLD));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.death_as_home.context"));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.none"));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.area_destruction.title").copy().formatted(Formatting.BOLD));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.area_destruction.context"));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.none"));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.backstabber.title").copy().formatted(Formatting.BOLD));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.backstabber.context"));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.none"));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.lostcurse.title").copy().formatted(Formatting.BOLD));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.lostcurse.context"));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.none"));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.seacurse.title").copy().formatted(Formatting.BOLD));
        source.sendMessage(Text.translatable("command.more_enchantment.me.info.enchant.seacurse.context"));

    }
}
