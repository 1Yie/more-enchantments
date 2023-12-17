package moe.ingstar.enchant.Item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ContainerOfHeart extends Item {

    public ContainerOfHeart(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new TranslatableText("item.more_enchantments.health_box.tooltip_1").formatted(Formatting.RED));
        tooltip.add(new TranslatableText("item.more_enchantments.health_box.tooltip_2").formatted(Formatting.DARK_GRAY));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 3, 8, false, false));
            ItemStack heldItem = user.getStackInHand(hand);
            heldItem.decrement(1);
            user.getItemCooldownManager().set(this, 10);
        }

        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
