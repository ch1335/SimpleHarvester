package com.chen1335.simpleHarvester.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.BaseEntityBlock;
import org.jetbrains.annotations.NotNull;

public class SimpleHarvesterBlock extends BaseHarvester{

    private final int range;

    public SimpleHarvesterBlock(Properties properties, int range) {
        super(properties,range);
        this.range = range;
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(properties -> new SimpleHarvesterBlock(properties,range));
    }

}
