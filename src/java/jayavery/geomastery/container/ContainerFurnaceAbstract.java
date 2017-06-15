/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.container;

import jayavery.geomastery.blocks.BlockComplexAbstract;
import jayavery.geomastery.tileentities.TEFurnaceAbstract;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Abstract superclass for furnace containers. */
public abstract class ContainerFurnaceAbstract extends ContainerAbstract {

    /** X-position of flame in the GUI. */
    public int fireX;
    /** Y-position of flame in the GUI. */
    public int fireY = 36;
    /** X-position of progress arrow in the GUI. */
    public int arrowX;
    /** Y-position of progress arrow in the GUI. */
    public int arrowY = 34;
    /** Number of input, fuel, and output slots. */
    public int size;
    
    /** Y-position of start of input slots. */
    protected static final int INPUT_Y = 17;
    /** Y-position of start of fuel slots. */
    protected static final int FUEL_Y = 53;

    /** Index of first input slot. */
    protected static final int INPUT_START = 0;
    
    /** Index of last input slot. */
    protected final int inputEnd;
    /** Index of first fuel slot. */
    protected final int fuelStart;
    /** Index of last fuel slot. */
    protected final int fuelEnd;
    /** Index of first output slot. */
    protected final int outputStart;
    /** Index of last output slot. */
    protected final int outputEnd;
    /** Index of first hotbar slot. */
    protected final int hotStart;
    /** Index of last hotbar slot. */
    protected final int hotEnd;
    /** Index of first inventory slot. */
    protected final int invStart;
    /** Index of last inventory slot. */
    protected final int invEnd;

    /** The furnace TileEntity for this container. */
    public final TEFurnaceAbstract<?> furnace;
    /** The position of this container. */
    protected BlockPos pos;

    public ContainerFurnaceAbstract(EntityPlayer player, World world,
            TEFurnaceAbstract<?> furnace, BlockPos pos) {

        super(player, world);
        this.furnace = furnace;
        this.pos = pos;
        this.size = furnace.size();
        
        // Furnace slots
        this.buildInput();
        this.buildFuel();
        this.buildOutput();

        // Inventory slots
        this.buildHotbar();
        int invIndex = this.buildInvgrid();

        // Container indices
        this.inputEnd = INPUT_START + this.size - 1;
        this.fuelStart = this.inputEnd + 1;
        this.fuelEnd = this.fuelStart + this.size - 1;
        this.outputStart = this.fuelEnd + 1;
        this.outputEnd = this.outputStart + this.size - 1;
        this.hotStart = this.outputEnd + 1;
        this.hotEnd = this.hotStart + 8;
        this.invStart = this.hotEnd + 1;
        this.invEnd = this.invStart + invIndex;
        
        this.detectAndSendChanges();
    }
    
    /** Builds this furnace's input slots. */
    protected abstract void buildInput();
    
    /** Builds this furnace's fuel slots. */
    protected abstract void buildFuel();
    
    /** Builds this furnace's output slots. */
    protected abstract void buildOutput();

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

    /** Sorts the input and fuel lists when any slot is clicked. */
    @Override
    public ItemStack slotClick(int slot, int drag, ClickType click,
            EntityPlayer player) {
        
        ItemStack result = super.slotClick(slot, drag, click, player);
        this.furnace.sort();
        return result;
    }
    
    /** Prevents slot click from being repeated if the
     * same item moves into the slot after sorting. */
    @Override
    public void retrySlotClick(int slot, int click, boolean mode,
            EntityPlayer player) {}
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {

        ItemStack result = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {

            ItemStack inSlot = slot.getStack();
            result = inSlot.copy();

            if (index >= this.outputStart && index <= this.outputEnd) {

                if (!this.mergeItemStack(inSlot, this.hotStart,
                        this.invEnd + 1, true)) {

                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(inSlot, result);

            } else if (!(index >= INPUT_START && index <= this.inputEnd) &&
                    !(index >= this.fuelStart && index <= this.fuelEnd)) {

                if (!this.furnace.recipes.getCookingResult(inSlot, this.world)
                        .isEmpty()) {

                    if (!this.mergeItemStack(inSlot, INPUT_START,
                            this.inputEnd + 1, false)) {

                        return ItemStack.EMPTY;
                    }

                } else if (this.furnace.recipes.getFuelTime(inSlot) > 0) {

                    if (!this.mergeItemStack(inSlot, this.fuelStart,
                            this.fuelEnd + 1, false)) {

                        return ItemStack.EMPTY;
                    }

                } else if (index >= this.invStart && index <= this.invEnd) {

                    if (!this.mergeItemStack(inSlot, this.hotStart,
                            this.hotEnd + 1, false)) {

                        return ItemStack.EMPTY;
                    }

                } else if (index >= this.hotStart && index <= this.hotEnd)

                    if (!this.mergeItemStack(inSlot, this.invStart,
                            this.invEnd + 1, false)) {

                        return ItemStack.EMPTY;
                    }

            } else if (!this.mergeItemStack(inSlot, this.hotStart,
                    this.invEnd + 1, false)) {

                return ItemStack.EMPTY;
            }

            if (inSlot.getCount() == 0) {

                slot.putStack(ItemStack.EMPTY);

            } else {

                slot.onSlotChanged();
            }

            if (inSlot.getCount() == result.getCount()) {

                return ItemStack.EMPTY;
            }

            slot.onTake(player, inSlot);
        }

        return result;
    }
}
