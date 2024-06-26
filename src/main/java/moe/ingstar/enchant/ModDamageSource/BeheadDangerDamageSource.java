package moe.ingstar.enchant.ModDamageSource;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageEffects;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public class BeheadDangerDamageSource extends DamageSource {
    public static final RegistryEntry<DamageType> DAMAGE_TYPE_REGISTRY_ENTRY = RegistryEntry.of(new DamageType("behead_killed", 0.1F, DamageEffects.BURNING));

    public BeheadDangerDamageSource(@Nullable Entity source, @Nullable Entity attacker) {
        super(DAMAGE_TYPE_REGISTRY_ENTRY, source, attacker);
    }

    @Override
    public Text getDeathMessage(LivingEntity killed) {
        return Text.translatable("death.behead.killed", killed.getDisplayName());
    }

    public static void playSound(PlayerEntity player) {
        if (player.hurtTime == 10) {
            player.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ENTITY_PLAYER_HURT, SoundCategory.PLAYERS, 1.0f, 1.0f);
        }
    }
}
