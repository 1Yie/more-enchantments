package moe.ingstar.enchant;

import io.netty.buffer.Unpooled;
import moe.ingstar.enchant.Encantment.ModEnchantments;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class MoreEnchantmentsClient implements ClientModInitializer {
    public static boolean areaDestructionToggleKey = false;

    @Override
    public void onInitializeClient() {
        KeyBinding areaDestructionToggle = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.enchant.toggle_area_destruction",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Y,
                "category.more_enchantment.enchant"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                ItemStack itemStack = client.player.getMainHandStack();
                Text yes = Text.translatable("yes");
                Text no = Text.translatable("no");

                while (areaDestructionToggle.wasPressed() && hasSpecificEnchantment(itemStack)) {
                    areaDestructionToggleKey = !areaDestructionToggleKey;
                    boolean toggleState = areaDestructionToggleKey;
                    sendToggleAreaDestructionPacketToServer(toggleState);
                    client.player.sendMessage(Text.translatable("message.enchant.area_destruction", (toggleState ? yes : no)));
                    MoreEnchantments.LOGGER.info("[Client] [Area Destruction - KeyBinding]" + " ToggleState: " + toggleState + ", Key: " + areaDestructionToggleKey + ", Toggle: " + areaDestructionToggle);
                }
            }
        });
    }

    private static void sendToggleAreaDestructionPacketToServer(boolean toggleState) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeBoolean(toggleState);

        ClientPlayNetworking.send(new Identifier(MoreEnchantments.MOD_ID, "toggle_area_destruction"), buf);
    }

    private boolean hasSpecificEnchantment(ItemStack stack) {
        return EnchantmentHelper.getLevel(ModEnchantments.AREA_DESTRUCTION, stack) > 0;
    }
}

