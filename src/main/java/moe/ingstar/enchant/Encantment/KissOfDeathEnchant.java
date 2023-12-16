package moe.ingstar.enchant.Encantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;

import net.minecraft.util.registry.Registry;

import java.util.List;
import java.util.Random;

public class KissOfDeathEnchant extends Enchantment {

    protected KissOfDeathEnchant(Rarity rarity, EnchantmentTarget target, EquipmentSlot[] slotTypes) {
        super(rarity, target, slotTypes);
    }

    @Override
    public int getMinPower(int level) {
        return level;
    }

    @Override
    public int getMaxPower(int level) {
        return level * 6;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }


    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (target instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) target;
            PlayerEntity player = (PlayerEntity) user;

            if (level > 0 && ((LivingEntity) target).isDead()) {
                StatusEffect randomDebuff = getRandomDebuff();
                applyRandomDebuff(livingEntity, randomDebuff);
                spreadDebuff(livingEntity, randomDebuff);
            }
        }
    }

    private static StatusEffect getRandomDebuff() {
        List<StatusEffect> debuffs = Registry.STATUS_EFFECT.stream()
                .filter(statusEffect -> !statusEffect.isBeneficial())
                .toList();

        if (!debuffs.isEmpty()) {
            Random random = new Random();
            return debuffs.get(random.nextInt(debuffs.size()));
        }

        return null;
    }


    private static void applyRandomDebuff(LivingEntity livingEntity, StatusEffect debuff) {
        if (debuff != null) {
            int duration = getEffectDuration(debuff);
            int amplifier = getEffectAmplifier(debuff);

            livingEntity.addStatusEffect(new StatusEffectInstance(debuff, duration, amplifier));
        }
    }

    private static void spreadDebuff(LivingEntity sourceEntity, StatusEffect debuff) {
        sourceEntity.getWorld().getEntitiesByClass(LivingEntity.class, sourceEntity.getBoundingBox().expand(10.0D),
                        entity -> entity.getClass() == sourceEntity.getClass())
                .forEach(entity -> applyRandomDebuff(entity, debuff));
    }


    private static int getRandomDuration() {
        Random random = new Random();
        return random.nextInt(400) + 100;
    }

    private static int getRandomAmplifier() {
        Random random = new Random();
        return random.nextInt(3) + 1;
    }

    private static int getEffectDuration(StatusEffect effect) {
        return effect.isInstant() ? 10 : getRandomDuration();
    }

    private static int getEffectAmplifier(StatusEffect effect) {
        return effect.isInstant() ? 0 : getRandomAmplifier();
    }
}

