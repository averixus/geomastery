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
    private final Map<ItemStack, Integer> fuels;

    public CookingManager() {

        this.recipes = Maps.<ItemStack, ItemStack>newHashMap();
        this.fuels = Maps.<ItemStack, Integer>newHashMap();
    }

    /** Adds a smelting recipe. */
    public void addSmeltingRecipe(ItemStack input, ItemStack output) {

        this.recipes.put(input, output);
    }

    /** Gets the smelting result for the input.
     * @return The output ItemStack smelted from the input. */
    public ItemStack getSmeltingResult(ItemStack stack) {

        for (Entry<ItemStack, ItemStack> entry: this.recipes.entrySet()) {

            if (ItemStack.areItemsEqual(stack, entry.getKey())) {

                return entry.getValue();
            }
        }

        return ItemStack.EMPTY;
    }
    
    public void addFuel(ItemStack fuel, int time) {
        
        this.fuels.put(fuel, time);
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
