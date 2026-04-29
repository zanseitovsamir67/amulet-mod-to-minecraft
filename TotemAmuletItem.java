package com.example.amuletmod.screen;

import com.example.amuletmod.block.AmuletTableBlockEntity;
import com.example.amuletmod.item.AmuletData;
import com.example.amuletmod.item.AmuletItem;
import com.example.amuletmod.registry.ModBlocks;
import com.example.amuletmod.registry.ModScreenHandlers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;

public class AmuletTableScreenHandler extends ScreenHandler {

    private final Inventory inventory;
    private final ScreenHandlerContext context;

    /** Client-side constructor (called by Fabric). */
    public AmuletTableScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(2), ScreenHandlerContext.EMPTY);
    }

    /** Server-side constructor. */
    public AmuletTableScreenHandler(int syncId, PlayerInventory playerInventory,
                                    Inventory inventory, ScreenHandlerContext context) {
        super(ModScreenHandlers.AMULET_TABLE, syncId);
        checkSize(inventory, 2);
        this.inventory = inventory;
        this.context = context;
        inventory.onOpen(playerInventory.player);

        // Slot 0: amulet (only amulet items)
        addSlot(new Slot(inventory, 0, 26, 47) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.getItem() instanceof AmuletItem;
            }
            @Override public int getMaxItemCount() { return 1; }
        });

        // Slot 1: diamonds
        addSlot(new Slot(inventory, 1, 134, 47) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isOf(Items.DIAMOND);
            }
        });

        // Player inventory (3 rows x 9 cols)
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 104 + row * 18));
            }
        }
        // Hotbar
        for (int col = 0; col < 9; col++) {
            addSlot(new Slot(playerInventory, col, 8 + col * 18, 162));
        }
    }

    /** Button id 0..7 = upgrade stat. */
    @Override
    public boolean onButtonClick(PlayerEntity player, int id) {
        if (id < 0 || id >= AmuletData.STATS.length) return false;
        ItemStack amulet = inventory.getStack(0);
        ItemStack diamonds = inventory.getStack(1);
        if (amulet.isEmpty() || diamonds.isEmpty()) return false;
        if (!(amulet.getItem() instanceof AmuletItem)) return false;
        if (!diamonds.isOf(Items.DIAMOND)) return false;

        if (AmuletData.upgrade(amulet, id)) {
            diamonds.decrement(1);
            inventory.markDirty();
            sendContentUpdates();
            return true;
        }
        return false;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot s = this.slots.get(slot);
        if (s != null && s.hasStack()) {
            ItemStack original = s.getStack();
            newStack = original.copy();
            if (slot < 2) {
                if (!insertItem(original, 2, slots.size(), true)) return ItemStack.EMPTY;
            } else {
                if (original.getItem() instanceof AmuletItem) {
                    if (!insertItem(original, 0, 1, false)) return ItemStack.EMPTY;
                } else if (original.isOf(Items.DIAMOND)) {
                    if (!insertItem(original, 1, 2, false)) return ItemStack.EMPTY;
                } else {
                    return ItemStack.EMPTY;
                }
            }
            if (original.isEmpty()) s.setStack(ItemStack.EMPTY);
            else s.markDirty();
        }
        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return canUse(context, player, ModBlocks.AMULET_TABLE);
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        // Drop or return items in slots back to player
        if (inventory instanceof AmuletTableBlockEntity) {
            // contents stay in block
        } else {
            this.dropInventory(player, inventory);
        }
        inventory.onClose(player);
    }
}
