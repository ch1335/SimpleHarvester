package com.chen1335.simpleHarvester.API.capabilities;

import com.chen1335.simpleHarvester.blockEntity.HarvesterBlockEntity;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.items.wrapper.InvWrapper;

public class HarvesterItemHandler extends InvWrapper {

    public final HarvesterBlockEntity blockEntity;

    public HarvesterItemHandler(HarvesterBlockEntity blockEntity, Direction context) {
        super(blockEntity);
        this.blockEntity = blockEntity;
    }

}
