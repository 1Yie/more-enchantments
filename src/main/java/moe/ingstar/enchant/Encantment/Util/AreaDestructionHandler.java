package moe.ingstar.enchant.Encantment.Util;

import moe.ingstar.enchant.MoreEnchantments;
import moe.ingstar.enchant.Registry.ModEnchantments;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

import static moe.ingstar.enchant.Encantment.Util.DiamondLuckHandler.dropBlocks;
import static moe.ingstar.enchant.Encantment.Util.DiamondLuckHandler.shouldDropAdditionalDiamond;

public class AreaDestructionHandler {

    private static final Random random = new Random();

    public static void initialize() {

        ServerPlayNetworking.registerGlobalReceiver(new Identifier(MoreEnchantments.MOD_ID, "toggle_area_destruction"), ((server, player, handler, buf, responseSender) -> {
            boolean toggleState = buf.readBoolean();
            handleToggleAreaDestruction(player, toggleState);
        }));

        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            Direction playerFacing = player.getHorizontalFacing();

            if (hasCustomEnchantment(player.getMainHandStack()) && player.getMainHandStack().getNbt().getBoolean("Area_Destruction_Key")) {
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
                    destroyBlock(world, player, targetPos);
                }
            }
        }
        player.getMainHandStack().damage(2, player, (p -> p.sendToolBreakStatus(Hand.MAIN_HAND)));
    }

    private static void destroyBlock(World world, PlayerEntity player, BlockPos targetPos) {
        BlockState targetState = world.getBlockState(targetPos);
        Block targetBlock = targetState.getBlock();
        String blockId = targetBlock.getTranslationKey();

        if (targetBlock.getHardness() > -1.0) {
            boolean hasSilkTouch = EnchantmentHelper.get(player.getMainHandStack()).containsKey(Enchantments.SILK_TOUCH);
            boolean hasFortune = EnchantmentHelper.get(player.getMainHandStack()).containsKey(Enchantments.FORTUNE);
            boolean hasDiamondLucky = EnchantmentHelper.get(player.getMainHandStack()).containsKey(ModEnchantments.DIAMOND_LUCK);

            int diamondLuckyLevel = EnchantmentHelper.getLevel(ModEnchantments.DIAMOND_LUCK, player.getMainHandStack());
            int fortuneLevel = EnchantmentHelper.getLevel(Enchantments.FORTUNE, player.getMainHandStack());

            if (hasSilkTouch) {
                ItemStack itemStack = new ItemStack(targetBlock.asItem());
                spawnAsEntity(world, targetPos, itemStack);
                world.breakBlock(targetPos, false, player);
            } else {
                world.breakBlock(targetPos, true, player);
            }

            if (hasFortune && blockId.endsWith("_ore") || blockId.endsWith("_ores")) {
                List<ItemStack> drops = Block.getDroppedStacks(targetState, (ServerWorld) world, targetPos, world.getBlockEntity(targetPos), player, player.getMainHandStack());

                int dropCount = getFortuneAdjustedDropCount(fortuneLevel);
                triggerFortuneEffect(world, targetPos, drops, dropCount);
            }

            if (hasDiamondLucky && (blockId.endsWith("_ore") || blockId.endsWith("_ores"))) {
                if (DiamondLuckHandler.shouldDropDiamond(diamondLuckyLevel, fortuneLevel)) {
                    dropBlocks(world, targetPos);

                    if (fortuneLevel != 0) {
                        for (int i = 0; i < 2 && shouldDropAdditionalDiamond(fortuneLevel); i++) {
                            dropBlocks(world, targetPos);
                        }
                    }
                }
            }
        }
    }

    private static int getFortuneAdjustedDropCount(int fortuneLevel) {
        int baseDropCount = 1;

        return switch (fortuneLevel) {
            case 1 -> calculateDropCount(0.33, baseDropCount, 2);
            case 2 -> calculateDropCount(0.25, baseDropCount, 2, 0.5, baseDropCount * 3);
            case 3 -> calculateDropCount(0.2, baseDropCount, 2, 0.4, baseDropCount * 3, 0.6, baseDropCount * 4);
            default -> baseDropCount;
        };
    }

    private static int calculateDropCount(double... chances) {
        double randomValue = random.nextDouble();
        double cumulativeProbability = 0;

        for (int i = 0; i < chances.length; i += 2) {
            cumulativeProbability += chances[i];
            if (randomValue < cumulativeProbability) {
                return (int) chances[i + 1];
            }
        }

        return (int) chances[chances.length - 1];
    }

    private static void triggerFortuneEffect(World world, BlockPos pos, List<ItemStack> drops, int dropCount) {
        for (int i = 0; i < dropCount; i++) {
            for (ItemStack drop : drops) {
                ItemEntity itemEntity = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, drop);
                world.spawnEntity(itemEntity);
            }
        }
    }

    public static void handleToggleAreaDestruction(ServerPlayerEntity player, boolean toggleState) {
        NbtCompound nbtCompound = player.getMainHandStack().getOrCreateNbt();
        nbtCompound.putBoolean("Area_Destruction_Key", toggleState);
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