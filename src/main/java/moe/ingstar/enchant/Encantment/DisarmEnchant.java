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
import net.minecraft.item.SwordItem;

public class DisarmEnchant extends Enchantment {
    public DisarmEnchant(Rarity weight, EnchantmentTarget target, EquipmentSlot[] slotTypes) {
        super(weight, target, slotTypes);
    }

    @Override
    public int getMinPower(int level) {
        return level;
    }

    @Override
    public int getMaxPower(int level) {
        return level * 8;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof SwordItem;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity targetEntity, int level) {
        if (user instanceof PlayerEntity && targetEntity instanceof LivingEntity
                && !(targetEntity instanceof PlayerEntity)
                && !(targetEntity instanceof VillagerEntity)) {
            handleDisarm((LivingEntity) targetEntity);
        }

        if (user instanceof PlayerEntity && targetEntity instanceof VillagerEntity villager) {
            ItemStack heldItem = villager.getMainHandStack();
            if (!heldItem.isEmpty() && heldItem.isStackable()) {
                ItemEntity itemEntity = new ItemEntity(villager.getWorld(),
                        villager.getX(), villager.getY(), villager.getZ(), heldItem.copy());
                villager.getWorld().spawnEntity(itemEntity);
            }
        }
    }

    private void handleDisarm(LivingEntity targetEntity) {
        ItemStack heldItem = targetEntity.getMainHandStack();
        if (!heldItem.isEmpty()) {
            ItemStack newItemStack = heldItem.copy();
            newItemStack.setDamage(heldItem.getDamage());

            ItemEntity itemEntity = new ItemEntity(targetEntity.getWorld(),
                    targetEntity.getX(), targetEntity.getY(), targetEntity.getZ(), newItemStack);
            targetEntity.getWorld().spawnEntity(itemEntity);
            heldItem.setCount(0);
        }
    }

}
