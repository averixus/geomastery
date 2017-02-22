package com.jj.jjmod.crafting;

import java.util.Arrays;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.world.World;

/** ShapedRecipe for variable sized crafting grids. */
public class ShapedRecipe extends ShapedRecipes {

    public ShapedRecipe(int width, int height, ItemStack[] inputs,
            ItemStack output) {

        super(width, height, inputs, output);
    }

    @Override
    public boolean matches(InventoryCrafting inv, World world) {

        for (int i = 0; i <= inv.getWidth() - this.recipeWidth; i++) {

            for (int j = 0; j <= inv.getHeight() - this.recipeHeight; j++) {

                if (this.checkMatch(inv, i, j, true) ||
                        this.checkMatch(inv, i, j, false)) {

                    return true;
                }
            }
        }

        return false;
    }
    
    /** Gets the position in the crafting grid where this recipe starts.
     * @return An array of 3 ints, {x, y, flipped}: flipped is a 0/1 boolean
     * representing whether the recipe is mirrored. */
    private int[] getMatchPosition(InventoryCrafting inv) {
        
        for (int i = 0; i <= inv.getWidth() - this.recipeWidth; i++) {

            for (int j = 0; j <= inv.getHeight() - this.recipeHeight; j++) {

                if (this.checkMatch(inv, i, j, true)) {

                    return new int[] {i, j, 1};
                }
                
                if (this.checkMatch(inv, i, j, false)) {
                    
                    return new int[] {i, j, 0};
                }
            }
        }
        // Shouldn't happen
        return null;
    }
    
    /** Gets the number of items used from each input in the InventoryCrafting.
     * @return An array of ints corersponding to
     * number of items used from each ItemStack. */
    public int[] getAmountsUsed(InventoryCrafting inv) {
        
        int[] match = getMatchPosition(inv);
        int a = match[0];
        int b = match[1];
        boolean flag = match[2] == 1;
        
        int[] amounts = new int[inv.getSizeInventory()];
        
        for (int i = 0; i < inv.getWidth(); ++i) {

            for (int j = 0; j < inv.getHeight(); ++j) {

                int k = i - a;
                int l = j - b;
                int amount = (j * inv.getWidth()) + i;
                amounts[amount] = 0;

                if (k >= 0 && l >= 0 && k < this.recipeWidth &&
                        l < this.recipeHeight) {

                    if (flag) {

                        amounts[amount] = this.recipeItems[this.recipeWidth -
                                k - 1 + l * this.recipeWidth].getCount();

                    } else {

                        amounts[amount] = this.recipeItems[k + l *
                                this.recipeWidth].getCount();
                    }
                }
            }
        }

        return amounts;
    }

    /** Check whether this recipe matches the InventoryCrafting in the given
     * position and flipped according to flag.
     * @return Whether or not this recipe matches. */
    protected boolean checkMatch(InventoryCrafting inv, int a, int b,
            boolean flag) {

        for (int i = 0; i < inv.getWidth(); ++i) {

            for (int j = 0; j < inv.getHeight(); ++j) {

                int k = i - a;
                int l = j - b;
                ItemStack required = ItemStack.EMPTY;

                if (k >= 0 && l >= 0 && k < this.recipeWidth &&
                        l < this.recipeHeight) {

                    if (flag) {

                        required = this.recipeItems[this.recipeWidth -
                                k - 1 + l * this.recipeWidth];

                    } else {

                        required = this.recipeItems[k + l * this.recipeWidth];
                    }
                }

                ItemStack inInv = inv.getStackInRowAndColumn(i, j);

                if (inInv != ItemStack.EMPTY || required != ItemStack.EMPTY) {

                    if (inInv.isEmpty() && !required.isEmpty() ||
                            !inInv.isEmpty() && required.isEmpty()) {

                        return false;
                    }

                    if (required.getItem() != inInv.getItem()) {

                        return false;
                    }

                    if (required.getMetadata() != 32767 &&
                            required.getMetadata() != inInv.getMetadata()) {

                        return false;
                    }

                    if (required.getCount() > inInv.getCount()) {
                        
                        return false;
                    }
                }
            }
        }

        return true;
    }
}