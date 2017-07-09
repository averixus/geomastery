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
import jayavery.geomastery.compat.jei.GeoCraftingCategory.Wrapper;
import jayavery.geomastery.crafting.ShapedRecipe;
import jayavery.geomastery.main.Geomastery;
import mezz.jei.api.gui.ICraftingGridHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import scala.actors.threadpool.Arrays;

/** Category for Geomastery crafting recipes. */
public class GeoCraftingCategory extends BlankRecipeCategory<Wrapper> {

    /** Location of background image. */
    private static final ResourceLocation BG_RES = new ResourceLocation(
            "minecraft", "textures/gui/container/crafting_table.png");
    /** X position of background image. */
    private static final int BG_X = 29;
    /** Y position of background image. */
    private static final int BG_Y = 16;
    /** Width of background image. */
    private static final int BG_WIDTH = 116;
    /** Height of background image. */
     private static final int BG_HEIGHT = 54;
     
     /** Index of input slot. */
     private static final int IN_I = 1;
     /** Index of output slot. */
     private static final int OUT_I = 0;
    
    /** Recipe tab name. */
    private final String name;
    /** Internal unique id. */
    private final String uid;
    /** Background image. */
    private final IDrawable background;
    /** Crafting grid helper. */
    private final ICraftingGridHelper gridHelper;
    
    public GeoCraftingCategory(String name, String uid) {
        
        this.background = GeoJei.guiHelper.createDrawable(BG_RES,
                BG_X, BG_Y, BG_WIDTH, BG_HEIGHT);
        this.gridHelper = GeoJei.guiHelper
                .createCraftingGridHelper(IN_I, OUT_I);
        this.uid = uid;
        this.name = name;
    }
    
    @Override
    public IDrawable getBackground() {
        
        return this.background;
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
    
    @Override
    public void setRecipe(IRecipeLayout layout, Wrapper wrapper,
            IIngredients ingredients) {
        
        // Mostly copied from JEI's CraftingRecipeCategory
        
        IGuiItemStackGroup stacks = layout.getItemStacks();
        stacks.init(OUT_I, false, 94, 18);

        for (int y = 0; y < 3; ++y) {
            
            for (int x = 0; x < 3; ++x) {
                
                int index = IN_I + x + (y * 3);
                stacks.init(index, true, x * 18, y * 18);
            }
        }

        List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
        List<List<ItemStack>> outputs = ingredients.getOutputs(ItemStack.class);

        this.gridHelper.setInputs(stacks, inputs, wrapper.getWidth(),
                wrapper.getHeight());
        stacks.set(OUT_I, outputs.get(0));
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
