package moe.ingstar.enchant.Encantment;

import moe.ingstar.enchant.Registry.BuffEffectRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Random;

public class ConfusionEnchant extends Enchantment {
    public ConfusionEnchant(Rarity weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
        super(weight, type, slotTypes);
    }

    @Override
    public int getMinPower(int level) {
        return level;
    }

    @Override
    public int getMaxPower(int level) {
        return level * 10;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        Random random = new Random();
        if (random.nextInt(60 - (level * 10)) + 1 == 1) {
            LivingEntity entity = (LivingEntity) target;
            addEffect(entity);
        }
    }

    private void addEffect(LivingEntity player) {
        StatusEffectInstance attackBoostEffect = new StatusEffectInstance(BuffEffectRegistry.CONFUSION_EFFECT, 3 * 20, 0);
        player.addStatusEffect(attackBoostEffect);
    }
}
