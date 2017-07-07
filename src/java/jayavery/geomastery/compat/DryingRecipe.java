/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.compat;

import java.util.Map.Entry;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.item.ItemStack;

public class DryingRecipe {
    
    final ItemStack input;
    final ItemStack output;
    
    public DryingRecipe(Entry<ItemStack, ItemStack> entry) {
        
        this.input = entry.getKey();
        this.output = entry.getValue();
    }
    
    public static class Wrapper extends BlankRecipeWrapper {
        
        private final DryingRecipe recipe;
        
        public Wrapper(DryingRecipe recipe) {
            
            this.recipe = recipe;
        }
        
        @Override
        public void getIngredients(IIngredients ingredients) {
            
            ingredients.setInput(ItemStack.class, this.recipe.input);
            ingredients.setOutput(ItemStack.class, this.recipe.output);
        }
    }
}
