package moe.ingstar.enchant.Encantment.EnchantConfigs;

import moe.ingstar.enchant.Encantment.ModEnchantments;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class HealthBoostHandler {
    public static void initialize() {
        ServerTickEvents.START_WORLD_TICK.register(world -> {
            List<ServerPlayerEntity> players = world.getPlayers();
            players.forEach(HealthBoostHandler::updatePlayerHealth);
        });
    }

    private static void updatePlayerHealth(ServerPlayerEntity player) {
        List<String> enchantmentKeys = getPlayerEquipmentEnchantmentKeys(player);
        boolean hasEnchant = enchantmentKeys.contains("enchantment.more_enchantments.health_boost_armor");

        double finalHealth = 0.0;

        if (hasEnchant) {
            for (ItemStack itemStack : player.getInventory().armor) {
                if (itemStack.getItem() instanceof ArmorItem armorItem) {
                    int level = EnchantmentHelper.getLevel(ModEnchantments.HEALTH_BOOST_ARMOR, itemStack);
                    finalHealth += (level * 2) * 2;
                }
            }
        }

        double newMaxHealth = player.defaultMaxHealth + finalHealth;
        Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(newMaxHealth);

        if (!hasEnchant) {
            player.setHealth((float) Math.min(player.getHealth(), newMaxHealth));
        }
    }

    private static List<String> getPlayerEquipmentEnchantmentKeys(PlayerEntity player) {
        return StreamSupport.stream(player.getArmorItems().spliterator(), false)
                .filter(itemStack -> !itemStack.isEmpty())
                .flatMap(itemStack -> EnchantmentHelper.get(itemStack).keySet().stream())
                .map(Enchantment::getTranslationKey)
                .collect(Collectors.toList());
    }
}
