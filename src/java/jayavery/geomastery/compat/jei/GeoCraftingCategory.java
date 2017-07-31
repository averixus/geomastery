/*******************************************************************************
 *  * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.compat.jei;

import java.util.ArrayList;
import java.util.Arrays;
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
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

/** Category for Geomastery crafting recipes. */
public class GeoCraftingCategory extends BlankRecipeCategory<Wrapper> {

    /** Location of background image. */
    private static final ResourceLocation BG_RES = new ResourceLocation("minecraft", "textures/gui/container/crafting_table.png");
    /** X position of background image. */
    private static final int BG_X = 29;
    /** Y position of background image. */
    private static final int BG_Y = 16;
    /** Width of background image. */
    private static final int BG_WIDTH = 116;
    /** Height of background image. */
     private static final int BG_HEIGHT = 54;
    
     /** Index of output slot. */
     private static final int OUTPUT_I = 0;
     /** X position of output slot. */
     private static final int OUTPUT_X = 94;
     /** Y position of output slot. */
     private static final int OUTPUT_Y = 18;
     /** Number of crafting rows and columns. */
     private static final int CRAFT_SIZE = 3;
     /** Index of input slot. */
     private static final int INPUT_I = 1;
     /** Size of crafting slot. */
     private static final int SLOT_SIZE = 18;
    
    /** Recipe tab unlocalised name. */
    private final String name;
    /** Background image. */
    private final IDrawable background;
    /** Crafting grid helper. */
    private final ICraftingGridHelper gridHelper;
    
    public GeoCraftingCategory(String name) {
        
        this.background = GeoJei.guiHelper.createDrawable(BG_RES,
                BG_X, BG_Y, BG_WIDTH, BG_HEIGHT);
        this.gridHelper = GeoJei.guiHelper
                .createCraftingGridHelper(INPUT_I, OUTPUT_I);
        this.name = name;
    }
    
    @Override
    public IDrawable getBackground() {
        
        return this.background;
    }
    
    @Override
    public String getUid() {
        
        return this.name;
    }
    
    @Override
    public String getTitle() {
        
        return I18n.format(this.name);
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
        stacks.init(OUTPUT_I, false, OUTPUT_X, OUTPUT_Y);

        for (int y = 0; y < CRAFT_SIZE; ++y) {
            
            for (int x = 0; x < CRAFT_SIZE; ++x) {
                
                int index = INPUT_I + x + (y * CRAFT_SIZE);
                stacks.init(index, true, x * SLOT_SIZE, y * SLOT_SIZE);
            }
        }

        List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
        List<List<ItemStack>> outputs = ingredients.getOutputs(ItemStack.class);

        this.gridHelper.setInputs(stacks, inputs, wrapper.getWidth(),
                wrapper.getHeight());
        stacks.set(OUTPUT_I, outputs.get(0));
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
                
                List<List<Ingredient>> lists = new ArrayList<List<Ingredient>>();
                
                for (int i = 0; i < first.recipeItems.size(); i++) {
                    
                    lists.add(new ArrayList<Ingredient>());
                }
                
                for (ShapedRecipe recipe : this.recipe.recipes) {
                    
                    for (int i = 0; i < lists.size(); i++) {
                        
                        lists.get(i).add(recipe.recipeItems.get(i));
                    }
                }
                
                ingredients.setInputLists(Ingredient.class, lists);
                
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
