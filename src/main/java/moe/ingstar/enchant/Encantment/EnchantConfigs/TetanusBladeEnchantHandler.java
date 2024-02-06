package moe.ingstar.enchant.Encantment.EnchantConfigs;

import moe.ingstar.enchant.Encantment.ModEnchantments;
import moe.ingstar.enchant.StatusEffect.BuffEffectRegistry;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;

public class TetanusBladeEnchantHandler {
    public static void initialize() {
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (entity instanceof LivingEntity) {
                ItemStack itemStack = player.getMainHandStack();

                if (hasRequiredEnchantment(itemStack)) {
                    if (world.random.nextInt(50) < 1) {
                        ((LivingEntity) entity).addStatusEffect(
                                new StatusEffectInstance(
                                        BuffEffectRegistry.TETANUS_BUFF_EFFECT,
                                        4 * 20,
                                        3,
                                        true,
                                        true
                                ));
                    }
                }
            }
            return ActionResult.PASS;
        });
    }

    private static boolean hasRequiredEnchantment(ItemStack itemStack) {
        return EnchantmentHelper.getLevel(ModEnchantments.TETANUS_BLADE, itemStack) > 0;
    }
}
