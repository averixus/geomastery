package com.jj.jjmod.crafting;

import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import com.google.common.collect.Maps;
import com.jj.jjmod.init.ModCaps;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Smelting recipe and fuel manager.
 */
public class CookingManager {

    /** Map of inputs to outputs. */
    private final Map<ItemStack, ItemStack> recipes;
    /** Map of inputs to cooking times. */
    private final Map<ItemStack, Integer> cookingTimes;
    /** Map of fuels to burning times. */
    private final Map<ItemStack, Integer> fuels;
    /** Multiplier for cooking times. */
    private final int multiplier;

    public CookingManager(int multiplier) {

        this.recipes = Maps.<ItemStack, ItemStack>newHashMap();
        this.cookingTimes = Maps.<ItemStack, Integer>newHashMap();
        this.fuels = Maps.<ItemStack, Integer>newHashMap();
        this.multiplier = multiplier;
    }

    /** Adds a smelting recipe. */
    public void addCookingRecipe(ItemStack input,
            ItemStack output, int cookTime) {

        this.recipes.put(input, output);
        this.cookingTimes.put(input, cookTime * this.multiplier);
    }
    
    public void addFuel(ItemStack fuel, int time) {
        
        this.fuels.put(fuel, time);
    }

    /** Gets the smelting result for the input.
     * @return The output ItemStack smelted from the input. */
    public ItemStack getCookingResult(ItemStack input) {

        if (input.hasCapability(ModCaps.CAP_DECAY, null) &&
                input.getCapability(ModCaps.CAP_DECAY, null).isRot()) {
            
            return ItemStack.EMPTY;
        }
        
        for (Entry<ItemStack, ItemStack> entry : this.recipes.entrySet()) {

            if (ItemStack.areItemsEqual(input, entry.getKey())) {
                
                return entry.getValue();
            }
        }

        return ItemStack.EMPTY;
    }
    
    /** Gets the time taken to cook the input.
     * @return The number of ticks taken to cook this input. */
    public int getCookingTime(ItemStack input) {
        
        for (Entry<ItemStack, Integer> entry : this.cookingTimes.entrySet()) {
            
            if (ItemStack.areItemsEqual(input, entry.getKey())) {
                
                return entry.getValue();
            }
        }
        
        return 0;
    }
    
    /** Gets the cook time for the input.
     * @return The ticks the input fuel cooks for. */
    public int getFuelTime(ItemStack fuel) {
        
        for (Entry<ItemStack, Integer> entry: this.fuels.entrySet()) {

            if (ItemStack.areItemsEqual(fuel, entry.getKey())) {

                return entry.getValue();
            }
        }

        return 0;
    }
}
