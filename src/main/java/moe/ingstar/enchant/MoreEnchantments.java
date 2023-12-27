package moe.ingstar.enchant;

import moe.ingstar.enchant.Encantment.EnchantConfigs.*;
import moe.ingstar.enchant.Encantment.ModEnchantments;
import moe.ingstar.enchant.Item.ItemRegister;
import moe.ingstar.enchant.StatusEffect.BuffEffectRegistry;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoreEnchantments implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("more_enchantments");
	public static final String MOD_ID = "more_enchantments";

	@Override
	public void onInitialize() {
		ModEnchantments.registerEnchantments();
		ItemRegister.register();

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

		BuffEffectRegistry.register();
	}
}