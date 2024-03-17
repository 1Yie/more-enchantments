package moe.ingstar.enchant.Encantment.Util;

import moe.ingstar.enchant.Registry.ItemRegister;
import moe.ingstar.enchant.Registry.ModEnchantments;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class ContainerOfHeartHandler {
    public static void initialize() {
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
            if (entity instanceof LivingEntity && damageSource.getAttacker() instanceof PlayerEntity) {
                LivingEntity livingEntity = (LivingEntity) entity;

                PlayerEntity player = (PlayerEntity) damageSource.getAttacker();

                ItemStack heldItem = player.getMainHandStack();
                if (hasRequiredEnchantment(heldItem)) {
                    dropBlocks(livingEntity.getWorld(), livingEntity.getBlockPos(), livingEntity);
                }

            }
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(content -> {
            content.addAfter(Items.ENDER_EYE, ItemRegister.CONTAINER_OF_HEART);
        });
    }

    private static void dropBlocks(World world, BlockPos pos, LivingEntity livingEntity) {
        Box boundingBox = livingEntity.getBoundingBox();
        double bottomY = boundingBox.minY;

        ItemStack containerOfHeartStack = new ItemStack(ItemRegister.CONTAINER_OF_HEART);
        net.minecraft.entity.ItemEntity itemEntity = new net.minecraft.entity.ItemEntity(world,
                pos.getX() + 0.5, bottomY, pos.getZ() + 0.5, containerOfHeartStack);
        world.spawnEntity(itemEntity);
    }

    private static boolean hasRequiredEnchantment(ItemStack itemStack) {
        return EnchantmentHelper.getLevel(ModEnchantments.CONTAINER_OF_HEART, itemStack) > 0;
    }
}
