package com.chen1335.simpleHarvester;

import com.chen1335.simpleHarvester.API.capabilities.HarvesterItemHandler;
import com.chen1335.simpleHarvester.API.object.SHBlockEntities;
import com.chen1335.simpleHarvester.API.object.SHBlocks;
import com.chen1335.simpleHarvester.API.object.SHItems;
import com.chen1335.simpleHarvester.API.object.SHMenuTypes;
import com.chen1335.simpleHarvester.block.BaseHarvester;
import com.chen1335.simpleHarvester.block.SimpleHarvesterBlock;
import com.chen1335.simpleHarvester.client.inventoryScreen.HarvesterScreen;
import com.chen1335.simpleHarvester.data.*;
import com.google.common.collect.ImmutableSet;
import com.mojang.logging.LogUtils;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import org.slf4j.Logger;

@Mod(SimpleHarvester.MODID)
public class SimpleHarvester {
    public static final String MODID = "simple_harvester";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.simple_harvester"))
            .icon(SHItems.WOODEN_HARVESTER::toStack)
            .displayItems((parameters, output) -> {
                SHItems.ITEMS.getEntries().forEach(itemDeferredHolder -> {
                    output.accept(itemDeferredHolder.value());
                });
            }).build());

    public SimpleHarvester(IEventBus modEventBus, ModContainer modContainer) {
        SHBlocks.BLOCKS.register(modEventBus);
        SHItems.ITEMS.register(modEventBus);
        SHBlockEntities.BLOCK_ENTITY_TYPES.register(modEventBus);
        SHMenuTypes.MENU_TYPES.register(modEventBus);
        modEventBus.register(this);
        CREATIVE_MODE_TABS.register(modEventBus);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    @SubscribeEvent
    public void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, SHBlockEntities.HARVESTER_BLOCK_ENTITY.value(), HarvesterItemHandler::new);
    }

    @SubscribeEvent
    public void gatherData(GatherDataEvent event) {
        DataGenerator dataGenerator = event.getGenerator();
        dataGenerator.addProvider(event.includeServer(), new SHBlockTagsProvider(dataGenerator.getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper()));
        dataGenerator.addProvider(event.includeServer(), new SHLootTableProvider(dataGenerator.getPackOutput(), event.getLookupProvider()));
        dataGenerator.addProvider(event.includeServer(), new SHBlockStateProvider(dataGenerator.getPackOutput(), event.getExistingFileHelper()));
        dataGenerator.addProvider(event.includeServer(), new SHItemModelProvider(dataGenerator.getPackOutput(), event.getExistingFileHelper()));
        dataGenerator.addProvider(event.includeServer(), new SHRecipeProvider(dataGenerator.getPackOutput(), event.getLookupProvider()));
    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void registerMenu(RegisterMenuScreensEvent event) {
            event.register(SHMenuTypes.HARVESTER_MENU.value(), HarvesterScreen::new);
        }
    }
}
