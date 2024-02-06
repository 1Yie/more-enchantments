package moe.ingstar.enchant.Item;

import moe.ingstar.enchant.MoreEnchantments;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ItemRegister {
    public static final Item CONTAINER_OF_HEART = new ContainerOfHeart(new FabricItemSettings());



    public static void register() {
        Registry.register(Registries.ITEM, new Identifier(MoreEnchantments.MOD_ID, "container_of_heart"), CONTAINER_OF_HEART);
    }
}
