/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.compat;

import java.awt.Color;
import java.util.Map.Entry;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class CookingRecipe {
    
    final ItemStack input;
    final ItemStack output;
    final String level;
    
    public CookingRecipe(Entry<ItemStack, ItemStack> entry, String level) {
        
        this.input = entry.getKey();
        this.output = entry.getValue();
        this.level = level;
    }
    
    public static class Wrapper extends BlankRecipeWrapper {
    
        private final CookingRecipe recipe;
        
        public Wrapper(CookingRecipe recipe) {
            
            this.recipe = recipe; 
        }
        
        @Override
        public void getIngredients(IIngredients ingredients) {
            
            ingredients.setInput(ItemStack.class, this.recipe.input);
            ingredients.setOutput(ItemStack.class, this.recipe.output);
        }
        
        @Override
        public void drawInfo(Minecraft minecraft, int width, int height, int mouseX, int mouseY) {
            
            minecraft.fontRendererObj.drawString("Min: " + this.recipe.level, 24, 4, Color.gray.getRGB());
        }
    }
}
