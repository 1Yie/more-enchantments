package moe.ingstar.enchant.Encantment.EnchantConfigs;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public class LeechHandler {
    public static void initialize() {
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (entity instanceof LivingEntity) {
                float damage = getAttackDamage(player);
                DamageData.setDamage(damage);
            }
            return ActionResult.PASS;
        });
    }

    private static float getAttackDamage(PlayerEntity player) {
        EntityAttributeInstance attributeInstance = player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);

        if (attributeInstance != null) {
            double finalValue = attributeInstance.getValue();
            return (float) finalValue;
        }

        return 0.0F;
    }

}