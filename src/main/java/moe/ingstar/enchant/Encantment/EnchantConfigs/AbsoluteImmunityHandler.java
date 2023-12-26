package moe.ingstar.enchant.Encantment.EnchantConfigs;

import moe.ingstar.enchant.Encantment.ModEnchantments;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;


import java.util.Random;

public class AbsoluteImmunityHandler {
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
        for (ItemStack itemStack : player.getArmorItems()) {
            int level = EnchantmentHelper.getLevel(ModEnchantments.ABSOLUT_IMMUNITY, itemStack);
            if (random.nextDouble() < 0.1 * level) {
                player.sendMessage(Text.translatable("enchantment.more_enchantments.key.absolute_immunity").formatted(Formatting.YELLOW), true);
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

