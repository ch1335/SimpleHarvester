package com.chen1335.simpleHarvester.API.object;

import com.chen1335.simpleHarvester.SimpleHarvester;
import com.chen1335.simpleHarvester.inventory.HarvesterMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SHMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(BuiltInRegistries.MENU, SimpleHarvester.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<HarvesterMenu>> HARVESTER_MENU = MENU_TYPES.register("harvester",()-> new MenuType<>(HarvesterMenu::new, FeatureFlags.VANILLA_SET));
}
