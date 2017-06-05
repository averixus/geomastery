package jayavery.geomastery.container.slots;

import jayavery.geomastery.tileentities.TEFurnaceAbstract;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/** Container inventory slot for furnace inputs. */
public class SlotFurnaceInput extends Slot {

    /** The furnace this slot draws inventory from. */
    private final TEFurnaceAbstract<?> furnace;
    /** The index of this slot in the furnace inputs list. */
    private final int index;
    
    public SlotFurnaceInput(TEFurnaceAbstract<?> furnace, int index,
            int xPos, int yPos) {
        
        super(null, 0, xPos, yPos);
        this.furnace = furnace;
        this.index = index;
    }
    
    @Override
    public boolean isItemValid(ItemStack stack) {
        
        return !this.furnace.recipes.getCookingResult(stack,
                this.furnace.getWorld()).isEmpty();
    }
    
    @Override
    public ItemStack getStack() {
        
        return this.furnace.getInput(this.index);
    }
    
    @Override
    public void putStack(ItemStack stack) {
        
        this.furnace.setInput(stack, this.index);
    }
    
    @Override
    public void onSlotChanged() {

        this.furnace.sort();
    }
    
    @Override
    public int getSlotStackLimit() {
        
        return 64;
    }
    
    @Override
    public ItemStack decrStackSize(int amount) {
        
        return this.furnace.getInput(this.index).splitStack(amount);
    }
    
    @Override
    public boolean isHere(IInventory inv, int slot) {
        
        return false;
    }
    
    @Override
    public boolean isSameInventory(Slot slot) {
        
        return false;
    }
}
