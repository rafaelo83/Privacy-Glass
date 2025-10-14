package de.rafaelo83.zva.Items;

import de.rafaelo83.zva.Zva;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item MECHANOCHROMIC_POWDER = registerItem("mechanochromic_powder", new Item(new Item.Settings().maxCount(64)));
    public static final Item ELECTROCHROMIC_POWDER = registerItem("electrochromic_powder", new Item(new Item.Settings().maxCount(64)));



    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(Zva.MOD_ID, name), item);
    }

    public static void init() {}
}
