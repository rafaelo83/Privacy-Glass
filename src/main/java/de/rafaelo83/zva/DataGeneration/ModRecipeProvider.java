package de.rafaelo83.zva.DataGeneration;

import de.rafaelo83.zva.Blocks.ModBlocks;
import de.rafaelo83.zva.Items.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter recipeExporter) {
        //offerStonecuttingRecipe(recipeExporter, RecipeCategory.MISC, ModBlocks.SKIBIDI_BLOCK, Blocks.QUARTZ_BLOCK);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModBlocks.PRIVACY_GLASS)
                .input(ModItems.MECHANOCHROMIC_POWDER)
                .input(Items.GLASS)
                .criterion(hasItem(ModBlocks.PRIVACY_GLASS), conditionsFromItem(ModItems.MECHANOCHROMIC_POWDER))
                .offerTo(recipeExporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModItems.MECHANOCHROMIC_POWDER)
                .input(Items.GLOWSTONE_DUST)
                .input(Items.GUNPOWDER)
                .criterion(hasItem(ModItems.MECHANOCHROMIC_POWDER),conditionsFromItem(Items.GLOWSTONE))
                .offerTo(recipeExporter);
    }
}
