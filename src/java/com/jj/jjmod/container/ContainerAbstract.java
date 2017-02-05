package com.jj.jjmod.container;

import com.jj.jjmod.capabilities.CapInventory;
import com.jj.jjmod.capabilities.DefaultCapInventory;
import com.jj.jjmod.container.slots.SlotInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class ContainerAbstract extends Container {

    public static final int SLOT_SIZE = 18;
    public static final int HOT_Y = 142;
    public static final int ROW_LENGTH = 9;
    public static final int INV_Y = 84;
    public static final int INV_X = 8;

    public EntityPlayer player;
    public DefaultCapInventory capInv;
    public InventoryPlayer playerInv;
    public World world;

    public ContainerAbstract(EntityPlayer player, World world) {

        this.player = player;
        this.capInv = (DefaultCapInventory) player
                .getCapability(CapInventory.CAP_INVENTORY, null);
        this.playerInv = player.inventory;
        this.world = world;

    }

    protected InventoryCrafting buildCraftMatrix(int columns, int rows,
            int startX, int startY) {

        InventoryCrafting craftMatrix =
                new InventoryCrafting(this, columns, rows);

        for (int i = 0; i < columns; i++) {

            for (int j = 0; j < rows; j++) {

                this.addSlotToContainer(new Slot(craftMatrix,
                        j * columns + i, startX + (i * SLOT_SIZE),
                        startY + (j * SLOT_SIZE)));
            }
        }

        return craftMatrix;
    }

    protected int buildInvGrid() {

        int invIndex = 0;

        for (int m = 0; m < ROW_LENGTH; m++, invIndex++) {

            this.addSlotToContainer(new SlotInventory(this.playerInv,
                    invIndex, getInvX(m), HOT_Y));
        }
        
        invIndex--;
        
        if (this.capInv.getInventoryRows() == 0) {
            
            return invIndex;
        }

        for (int k = 0; k < this.capInv.getInventoryRows(); k++, invIndex++) {

            for (int l = 0; l < ROW_LENGTH; l++) {

                this.addSlotToContainer(new SlotInventory(
                        this.playerInv, invIndex,
                        getInvX(l), getInvY(k)));
            }
        }
        
        return --invIndex;
    }

    protected static int getInvX(int index) {

        return INV_X + index * SLOT_SIZE;
    }

    protected static int getInvY(int index) {

        return INV_Y + index * SLOT_SIZE;
    }

    protected static int getInvIndex(int rowPos, int row) {

        return rowPos + (row + 1) * ROW_LENGTH;
    }

    protected boolean areItemStacksEqual(ItemStack stackA,
            ItemStack stackB) {

        boolean sameItem = stackA.getItem() == stackB.getItem();
        boolean sameMeta = !stackA.getHasSubtypes() || stackA
                .getMetadata() == stackB.getMetadata();
        boolean sameTags = ItemStack.areItemStackTagsEqual(stackA, stackB);

        return sameItem && sameMeta && sameTags;
    }
}
