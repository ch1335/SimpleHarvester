package com.chen1335.simpleHarvester.inventory;

import com.chen1335.simpleHarvester.API.object.SHBlocks;
import com.chen1335.simpleHarvester.API.object.SHMenuTypes;
import com.chen1335.simpleHarvester.blockEntity.HarvesterBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class HarvesterMenu extends AbstractContainerMenu {
    private final Container container;


    public HarvesterMenu(int containerId, Inventory playerInventory, Container harvesterBlockEntity) {
        super(SHMenuTypes.HARVESTER_MENU.get(), containerId);
        container = harvesterBlockEntity;
        container.startOpen(playerInventory.player);
        int i = -1 * 18;
        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 9; k++) {
                this.addSlot(new Slot(container, k + j * 9, 8 + k * 18, 18 + j * 18));
            }
        }

        for (int l = 0; l < 3; l++) {
            for (int j1 = 0; j1 < 9; j1++) {
                this.addSlot(new Slot(playerInventory, j1 + l * 9 + 9, 8 + j1 * 18, 102 + l * 18 + i));
            }
        }

        for (int i1 = 0; i1 < 9; i1++) {
            this.addSlot(new Slot(playerInventory, i1, 8 + i1 * 18, 160 + i));
        }

    }

    public HarvesterMenu(int containerId, Inventory inventory) {
        this(containerId, inventory, new HarvesterBlockEntity(new BlockPos(0, 0, 0), SHBlocks.WOODEN_HARVESTER.value().defaultBlockState()));
    }


    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < 27) {
                if (!this.moveItemStackTo(itemstack1, 27, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, 27, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return container.stillValid(player);
    }

}
