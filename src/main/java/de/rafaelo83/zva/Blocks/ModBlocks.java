package de.rafaelo83.zva.Blocks;

import de.rafaelo83.zva.Blocks.AdvancedBlocks.PGlass.Privacy_Glass.PrivacyGlassBlock;
import de.rafaelo83.zva.Blocks.AdvancedBlocks.PGlass.Privacy_Glass_Electric.PrivacyGlassElectricBlock;
import de.rafaelo83.zva.Zva;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block PRIVACY_GLASS = registerBlock("privacy_glass",
            new PrivacyGlassBlock(AbstractBlock.Settings.create()));
    public static final Block PRIVACY_GLASS_ELECTRIC = registerBlock("privacy_glass_electric",
            new PrivacyGlassElectricBlock(AbstractBlock.Settings.create()));


    private static Block registerBlockWithoutBlockItem(String name, Block block) {
        return Registry.register(Registries.BLOCK, Identifier.of(Zva.MOD_ID, name), block);
    }

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(Zva.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(Zva.MOD_ID, name),
                new BlockItem(block,new  Item.Settings()));
    }

    public static void init() {}
}
