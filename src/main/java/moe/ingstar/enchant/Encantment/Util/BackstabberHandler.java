package moe.ingstar.enchant.Encantment.Util;

import moe.ingstar.enchant.Registry.ModEnchantments;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;

import net.minecraft.entity.player.PlayerEntity;

import net.minecraft.item.ItemStack;

import net.minecraft.util.ActionResult;

import net.minecraft.util.math.Vec3d;

public class BackstabberHandler {
    private static Vec3d entityPos;
    private static Vec3d playerPos;
    private static Vec3d entityToPlayer;
    private static Vec3d entityForward;
    private static double angle;

    private static final double threshold = 45.0;
    private static boolean isCharging = false;
    private static int chargeTicks = 0;


    public static void initialize() {
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (hasRequiredEnchantment(player)) {
                LivingEntity livingEntity = (LivingEntity) entity;

                entityPos = livingEntity.getPos();
                playerPos = player.getPos();

                entityToPlayer = playerPos.subtract(entityPos).normalize();
                entityForward = livingEntity.getRotationVector();

                angle = Math.toDegrees(Math.acos(entityForward.dotProduct(entityToPlayer)));

                ItemStack weaponStack = player.getStackInHand(hand);

                int sharpnessLevel = EnchantmentHelper.getLevel(Enchantments.SHARPNESS, weaponStack);
                float enchantmentBonus = (float) (sharpnessLevel * 0.5 + 0.5);

                float attackSpeed = getAttackSpeed(player);
                float attackCooldown = player.getAttackCooldownProgress(attackSpeed);

                if (isBehind() && attackCooldown >= 1) {
                    float damage = getAttackDamage(player);
                    float additionalDamage = damage * 0.4F;
                    float totalDamage = sharpnessLevel > 0 ? damage + additionalDamage + enchantmentBonus : damage + additionalDamage;

                    livingEntity.damage(player.getDamageSources().mobAttack(player), totalDamage);
                }
            }
            return ActionResult.PASS;
        });
    }

    private static boolean isBehind() {
        return angle > (180.0 - threshold) && angle < (180.0 + threshold);
    }

    private static float getAttackDamage(PlayerEntity player) {
        EntityAttributeInstance attributeInstance = player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);

        if (attributeInstance != null) {
            double finalValue = attributeInstance.getValue();
            return (float) finalValue;
        }
        return 0.0F;
    }

    private static float getAttackSpeed(PlayerEntity player) {
        EntityAttributeInstance attributeInstance = player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_SPEED);

        if (attributeInstance != null) {
            double finalValue = attributeInstance.getValue();
            return (float) finalValue;
        }
        return 0.0F;
    }

    private static boolean hasRequiredEnchantment(PlayerEntity player) {
        for (ItemStack itemStack : player.getArmorItems()) {
            if (EnchantmentHelper.getLevel(ModEnchantments.BACKSTABBER, itemStack) > 0) {
                return true;
            }
        }
        return false;
    }
}
