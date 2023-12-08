package moe.ingstar.enchant.Encantment.EnchantConfigs;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public class DamageData {
    private static float damage;

    public static float getDamage() {
        return damage;
    }

    public static void setDamage(float value) {
        damage = value;
    }

}