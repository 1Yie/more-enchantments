package moe.ingstar.enchant.Encantment.Util;

import moe.ingstar.enchant.Registry.ModEnchantments;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class DeathBacktrackHandler {
    private static final int RECORD_INTERVAL_TICKS = 20;
    private static final int RECORD_DURATION_TICKS = 20 * 100;
    private static final int COOLDOWN_TICKS = 20 * 60;

    private static final Map<PlayerEntity, Queue<PlayerState>> playerStateMap = new HashMap<>();
    private static final Map<PlayerEntity, Integer> cooldownMap = new HashMap<>();

    public static void initialize() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (PlayerEntity player : server.getPlayerManager().getPlayerList()) {
                if (hasRequiredEnchantment(player)) {
                    recordPlayerState(player);
                    updateCooldown(player);
                }
            }
        });

        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {
            if (entity instanceof PlayerEntity player) {
                if (hasRequiredEnchantment(player) && player.getHealth() - amount <= 0 && !isInCooldown(player)) {
                    Queue<PlayerState> states = playerStateMap.get(player);
                    if (states != null && !states.isEmpty()) {
                        PlayerState state = states.poll();
                        if (state != null) {
                            state.restore((ServerPlayerEntity) player);
                            addBacktrackEffects(player);
                            setCooldown(player, COOLDOWN_TICKS);
                            return false;
                        }
                    }
                }
            }
            return true;
        });
    }

    public static boolean hasRequiredEnchantment(PlayerEntity player) {
        for (ItemStack itemStack : player.getArmorItems()) {
            if (EnchantmentHelper.getLevel(ModEnchantments.DEATH_BACKTRACK, itemStack) > 0) {
                return true;
            }
        }
        return false;
    }

    public static void recordPlayerState(PlayerEntity player) {
        Queue<PlayerState> states = playerStateMap.computeIfAbsent(player, k -> new LinkedList<>());
        states.add(new PlayerState(player));
        if (states.size() > RECORD_DURATION_TICKS / RECORD_INTERVAL_TICKS) {
            states.poll();
        }
    }

    public static void updateCooldown(PlayerEntity player) {
        cooldownMap.computeIfPresent(player, (p, ticks) -> {
            if (ticks > 0) {
                long newTicks = ticks - 1;
                return Math.toIntExact(newTicks);
            } else {
                return 0;
            }
        });
    }

    public static void setCooldown(PlayerEntity player, int ticks) {
        cooldownMap.put(player, ticks);
    }

    public static boolean isInCooldown(PlayerEntity player) {
        return cooldownMap.getOrDefault(player, 0) > 0;
    }

    public static int getCooldown(PlayerEntity player) {
        return cooldownMap.getOrDefault(player, 0);
    }

    private static void addBacktrackEffects(PlayerEntity player) {
        World world = player.getWorld();
        BlockPos pos = player.getBlockPos();

        if (player.getWorld() instanceof ServerWorld serverWorld) {
            BlockPos blockPos = player.getBlockPos();

            for (int i = 0; i < 50; i++) {
                double offsetX = (serverWorld.random.nextDouble() - 0.5) * 2.0;
                double offsetY = serverWorld.random.nextDouble();
                double offsetZ = (serverWorld.random.nextDouble() - 0.5) * 2.0;
                serverWorld.spawnParticles(ParticleTypes.PORTAL,
                        blockPos.getX() + offsetX,
                        blockPos.getY() + offsetY,
                        blockPos.getZ() + offsetZ,
                        10, 0.0, 0.0, 0.0, 1.0);
            }

            world.playSound(null, pos, SoundEvents.ENTITY_ENDERMAN_TELEPORT,
                    SoundCategory.PLAYERS, 1.0f, 1.0f);

            player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 20, 4));
        }
    }
}
