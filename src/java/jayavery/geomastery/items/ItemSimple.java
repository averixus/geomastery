/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.items;

import jayavery.geomastery.main.GeoCaps;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/** General superclass for new items and basic implementation. */
public class ItemSimple extends Item {
    
    public ItemSimple(String name, int stackSize, CreativeTabs tab) {
        
        setupItem(this, name, stackSize, tab);
    }
    
    public ItemSimple(String name, int stackSize) {
        
        this(name, stackSize, CreativeTabs.MATERIALS);
    }
    
    public ItemSimple(String name) {
        
        this(name, 1);
    }
    
    /** Applies constructor functions statically. */
    public static void setupItem(Item item, String name,
            int stackSize, CreativeTabs tab) {
        
        item.setRegistryName("item_" + name);
        item.setUnlocalizedName(item.getRegistryName().toString());
        item.setMaxStackSize(stackSize);
        item.setCreativeTab(tab);
    }
    
    /** @return A new stack of the item with a decay capability
     * set to the current world time if applicable. */
    public static ItemStack newStack(Item item, int count, World world) {
        
        ItemStack stack = new ItemStack(item, count);
        
        if (stack.hasCapability(GeoCaps.CAP_DECAY, null)) {
            
            long birth = world == null ? -1 : world.getTotalWorldTime();
            stack.getCapability(GeoCaps.CAP_DECAY, null).setBirthTime(birth);
        }
        
        return stack;
    }
    
    /** @return A new stack of the item with a decay capability
     * set to {@code Long.MIN_VALUE} if applicable. */
    public static ItemStack rottenStack(Item item, int count) {
        
        ItemStack stack = new ItemStack(item, count);
        
        if (stack.hasCapability(GeoCaps.CAP_DECAY, null)) {
            
            stack.getCapability(GeoCaps.CAP_DECAY, null)
                    .setBirthTime(-100000);
        }
        
        return stack;
    }
}
