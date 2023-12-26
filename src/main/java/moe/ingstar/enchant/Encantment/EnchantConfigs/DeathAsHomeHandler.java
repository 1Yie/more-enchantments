package moe.ingstar.enchant.Encantment.EnchantConfigs;

import moe.ingstar.enchant.Encantment.ModEnchantments;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DeathAsHomeHandler {
    private static final Map<PlayerEntity, Long> cooldownMap = new HashMap<>();
    private static final long COOLDOWN_TIME_MS = 60000; // 10 000
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static void initialize() {
        scheduler.scheduleAtFixedRate(() -> {
            for (Map.Entry<PlayerEntity, Long> entry : cooldownMap.entrySet()) {
                PlayerEntity player = entry.getKey();
                if (isOnCooldown(player)) {
                    long lastTriggerTime = entry.getValue();
                    long cooldownTimeRemaining = COOLDOWN_TIME_MS - (System.currentTimeMillis() - lastTriggerTime);
                    player.sendMessage(Text.of("视死如归冷却: " + cooldownTimeRemaining / 1000 + " 秒"), true);
                }
            }
        }, 0, 1, TimeUnit.SECONDS);

        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {
            if (entity instanceof ServerPlayerEntity player) {
                if (hasRequiredEnchantment(player)) {
                    if (!isOnCooldown(player) && player.getHealth() <= amount) {
                        long currentTime = System.currentTimeMillis();
                        cooldownMap.put(player, currentTime);
                        return false;
                    }
                }
            }
            return true;
        });
    }

    private static boolean isOnCooldown(PlayerEntity player) {
        if (cooldownMap.containsKey(player)) {
            long lastTriggerTime = cooldownMap.get(player);
            if (System.currentTimeMillis() - lastTriggerTime < COOLDOWN_TIME_MS) {
                return true;
            } else {
                cooldownMap.remove(player);
            }
        }
        return false;
    }

    private static boolean hasRequiredEnchantment(PlayerEntity player) {
        for (ItemStack itemStack : player.getArmorItems()) {
            if (EnchantmentHelper.getLevel(ModEnchantments.DEATH_AS_HOME, itemStack) > 0) {
                return true;
            }
        }
        return false;
    }

}