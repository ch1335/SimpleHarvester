package com.chen1335.simpleHarvester.client.inventoryScreen;

import com.chen1335.simpleHarvester.SimpleHarvester;
import com.chen1335.simpleHarvester.inventory.HarvesterMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class HarvesterScreen extends AbstractContainerScreen<HarvesterMenu> {

    private static final ResourceLocation HARVESTER_GUI = ResourceLocation.fromNamespaceAndPath(SimpleHarvester.MODID,"textures/gui/container/harvester_gui.png");


    public HarvesterScreen(HarvesterMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageHeight = 168;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(HARVESTER_GUI,i,j,0,0,175,165);
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics,mouseX,mouseY);
    }
}
