package moe.ingstar.enchant;

import moe.ingstar.enchant.Command.ModInfoCommand;
import moe.ingstar.enchant.Encantment.EnchantConfigs.*;
import moe.ingstar.enchant.Encantment.ModEnchantments;
import moe.ingstar.enchant.Item.ItemRegister;
import moe.ingstar.enchant.StatusEffect.BuffEffectRegistry;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoreEnchantments implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("More Enchantments");
	public static final String MOD_ID = "more_enchantments";

	@Override
	public void onInitialize() {
		LOGGER.info("Loaded correctly. Mod's namespace is: " + MOD_ID);

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

		BuffEffectRegistry.register();
	}

}