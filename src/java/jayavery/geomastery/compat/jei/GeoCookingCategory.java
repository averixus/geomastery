/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.compat.jei;

import java.awt.Color;
import java.util.Map.Entry;
import jayavery.geomastery.compat.jei.GeoCookingCategory.Wrapper;
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

/** Category for Geomastery cooking recipes. */
public class GeoCookingCategory extends BlankRecipeCategory<Wrapper> {

    /** Location of background image. */
    private static final ResourceLocation BG_RES = new ResourceLocation(Geomastery.MODID, "textures/gui/furnace1_0.png");
    /** X position of background image. */
    private static final int BG_X = 54;
    /** Y position of background image. */
    private static final int BG_Y = 16;
    /** Width of background image. */
    private static final int BG_WIDTH = 83;
    /** Height of background image. */
    private static final int BG_HEIGHT = 54;
    
    /** X position of flame and arrow origin. */
    private static final int ANIM_ORIG_X = 176;
    /** Y position of flame origin. */
    private static final int FLAME_ORIG_Y = 0;
    /** Size of flame. */
    private static final int FLAME_SIZE = 14;
    /** Ticks for flame animation to last. */
    private static final int FLAME_TICKS = 300;
    /** Y position of arrow origin. */
    private static final int ARROW_ORIG_Y = 14;
    /** Width of arrow. */
    private static final int ARROW_WIDTH = 24;
    /** Height of arrow. */
    private static final int ARROW_HEIGHT = 17;
    /** Ticks for arrow animation to last. */
    private static final int ARROW_TICKS = 200;
    
    /** X position of flame animation. */
    private static final int FLAME_X = 2;
    /** Y position of flame animation. */
    private static final int FLAME_Y = 20;
    /** X position of arrow animation. */
    private static final int ARROW_X = 24;
    /** Y position of arrow animation. */
    private static final int ARROW_Y = 18;
    
    /** Index of input slot. */
    private static final int INPUT_I = 0;
    /** Index of output slot. */
    private static final int OUTPUT_I = 2;
    /** X and Y position of input slot. */
    private static final int INPUT_POS = 0;
    /** X position of output slot. */
    private static final int OUTPUT_X = 60;
    /** Y position of output slot. */
    private static final int OUTPUT_Y = 18;

    /** Background image. */
    private final IDrawable background;
    /** Flame animation. */
    private final IDrawableAnimated flame;
    /** Arrow animation. */
    private final IDrawableAnimated arrow;
    
    public GeoCookingCategory() {
        
        this.background = GeoJei.guiHelper.createDrawable(BG_RES, 
                BG_X, BG_Y, BG_WIDTH, BG_HEIGHT);
        IDrawableStatic flame = GeoJei.guiHelper.createDrawable(BG_RES,
                ANIM_ORIG_X, FLAME_ORIG_Y, FLAME_SIZE, FLAME_SIZE);
        this.flame = GeoJei.guiHelper.createAnimatedDrawable(flame, FLAME_TICKS,
                IDrawableAnimated.StartDirection.TOP, true);
        IDrawableStatic arrow = GeoJei.guiHelper.createDrawable(BG_RES,
                ANIM_ORIG_X, ARROW_ORIG_Y, ARROW_WIDTH, ARROW_HEIGHT);
        this.arrow = GeoJei.guiHelper.createAnimatedDrawable(arrow, ARROW_TICKS,
                IDrawableAnimated.StartDirection.LEFT, false);
    }
    
    @Override
    public IDrawable getBackground() {
        
        return this.background;
    }
    
    @Override
    public void drawExtras(Minecraft minecraft) {
        
        this.flame.draw(minecraft, FLAME_X, FLAME_Y);
        this.arrow.draw(minecraft, ARROW_X, ARROW_Y);
    }
    
    @Override
    public void setRecipe(IRecipeLayout layout, Wrapper wrapper,
            IIngredients ingredients) {
        
        IGuiItemStackGroup stacks = layout.getItemStacks();
        stacks.init(INPUT_I, true, INPUT_POS, INPUT_POS);
        stacks.init(OUTPUT_I, false, OUTPUT_X, OUTPUT_Y);
        stacks.set(ingredients);
    }
    
    @Override
    public String getUid() {
        
        return Lang.JEICAT_COOKING;
    }
    
    @Override
    public String getTitle() {
        
        return I18n.format(Lang.JEICAT_COOKING);
    }
    
    @Override
    public String getModName() {
        
        return Geomastery.NAME;
    }
    
    /** Recipe containing input, output, and minimum furnace level. */
    public static class Recipe {
        
        /** The input stack. */
        final ItemStack input;
        /** The result stack. */
        final ItemStack output;
        /** The minimum furnace level this recipe can be used at. */
        final String level;
        
        public Recipe(Entry<ItemStack, ItemStack> entry, String level) {
            
            this.input = entry.getKey();
            this.output = entry.getValue();
            this.level = level;
        }
    }
    
    /** Wrapper for one recipe. */
    public static class Wrapper extends BlankRecipeWrapper {
        
        /** X position of level string. */
        private static final int STRING_X = 24;
        /** Y position of level string. */
        private static final int STRING_Y = 4;
        /** Colour of level string. */
        private static final int GREY = Color.gray.getRGB();
        
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
        
        @Override
        public void drawInfo(Minecraft minecraft, int width, int height,
                int mouseX, int mouseY) {
            
            minecraft.fontRendererObj.drawString("Min: " +
                    I18n.format(this.recipe.level), STRING_X, STRING_Y, GREY);
        }
    }
}
