package com.chen1335.simpleHarvester.API.object;

import com.chen1335.simpleHarvester.SimpleHarvester;
import com.chen1335.simpleHarvester.block.BaseHarvester;
import com.chen1335.simpleHarvester.item.HarvesterBlockItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SHItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SimpleHarvester.MODID);

    public static final DeferredItem<BlockItem> WOODEN_HARVESTER = registerHarvesterBlockItem("wooden_harvester", SHBlocks.WOODEN_HARVESTER);
    public static final DeferredItem<BlockItem> IRON_HARVESTER = registerHarvesterBlockItem("iron_harvester", SHBlocks.IRON_HARVESTER);
    public static final DeferredItem<BlockItem> GOLD_HARVESTER = registerHarvesterBlockItem("gold_harvester", SHBlocks.GOLD_HARVESTER);
    public static final DeferredItem<BlockItem> DIAMOND_HARVESTER = registerHarvesterBlockItem("diamond_harvester", SHBlocks.DIAMOND_HARVESTER);

    private static DeferredItem<BlockItem> registerHarvesterBlockItem(String name, DeferredHolder<Block, ? extends BaseHarvester> harvester) {
       return ITEMS.register(name, () -> new HarvesterBlockItem(harvester.value(), new Item.Properties()));
    }
}
