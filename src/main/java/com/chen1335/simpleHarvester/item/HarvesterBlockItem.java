package com.chen1335.simpleHarvester.item;

import com.chen1335.simpleHarvester.block.BaseHarvester;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HarvesterBlockItem extends BlockItem {
    public HarvesterBlockItem(BaseHarvester block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add(Component.translatable("harvest_radius").append(Component.literal(String.valueOf(((BaseHarvester) getBlock()).range))).withColor(ChatFormatting.DARK_GRAY.getColor()));
    }
}
