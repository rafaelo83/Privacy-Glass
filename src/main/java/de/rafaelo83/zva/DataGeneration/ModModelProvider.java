package de.rafaelo83.zva.DataGeneration;

import de.rafaelo83.zva.Items.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class ModModelProvider extends FabricModelProvider {

    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        //generator.registerSimpleCubeAll(ModBlocks.PRIVACY_GLASS);
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
        generator.register(ModItems.MECHANOCHROMIC_POWDER, Models.GENERATED);
        generator.register(ModItems.ELECTROCHROMIC_POWDER, Models.GENERATED);
    }
}
