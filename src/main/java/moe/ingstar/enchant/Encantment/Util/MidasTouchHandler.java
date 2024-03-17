package moe.ingstar.enchant.Encantment.Util;

import moe.ingstar.enchant.Registry.ModEnchantments;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MidasTouchHandler {
    private static final Set<Block> MIDAS_TOUCH_BLOCKS = new HashSet<>();

    static {
        MIDAS_TOUCH_BLOCKS.add(Blocks.STONE);
        MIDAS_TOUCH_BLOCKS.add(Blocks.DEEPSLATE);
        MIDAS_TOUCH_BLOCKS.add(Blocks.GRANITE);
        MIDAS_TOUCH_BLOCKS.add(Blocks.DIORITE);
        MIDAS_TOUCH_BLOCKS.add(Blocks.ANDESITE);
        MIDAS_TOUCH_BLOCKS.add(Blocks.CALCITE);
        MIDAS_TOUCH_BLOCKS.add(Blocks.TUFF);
        MIDAS_TOUCH_BLOCKS.add(Blocks.BLACKSTONE);
        MIDAS_TOUCH_BLOCKS.add(Blocks.BASALT);
        MIDAS_TOUCH_BLOCKS.add(Blocks.SMOOTH_BASALT);
        MIDAS_TOUCH_BLOCKS.add(Blocks.BEDROCK);
    }

    public static void registerMidasTouchBlock(Block block) {
        MIDAS_TOUCH_BLOCKS.add(block);
    }

    public static void initialize() {
        for (Block block : MIDAS_TOUCH_BLOCKS) {
            registerMidasTouchBlock(block);
        }

        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            ItemStack mainHandStack = player.getMainHandStack();
            if (EnchantmentHelper.get(mainHandStack).containsKey(ModEnchantments.MIDAS_TOUCH)
                    && MIDAS_TOUCH_BLOCKS.contains(state.getBlock())) {
                handleMidasTouch(world, pos);
            }
        });
    }

    private static void handleMidasTouch(World world, BlockPos pos) {
        Random random = new Random();

        if (random.nextInt(5) + 1 == 1) {
            dropBlocks(world, pos);
        }
    }

    private static void dropBlocks(World world, BlockPos pos) {
        ItemStack itemStack = new ItemStack(Items.GOLD_NUGGET);
        net.minecraft.entity.ItemEntity itemEntity = new net.minecraft.entity.ItemEntity(world,
                pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, itemStack);
        world.spawnEntity(itemEntity);
    }
}
