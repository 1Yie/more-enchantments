package moe.ingstar.enchant.Encantment.EnchantConfigs;

import moe.ingstar.enchant.Encantment.ModEnchantments;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class DiamondLuckHandler {
    private static final Set<Block> DIAMOND_DROP_BLOCKS = new HashSet<>();
    private static final Random random = new Random();

    static {
        // Iron
        DIAMOND_DROP_BLOCKS.add(Blocks.IRON_ORE);
        DIAMOND_DROP_BLOCKS.add(Blocks.DEEPSLATE_IRON_ORE);
        // Coal
        DIAMOND_DROP_BLOCKS.add(Blocks.COAL_ORE);
        DIAMOND_DROP_BLOCKS.add(Blocks.DEEPSLATE_COAL_ORE);
        // Gold
        DIAMOND_DROP_BLOCKS.add(Blocks.GOLD_ORE);
        DIAMOND_DROP_BLOCKS.add(Blocks.DEEPSLATE_GOLD_ORE);
        DIAMOND_DROP_BLOCKS.add(Blocks.NETHER_GOLD_ORE);
        // Diamond
        DIAMOND_DROP_BLOCKS.add(Blocks.DIAMOND_ORE);
        DIAMOND_DROP_BLOCKS.add(Blocks.DEEPSLATE_DIAMOND_ORE);
        // Copper
        DIAMOND_DROP_BLOCKS.add(Blocks.COPPER_ORE);
        DIAMOND_DROP_BLOCKS.add(Blocks.DEEPSLATE_COPPER_ORE);
        // Lapis Lazuli
        DIAMOND_DROP_BLOCKS.add(Blocks.LAPIS_ORE);
        DIAMOND_DROP_BLOCKS.add(Blocks.DEEPSLATE_LAPIS_ORE);
        // Emerald
        DIAMOND_DROP_BLOCKS.add(Blocks.EMERALD_ORE);
        DIAMOND_DROP_BLOCKS.add(Blocks.DEEPSLATE_EMERALD_ORE);
        // Nether Quartz
        DIAMOND_DROP_BLOCKS.add(Blocks.NETHER_QUARTZ_ORE);
        // Ancient Debris
        DIAMOND_DROP_BLOCKS.add(Blocks.ANCIENT_DEBRIS);
        // Redstone
        DIAMOND_DROP_BLOCKS.add(Blocks.REDSTONE_ORE);
        DIAMOND_DROP_BLOCKS.add(Blocks.DEEPSLATE_REDSTONE_ORE);
    }

    public static void registerDiamondDropBlock(Block block) {
        DIAMOND_DROP_BLOCKS.add(block);
    }

    public static void initialize() {
        for (Block block : DIAMOND_DROP_BLOCKS) {
            registerDiamondDropBlock(block);
        }

        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            ItemStack mainHandStack = player.getMainHandStack();

            if (EnchantmentHelper.get(mainHandStack).containsKey(ModEnchantments.DIAMOND_LUCK)
                    && DIAMOND_DROP_BLOCKS.contains(state.getBlock())) {

                int enchantmentLevel = EnchantmentHelper.getLevel(ModEnchantments.DIAMOND_LUCK, mainHandStack);
                int enchantLuckyLevel = EnchantmentHelper.getLevel(Enchantments.FORTUNE, mainHandStack);

                if (shouldDropDiamond(enchantmentLevel, enchantLuckyLevel)) {
                    dropBlocks(world, pos);

                    if (enchantLuckyLevel != 0 && shouldDropAdditionalDiamond(enchantLuckyLevel)) {
                        dropBlocks(world, pos);

                        if (shouldDropAdditionalDiamond(enchantLuckyLevel)) {
                            dropBlocks(world, pos);
                        }
                    }
                }
            }
        });
    }

    private static void dropBlocks(World world, BlockPos pos) {
        ItemStack itemStack = new ItemStack(Items.DIAMOND);
        net.minecraft.entity.ItemEntity itemEntity = new net.minecraft.entity.ItemEntity(world,
                pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, itemStack);
        world.spawnEntity(itemEntity);
    }

    private static boolean shouldDropDiamond(int enchantmentLevel, int enchantLuckyLevel) {
        int baseDropChance = 50;
        int minBaseDropChance = Math.max(1, baseDropChance - (enchantLuckyLevel) - (enchantmentLevel * 10));
        int tag = random.nextInt(minBaseDropChance) + 1;
        return tag == 1;
    }

    private static boolean shouldDropAdditionalDiamond(int enchantLuckyLevel) {
        int additionalDropChance = 10;
        int minAdditionalDropChance = Math.max(1, additionalDropChance - (enchantLuckyLevel * 2));
        int dropTag = random.nextInt(minAdditionalDropChance) + 1;
        return dropTag == 1;
    }
}