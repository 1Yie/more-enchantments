package moe.ingstar.enchant.Encantment.EnchantConfigs;

import moe.ingstar.enchant.Encantment.ModEnchantments;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class DeathAsHomeHandler {


    private static final Map<ServerPlayerEntity, Long> immunityCooldowns = new HashMap<>();
    private static final long IMMUNITY_DURATION = 80 * 20;


    public static void initialize() {

        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {
            if (entity instanceof ServerPlayerEntity player) {
                if (player.getHealth() <= amount && !isUnderImmunityCooldown(player) && hasRequiredEnchantment(player)) {
                    startImmunity(player);
                    return false;
                }
            }
            return true;
        });

        ServerTickEvents.END_SERVER_TICK.register(DeathAsHomeHandler::updateCooldowns);
    }


    private static boolean isUnderImmunityCooldown(ServerPlayerEntity player) {
        return immunityCooldowns.containsKey(player) && player.getServer().getTicks() < immunityCooldowns.get(player);
    }

    private static void startImmunity(ServerPlayerEntity player) {
        immunityCooldowns.put(player, player.getServer().getTicks() + IMMUNITY_DURATION);

        player.incrementStat(Stats.USED.getOrCreateStat(Items.TOTEM_OF_UNDYING));
        Criteria.USED_TOTEM.trigger(player, player.getEquippedStack(EquipmentSlot.CHEST));
        player.clearStatusEffects();
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 500, 1));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 400, 0));
        player.getWorld().sendEntityStatus(player, (byte) 35);

        player.sendMessage(Text.translatable("enchantment.translatable.death_as_home.used"), true);
    }

    private static void executeOperation(ServerPlayerEntity player) {
        player.sendMessage(Text.translatable("enchantment.translatable.death_as_home.done"), true);
    }

    private static void updateCooldowns(MinecraftServer server) {
        AtomicLong currentTick = new AtomicLong(server.getTicks());
        immunityCooldowns.entrySet().removeIf(entry -> {
            ItemStack playerArmor = entry.getKey().getEquippedStack(EquipmentSlot.CHEST);

            if (currentTick.get() >= entry.getValue()) {
                executeOperation(entry.getKey());
                NbtHelper.removeNbt(playerArmor, "DeathAsHomeCooldown");
                return true;

            } else {
                long remainingTime = entry.getValue() - currentTick.get();
                writeCooldownToArmor(playerArmor, remainingTime / 20);
                entry.getKey().sendMessage(Text.translatable("enchantment.translatable.death_as_home.cooldown", remainingTime / 20), true);

                return false;
            }

        });
    }

    private static void writeCooldownToArmor(ItemStack armor, long cooldownEndTime) {
        NbtCompound tag = armor.getOrCreateNbt();

        tag.putLong("DeathAsHomeCooldown", cooldownEndTime);
        armor.setNbt(tag);

        if (tag.getLong("DeathAsHomeCooldown") == 0L) {
            NbtHelper.removeNbt(armor, "DeathAsHomeCooldown");
        }
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
