package com.jj.jjmod.crafting;

import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import com.google.common.collect.Maps;
import net.minecraft.item.ItemStack;

public class CookingManager {

    private final Map<ItemStack, ItemStack> recipes;

    public CookingManager() {

        this.recipes = Maps.<ItemStack, ItemStack>newHashMap();
    }

    public void addSmeltingRecipe(ItemStack input, ItemStack output) {

        this.recipes.put(input, output);
    }

    @Nullable
    public ItemStack getSmeltingResult(ItemStack stack) {

        for (Entry<ItemStack, ItemStack> entry: this.recipes.entrySet()) {

            if (this.compareItemStacks(stack, (ItemStack) entry.getKey())) {

                return (ItemStack) entry.getValue();
            }
        }

        return null;
    }

    private static boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {

        return stack2.getItem() == stack1.getItem() &&
                (stack2.getMetadata() == 32767 ||
                stack2.getMetadata() == stack1.getMetadata());
    }
}
