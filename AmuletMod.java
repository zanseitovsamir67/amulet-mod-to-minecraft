package com.example.amuletmod.registry;

import com.example.amuletmod.AmuletMod;
import com.example.amuletmod.screen.AmuletTableScreenHandler;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandlers {

    public static ScreenHandlerType<AmuletTableScreenHandler> AMULET_TABLE;

    public static void register() {
        AMULET_TABLE = Registry.register(
                Registries.SCREEN_HANDLER,
                AmuletMod.id("amulet_table"),
                new ScreenHandlerType<>(AmuletTableScreenHandler::new, net.minecraft.resource.featuretoggle.FeatureSet.empty())
        );
    }
}
