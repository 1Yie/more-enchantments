package moe.ingstar.enchant.Encantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class ArrowShieldEnchant extends Enchantment {
    public ArrowShieldEnchant(Rarity rarity, EnchantmentTarget target, EquipmentSlot[] slotTypes) {
        super(rarity, target, slotTypes);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMaxPower(int level) {
        return super.getMaxPower(level) * 16;
    }

    @Override
    public int getMinPower(int level) {
        return super.getMinPower(level);
    }


    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof ArmorItem armorItem && armorItem.getSlotType() == EquipmentSlot.HEAD;
    }

    @Override
    public void onUserDamaged(LivingEntity user, Entity attacker, int level) {
        if (attacker instanceof LivingEntity) {
            Random random = new Random();
            int baseProbability = 20;
            int levelMultiplier = baseProbability * level;

            if (random.nextInt(100) + 1 <= levelMultiplier) {
                if (isFatalDamage(user.getRecentDamageSource())) {
                    user.addStatusEffect(new StatusEffectInstance(
                            StatusEffects.RESISTANCE,
                            200,
                            1,
                            false,
                            false
                    ));
                }
            }
        }
    }

    private static boolean isFatalDamage(DamageSource damageSource) {
        return damageSource != null && damageSource.isOf(DamageTypes.ARROW);
    }
}
