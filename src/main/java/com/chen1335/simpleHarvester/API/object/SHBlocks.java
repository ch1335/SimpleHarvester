package com.chen1335.simpleHarvester.API.object;

import com.chen1335.simpleHarvester.SimpleHarvester;
import com.chen1335.simpleHarvester.block.SimpleHarvesterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SHBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.createBlocks(SimpleHarvester.MODID);

    public static final DeferredHolder<Block, SimpleHarvesterBlock> WOODEN_HARVESTER = BLOCKS.register("wooden_harvester",
            () -> new SimpleHarvesterBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WOOD)
                    .sound(SoundType.WOOD)
                    .strength(1.0F),
                    1
            ));
    public static final DeferredHolder<Block, SimpleHarvesterBlock> IRON_HARVESTER = BLOCKS.register("iron_harvester",
            () -> new SimpleHarvesterBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .sound(SoundType.STONE)
                    .strength(5,6),
                    2
            ));
    public static final DeferredHolder<Block, SimpleHarvesterBlock> GOLD_HARVESTER = BLOCKS.register("gold_harvester",
            () -> new SimpleHarvesterBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.GOLD)
                    .sound(SoundType.STONE)
                    .strength(3,6),
                    3
            ));
    public static final DeferredHolder<Block, SimpleHarvesterBlock> DIAMOND_HARVESTER = BLOCKS.register("diamond_harvester",
            () -> new SimpleHarvesterBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.DIAMOND)
                    .sound(SoundType.STONE)
                    .strength(5,6),
                    4
            ));
}
