package com.chen1335.simpleHarvester.blockEntity;

import com.chen1335.simpleHarvester.API.object.SHBlockEntities;
import com.chen1335.simpleHarvester.Config;
import com.chen1335.simpleHarvester.inventory.HarvesterMenu;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class HarvesterBlockEntity extends BaseContainerBlockEntity {

    private int range;

    private NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);

    private int step = 0;

    private int totalStep;

    public HarvesterBlockEntity(BlockPos pos, BlockState blockState) {
        this(pos, blockState, 1);
    }

    public HarvesterBlockEntity(BlockPos pos, BlockState blockState, int range) {
        super(SHBlockEntities.HARVESTER_BLOCK_ENTITY.value(), pos, blockState);
        this.range = range;
        totalStep = (int) Math.pow(range * 2 + 1, 2);
    }

    @Override
    protected @NotNull Component getDefaultName() {
        return Component.translatable("simple_harvester.harvester");
    }

    @Override
    protected @NotNull NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void setItems(@NotNull NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int containerId, @NotNull Inventory inventory) {
        return new HarvesterMenu(containerId, inventory, this);
    }

    @Override
    public int getContainerSize() {
        return items.size();
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);
        range = tag.getInt("range");
        totalStep = (int) Math.pow(range * 2 + 1, 2);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, this.items, registries);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("range", range);
        ContainerHelper.saveAllItems(tag, this.items, registries);
    }

    public ItemStack insertItem(int slot, ItemStack inputItem) {
        ItemStack originalItem = getItem(slot);
        if (originalItem.isEmpty()) {
            setItem(slot, inputItem);
            setChanged();
            return ItemStack.EMPTY;
        }

        if (ItemStack.isSameItemSameComponents(originalItem, inputItem)) {
            int remain = originalItem.getMaxStackSize() - originalItem.getCount();
            int moveCount = Math.min(remain, inputItem.getCount());
            originalItem.grow(moveCount);
            inputItem.shrink(moveCount);
            setChanged();
            return inputItem;
        }
        return inputItem;
    }

    private ItemStack autoOutput(ServerLevel serverLevel, ItemStack outputStack) {
        @Nullable IItemHandler itemHandler = serverLevel.getCapability(Capabilities.ItemHandler.BLOCK, this.getBlockPos().above(), Direction.DOWN);
        if (itemHandler != null) {
            for (int i = 0; i < itemHandler.getSlots(); i++) {
                outputStack = itemHandler.insertItem(i, outputStack, false);
                if (outputStack.isEmpty()) {
                    break;
                }
            }
        }
        return outputStack;
    }

    private void tryHarvest(@NotNull ServerLevel level, @NotNull BlockPos harvestingPos, BlockState cropState, CropBlock cropBlock, BlockState harvesterState) {
        if (cropBlock.getAge(cropState) >= cropBlock.getMaxAge()) {
            boolean success = false;
            LootParams.Builder lootparams$builder = new LootParams.Builder(level)
                    .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(harvestingPos))
                    .withParameter(LootContextParams.BLOCK_STATE, cropState)
                    .withParameter(LootContextParams.TOOL, Items.NETHERITE_HOE.getDefaultInstance());
            List<ItemStack> drops = cropState.getDrops(lootparams$builder);
            for (ItemStack drop : drops) {
                if (drop.isEmpty()) {
                    return;
                }
                for (int i = 0; i < items.size(); i++) {
                    ItemStack output = autoOutput(level, drop.copy());
                    ItemStack itemStack = insertItem(i, output);
                    if (!ItemStack.matches(output, itemStack)) {
                        success = true;
                    }
                    if (itemStack.isEmpty()) {
                        success = true;
                        break;
                    }
                }
            }

            if (success) {
                level.playSound(null, harvestingPos, cropBlock.getSoundType(cropState, level, harvestingPos, null).getBreakSound(), SoundSource.BLOCKS, 1F, 1F);
                level.setBlock(harvestingPos, cropBlock.getStateForAge(0), 2);
            }
        }
    }

    private void tryHarvestCactusOrSugarCane(@NotNull ServerLevel level, @NotNull BlockPos harvestingPos, BlockState cropBlockState, Block cropBlock, BlockState harvesterState) {
        List<Pair<BlockPos, BlockState>> blockPos = new ArrayList<>();

        int up = 1;
        while (true) {
            BlockPos blockPosToCheck = harvestingPos.above(up);
            BlockState blockState = level.getBlockState(blockPosToCheck);
            if (blockState.getBlock() == cropBlock) {
                blockPos.add(Pair.of(blockPosToCheck, blockState));
                up++;
            } else {
                break;
            }
        }
        blockPos = blockPos.reversed();

        blockPos.forEach(pair -> {
            boolean success = false;
            LootParams.Builder lootparams$builder = new LootParams.Builder(level)
                    .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pair.getFirst()))
                    .withParameter(LootContextParams.BLOCK_STATE, pair.getSecond())
                    .withParameter(LootContextParams.TOOL, Items.NETHERITE_HOE.getDefaultInstance());
            List<ItemStack> drops = pair.getSecond().getDrops(lootparams$builder);
            for (ItemStack drop : drops) {
                if (drop.isEmpty()) {
                    return;
                }
                for (int i = 0; i < items.size(); i++) {
                    ItemStack output = autoOutput(level, drop.copy());
                    ItemStack itemStack = insertItem(i, output);
                    if (!ItemStack.matches(output, itemStack)) {
                        success = true;
                    }
                    if (itemStack.isEmpty()) {
                        success = true;
                        break;
                    }
                }
            }

            if (success) {
                level.playSound(null, harvestingPos, cropBlock.getSoundType(cropBlockState, level, harvestingPos, null).getBreakSound(), SoundSource.BLOCKS, 1F, 1F);
                level.setBlock(pair.getFirst(), Blocks.AIR.defaultBlockState(), 2);
            }
        });
    }

    public static void tick(Level level, BlockPos harvesterPos, BlockState harvesterState, HarvesterBlockEntity harvesterBlockEntity) {
        if (level.isClientSide || harvesterBlockEntity.range <= 0) {
            return;
        }
        int range = harvesterBlockEntity.range;
        if (level.getDayTime() % Config.harvestingTime == 0) {
            while (harvesterBlockEntity.step <= harvesterBlockEntity.totalStep) {
                int x = harvesterBlockEntity.step % (range * 2 + 1) - range;
                int y = harvesterBlockEntity.step / (range * 2 + 1) - range;
                BlockPos harvestingPos = harvesterPos.east(x).south(y);
                BlockState cropState = level.getBlockState(harvesterPos.east(x).south(y));
                if (cropState.getBlock() instanceof CropBlock cropBlock) {
                    harvesterBlockEntity.tryHarvest((ServerLevel) level, harvestingPos, cropState, cropBlock, harvesterState);
                    harvesterBlockEntity.step++;
                    break;
                } else if (cropState.getBlock() instanceof SugarCaneBlock || cropState.getBlock() instanceof CactusBlock) {
                    harvesterBlockEntity.tryHarvestCactusOrSugarCane((ServerLevel) level, harvestingPos, cropState, cropState.getBlock(), harvesterState);
                    harvesterBlockEntity.step++;
                    break;
                }
                harvesterBlockEntity.step++;
            }

            if (harvesterBlockEntity.step >= harvesterBlockEntity.totalStep) {
                harvesterBlockEntity.step = 0;
            }
        }
    }
}
