package com.chen1335.simpleHarvester.data;

import com.chen1335.simpleHarvester.API.object.SHBlocks;
import com.chen1335.simpleHarvester.SimpleHarvester;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.stream.Stream;

public class SHBlockStateProvider extends BlockStateProvider {
    public SHBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, SimpleHarvester.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        Stream.of(
                SHBlocks.WOODEN_HARVESTER.value(),
                SHBlocks.IRON_HARVESTER.value(),
                SHBlocks.GOLD_HARVESTER.value(),
                SHBlocks.DIAMOND_HARVESTER.value()
        ).forEach(this::simpleBlock);
    }
}
