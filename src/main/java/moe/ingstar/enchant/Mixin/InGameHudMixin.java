package moe.ingstar.enchant.Mixin;


import moe.ingstar.enchant.Encantment.Util.DeathBacktrackHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;

import net.minecraft.entity.player.PlayerEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Shadow public abstract TextRenderer getTextRenderer();
    @Inject(method = "render", at = @At("HEAD"))
    public void onRender(DrawContext context, float tickDelta, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        PlayerEntity player = client.player;

        if (player != null && DeathBacktrackHandler.hasRequiredEnchantment(player)) {
            int cooldownTicks = DeathBacktrackHandler.getCooldown(player);
            if (cooldownTicks > 0) {
                int cooldownSeconds = cooldownTicks / 20;

                // 获取屏幕宽度和高度
                int screenWidth = client.getWindow().getScaledWidth();
                int screenHeight = client.getWindow().getScaledHeight();

                // 获取文本渲染器
                TextRenderer textRenderer = client.textRenderer;
                String text = "死亡回溯冷却: " + cooldownSeconds + "秒";

                // 设置文本的位置 右下
                int x = screenWidth - getTextRenderer().getWidth(text);
                int y = screenHeight - 9;

                // 绘制文本
                int color = 0xFFFFFF;
                boolean shadow = true;
                context.drawText(textRenderer, text, x, y, color, shadow);
            }
        }
    }
}
