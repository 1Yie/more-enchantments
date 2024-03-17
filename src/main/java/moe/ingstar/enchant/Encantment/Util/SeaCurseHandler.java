package moe.ingstar.enchant.Encantment.Util;

import moe.ingstar.enchant.ModDamageSource.SeaCurseDamageSource;
import moe.ingstar.enchant.Registry.ModEnchantments;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import net.minecraft.enchantment.EnchantmentHelper;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;

import net.minecraft.item.ItemStack;

public class SeaCurseHandler {
    public static void onInitialize() {
        ServerTickEvents.START_SERVER_TICK.register(server -> {
            server.getPlayerManager().getPlayerList().forEach(player -> {
                if (player.isTouchingWater() && (hasItemEnchant(player) || hasRequiredEnchantment(player))) {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 20, 2));
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 20, 3));
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20, 4));

                    player.damage(new SeaCurseDamageSource(player, null), 1.0F);
                    SeaCurseDamageSource.playSound(player);
                }
            });
        });
    }

    private static boolean hasItemEnchant(PlayerEntity player) {
        for (ItemStack itemStack : player.getInventory().main) {
            if (hasEnchantment(itemStack)) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasEnchantment(ItemStack itemStack) {
        return EnchantmentHelper.getLevel(ModEnchantments.SEACURSE, itemStack) > 0;
    }

    private static boolean hasRequiredEnchantment(PlayerEntity player) {
        for (ItemStack itemStack : player.getArmorItems()) {
            if (hasEnchantment(itemStack)) {
                return true;
            }
        }
        return false;
    }
}
