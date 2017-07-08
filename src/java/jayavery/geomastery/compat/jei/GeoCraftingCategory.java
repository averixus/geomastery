/*******************************************************************************
 *  * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.compat.jei;

import java.util.ArrayList;
import java.util.List;
import com.google.common.collect.Lists;
import jayavery.geomastery.crafting.ShapedRecipe;
import jayavery.geomastery.main.Geomastery;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import mezz.jei.plugins.vanilla.crafting.CraftingRecipeCategory;
import net.minecraft.item.ItemStack;
import scala.actors.threadpool.Arrays;

/** Category for Geomastery crafting recipes. */
public class GeoCraftingCategory extends CraftingRecipeCategory {

    /** Recipe tab name. */
    private final String name;
    /** Internal unique id. */
    private final String uid;
    
    public GeoCraftingCategory(String name, String uid) {
        
        super(GeoJei.guiHelper);
        this.uid = uid;
        this.name = name;
    }
    
    @Override
    public String getUid() {
        
        return this.uid;
    }
    
    @Override
    public String getTitle() {
        
        return this.name;
    }
    
    @Override
    public String getModName() {
        
        return Geomastery.NAME;
    }
    
    /** Recipe containing a group of matching shaped recipes. */
    public static class Recipe {
        
        /** List of matching recipes. */
        final List<ShapedRecipe> recipes = Lists.newArrayList();
        
        public Recipe(ShapedRecipe recipe) {
            
            this.recipes.add(recipe);
        }
        
        /** Add another recipe to the list. */
        public void addRecipe(ShapedRecipe recipe) {
            
            this.recipes.add(recipe);
        }
    }
    
    /** Wrapper for one recipe. */
    public static class Wrapper extends BlankRecipeWrapper
            implements IShapedCraftingRecipeWrapper {
        
        /** The wrapped recipe. */
        private final Recipe recipe;
        
        public Wrapper(Recipe recipe) {
            
            this.recipe = recipe;
        }
        
        @Override
        public void getIngredients(IIngredients ingredients) {
            
            ShapedRecipe first = this.recipe.recipes.get(0);
            
            if (this.recipe.recipes.size() > 1) {
                
                List<List<ItemStack>> lists = new ArrayList<List<ItemStack>>();
                
                for (int i = 0; i < first.recipeItems.length; i++) {
                    
                    lists.add(new ArrayList<ItemStack>());
                }
                
                for (ShapedRecipe recipe : this.recipe.recipes) {
                    
                    for (int i = 0; i < lists.size(); i++) {
                        
                        lists.get(i).add(recipe.recipeItems[i]);
                    }
                }
                
                ingredients.setInputLists(ItemStack.class, lists);
                
            } else {
                
                ingredients.setInputs(ItemStack.class,
                        Arrays.asList(first.recipeItems));
            }
            
            ingredients.setOutput(ItemStack.class, first.getRecipeOutput());
        }
        
        @Override
        public int getWidth() {
            
            return this.recipe.recipes.get(0).recipeWidth;
        }
        
        @Override
        public int getHeight() {
            
            return this.recipe.recipes.get(0).recipeHeight;
        }
    }
}
