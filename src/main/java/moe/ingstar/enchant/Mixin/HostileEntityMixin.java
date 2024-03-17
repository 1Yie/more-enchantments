package moe.ingstar.enchant.Mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HostileEntity.class)
public abstract class HostileEntityMixin extends LivingEntity {

    protected HostileEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    // 在生物更新时触发的方法
    @Inject(method = "isAngryAt", at = @At("HEAD"))
    private void onTick(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        checkAggro();
    }

    @Unique
    private void checkAggro() {
        ServerWorld serverWorld = (ServerWorld) this.getWorld();

        // 设置一个目标检测条件
        TargetPredicate targetPredicate = TargetPredicate.createAttackable().setBaseMaxDistance(20.0D);

        // 获取附近的玩家
        PlayerEntity player = serverWorld.getClosestPlayer(targetPredicate, this);

        if (player != null) {
            System.out.println("生物对玩家产生了仇恨");
        }
    }
}
