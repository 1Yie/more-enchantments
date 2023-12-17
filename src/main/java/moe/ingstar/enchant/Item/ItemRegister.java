package moe.ingstar.enchant.Item;

import moe.ingstar.enchant.MoreEnchantments;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemRegister {
    public static final Item CONTAINER_OR_HEART = new ContainerOfHeart(new FabricItemSettings());

    public static void register() {
        Registry.register(Registry.ITEM, new Identifier(MoreEnchantments.MOD_ID, "container_or_heart"), CONTAINER_OR_HEART);
    }
}
