package moe.ingstar.enchant;

import moe.ingstar.enchant.Encantment.EnchantConfigs.*;
import moe.ingstar.enchant.Encantment.ModEnchantments;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoreEnchantments implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("more_enchantments");
	public static final String MOD_ID = "more_enchantments";

	@Override
	public void onInitialize() {
		ModEnchantments.registerEnchantments();

		MidasTouchHandler.initialize();
		DiamondLuckHandler.initialize();
		LeechHandler.initialize();
		HealthBoostHandler.initialize();
	}
}