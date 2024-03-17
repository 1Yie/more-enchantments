package moe.ingstar.enchant.Encantment.Util;

import moe.ingstar.enchant.Registry.BuffEffectRegistry;

import moe.ingstar.enchant.Registry.ModEnchantments;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import net.minecraft.enchantment.EnchantmentHelper;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;


import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

import net.minecraft.world.World;


import java.util.function.Predicate;

public class ComingDamageHandler {
    private static final Predicate<LivingEntity> PEACE_AREA = entity -> entity.isAlive() && !(entity instanceof ServerPlayerEntity);
    private static final int AREA = 30;

    public static void onInitialize() {
        ServerTickEvents.START_WORLD_TICK.register(ComingDamageHandler::checkForHostileEntities);
    }

    private static void checkForHostileEntities(World world) {
        world.getPlayers().forEach(player -> {
            for (LivingEntity livingEntity : player.getWorld().getEntitiesByClass(LivingEntity.class,
                    player.getBoundingBox().expand(AREA), PEACE_AREA)) {
                if (livingEntity instanceof MobEntity mob && hasRequiredEnchantment(player)) {

                    if (mob.getTarget() == player && !(mob.getLastAttacker() == player)) {
                        applyEffect(player);
                    } else if (mob.getLastAttacker() == player) {
                        clearEffect(player);
                    }
                }
            }
        });
    }

    private static void applyEffect(PlayerEntity player) {
        StatusEffectInstance applyEffect = new StatusEffectInstance(BuffEffectRegistry.COMING_DANGER_EFFECT, 2 * 20, 0);
        player.addStatusEffect(applyEffect);
    }

    private static void clearEffect(PlayerEntity player) {
        player.removeStatusEffect(BuffEffectRegistry.COMING_DANGER_EFFECT);
    }

    private static boolean hasRequiredEnchantment(PlayerEntity player) {
        for (ItemStack itemStack : player.getArmorItems()) {
            if (EnchantmentHelper.getLevel(ModEnchantments.COMING_DAMAGE, itemStack) > 0) {
                return true;
            }
        }
        return false;
    }
}
