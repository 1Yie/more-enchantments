package moe.ingstar.enchant.Encantment.EnchantConfigs;

import moe.ingstar.enchant.Encantment.ModEnchantments;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class AreaDestructionHandler {
    public static void initialize() {
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {

            Direction playerFacing = player.getHorizontalFacing();

            if (player.getPitch() >= -90 && player.getPitch() < -40) {
                System.out.println("UP");
            } else if (player.getPitch() > 20) {
                System.out.println("DOWN");
            } else {
                System.out.println("W,S,N,E");
            }
            if (hasCustomEnchantment(player.getMainHandStack())) {
                if (player.getPitch() >= -90 && player.getPitch() < -40) {
                    destroyBlocksHorizontally(world, player, pos, playerFacing);
                } else if (player.getPitch() > 20) {
                    destroyBlocksHorizontally(world, player, pos, playerFacing);
                } else if (playerFacing == Direction.NORTH || playerFacing == Direction.SOUTH || playerFacing == Direction.WEST || playerFacing == Direction.EAST) {
                    destroyBlocksVertically(world, player, pos, playerFacing);
                } else {
                    destroyBlocksVertically(world, player, pos, playerFacing);
                }
            }
        });
    }

    private static void destroyBlocksHorizontally(World world, PlayerEntity player, BlockPos pos, Direction playerFacing) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                BlockPos targetPos = null;

                if (playerFacing == Direction.NORTH || playerFacing == Direction.SOUTH) {
                    targetPos = new BlockPos(x + i, y, z + j);
                } else if (playerFacing == Direction.WEST || playerFacing == Direction.EAST) {
                    targetPos = new BlockPos(x + j, y, z + i);
                }

                if (targetPos != null) {
                    BlockState targetState = world.getBlockState(targetPos);
                    Block targetBlock = targetState.getBlock();

                    System.out.println(targetBlock);

                    if (targetBlock.getHardness() > -1.0) {
                        targetBlock.afterBreak(world, player, targetPos, targetState, null, player.getMainHandStack());
                        world.breakBlock(targetPos, true, player);
                        targetBlock.afterBreak(world, player, targetPos, targetState, null, player.getMainHandStack());

                        player.getMainHandStack().damage(1, player, (p -> p.sendToolBreakStatus(Hand.MAIN_HAND)));
                    }
                }
            }
        }
        player.getMainHandStack().damage(2, player, (p -> p.sendToolBreakStatus(Hand.MAIN_HAND)));
    }

    private static void destroyBlocksVertically(World world, PlayerEntity player, BlockPos pos, Direction playerFacing) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                BlockPos targetPos = null;

                if (playerFacing == Direction.NORTH || playerFacing == Direction.SOUTH) {
                    targetPos = new BlockPos(x + i, y + j, z);
                } else if (playerFacing == Direction.WEST || playerFacing == Direction.EAST) {
                    targetPos = new BlockPos(x, y + j, z + i);
                }

                if (targetPos != null) {
                    BlockState targetState = world.getBlockState(targetPos);
                    Block targetBlock = targetState.getBlock();

                    if (targetBlock.getHardness() > -1.0) {
                        System.out.println(targetBlock.getHardness());
                        targetBlock.afterBreak(world, player, targetPos, targetState, null, player.getMainHandStack());
                        world.breakBlock(targetPos, true, player);
                        targetBlock.afterBreak(world, player, targetPos, targetState, null, player.getMainHandStack());
                    }
                }
            }
        }
        player.getMainHandStack().damage(2, player, (p -> p.sendToolBreakStatus(Hand.MAIN_HAND)));
    }

    private static boolean hasCustomEnchantment(ItemStack stack) {
        var enchantments = EnchantmentHelper.get(stack);
        int customEnchantmentLevel = enchantments.getOrDefault(ModEnchantments.AREA_DESTRUCTION, 0);

        return customEnchantmentLevel >= 1;
    }
}