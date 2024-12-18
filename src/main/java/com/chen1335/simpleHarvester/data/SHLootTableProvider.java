package com.chen1335.simpleHarvester.data;

import com.chen1335.simpleHarvester.API.object.SHBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SHLootTableProvider extends LootTableProvider {
    public SHLootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, Set.of(), getSubProviders(), registries);
    }

    private static List<SubProviderEntry> getSubProviders() {
        return List.of(
                new LootTableProvider.SubProviderEntry(BlockLoot::new, LootContextParamSets.BLOCK)
        );
    }

    public static class BlockLoot extends BlockLootSubProvider {

        protected BlockLoot(HolderLookup.Provider registries) {
            super(EXPLOSION_RESISTANT, FeatureFlags.REGISTRY.allFlags(), registries);
        }

        private static final Set<Item> EXPLOSION_RESISTANT = Stream.of(
                        SHBlocks.WOODEN_HARVESTER.value(),
                        SHBlocks.IRON_HARVESTER.value(),
                        SHBlocks.GOLD_HARVESTER.value(),
                        SHBlocks.DIAMOND_HARVESTER.value()
                )
                .map(ItemLike::asItem)
                .collect(Collectors.toSet());

        @Override
        protected void generate() {
            Stream.of(
                    SHBlocks.WOODEN_HARVESTER.value(),
                    SHBlocks.IRON_HARVESTER.value(),
                    SHBlocks.GOLD_HARVESTER.value(),
                    SHBlocks.DIAMOND_HARVESTER.value()
            ).forEach(this::dropSelf);
        }

        @Override
        protected @NotNull Iterable<Block> getKnownBlocks() {
            return SHBlocks.BLOCKS.getEntries().stream().map(holder -> ((Block) holder.value())).toList();
        }

    }
}
