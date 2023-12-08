package moe.ingstar.enchant.Encantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;


public class DisarmEnchant extends Enchantment {
    protected DisarmEnchant(Rarity weight, EnchantmentTarget target, EquipmentSlot[] slotTypes) {
        super(weight, target, slotTypes);
    }

    @Override
    public int getMinPower(int level) {
        return level;
    }

    @Override
    public int getMaxPower(int level) {
        return super.getMinLevel() * 8;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity targetEntity, int level) {
        if (user instanceof PlayerEntity && targetEntity instanceof LivingEntity
                && !(targetEntity instanceof PlayerEntity)
                && !(targetEntity instanceof VillagerEntity)) {
            ItemStack heldItem = ((LivingEntity) targetEntity).getMainHandStack();

            if (!heldItem.isEmpty()) {
                ItemEntity itemEntity = new ItemEntity(targetEntity.getWorld(),
                        targetEntity.getX(), targetEntity.getY(), targetEntity.getZ(), heldItem.copy());
                targetEntity.getWorld().spawnEntity(itemEntity);
                heldItem.setCount(0);
            }
        }

        if (user instanceof PlayerEntity && targetEntity instanceof VillagerEntity) {
            ItemStack heldItem = ((LivingEntity) targetEntity).getMainHandStack();
            if (!heldItem.isEmpty() && heldItem.isStackable()) {
                ItemEntity itemEntity = new ItemEntity(targetEntity.getWorld(),
                        targetEntity.getX(), targetEntity.getY(), targetEntity.getZ(), heldItem.copy());
                targetEntity.getWorld().spawnEntity(itemEntity);
                heldItem.setCount(0);
            }
        }
    }
}
