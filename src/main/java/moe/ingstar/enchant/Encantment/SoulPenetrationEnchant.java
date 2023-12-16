package moe.ingstar.enchant.Encantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;


public class SoulPenetrationEnchant extends Enchantment {

    protected SoulPenetrationEnchant(Rarity weight, EnchantmentTarget target, EquipmentSlot[] slotTypes) {
        super(weight, target, slotTypes);
    }

    @Override
    public int getMinPower(int level) {
        return level;
    }

    @Override
    public int getMaxPower(int level) {
        return super.getMinLevel() * 5;
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof SwordItem || stack.getItem() instanceof AxeItem;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (user instanceof PlayerEntity && target instanceof LivingEntity) {
            float armorValue = getTargetArmorValue(target);

            if (armorValue > 0 && level >= 1 && level <= 4) {
                float damageIncreasePercentage = calculateDamageIncreasePercentage(user, target, level, armorValue);
                target.damage(user.getDamageSources().mobAttack(user), damageIncreasePercentage);
            }
        }
    }

    private float getTargetArmorValue(Entity target) {
        EntityAttributeInstance attributeInstance = ((LivingEntity) target).getAttributeInstance(EntityAttributes.GENERIC_ARMOR);
        return attributeInstance != null ? (float) attributeInstance.getValue() : 0.0F;
    }

    private float calculateDamageIncreasePercentage(LivingEntity user, Entity target, int level, float armorValue) {
        ItemStack heldItem = user.getMainHandStack();
        ToolItem toolItem = (ToolItem) heldItem.getItem();
        float attackDamage = toolItem.getMaterial().getAttackDamage() +
                EnchantmentHelper.getAttackDamage(heldItem, EntityGroup.DEFAULT);

        float percentage = level * 0.01F + 0.10F;

        return attackDamage * armorValue * percentage;
    }

}