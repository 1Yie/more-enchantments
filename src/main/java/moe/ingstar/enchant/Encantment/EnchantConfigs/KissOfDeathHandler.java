package moe.ingstar.enchant.Encantment.EnchantConfigs;

import moe.ingstar.enchant.Encantment.ModEnchantments;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;

import java.util.List;
import java.util.Random;

public class KissOfDeathHandler {

    public static void initialize() {
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
            if (entity instanceof LivingEntity && damageSource.getAttacker() instanceof PlayerEntity) {
                LivingEntity livingEntity = (LivingEntity) entity;
                PlayerEntity player = (PlayerEntity) damageSource.getAttacker();

                ItemStack heldItem = player.getMainHandStack();
                Enchantment specificEnchantment = ModEnchantments.KISS_OF_DEATH;
                if (specificEnchantment != null && EnchantmentHelper.getLevel(specificEnchantment, heldItem) > 0) {
                    StatusEffect randomDebuff = getRandomDebuff();
                    applyRandomDebuff(livingEntity, randomDebuff);
                    spreadDebuff(livingEntity, randomDebuff);
                }
            }
        });
    }

    private static StatusEffect getRandomDebuff() {
        List<StatusEffect> debuffs = Registries.STATUS_EFFECT.stream()
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
