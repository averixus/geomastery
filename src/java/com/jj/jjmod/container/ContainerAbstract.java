package com.jj.jjmod.container;

import com.jj.jjmod.capabilities.CapPlayer;
import com.jj.jjmod.capabilities.DefaultCapPlayer;
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
    public DefaultCapPlayer capability;
    public InventoryPlayer playerInv;
    public World world;

    public ContainerAbstract(EntityPlayer player, World world) {

        this.player = player;
        this.capability = (DefaultCapPlayer) player
                .getCapability(CapPlayer.CAP_PLAYER, null);
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

    protected void buildHotbar() {


        for (int m = 0; m < ROW_LENGTH; m++) {

            this.addSlotToContainer(new SlotInventory(this.playerInv,
                    m, getInvX(m), HOT_Y));
        }
    }
    
    protected int buildInvgrid() { 
        
        int invIndex = -1;

        if (this.capability.getInventoryRows() == 0) {
            
            return invIndex;
        }

        for (int k = 0; k < this.capability.getInventoryRows(); k++) {

            for (int l = 0; l < ROW_LENGTH; l++) {

                this.addSlotToContainer(new SlotInventory(
                        this.playerInv, ++invIndex + ROW_LENGTH,
                        getInvX(l), getInvY(invertRowIndex(k))));
            }
        }
        
        return invIndex;
    }

    protected static int getInvX(int index) {

        return INV_X + index * SLOT_SIZE;
    }

    protected static int getInvY(int index) {
        System.out.println("inventory slot y " + (INV_Y + index * SLOT_SIZE));
        return INV_Y + index * SLOT_SIZE;
    }
    
    protected static int invertRowIndex(int index) {
        
        if (index == 0) {
            
            return 2;
        }
        
        if (index == 2) {
            
            return 0;
        }
        
        return index;
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
