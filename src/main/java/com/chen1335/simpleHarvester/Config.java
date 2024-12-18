package com.chen1335.simpleHarvester;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = SimpleHarvester.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    private static final ModConfigSpec.IntValue HARVESTING_TIME = BUILDER
            .comment("The time to harvest a crop")
            .defineInRange("HarvestingTime", 2, 0, Integer.MAX_VALUE);

    static final ModConfigSpec SPEC = BUILDER.build();

    public static int harvestingTime;
    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        harvestingTime = HARVESTING_TIME.get();
    }
}
