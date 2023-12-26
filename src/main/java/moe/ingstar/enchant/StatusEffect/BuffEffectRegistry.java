package moe.ingstar.enchant.StatusEffect;

import moe.ingstar.enchant.Encantment.ModEnchantments;
import moe.ingstar.enchant.MoreEnchantments;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BuffEffectRegistry {

    public static final StatusEffect TETANUS_BUFF_EFFECT = new TetanusBuffEffect(StatusEffectCategory.BENEFICIAL, 0xA52A2A);

    public static void register() {
        Registry.register(Registries.STATUS_EFFECT, new Identifier(MoreEnchantments.MOD_ID, "tetanus"), TETANUS_BUFF_EFFECT);
    }
}
