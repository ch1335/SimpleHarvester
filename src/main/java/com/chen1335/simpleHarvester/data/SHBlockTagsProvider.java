package com.chen1335.simpleHarvester.data;

import com.chen1335.simpleHarvester.API.object.SHBlocks;
import com.chen1335.simpleHarvester.SimpleHarvester;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class SHBlockTagsProvider extends BlockTagsProvider {
    public SHBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, SimpleHarvester.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(BlockTags.MINEABLE_WITH_AXE).add(
                SHBlocks.WOODEN_HARVESTER.value()
        );

        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                SHBlocks.IRON_HARVESTER.value(),
                SHBlocks.GOLD_HARVESTER.value(),
                SHBlocks.DIAMOND_HARVESTER.value()
        );
    }
}
