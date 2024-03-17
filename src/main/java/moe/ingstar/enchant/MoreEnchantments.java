package moe.ingstar.enchant;

import moe.ingstar.enchant.Command.ModInfoCommand;
import moe.ingstar.enchant.Encantment.Util.*;
import moe.ingstar.enchant.Registry.ItemRegister;
import moe.ingstar.enchant.Registry.BuffEffectRegistry;
import moe.ingstar.enchant.Registry.ModEnchantments;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoreEnchantments implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("More Enchantments");
	public static final String MOD_ID = "more_enchantments";

	@Override
	public void onInitialize() {
		LOGGER.info("Loaded correctly. Mod's namespace is: " + MOD_ID);

		ClientTickEvents.START_CLIENT_TICK.register(client -> {
		
			MinecraftClient mc = MinecraftClient.getInstance();
			Window window = mc.getWindow();
			if (window != null) {
				long windowHandle = window.getHandle();
				ConfusionHandler.initialize(mc, windowHandle);
			}
		});

		ModEnchantments.registerEnchantments();
		ItemRegister.register();

		ModInfoCommand.initialize();
		MidasTouchHandler.initialize();
		DiamondLuckHandler.initialize();
		LeechHandler.initialize();
		HealthBoostHandler.initialize();
		KissOfDeathHandler.initialize();
		BenevolenceHandler.initialize();
		ContainerOfHeartHandler.initialize();
		TetanusBladeEnchantHandler.initialize();
		AbsoluteImmunityHandler.initialize();
		DeathAsHomeHandler.initialize();
		AreaDestructionHandler.initialize();
		BackstabberHandler.initialize();
		LostCurseHandler.onInitialize();
		SeaCurseHandler.onInitialize();
		ComingDamageHandler.onInitialize();

		BuffEffectRegistry.register();
	}
}