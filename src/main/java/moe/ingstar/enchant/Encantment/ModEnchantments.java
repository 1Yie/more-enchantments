package moe.ingstar.enchant.Encantment;

import moe.ingstar.enchant.MoreEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;


public class ModEnchantments {
    public static final Identifier ARROW_SHIELD_ID = new Identifier(MoreEnchantments.MOD_ID, "arrow_shield");
    public static final Identifier LIFE_STEAL_ID = new Identifier(MoreEnchantments.MOD_ID, "life_steal");
    public static final Identifier MIDAS_TOUCH_ID = new Identifier(MoreEnchantments.MOD_ID, "midas_touch");
    public static final Identifier DIAMOND_LUCK_ID = new Identifier(MoreEnchantments.MOD_ID, "diamond_luck");
    public static final Identifier LEECH_ID = new Identifier(MoreEnchantments.MOD_ID, "leech");
    public static final Identifier SATURATION_ID = new Identifier(MoreEnchantments.MOD_ID, "saturation");
    public static final Identifier HEALTH_BOOST_ARMOR_ID = new Identifier(MoreEnchantments.MOD_ID, "health_boost_armor");
    public static final Identifier DISARM_ID = new Identifier(MoreEnchantments.MOD_ID, "disarm");
    public static final Identifier SOUL_PENETRATION_ID = new Identifier(MoreEnchantments.MOD_ID, "soul_penetration");
    public static final Identifier THUMP_ID = new Identifier(MoreEnchantments.MOD_ID, "thump");
    public static final Identifier KISS_OF_DEATH_ID = new Identifier(MoreEnchantments.MOD_ID, "kiss_of_death");
    public static final Identifier CONTAINER_OR_HEART_ID = new Identifier(MoreEnchantments.MOD_ID, "container_of_heart");
    public static final Identifier BENEVOLENCE_ID = new Identifier(MoreEnchantments.MOD_ID, "benevolence");
    public static final Identifier TETANUS_BLADE_ID = new Identifier(MoreEnchantments.MOD_ID, "tetanus_blade");
    public static final Identifier ABSOLUT_IMMUNITY_ID = new Identifier(MoreEnchantments.MOD_ID, "absolute_immunity");
    public static final Identifier DEATH_AS_HOME_ID = new Identifier(MoreEnchantments.MOD_ID, "death_as_home");

    public static final Enchantment ARROW_SHIELD = new ArrowShieldEnchant(Enchantment.Rarity.RARE,
            EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment LIFE_STEAL = new LifeStealEnchant(Enchantment.Rarity.RARE,
            EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment MIDAS_TOUCH = new MidasTouchEnchant(Enchantment.Rarity.RARE,
            EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment DIAMOND_LUCK = new DiamondLuckEnchant(Enchantment.Rarity.RARE,
            EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment LEECH = new LeechEnchant(Enchantment.Rarity.RARE,
            EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment SATURATION = new SaturationEnchant(Enchantment.Rarity.RARE,
            EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment HEALTH_BOOST_ARMOR = new HealthBoostEnchant(Enchantment.Rarity.RARE,
            EnchantmentTarget.ARMOR, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment DISARM = new DisarmEnchant(Enchantment.Rarity.RARE,
            EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment SOUL_PENETRATION = new SoulPenetrationEnchant(Enchantment.Rarity.RARE,
            EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment THUMP = new ThumpEnchant(Enchantment.Rarity.RARE,
            EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment KISS_OF_DEATH = new KissOfDeathEnchant(Enchantment.Rarity.RARE,
            EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment CONTAINER_OF_HEART = new ContainerOfHeartEnchant(Enchantment.Rarity.RARE,
            EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment BENEVOLENCE = new BenevolenceEnchant(Enchantment.Rarity.RARE,
            EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment TETANUS_BLADE = new TetanusBladeEnchant(Enchantment.Rarity.RARE,
            EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment ABSOLUT_IMMUNITY = new AbsoluteImmunityEnchant(Enchantment.Rarity.RARE,
            EnchantmentTarget.ARMOR, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static final Enchantment DEATH_AS_HOME = new DeathAsHomeEnchant(Enchantment.Rarity.RARE,
            EnchantmentTarget.ARMOR, new EquipmentSlot[]{EquipmentSlot.MAINHAND});

    public static void registerEnchantments() {
        Registry.register(Registries.ENCHANTMENT, ARROW_SHIELD_ID, ARROW_SHIELD);
        Registry.register(Registries.ENCHANTMENT, LIFE_STEAL_ID, LIFE_STEAL);
        Registry.register(Registries.ENCHANTMENT, MIDAS_TOUCH_ID, MIDAS_TOUCH);
        Registry.register(Registries.ENCHANTMENT, DIAMOND_LUCK_ID, DIAMOND_LUCK);
        Registry.register(Registries.ENCHANTMENT, LEECH_ID, LEECH);
        Registry.register(Registries.ENCHANTMENT, SATURATION_ID, SATURATION);
        Registry.register(Registries.ENCHANTMENT, HEALTH_BOOST_ARMOR_ID, HEALTH_BOOST_ARMOR);
        Registry.register(Registries.ENCHANTMENT, DISARM_ID, DISARM);
        Registry.register(Registries.ENCHANTMENT, SOUL_PENETRATION_ID, SOUL_PENETRATION);
        Registry.register(Registries.ENCHANTMENT, THUMP_ID, THUMP);
        Registry.register(Registries.ENCHANTMENT, KISS_OF_DEATH_ID, KISS_OF_DEATH);
        Registry.register(Registries.ENCHANTMENT, CONTAINER_OR_HEART_ID, CONTAINER_OF_HEART);
        Registry.register(Registries.ENCHANTMENT, BENEVOLENCE_ID, BENEVOLENCE);
        Registry.register(Registries.ENCHANTMENT, TETANUS_BLADE_ID, TETANUS_BLADE);
        Registry.register(Registries.ENCHANTMENT, ABSOLUT_IMMUNITY_ID, ABSOLUT_IMMUNITY);
        Registry.register(Registries.ENCHANTMENT, DEATH_AS_HOME_ID, DEATH_AS_HOME);
    }
}
