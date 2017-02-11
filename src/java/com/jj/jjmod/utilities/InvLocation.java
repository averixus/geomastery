package com.jj.jjmod.utilities;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/** Object to store and interact with a particular InventoryPlayer slot. */
public class InvLocation {
    
    private InvType type;
    private int index;
    private EntityPlayer player;
    
    public InvLocation(EntityPlayer player, InvType type, int index) {
        
        this.player = player;
        this.type = type;
        this.index = index;
    }
    
    /** @return The InvType of this InvLocation. */
    public InvType getType() {
        
        return this.type;
    }
    
    /** @return The index of this InvLocation. */
    public int getIndex() {
        
        return this.index;
    }
    
    /** Puts the given stack in this InvLocation. */
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
    
    /** @return The ItemStack in this InvLocation. */
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
     *  @return Whether this object refers to a valid InvLocation. */
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
