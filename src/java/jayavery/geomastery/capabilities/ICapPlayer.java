/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.capabilities;

import java.util.List;
import jayavery.geomastery.items.ItemEdible;
import jayavery.geomastery.utilities.EFoodType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

/** Capability adding various information and functions to the Player. */
public interface ICapPlayer extends INBTSerializable<NBTTagCompound> {

    /** Updates all ticking features. */
    public void tick();
    /** Updates all stats with given heal amount. */
    public void sleep(float healAmount);
    /** Gets the amount of main inventory rows the player has
     * available, excluding the hotbar.
     * @return Between 0 and 3. */
    public int getInventoryRows();
    /** Gets the amount of inventory slots the player has available,
     * including the hotbar.
     * @return between 9 and 36. */
    public int getInventorySize();
    /** Checks whether the player is carrying heavy items.
     * @return Whether the player is allowed to sprint. */
    public boolean canSprint();
    /** @return The ItemStack in the backpack slot. */
    public ItemStack getBackpack();
    /** @return The ItemStack in the yoke slot. */
    public ItemStack getYoke();
    /** Removes the ItemStack in the backpack slot.
     * @return The ItemStack removed. */
    public ItemStack removeBackpack();
    /** Removes the ItemStack in the yoke slot.
     * @return the ItemStack removed. */
    public ItemStack removeYoke();
    /** Puts an ItemStack in the backpack slot, does not check validity. */
    public void putBackpack(ItemStack stack);
    /** Puts an ItemStack in the yoke slot, does not check validity. */
    public void putYoke(ItemStack stack);
    /** Gets the ResourceLocation for the GUI icon representing the
     * current temperature.
     * @return The ResourceLocation for one of five icons. */
    public ResourceLocation getTempIcon();
    /** Gets the food level for the EFoodType.
     * @return An int from 0 to 20. */
    public int foodLevel(EFoodType type);
    /** Checks whether the player has less than full hunger for the EFoodType.
     * @return Whether the player is allowed to eat the EFoodType. */
    public boolean canEat(EFoodType type);
    /** Gets the lowest food level of all hunger types.
     * @return An int from 0 to 20. */
    public int getFoodLevel();
    /** Adds the exhaustion to all food types. */
    public void addExhaustion(float exhaustion);
    /** Adds the item's food value to its food type. */
    public void addStats(ItemEdible item, ItemStack stack);
    /** Adds the item pickup delay. */
    public void addDelay(Item item, long time);
    /** Checks whether this item is currently delayed.
     * @return Whether this item can be picked up. */
    public boolean canPickup(Item item);
    /** Sends all necessary initial update packets to the client. */
    public void syncAll();
    /** @return A list of info to be added to the debug screen. */
    public List<String> getDebug();
}
