package moe.ingstar.enchant.Encantment.EnchantConfigs;

import moe.ingstar.enchant.Encantment.ModEnchantments;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;

import net.minecraft.item.ItemStack;

public class LostCurseHandler {
    public static void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
                if (entity instanceof ItemEntity itemEntity) {
                    ItemStack itemStack = itemEntity.getStack();

                    if (itemStack.hasEnchantments() && hasEnchantment(itemStack)) {
                        itemEntity.remove(Entity.RemovalReason.DISCARDED);
                    }
                }
            });
        });
    }

    private static boolean hasEnchantment(ItemStack itemStack) {
        return EnchantmentHelper.getLevel(ModEnchantments.LOSTCURSE, itemStack) > 0;
    }
}
