package moe.ingstar.enchant.Encantment.Util;

import moe.ingstar.enchant.ModDamageSource.BeheadDangerDamageSource;
import moe.ingstar.enchant.ModDamageSource.SoulPenetrationDamageSource;
import moe.ingstar.enchant.Registry.ModEnchantments;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;


public class BeheadHandler {
    private static double entityHealth = 0;
    private static double playerHealth = 0;
    public static void initialize() {
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            ItemStack heldItem = player.getMainHandStack();
            if (hasCustomEnchantment(heldItem)) {
                if (entity instanceof LivingEntity) {
                    entityHealth = ((LivingEntity) entity).getHealth();
                    double maxHealth = ((LivingEntity) entity).getMaxHealth();
                    double tenPercentOfMaxHealth = maxHealth * 0.15;

                    if (entityHealth <= tenPercentOfMaxHealth) {
                        entity.damage(player.getDamageSources().mobAttack(player), ((LivingEntity) entity).getMaxHealth() + 1);
                    }
                }

                if (entity instanceof PlayerEntity) {
                    playerHealth = ((PlayerEntity) entity).getHealth();
                    double maxHealth = ((PlayerEntity) entity).getMaxHealth();
                    double tenPercentOfMaxHealth = maxHealth * 0.1;

                    if (playerHealth <= tenPercentOfMaxHealth) {
                        entity.damage(new BeheadDangerDamageSource(entity, player), player.getMaxHealth() + 1);
                        BeheadDangerDamageSource.playSound(player);
                    }
                }
            }

            return ActionResult.PASS;
        });
    }

    private static boolean hasCustomEnchantment(ItemStack stack) {
        var enchantments = EnchantmentHelper.get(stack);
        int customEnchantmentLevel = enchantments.getOrDefault(ModEnchantments.BEHEAD, 0);

        return customEnchantmentLevel >= 1;
    }
}
