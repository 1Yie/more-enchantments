package moe.ingstar.enchant.Mixin;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin {

    @Unique
    public LivingEntity currentTarget;

    @Inject(method = "render*", at = @At("HEAD"))
    private void render(AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        checkCrosshairTarget((PlayerEntity) abstractClientPlayerEntity);
    }

    @Unique
    private void checkCrosshairTarget(PlayerEntity player) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();

        if (minecraftClient.world != null) {
            HitResult hitResult = minecraftClient.crosshairTarget;

            if (hitResult != null) {
                if (hitResult.getType() == HitResult.Type.ENTITY) {
                    LivingEntity targetEntity = (LivingEntity) ((EntityHitResult) hitResult).getEntity();
                    if (targetEntity != currentTarget) {
                        // 准星目标变化
                        System.out.println("玩家准心指向了生物：" + targetEntity.getName());
                        currentTarget = targetEntity;
                        applyAttackBoostEffect(player);
                    }
                } else {
                    // 准星没有命中生物
                    if (currentTarget != null) {
                        System.out.println("玩家准心没有指向生物");
                        currentTarget = null;
                        removeAttackBoostEffect(player);
                    }
                }
            }
        }
    }

    @Unique
    private void applyAttackBoostEffect(PlayerEntity player) {
        StatusEffectInstance attackBoostEffect = new StatusEffectInstance(StatusEffects.STRENGTH, 200, 5);
        player.addStatusEffect(attackBoostEffect);
        System.out.println("玩家获得攻击力提升效果");
    }

    @Unique
    private void removeAttackBoostEffect(PlayerEntity player) {
        player.removeStatusEffect(StatusEffects.STRENGTH);
    }

    // 监听HitResultChangedEvent
    @Inject(method = "<init>*", at = @At("RETURN"))
    private void onInit(CallbackInfo ci) {
        ClientTickEvents.END_CLIENT_TICK.register(client -> checkCrosshairTarget(client.player));
    }
}
