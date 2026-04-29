package com.example.amuletmod.registry;

import com.example.amuletmod.AmuletMod;
import com.example.amuletmod.block.AmuletTableBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModBlockEntities {

    public static BlockEntityType<AmuletTableBlockEntity> AMULET_TABLE;

    public static void register() {
        AMULET_TABLE = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                AmuletMod.id("amulet_table"),
                BlockEntityType.Builder.create(AmuletTableBlockEntity::new, ModBlocks.AMULET_TABLE).build(null)
        );
    }
}
