package com.jj.jjmod.crafting;

import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import com.google.common.collect.Maps;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Smelting recipe and fuel manager.
 */
public class CookingManager {

    private final Map<ItemStack, ItemStack> recipes;
    private final Map<ItemStack, Integer> cookingTimes;
    private final Map<ItemStack, Integer> fuels;
    private final int multiplier;

    public CookingManager(int multiplier) {

        this.recipes = Maps.<ItemStack, ItemStack>newHashMap();
        this.cookingTimes = Maps.<ItemStack, Integer>newHashMap();
        this.fuels = Maps.<ItemStack, Integer>newHashMap();
        this.multiplier = multiplier;
    }

    /** Adds a smelting recipe. */
    public void addCookingRecipe(ItemStack input, ItemStack output, int cookTime) {
        System.out.println("adding recipe for " + input);
        this.recipes.put(input, output);
        this.cookingTimes.put(input, cookTime * this.multiplier);
    }
    
    public void addFuel(ItemStack fuel, int time) {
        
        this.fuels.put(fuel, time);
    }

    /** Gets the smelting result for the input.
     * @return The output ItemStack smelted from the input. */
    public ItemStack getCookingResult(ItemStack stack) {
        System.out.println("checking for recipe of " + stack);
        for (Entry<ItemStack, ItemStack> entry : this.recipes.entrySet()) {
            System.out.println("comparing to entry " + entry.getKey());
            System.out.println("equal? " + ItemStack.areItemsEqual(stack, entry.getKey()));
            if (ItemStack.areItemsEqual(stack, entry.getKey())) {

                return entry.getValue();
            }
        }

        return ItemStack.EMPTY;
    }
    
    public int getCookingTime(ItemStack stack) {
        
        for (Entry<ItemStack, Integer> entry : this.cookingTimes.entrySet()) {
            
            if (ItemStack.areItemsEqual(stack, entry.getKey())) {
                
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
