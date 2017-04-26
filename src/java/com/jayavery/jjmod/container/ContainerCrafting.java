package com.jayavery.jjmod.container;

import javax.annotation.Nullable;
import com.jayavery.jjmod.blocks.BlockComplexAbstract;
import com.jayavery.jjmod.container.slots.SlotCrafting;
import com.jayavery.jjmod.crafting.CraftingManager;
import com.jayavery.jjmod.init.ModBlocks;
import com.jayavery.jjmod.init.ModCaps;
import com.jayavery.jjmod.tileentities.TECraftingAbstract;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Container for Crafting devices. */
public class ContainerCrafting extends ContainerAbstract {

    /** X-position of the output slot. */
    private static final int OUTPUT_X = 124;
    /** Y-position of the output slot. */
    private static final int OUTPUT_Y = 35;
    /** X-position of the start of the craft grid. */
    private static final int CRAFT_X = 30;
    /** Y-position of the start of the craft grid. */
    private static final int CRAFT_Y = 17;
    /** Columns of the craft grid. */
    private static final int CRAFT_COLS = 3;
    /** Rows of the craft grid. */
    private static final int CRAFT_ROWS = 3;

    /** Index of the start of player hotbar. */
    private static final int HOT_START = 0;
    /** Index of the end of player hotbar. */
    private static final int HOT_END = 8;
    /** Index of the start of player inventory. */
    private static final int INV_START = 9;

    /** Index of the end of player inventory. */
    private final int invEnd;
    /** Index of the start of craft grid. */
    private final int craftStart;
    /** Index of the end of craft grid. */
    private final int craftEnd;
    /** Index of the output slot. */
    private final int outputI;

    /** Inventory of the craft grid. */
    private InventoryCrafting craftMatrix;
    /** Inventory of the craft output. */
    private IInventory craftResult = new InventoryCraftResult();
    /** Crafting manager of this crafter. */
    private CraftingManager craftManager;
    /** Position of this crafter. */    
    private BlockPos pos;
    /** TileEntity of this crafter. */
    public TECraftingAbstract tile;

    public ContainerCrafting(EntityPlayer player, World world, BlockPos pos,
            CraftingManager craftManager, TECraftingAbstract tile) {

        super(player, world);
        this.craftManager = craftManager;
        this.pos = pos;
        this.tile = tile;

        // Inventory
        this.buildHotbar();
        int invIndex = this.buildInvgrid();
        
        // Craft grid
        this.craftMatrix = this.buildCraftMatrix(CRAFT_COLS, CRAFT_ROWS,
                CRAFT_X, CRAFT_Y);
        
        // Output slot
        this.addSlotToContainer(new SlotCrafting(this.player,
                this.craftMatrix, this.craftResult, OUTPUT_X,
                OUTPUT_Y, this.craftManager));

        // Container indices
        this.invEnd = INV_START + invIndex;
        this.craftStart = this.invEnd + 1;
        this.craftEnd = this.invEnd + this.craftMatrix.getSizeInventory();
        this.outputI = this.craftEnd + 1;
        
        this.onCraftMatrixChanged(this.craftMatrix);
    }

    @Override
    public void onCraftMatrixChanged(IInventory inventory) {

        @Nullable
        ItemStack stack = this.craftManager
                .findMatchingRecipe(this.craftMatrix, this.world);
        
        if (stack.hasCapability(ModCaps.CAP_DECAY, null)) {
            
            stack.getCapability(ModCaps.CAP_DECAY, null)
                    .setBirthTime(this.world.getTotalWorldTime());
        }
        
        this.craftResult.setInventorySlotContents(0, stack);
    }
    
    @Override
    public ItemStack slotClick(int slot, int dragType,
            ClickType clickType, EntityPlayer player) {
        
        ItemStack result = super.slotClick(slot, dragType, clickType, player);
        this.onCraftMatrixChanged(this.craftMatrix);
        return result;
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {

        super.onContainerClosed(player);

        if (!this.world.isRemote) {

            for (int i = 0; i < this.craftMatrix.getSizeInventory(); i++) {

                ItemStack stack = this.craftMatrix.removeStackFromSlot(i);

                if (!stack.isEmpty()) {

                    player.dropItem(stack, false);
                }
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {

        boolean correctBlock = this.world.getBlockState(this.pos)
                .getBlock() instanceof BlockComplexAbstract;

        if (correctBlock) {

            return player.getDistanceSq(this.pos.getX() + 0.5,
                    this.pos.getY() + 0.5, this.pos.getZ() + 0.5) <= 64;
        }

        return false;
    }

    @Override
    @Nullable
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {

        ItemStack result = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {

            ItemStack slotStack = slot.getStack();
            result = slotStack.copy();

            if (index == this.outputI) {

                if (!ModBlocks.OFFHAND_ONLY.contains(slotStack) &&
                        !this.mergeItemStack(slotStack, HOT_START,
                        this.invEnd + 1, true)) {

                    return ItemStack.EMPTY;
                    
                } else {

                    slot.onTake(player, slotStack);
                }
                
                slot.onSlotChange(slotStack, result);

            } else if (index >= HOT_START && index <= HOT_END) {

                if (!this.mergeItemStack(slotStack, INV_START,
                        this.invEnd + 1, true)) {

                    return ItemStack.EMPTY;
                }

            } else if (index >= INV_START && index <= this.invEnd) {

                if (!this.mergeItemStack(slotStack, HOT_START,
                        HOT_END + 1, true)) {

                    return ItemStack.EMPTY;
                }

            } else if (index >= this.craftStart && index <= this.craftEnd) {

                if (!this.mergeItemStack(slotStack, HOT_START,
                        this.invEnd + 1, true)) {

                    return ItemStack.EMPTY;
                }

            }

            if (slotStack.getCount() == 0) {

                slot.putStack(ItemStack.EMPTY);
                
            } else {

                slot.onSlotChanged();
            }
        }

        return result;
    }

    @Override
    public boolean canMergeSlot(ItemStack stack, Slot slot) {

        return slot.inventory != this.craftResult &&
                super.canMergeSlot(stack, slot);
    }
}
