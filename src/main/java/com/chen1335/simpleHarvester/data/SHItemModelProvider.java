package com.chen1335.simpleHarvester.data;

import com.chen1335.simpleHarvester.API.object.SHBlocks;
import com.chen1335.simpleHarvester.SimpleHarvester;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class SHItemModelProvider extends ItemModelProvider {
    public SHItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, SimpleHarvester.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        SHBlocks.BLOCKS.getEntries().forEach(blockDeferredHolder -> {
            simpleBlockItem(blockDeferredHolder.value());
        });
    }
}
