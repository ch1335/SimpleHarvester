package com.chen1335.simpleHarvester.API.object;

import com.chen1335.simpleHarvester.SimpleHarvester;
import com.chen1335.simpleHarvester.blockEntity.HarvesterBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SHBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, SimpleHarvester.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<HarvesterBlockEntity>> HARVESTER_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("harvester_block_entity",
            () -> BlockEntityType.Builder.of(HarvesterBlockEntity::new,
                    SHBlocks.WOODEN_HARVESTER.value(),
                    SHBlocks.IRON_HARVESTER.value(),
                    SHBlocks.GOLD_HARVESTER.value(),
                    SHBlocks.DIAMOND_HARVESTER.value()
                    ).build(null));
}
