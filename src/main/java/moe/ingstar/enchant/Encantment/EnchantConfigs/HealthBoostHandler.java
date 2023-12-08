package moe.ingstar.enchant.Encantment.EnchantConfigs;

import moe.ingstar.enchant.Encantment.ModEnchantments;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.Objects;

public class HealthBoostHandler {
    public static void initialize() {
        ServerEntityEvents.EQUIPMENT_CHANGE.register((livingEntity, equipmentSlot, previous, next) -> {
            if (livingEntity instanceof PlayerEntity) {
                if (equipmentSlot == EquipmentSlot.CHEST) {
                    if (hasCustomEnchantment(next)) {
                        int level = EnchantmentHelper.getLevel(ModEnchantments.HEALTH_BOOST_ARMOR, next);
                        if (level > 0) {
                            Objects.requireNonNull(livingEntity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(livingEntity.defaultMaxHealth + (level * 2) * 2);
                        }
                    } else {
                        Objects.requireNonNull(livingEntity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(livingEntity.defaultMaxHealth);
                    }
                }
            }
        });
    }

    private static boolean hasCustomEnchantment(ItemStack stack) {
        var enchantments = EnchantmentHelper.get(stack);
        int customEnchantmentLevel = enchantments.getOrDefault(ModEnchantments.HEALTH_BOOST_ARMOR, 0);

        return customEnchantmentLevel >= 1;
    }
}







