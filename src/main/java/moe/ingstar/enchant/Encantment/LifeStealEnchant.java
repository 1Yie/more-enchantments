package moe.ingstar.enchant.Encantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;

import java.util.Random;

public class LifeStealEnchant extends Enchantment {
    protected LifeStealEnchant(Rarity weight, EnchantmentTarget target, EquipmentSlot[] slotTypes) {
        super(weight, target, slotTypes);
    }

    @Override
    public int getMinPower(int level) {
        return level;
    }

    @Override
    public int getMaxPower(int level) {
        return super.getMaxPower(level) * 7;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {

        if (target instanceof LivingEntity && !target.getWorld().isClient) {
            DamageSource recentDamageSource = ((LivingEntity) target).getRecentDamageSource();
            if (recentDamageSource != null && recentDamageSource.getAttacker() instanceof PlayerEntity) {
                Random random = new Random();

                if (level >= getMinLevel() && level <= getMaxLevel()) {
                    float baseHeal = level * 0.5f;
                    float additionalHeal = random.nextFloat() * (level + 0.5f);
                    float healAmount = baseHeal + additionalHeal;
                    user.heal(healAmount);
                }

                int baseTriggerChance = 100 - (level - 1) * 20;
                if (level >= getMinLevel() && level <= getMaxLevel() && random.nextInt(baseTriggerChance) + 1 == 1) {
                    user.heal(10);
                    user.sendSystemMessage(new TranslatableText("enchantment.more_enchantments.key.life_steal"), Util.NIL_UUID);
                }
            }
        }
    }
}