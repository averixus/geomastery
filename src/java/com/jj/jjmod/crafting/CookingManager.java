package com.jj.jjmod.crafting;

import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import com.google.common.collect.Maps;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CookingManager {

    private final Map<ItemStack, ItemStack> recipes;
    private final Map<ItemStack, Integer> fuels;

    public CookingManager() {

        this.recipes = Maps.<ItemStack, ItemStack>newHashMap();
        this.fuels = Maps.<ItemStack, Integer>newHashMap();
    }

    public void addSmeltingRecipe(ItemStack input, ItemStack output) {

        this.recipes.put(input, output);
    }

    @Nullable
    public ItemStack getSmeltingResult(ItemStack stack) {

        for (Entry<ItemStack, ItemStack> entry: this.recipes.entrySet()) {

            if (compareItemStacks(stack, entry.getKey())) {

                return entry.getValue();
            }
        }

        return null;
    }
    
    public void addFuel(ItemStack fuel, int time) {
        
        this.fuels.put(fuel, time);
    }
    
    public int getFuelTime(ItemStack fuel) {
        
        for (Entry<ItemStack, Integer> entry: this.fuels.entrySet()) {

            if (compareItemStacks(fuel, entry.getKey())) {

                return entry.getValue();
            }
        }

        return 0;
    }

    private static boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {

        return stack2.getItem() == stack1.getItem() &&
                (stack2.getMetadata() == 32767 ||
                stack2.getMetadata() == stack1.getMetadata());
    }
}
