package moe.ingstar.enchant.Mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    @Shadow
    public int experienceLevel;

    @Shadow
    public abstract void jump();

    @Shadow
    public abstract boolean isPlayer();

    @Shadow
    public abstract void tick();

    @Shadow
    public abstract float getDamageTiltYaw();

    @Shadow
    public abstract void addExperience(int experience);


    @Unique
    private boolean isAddingExperience = false;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo info) {

    }

    @Inject(method = "addExperience", at = @At("HEAD"))
    private void onAddExperience(int experience, CallbackInfo ci) {
        if (!isAddingExperience) {
            isAddingExperience = true;

            int doubledExperience = experience * 2;
            addExperience(doubledExperience);

            isAddingExperience = false;
        }
    }

}


