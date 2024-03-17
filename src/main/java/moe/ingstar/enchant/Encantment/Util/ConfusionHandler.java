package moe.ingstar.enchant.Encantment.Util;

import moe.ingstar.enchant.Registry.BuffEffectRegistry;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public class ConfusionHandler {
    private static long windowHandle;
    public static void initialize(MinecraftClient client, long windowHandlePublic) {
        windowHandle = windowHandlePublic;
        ConfusionHandler.windowHandle = client.getWindow().getHandle();
        ClientTickEvents.END_CLIENT_TICK.register(ConfusionHandler::handleMouseMovement);
    }

    private static void handleMouseMovement(MinecraftClient client) {
        if (client.player != null && client.world != null) {
            if (hasBuff(client.player)) {
                Mouse mouse = client.mouse;
                double mouseX = mouse.getX();
                double mouseY = mouse.getY();

                Vec3d rotation = client.player.getRotationVec(1.0F);
                float yaw = (float) Math.atan2(rotation.z, rotation.x);
                float pitch = (float) Math.atan2(Math.sqrt(rotation.x * rotation.x + rotation.z * rotation.z), rotation.y);
                float yawDegrees = MathHelper.wrapDegrees(yaw * 57.295776F - 90.0F);
                float pitchDegrees = MathHelper.wrapDegrees(pitch * 57.295776F);

                double newMouseX = mouseX - yawDegrees;
                double newMouseY = mouseY + pitchDegrees;

                if (!client.isPaused() || !client.isWindowFocused()) {
                    GLFW.glfwSetCursorPos(windowHandle, newMouseX, newMouseY);
                }
            }
        }
    }

    private static boolean hasBuff(ClientPlayerEntity player) {
        StatusEffectInstance effectInstance = player.getStatusEffect(BuffEffectRegistry.CONFUSION_EFFECT);
        return effectInstance != null && effectInstance.getDuration() > 0;
    }
}
