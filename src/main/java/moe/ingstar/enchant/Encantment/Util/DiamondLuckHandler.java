package moe.ingstar.enchant.Encantment.Util;

import moe.ingstar.enchant.Registry.ModEnchantments;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class DiamondLuckHandler {

    private static final Random random = new Random();


    public static void initialize() {
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            ItemStack mainHandStack = player.getMainHandStack();
            String stateBlock = state.getBlock().getTranslationKey();

            if (EnchantmentHelper.get(mainHandStack).containsKey(ModEnchantments.DIAMOND_LUCK)) {
                if (stateBlock.endsWith("_ore") || stateBlock.endsWith("_ores")) {

                    int enchantmentLevel = EnchantmentHelper.getLevel(ModEnchantments.DIAMOND_LUCK, mainHandStack);
                    int enchantLuckyLevel = EnchantmentHelper.getLevel(Enchantments.FORTUNE, mainHandStack);

                    if (shouldDropDiamond(enchantmentLevel, enchantLuckyLevel)) {
                        dropBlocks(world, pos);

                        if (enchantLuckyLevel != 0) {
                            for (int i = 0; i < 2 && shouldDropAdditionalDiamond(enchantLuckyLevel); i++) {
                                dropBlocks(world, pos);
                            }
                        }
                    }
                }
            }
        });
    }

    public static void dropBlocks(World world, BlockPos pos) {
        ItemStack itemStack = new ItemStack(Items.DIAMOND);
        net.minecraft.entity.ItemEntity itemEntity = new net.minecraft.entity.ItemEntity(world,
                pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, itemStack);
        world.spawnEntity(itemEntity);
    }

    public static boolean shouldDropDiamond(int enchantmentLevel, int enchantLuckyLevel) {
        int baseDropChance = 50;
        int minBaseDropChance = Math.max(1, baseDropChance - (enchantLuckyLevel) - (enchantmentLevel * 10));
        int tag = random.nextInt(minBaseDropChance) + 1;
        return tag == 1;
    }

    public static boolean shouldDropAdditionalDiamond(int enchantLuckyLevel) {
        int additionalDropChance = 10;
        int minAdditionalDropChance = Math.max(1, additionalDropChance - (enchantLuckyLevel * 2));
        int dropTag = random.nextInt(minAdditionalDropChance) + 1;
        return dropTag == 1;
    }
}