package moe.ingstar.enchant.StatusEffect;

import moe.ingstar.enchant.ModDamageSource.TetanusDamageSource;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Random;

public class TetanusBuffEffect extends StatusEffect {
    public TetanusBuffEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof LivingEntity) {
            Random random = new Random();

            if (amplifier >= 0 && amplifier <= 255) {
                int minDamage = 0;
                int damage = minDamage + random.nextInt((amplifier - minDamage) + 1);

                entity.damage(new TetanusDamageSource(entity, null), damage);

                if (entity instanceof PlayerEntity) {
                    TetanusDamageSource.playSound((PlayerEntity) entity);
                }
            }


        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
