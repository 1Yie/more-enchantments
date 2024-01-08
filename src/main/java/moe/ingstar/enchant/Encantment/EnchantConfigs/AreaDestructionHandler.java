package moe.ingstar.enchant.Encantment.EnchantConfigs;

import moe.ingstar.enchant.Encantment.ModEnchantments;
import moe.ingstar.enchant.MoreEnchantments;
import moe.ingstar.enchant.MoreEnchantmentsClient;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class AreaDestructionHandler {
    private static boolean areaDestructionToggleKeyServer = false;
    public static void initialize() {

        ServerPlayNetworking.registerGlobalReceiver(new Identifier(MoreEnchantments.MOD_ID, "toggle_area_destruction"), ((server, player, handler, buf, responseSender) -> {
            boolean toggleState = buf.readBoolean();
            handleToggleAreaDestruction(player, toggleState);
        }));

        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            Direction playerFacing = player.getHorizontalFacing();

            if (areaDestructionToggleKeyServer && hasCustomEnchantment(player.getMainHandStack())) {
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

        player.getMainHandStack().damage(2, player, (p -> p.sendToolBreakStatus(Hand.MAIN_HAND)));

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                BlockPos targetPos = null;

                if (playerFacing == Direction.NORTH || playerFacing == Direction.SOUTH) {
                    targetPos = new BlockPos(x + i, y, z + j);
                } else if (playerFacing == Direction.WEST || playerFacing == Direction.EAST) {
                    targetPos = new BlockPos(x + j, y, z + i);
                }

                if (targetPos != null) {
                    destroyBlock(world, player, targetPos);
                }
            }
        }
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
                    destroyBlock(world, player, targetPos);
                }
            }
        }
        player.getMainHandStack().damage(2, player, (p -> p.sendToolBreakStatus(Hand.MAIN_HAND)));
    }

    private static void destroyBlock(World world, PlayerEntity player, BlockPos targetPos) {
        BlockState targetState = world.getBlockState(targetPos);
        Block targetBlock = targetState.getBlock();

        if (targetBlock.getHardness() > -1.0) {
            boolean hasSilkTouch = EnchantmentHelper.get(player.getMainHandStack()).containsKey(Enchantments.SILK_TOUCH);

            if (hasSilkTouch) {
                ItemStack itemStack = new ItemStack(targetBlock.asItem());
                spawnAsEntity(world, targetPos, itemStack);
                world.breakBlock(targetPos, false, player);
            } else {
                world.breakBlock(targetPos, true, player);
            }
        }

    }

    public static void handleToggleAreaDestruction(ServerPlayerEntity player, boolean toggleState) {
        areaDestructionToggleKeyServer = toggleState;
        MoreEnchantments.LOGGER.info("[Server] [Area Destruction - KeyBinding]" + " Player: " + player + ", ToggleState: " + toggleState + ", ServerKeyState: " + areaDestructionToggleKeyServer);
    }

    private static void spawnAsEntity(World world, BlockPos pos, ItemStack stack) {
        if (!world.isClient && !stack.isEmpty() && world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS)) {
            ItemEntity itemEntity = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack);
            itemEntity.setToDefaultPickupDelay();
            world.spawnEntity(itemEntity);
        }
    }

    private static boolean hasCustomEnchantment(ItemStack stack) {
        var enchantments = EnchantmentHelper.get(stack);
        int customEnchantmentLevel = enchantments.getOrDefault(ModEnchantments.AREA_DESTRUCTION, 0);

        return customEnchantmentLevel >= 1;
    }
}