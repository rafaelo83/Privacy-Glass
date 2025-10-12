package de.rafaelo83.zva.DataGeneration;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;

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
        //generator.register(ModItems.SPARE_BLADE, Models.HANDHELD);
    }
}
