package moe.ingstar.enchant.Encantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class TetanusBladeEnchant extends Enchantment {
    protected TetanusBladeEnchant(Rarity rarity, EnchantmentTarget target, EquipmentSlot[] slotTypes) {
        super(rarity, target, slotTypes);
    }

    @Override
    public int getMinPower(int level) {
        return level;
    }

    @Override
    public int getMaxPower(int level) {
        return super.getMaxPower(level) * 10;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }
}
