package moe.ingstar.enchant.Encantment.EnchantConfigs;

import moe.ingstar.enchant.Encantment.ModEnchantments;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.enchantment.EnchantmentHelper;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AbsoluteImmunityHandler {
    private static final Map<PlayerEntity, Long> cooldownMap = new HashMap<>();
    public static void initialize() {

        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {
            if (entity instanceof PlayerEntity player) {
                if (entity.getAttacking() != null && hasRequiredEnchantment(player)) {
                    return !shouldImmune(player);
                }
            }
            return true;
        });
    }

    private static boolean shouldImmune(PlayerEntity player) {
        Random random = new Random();

        if (cooldownMap.containsKey(player)) {
            long lastTriggerTime = cooldownMap.get(player);
            long currentTime = player.getWorld().getTime();

            if (currentTime - lastTriggerTime < 20) {
                return false;
            }
        }

        for (ItemStack itemStack : player.getArmorItems()) {
            int level = EnchantmentHelper.getLevel(ModEnchantments.ABSOLUT_IMMUNITY, itemStack);
            if (random.nextDouble() < 0.15 * level) {
                player.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_SHIELD_BLOCK, SoundCategory.PLAYERS, 1.0f, 1.0f);
                player.getEquippedStack(EquipmentSlot.CHEST).damage(2, player, (p -> p.getEquippedStack(EquipmentSlot.CHEST)));
                cooldownMap.put(player, player.getWorld().getTime());

                return true;
            }
        }
        return false;
    }

    private static boolean hasRequiredEnchantment(PlayerEntity player) {
        for (ItemStack itemStack : player.getArmorItems()) {
            if (EnchantmentHelper.getLevel(ModEnchantments.ABSOLUT_IMMUNITY, itemStack) > 0) {
                return true;
            }
        }
        return false;
    }
}

