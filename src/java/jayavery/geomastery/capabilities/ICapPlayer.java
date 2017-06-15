/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.capabilities;

import jayavery.geomastery.items.ItemEdible;
import jayavery.geomastery.utilities.FoodType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

/** Capability adding various information and functions to the Player. */
public interface ICapPlayer extends INBTSerializable<NBTTagCompound> {

    /** Update all ticking features. */
    public void tick();
    
    /** Update all stats with given heal amount. */
    public void sleep(float healAmount);
    
    /** Get the amount of main inventory rows the player has
     * available, excluding the hotbar.
     * @return Between 0 and 3. */
    public int getInventoryRows();
    
    /** Get the amount of inventory slots the player has available,
     * including the hotbar.
     * @return between 9 and 36. */
    public int getInventorySize();
    
    /** Check whether the player is carrying heavy items.
     * @return Whether the player is allowed to sprint. */
    public boolean canSprint();
    
    /** @return The ItemStack in the backpack slot. */
    public ItemStack getBackpack();
    
    /** @return The ItemStack in the yoke slot. */
    public ItemStack getYoke();
    
    /** Remove the ItemStack in the backpack slot.
     * @return The ItemStack removed. */
    public ItemStack removeBackpack();
    
    /** Remove the ItemStack in the yoke slot.
     * @return the ItemStack removed. */
    public ItemStack removeYoke();
    
    /** Put an ItemStack in the backpack slot, does not check validity. */
    public void putBackpack(ItemStack stack);
    
    /** Put an ItemStack in the yoke slot, does not check validity. */
    public void putYoke(ItemStack stack);
    
    /** Get the ResourceLocation for the GUI icon representing the
     * current temperature.
     * @return The ResourceLocation for one of five icons. */
    public ResourceLocation getTempIcon();
    
    /** Get the food level for the FoodType.
     * @return An int from 0 to 20. */
    public int foodLevel(FoodType type);
    
    /** Check whether the player has less than full hunger for the FoodType.
     * @return Whether the player is allowed to eat the FoodType. */
    public boolean canEat(FoodType type);
    
    /** Get the lowest food level of all hunger types.
     * @return An int from 0 to 20. */
    public int getFoodLevel();
    
    /** Add the exhaustion to all food types. */
    public void addExhaustion(float exhaustion);
    
    /** Add the item's food value to its food type. */
    public void addStats(ItemEdible item, ItemStack stack);
    
    /** Adds the item pickup delay. */
    public void addDelay(Item item, long time);
    
    /** Checks whether this item is currently delayed.
     * @return Whether this item can be picked up. */
    public boolean canPickup(Item item);

    /** Send all necessary update packets to the client. */
    public void syncAll();
}
