package moe.ingstar.enchant.Registry;

import moe.ingstar.enchant.MoreEnchantments;
import moe.ingstar.enchant.StatusEffect.ComingDangerEffect;
import moe.ingstar.enchant.StatusEffect.ConfusionEffect;
import moe.ingstar.enchant.StatusEffect.TetanusBuffEffect;
import moe.ingstar.enchant.StatusEffect.Util.*;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BuffEffectRegistry {

    public static final StatusEffect TETANUS_BUFF_EFFECT = new TetanusBuffEffect(StatusEffectCategory.BENEFICIAL, 0xA52A2A);
    public static final StatusEffect COMING_DANGER_EFFECT = new ComingDangerEffect(StatusEffectCategory.BENEFICIAL, 0xFFC800);
    public static final StatusEffect CONFUSION_EFFECT = new ConfusionEffect(StatusEffectCategory.BENEFICIAL, 0xFFC800);

    public static void register() {
        registerHandler();

        Registry.register(Registries.STATUS_EFFECT, new Identifier(MoreEnchantments.MOD_ID, "tetanus"), TETANUS_BUFF_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, new Identifier(MoreEnchantments.MOD_ID, "coming_danger"), COMING_DANGER_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, new Identifier(MoreEnchantments.MOD_ID, "confusion"), CONFUSION_EFFECT);
    }

    public static void registerHandler() {
        ComingDangerBuffHandler.initialize();
    }
}