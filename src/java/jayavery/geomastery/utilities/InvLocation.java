/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.utilities;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/** Object to store and interact with a particular InventoryPlayer slot. */
public class InvLocation {
    
    /** The inventory type of this location. */
    private final InvType type;
    /** The index of this location. */
    private final int index;
    /** The player of this location. */
    private final EntityPlayer player;
    
    public InvLocation(EntityPlayer player, InvType type, int index) {
        
        this.player = player;
        this.type = type;
        this.index = index;
    }
    
    /** @return The InvType of this location. */
    public InvType getType() {
        
        return this.type;
    }
    
    /** @return The index of this location. */
    public int getIndex() {
        
        return this.index;
    }
    
    /** Puts the given stack in this location. */
    public void putStack(ItemStack stack) {
        
        List<ItemStack> inv = new ArrayList<ItemStack>();
        
        switch (this.type) {
            
            case MAIN: {
                
                inv = this.player.inventory.mainInventory;
                break;
            }
            
            case OFFHAND: {
                
                inv = this.player.inventory.offHandInventory;
                break;
            }
            
            case ARMOUR: {
                
                inv = this.player.inventory.armorInventory;
                break;
            }
        }
        
        if (this.index < 0 || this.index > inv.size()) {
            
            return;
            
        } else {
            
            inv.set(this.index, stack);
        }
    }
    
    /** @return The ItemStack in this location. */
    public ItemStack getStack() {
        
        List<ItemStack> inv = new ArrayList<ItemStack>();
        
        switch (this.type) {
            
            case MAIN: {
                
                inv = this.player.inventory.mainInventory;
                break;
            }
            
            case OFFHAND: {
                
                inv = this.player.inventory.offHandInventory;
                break;
            }
            
            case ARMOUR: {
                
                inv = this.player.inventory.armorInventory;
                break;
            }
        }
        
        if (this.index < 0 || this.index > inv.size()) {
            
            return ItemStack.EMPTY;
            
        } else {
            
            return inv.get(this.index);
        }
    }
    
    /** Check for invalid slots.
     *  @return Whether this object refers to a valid location. */
    public boolean isValid() {
        
        List<ItemStack> inv = new ArrayList<ItemStack>();
        
        switch (this.type) {
            
            case MAIN: {
                
                inv = this.player.inventory.mainInventory;
                break;
            }
            
            case OFFHAND: {
                
                inv = this.player.inventory.offHandInventory;
                break;
            }
            
            case ARMOUR: {
                
                inv = this.player.inventory.armorInventory;
                break;
            }
        }
        
        if (this.index < 0 || this.index > inv.size()) {
            
            return false;
            
        } else {
            
            return true;
        }
    }

    /** Enum defining the possible inventory stypes. */
    public enum InvType {

        MAIN,
        OFFHAND,
        ARMOUR;
    }
}
