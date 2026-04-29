package com.example.amuletmod.registry;

import com.example.amuletmod.AmuletMod;
import com.example.amuletmod.block.AmuletTableBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;

public class ModBlocks {

    public static final Block AMULET_TABLE = new AmuletTableBlock(
            AbstractBlock.Settings.copy(Blocks.CRAFTING_TABLE)
                    .strength(3.0f)
                    .sounds(BlockSoundGroup.AMETHYST_BLOCK)
    );

    public static void register() {
        Registry.register(Registries.BLOCK, AmuletMod.id("amulet_table"), AMULET_TABLE);
        Registry.register(Registries.ITEM, AmuletMod.id("amulet_table"),
                new BlockItem(AMULET_TABLE, new Item.Settings()));
    }
}
