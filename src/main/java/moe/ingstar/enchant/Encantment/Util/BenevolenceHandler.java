package moe.ingstar.enchant.Encantment.Util;

import moe.ingstar.enchant.Registry.ModEnchantments;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;

public class BenevolenceHandler {
    public static void initialize() {
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (entity instanceof LivingEntity) {
                LivingEntity targetEntity = (LivingEntity) entity;
                ItemStack mainHandItem = player.getMainHandStack();

                if (hasRequiredEnchantment(mainHandItem)) {
                    float damageAmount = getAttackDamage(player);
                    targetEntity.heal(damageAmount);
                }
            }
            return ActionResult.PASS;
        });
    }

    private static float getAttackDamage(PlayerEntity player) {
        ItemStack mainHandItem = player.getMainHandStack();

        float itemAttackDamage = (float) player.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        return hasRequiredEnchantment(mainHandItem) ? itemAttackDamage : 0.0f;
    }

    private static boolean hasRequiredEnchantment(ItemStack itemStack) {
        return EnchantmentHelper.getLevel(ModEnchantments.BENEVOLENCE, itemStack) > 0;
    }
}
