package moe.ingstar.enchant.Encantment;

import moe.ingstar.enchant.Item.ItemRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class ContainerOfHeartEnchant extends Enchantment {
    protected ContainerOfHeartEnchant(Rarity rarity, EnchantmentTarget target, EquipmentSlot[] slotTypes) {
        super(rarity, target, slotTypes);
    }

    @Override
    public int getMinPower(int level) {
        return level;
    }

    @Override
    public int getMaxPower(int level) {
        return level * 9;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (!target.isAlive()) {
            dropBlocks(target.getWorld(), target.getBlockPos(), (LivingEntity) target);
        }
    }

    private static void dropBlocks(World world, BlockPos pos, LivingEntity livingEntity) {
        Box boundingBox = livingEntity.getBoundingBox();
        double bottomY = boundingBox.minY;

        ItemStack containerOfHeartStack = new ItemStack(ItemRegister.CONTAINER_OR_HEART);
        net.minecraft.entity.ItemEntity itemEntity = new net.minecraft.entity.ItemEntity(world,
                pos.getX() + 0.5, bottomY, pos.getZ() + 0.5, containerOfHeartStack);
        world.spawnEntity(itemEntity);
    }

}
