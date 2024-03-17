package moe.ingstar.enchant.StatusEffect.Util;

import moe.ingstar.enchant.ModDamageSource.ComingDangerDamageSource;
import moe.ingstar.enchant.Registry.BuffEffectRegistry;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;

import net.minecraft.entity.player.PlayerEntity;


public class ComingDangerBuffHandler {
    public static float hurtValue;
    private static boolean isHandlingDamageEvent = false;

    public static float initialize() {
        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {
            if (entity instanceof PlayerEntity player && player.hasStatusEffect(BuffEffectRegistry.COMING_DANGER_EFFECT)) {
                if (!isHandlingDamageEvent) {
                    hurtValue = amount;
                    isHandlingDamageEvent = true;

                    boolean shouldBlockDamage = !shouldTimeHurt(player);
                    ComingDangerDamageSource.playSound(player);
                    isHandlingDamageEvent = false;
                    return shouldBlockDamage;
                }
            }
            return true;
        });
        return 0;
    }

    private static boolean shouldTimeHurt(PlayerEntity player) {
        if (player != null) {
            player.damage(new ComingDangerDamageSource(player, player.getAttacker()), hurtValue / 2);
            return true;
        }
        return false;
    }
}
