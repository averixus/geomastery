/*******************************************************************************

 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.crafting;

import jayavery.geomastery.main.GeoCaps;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;

/** ShapedRecipe for variable sized crafting grids. */
public class ShapedRecipe extends ShapedRecipes {

    public ShapedRecipe(int width, int height, NonNullList<Ingredient> ingredients,
            ItemStack output) {
        
        super("Recipe", width, height, ingredients, output);
    }
    
    public static ShapedRecipe create(int width, int height, ItemStack[] inputs, ItemStack output) {
        
        NonNullList<Ingredient> ingredients = NonNullList.create();
        
        for (ItemStack stack : inputs) {
            
            ingredients.add(CraftingHelper.getIngredient(stack));
        }
        
        return new ShapedRecipe(width, height, ingredients, output);
    }

    @Override
    public boolean matches(InventoryCrafting inv, World world) {

        for (int i = 0; i <= inv.getWidth() - this.recipeWidth; i++) {

            for (int j = 0; j <= inv.getHeight() - this.recipeHeight; j++) {

                if (this.checkMatch(inv, i, j, true, world) ||
                        this.checkMatch(inv, i, j, false, world)) {

                    return true;
                }
            }
        }

        return false;
    }
    
    /** Gets the position in the crafting grid where this recipe starts.
     * @return An array of 3 ints, {x, y, flipped}: flipped is a 0/1 boolean
     * representing whether the recipe is mirrored. */
    private int[] getMatchPosition(InventoryCrafting inv, World world) {
        
        for (int i = 0; i <= inv.getWidth() - this.recipeWidth; i++) {

            for (int j = 0; j <= inv.getHeight() - this.recipeHeight; j++) {

                if (this.checkMatch(inv, i, j, true, world)) {

                    return new int[] {i, j, 1};
                }
                
                if (this.checkMatch(inv, i, j, false, world)) {
                    
                    return new int[] {i, j, 0};
                }
            }
        }
       
        return null; // Shouldn't happen
    }
    
    /** Gets the number of items used from each input in the InventoryCrafting.
     * @return An array of ints corersponding to
     * number of items used from each ItemStack. */
    public int[] getAmountsUsed(InventoryCrafting inv, World world) {
        
        int[] match = getMatchPosition(inv, world);
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

                        amounts[amount] = this.recipeItems.get(this.recipeWidth -
                                k - 1 + l * this.recipeWidth).getMatchingStacks()[0].getCount();

                    } else {

                        amounts[amount] = this.recipeItems.get(k + l *
                                this.recipeWidth).getMatchingStacks()[0].getCount();
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
            boolean flag, World world) {

        for (int i = 0; i < inv.getWidth(); ++i) {

            for (int j = 0; j < inv.getHeight(); ++j) {

                int k = i - a;
                int l = j - b;
                ItemStack required = ItemStack.EMPTY;

                if (k >= 0 && l >= 0 && k < this.recipeWidth &&
                        l < this.recipeHeight) {

                    if (flag) {
                        
                        NonNullList<Ingredient> ings = null;
                        int index = 0;
                        Ingredient ing = null;
                        ItemStack[] stacks = null;

                        ings = this.recipeItems;
                        index = this.recipeWidth - k - 1 + l * this.recipeWidth;
                        ing = ings.get(index);
                        stacks = ing.getMatchingStacks();
 
                        if (stacks.length > 0) {
                            required = stacks[0];
                        }

                    } else {

                        required = this.recipeItems.get(k + l * this.recipeWidth).getMatchingStacks()[0];
                    }
                }

                ItemStack inInv = inv.getStackInRowAndColumn(i, j);
                
                if (inInv != ItemStack.EMPTY ||
                        required != ItemStack.EMPTY) {

                    if (inInv.isEmpty() && !required.isEmpty() ||
                            !inInv.isEmpty() && required.isEmpty()) {

                        return false;
                    }

                    if (required.getItem() != inInv.getItem()) {

                        return false;
                    }
                    
                    if (required.hasCapability(GeoCaps.CAP_DECAY, null) &&
                            inInv.hasCapability(GeoCaps.CAP_DECAY, null) &&
                            (required.getCapability(GeoCaps.CAP_DECAY, null)
                            .isRot(world) != inInv.getCapability(GeoCaps
                            .CAP_DECAY, null).isRot(world))) {
                        
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
