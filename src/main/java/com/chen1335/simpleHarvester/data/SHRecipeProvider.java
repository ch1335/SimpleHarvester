package com.chen1335.simpleHarvester.data;

import com.chen1335.simpleHarvester.API.object.SHBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class SHRecipeProvider extends RecipeProvider {
    public SHRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, SHBlocks.WOODEN_HARVESTER.value())
                .define('W', ItemTags.PLANKS)
                .define('H', Items.WOODEN_HOE)
                .define('R', Items.REDSTONE)
                .define('I', Items.IRON_INGOT)
                .pattern("WHW")
                .pattern("WIW")
                .pattern("WRW")
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .save(recipeOutput);

        buildHarvesterUpdateRecipe(recipeOutput, SHBlocks.WOODEN_HARVESTER.value(), Items.IRON_INGOT, SHBlocks.IRON_HARVESTER.value());
        buildHarvesterUpdateRecipe(recipeOutput, SHBlocks.IRON_HARVESTER.value(), Items.GOLD_INGOT, SHBlocks.GOLD_HARVESTER.value());
        buildHarvesterUpdateRecipe(recipeOutput, SHBlocks.GOLD_HARVESTER.value(), Items.DIAMOND, SHBlocks.DIAMOND_HARVESTER.value());
    }

    private void buildHarvesterUpdateRecipe(@NotNull RecipeOutput recipeOutput, ItemLike mainItem, ItemLike updateMaterial, ItemLike result) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result)
                .define('U', updateMaterial)
                .define('M', mainItem)
                .pattern("UUU")
                .pattern("UMU")
                .pattern("UUU")
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .save(recipeOutput);
    }
}
