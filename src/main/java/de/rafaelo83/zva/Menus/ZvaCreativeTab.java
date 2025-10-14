package de.rafaelo83.zva.Menus;

import de.rafaelo83.zva.Blocks.ModBlocks;
import de.rafaelo83.zva.Items.ModItems;
import de.rafaelo83.zva.Zva;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ZvaCreativeTab {
    public static final ItemGroup ZVA_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(Zva.MOD_ID, "pink_garnet_group"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.zva"))
                    .icon(() -> new ItemStack(ModBlocks.PRIVACY_GLASS)).entries((displayContext, entries) -> {
                        entries.add(ModBlocks.PRIVACY_GLASS);
                        entries.add(ModItems.PHOTOCHROMIC_POWDER);
                        entries.add(ModBlocks.PRIVACY_GLASS_ELECTRIC);
                        entries.add(ModItems.ELECTROCHROMIC_POWDER);
                    }).build());



    public static void init() {}
}
