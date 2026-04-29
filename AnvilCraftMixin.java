package com.example.amuletmod.registry;

import com.example.amuletmod.AmuletMod;
import com.example.amuletmod.item.AmuletItem;
import com.example.amuletmod.item.TotemAmuletItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModItems {

    public static final Item AMULET = new AmuletItem(new Item.Settings().maxCount(1));
    public static final Item TOTEM_AMULET = new TotemAmuletItem(new Item.Settings().maxCount(1).rarity(net.minecraft.util.Rarity.UNCOMMON));

    public static void register() {
        Registry.register(Registries.ITEM, AmuletMod.id("amulet"), AMULET);
        Registry.register(Registries.ITEM, AmuletMod.id("totem_amulet"), TOTEM_AMULET);

        // Block item registered separately in ModBlocks

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> {
            entries.add(AMULET);
            entries.add(TOTEM_AMULET);
            entries.add(ModBlocks.AMULET_TABLE.asItem());
        });
    }
}
