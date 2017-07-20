/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.compat.jei;

import java.util.Map.Entry;
import jayavery.geomastery.compat.jei.GeoDryingCategory.Wrapper;
import jayavery.geomastery.main.Geomastery;
import jayavery.geomastery.utilities.Lang;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/** Category for Geomastery drying recipes. */
public class GeoDryingCategory extends BlankRecipeCategory<Wrapper> {

    /** Location of background image. */
    private static final ResourceLocation BG_RES = new ResourceLocation(Geomastery.MODID, "textures/gui/drying_0.png");
    /** X position of background image. */
    private static final int BG_X = 48;
    /** Y position of background image. */
    private static final int BG_Y = 34;
    /** Width of background image. */
    private static final int BG_WIDTH = 82;
    /** Height of background image. */
    private static final int BG_HEIGHT = 18;
    
    /** X position of arrow image origin. */
    private static final int ARROW_ORIG_X = 176;
    /** Y position of arrow image origin. */
    private static final int ARROW_ORIG_Y = 14;
    /** Width of arrow image. */
    private static final int ARROW_WIDTH = 24;
    /** Height of arrow image. */
    private static final int ARROW_HEIGHT = 17;
    
    /** X position of arrow animation. */
    private static final int ARROW_X = 29;
    /** Y position of arrow animation. */
    private static final int ARROW_Y = 0;
    /** Ticks for progress animation to last. */
    private static final int ARROW_TICKS = 200;
    
    /** Index of input slot. */
    private static final int IN_I = 0;
    /** Index of output slot. */
    private static final int OUT_I = 1;
    /** X and Y position of input slot. */
    private static final int IN = 0;
    /** X position of output slot. */
    private static final int OUT_X = 64;
    /** Y position of output slot. */
    private static final int OUT_Y = 0;

    /** Background image. */
    private final IDrawable background;
    /** Animated progress arrow. */
    private final IDrawableAnimated arrow;
    
    public GeoDryingCategory() {
        
        this.background = GeoJei.guiHelper.createDrawable(BG_RES, 
                BG_X, BG_Y, BG_WIDTH, BG_HEIGHT);
        IDrawableStatic arrow = GeoJei.guiHelper.createDrawable(BG_RES, 
                ARROW_ORIG_X, ARROW_ORIG_Y, ARROW_WIDTH, ARROW_HEIGHT);
        this.arrow = GeoJei.guiHelper.createAnimatedDrawable(arrow,
                ARROW_TICKS, IDrawableAnimated.StartDirection.LEFT, false);
    }
    
    @Override
    public IDrawable getBackground() {
        
        return this.background;
    }
    
    @Override
    public void drawExtras(Minecraft minecraft) {
        
        this.arrow.draw(minecraft, ARROW_X, ARROW_Y);
    }
    
    @Override
    public void setRecipe(IRecipeLayout layout, Wrapper wrapper,
            IIngredients ingredients) {
        
        IGuiItemStackGroup stacks = layout.getItemStacks();
        stacks.init(IN_I, true, IN, IN);
        stacks.init(OUT_I, false, OUT_X, OUT_Y);
        stacks.set(ingredients);
    }
    
    @Override
    public String getUid() {
        
        return Lang.CONTAINER_DRYING;
    }
    
    @Override
    public String getTitle() {
        
        return I18n.format(Lang.CONTAINER_DRYING);
    }
    
    @Override
    public String getModName() {
        
        return Geomastery.NAME;
    }
    
    /** Recipe containing one input and output. */
    public static class Recipe {
        
        /** The input stack.*/
        final ItemStack input;
        /** The result stack. */
        final ItemStack output;
        
        public Recipe(Entry<ItemStack, ItemStack> entry) {
            
            this.input = entry.getKey();
            this.output = entry.getValue();
        }
    }
    
    /** Wrapper for one recipe. */
    public static class Wrapper extends BlankRecipeWrapper {
        
        /** The wrapped recipe. */
        private final Recipe recipe;
        
        public Wrapper(Recipe recipe) {
            
            this.recipe = recipe;
        }
        
        @Override
        public void getIngredients(IIngredients ingredients) {
            
            ingredients.setInput(ItemStack.class, this.recipe.input);
            ingredients.setOutput(ItemStack.class, this.recipe.output);
        }
    }
}
