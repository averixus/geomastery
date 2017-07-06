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

public class CookingRecipeWrapper extends BlankRecipeWrapper {
    
    private final ItemStack input;
    private final ItemStack output;
    
    public CookingRecipeWrapper(Entry<ItemStack, ItemStack> entry) {
        
        this.input = entry.getKey();
        this.output = entry.getValue();
    }
    
    @Override
    public void getIngredients(IIngredients ingredients) {
        
        ingredients.setInput(ItemStack.class, this.input);
        ingredients.setOutput(ItemStack.class, this.output);
    }
}
