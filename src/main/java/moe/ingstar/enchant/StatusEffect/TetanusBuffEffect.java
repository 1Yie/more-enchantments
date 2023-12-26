package moe.ingstar.enchant.StatusEffect;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Random;

public class TetanusBuffEffect extends StatusEffect {
    protected TetanusBuffEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof LivingEntity) {
            Random random = new Random();

            if (amplifier > 0 && amplifier <= 255) {
                int minDamage = 0;
                int damage = minDamage + random.nextInt((amplifier - minDamage) + 1);

                entity.damage(entity.getDamageSources().generic(), damage);
            }

            if (amplifier == 0) {
                entity.damage(entity.getDamageSources().generic(), 0.5F);
            }

        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
