package moe.ingstar.enchant.Encantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;

public class DeathBacktrackEnchant extends Enchantment {
    public DeathBacktrackEnchant(Rarity weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
        super(weight, type, slotTypes);
    }

    @Override
    public int getMinPower(int level) {
        return level;
    }

    @Override
    public int getMaxPower(int level) {
        return level * 11;
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        if (other instanceof AbsoluteImmunityEnchant) {
            return false;
        }
        return super.canAccept(other);
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof ArmorItem armorItem && armorItem.getSlotType() == EquipmentSlot.HEAD;
    }
}
