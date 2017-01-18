package com.jj.jjmod.crafting;

import java.util.Arrays;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.world.World;

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
    
    protected int[] getMatchPosition(InventoryCrafting inv) {
        
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
                                k - 1 + l * this.recipeWidth].func_190916_E();

                    } else {

                        amounts[amount] = this.recipeItems[k + l *
                                this.recipeWidth].func_190916_E();
                    }
                }
            }
        }

        return amounts;
    }

    protected boolean checkMatch(InventoryCrafting inv, int a, int b,
            boolean flag) {

        for (int i = 0; i < inv.getWidth(); ++i) {

            for (int j = 0; j < inv.getHeight(); ++j) {

                int k = i - a;
                int l = j - b;
                ItemStack required = null;

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

                if (inInv != null || required != null) {

                    if (inInv == null && required != null ||
                            inInv != null && required == null) {

                        return false;
                    }

                    if (required.getItem() != inInv.getItem()) {

                        return false;
                    }

                    if (required.getMetadata() != 32767 &&
                            required.getMetadata() != inInv.getMetadata()) {

                        return false;
                    }

                    if (required.func_190916_E() > inInv.func_190916_E()) {
                        
                        return false;
                    }
                }
            }
        }

        return true;
    }
}