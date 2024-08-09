package com.cwelth.banishedcore.gui;

import com.cwelth.banishedcore.tileentities.PortalCoreTE;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class PortalCoreContainer extends Container {
    protected PortalCoreTE tileEntity;
    protected int ownSlotsCount = 0;

    public PortalCoreContainer(IInventory playerInventory, PortalCoreTE te) {
        this.tileEntity = te;
        this.addOwnSlots();
        this.ownSlotsCount = super.inventorySlots.size();
        this.addPlayerSlots(playerInventory);
    }

    private void addOwnSlots() {
        this.addSlotToContainer(new SlotItemHandler(this.tileEntity.itemStackHandler, 0, 25, 40));
    }

    private void addPlayerSlots(IInventory playerInventory) {
        int row;
        int item;
        for(row = 0; row < 3; ++row) {
            for(item = 0; item < 9; ++item) {
                int x = 7 + item * 18;
                int y = row * 18 + 105;
                this.addSlotToContainer(new Slot(playerInventory, x + row * 9 + 10 - 1, x, y));
            }
        }

        for(row = 0; row < 9; ++row) {
            item = 7 + row * 18;
            int y = 163;
            this.addSlotToContainer(new Slot(playerInventory, row, item, y));
        }

    }

    @Nullable
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = null;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index < this.ownSlotsCount) {
                if (!this.mergeItemStack(itemstack1, this.ownSlotsCount, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, this.ownSlotsCount, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    public boolean canInteractWith(EntityPlayer playerIn) {
        return this.tileEntity.canInteractWith(playerIn);
    }
}
