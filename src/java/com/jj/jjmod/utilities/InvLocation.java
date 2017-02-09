package com.jj.jjmod.utilities;

import java.util.ArrayList;
import java.util.List;
import com.jj.jjmod.capabilities.CapPlayer;
import com.jj.jjmod.capabilities.ICapPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class InvLocation {
    
    private InvType type;
    private int index;
    private EntityPlayer player;
    
    public InvLocation(EntityPlayer player, InvType type, int index) {
        
        this.player = player;
        this.type = type;
        this.index = index;
    }
    
    public InvType getType() {
        
        return this.type;
    }
    
    public int getIndex() {
        
        return this.index;
    }
    
    public void setStack(ItemStack stack) {
        
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

    public enum InvType {

        MAIN,
        OFFHAND,
        ARMOUR;
    }
}
