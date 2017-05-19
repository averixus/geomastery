package com.jayavery.jjmod.tileentities;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;

public abstract class TEContainerAbstract extends TileEntity {
    
    /** Drops any items stored in this TileEntity. */
    public abstract void dropItems();
    
    /** Drops the given inventory items. */
    protected void dropInventory(NonNullList<ItemStack> inventory) {

        for (int i = 0; i < inventory.size(); i++) {
            
            this.dropItem(inventory.get(i));
        }
    }
    
    /** Drops the given stack. */
    protected void dropItem(ItemStack stack) {
        
        if (!stack.isEmpty()) {
            
            this.world.spawnEntity(new EntityItem(this.world, this.pos.getX(),
                    this.pos.getY(), this.pos.getZ(), stack));
        }
    }
}
