package com.jj.jjmod.crafting;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

/** Crafting recipe manager for variable sized crafting grids. */
public class CraftingManager {

    private final List<IRecipe> recipes;

    public CraftingManager() {

        this.recipes = Lists.<IRecipe>newArrayList();
    }

    /** Adds a grid shaped recipe. */
    public ShapedRecipe addShapedRecipe(ItemStack stack,
            Object... recipeComponents) {

        String s = "";
        int i = 0;
        int j = 0;
        int k = 0;

        // For array of strings
        if (recipeComponents[i] instanceof String[]) {

            String[] stringArray =
                    ((String[]) recipeComponents[i++]);

            for (String string : stringArray) {
                
                k++;
                j = string.length();
                s = s + string;
            }
        // For individual strings
        } else {
            
            while (recipeComponents[i] instanceof String) {

                String string = (String) recipeComponents[i++];
                k++;
                j = string.length();
                s = s + string;
            }
        }

        Map<Character, ItemStack> map;

        // Add coded components
        for (map = Maps.<Character, ItemStack>newHashMap();
                i < recipeComponents.length; i += 2) {

            Character character = (Character) recipeComponents[i];
            ItemStack itemstack = ItemStack.EMPTY;

            if (recipeComponents[i + 1] instanceof Item) {

                itemstack = new ItemStack((Item) recipeComponents[i + 1]);

            } else if (recipeComponents[i + 1] instanceof Block) {

                itemstack = new ItemStack((Block) recipeComponents[i + 1],
                        1, 32767);

            } else if (recipeComponents[i + 1] instanceof ItemStack) {

                itemstack = (ItemStack) recipeComponents[i + 1];
            }

            map.put(character, itemstack);
        }

        ItemStack[] stackArray = new ItemStack[j * k];

        // Key for components
        for (int l = 0; l < j * k; ++l) {

            char c0 = s.charAt(l);

            if (map.containsKey(Character.valueOf(c0))) {
                
                stackArray[l] = map.get(Character.valueOf(c0))
                                .copy();

            } else {

                stackArray[l] = ItemStack.EMPTY;
            }
        }

        ShapedRecipe recipe =
                new ShapedRecipe(j, k, stackArray, stack);
        this.recipes.add(recipe);

        return recipe;
    }

    /** Adds a shapeless recipes. */
    public void addShapelessRecipe(ItemStack stack,
            Object... recipeComponents) {

        List<ItemStack> list = Lists.<ItemStack>newArrayList();

        for (Object object : recipeComponents) {

            if (object instanceof ItemStack) {

                list.add(((ItemStack) object).copy());

            } else if (object instanceof Item) {

                list.add(new ItemStack((Item) object));

            } else {

                if (!(object instanceof Block)) {

                    throw new IllegalArgumentException(
                            "Invalid shapeless recipe: unknown type "
                            + object.getClass().getName() + "!");
                }

                list.add(new ItemStack((Block) object));
            }
        }

        this.recipes.add(new ShapelessRecipes(stack, list));
    }

    /** Gets the output recipe for the InventoryCrafting. 
     * @return The ItemStack result of the recipe. */
    public ItemStack findMatchingRecipe(InventoryCrafting craftMatrix,
            World world) {

        for (IRecipe recipe : this.recipes) {

            if (recipe.matches(craftMatrix, world)) {

                return recipe.getCraftingResult(craftMatrix);
            }
        }

        return ItemStack.EMPTY;
    }

    /** Gets the items left after the InventoryCrafting
     * inputs are used in the recipe.
     * @return A list of ItemStacks to be put back in the InventoryCrafting. */
    public NonNullList<ItemStack> getRemainingItems(
            InventoryCrafting craftMatrix, World world) {

        for (IRecipe recipe : this.recipes) {

            if (recipe.matches(craftMatrix, world)) {

                return recipe.getRemainingItems(craftMatrix);
            }
        }

        NonNullList<ItemStack> result = NonNullList
                .withSize(craftMatrix.getSizeInventory(), ItemStack.EMPTY);

        for (int i = 0; i < result.size(); ++i) {

            result.set(i, craftMatrix.getStackInSlot(i));
        }

        return result;
    }
    
    /** Gets the number of items from each input
     * ItemStack used in a single recipe.
     * @return An array of ints representing the amounts of items used. */
    public int[] getAmountsUsed(InventoryCrafting craftMatrix, World world) {
        
        for (IRecipe recipe : this.recipes) {

            if (recipe.matches(craftMatrix, world) &&
                    recipe instanceof ShapedRecipe) {

                return ((ShapedRecipe) recipe).getAmountsUsed(craftMatrix);
            }
        }
        
        int[] result = new int[craftMatrix.getSizeInventory()];
        Arrays.fill(result, 1);
        return result;
    }

    public List<IRecipe> getRecipeList() {

        return this.recipes;
    }
}
