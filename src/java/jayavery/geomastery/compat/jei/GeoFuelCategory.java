/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.compat.jei;

import java.awt.Color;
import jayavery.geomastery.compat.jei.GeoFuelCategory.Wrapper;
import jayavery.geomastery.main.Geomastery;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import mezz.jei.config.Constants;
import mezz.jei.plugins.vanilla.furnace.FurnaceRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/** Category for Geomastery fuel recipes. */
public class GeoFuelCategory extends FurnaceRecipeCategory<Wrapper> {

    /** X position of background image. */
    private static final int BG_X = 55;
    /** Y position of background image. */
    private static final int BG_Y = 38;
    /** Width of background image. */
    private static final int BG_WIDTH = 18;
    /** Height of background image. */
    private static final int BG_HEIGHT = 32;
    /** Padding for three sides. */
    private static final int PAD_ALL = 0;
    /** Padding for right side. */
    private static final int PAD_RIGHT = 80;
    
    /** X position of icon image. */
    private static final int ICON_X = 215;
    /** Y position of icon image. */
    private static final int ICON_Y = 0;
    /** Width and height of icon image. */
    private static final int ICON_SIZE = 14;
    
    /** X position of flame animation. */
    private static final int FLAME_X = 2;
    /** Y position of flame animation. */
    private static final int FLAME_Y = 0;
    
    /** Index of input slot. */
    private static final int IN_I = 0;
    /** X position of input slot. */
    private static final int IN_X = 0;
    /** Y position of input slot. */
    private static final int IN_Y = 14;
    
    /** Recipe tab name. */
    private final String name = "Fuel";
    /** Internal unique id. */
    private final String uid = Geomastery.MODID + ":fuel";
    /** Backgound image. */
    private final IDrawable background;
    /** Recipe tab icon. */
    private final IDrawableStatic icon;
    
    public GeoFuelCategory() {
        
        super(GeoJei.guiHelper);
        this.background = GeoJei.guiHelper.createDrawable(
                this.backgroundLocation, BG_X, BG_Y, BG_WIDTH, BG_HEIGHT,
                PAD_ALL, PAD_ALL, PAD_ALL, PAD_RIGHT);
        this.icon = GeoJei.guiHelper.createDrawable(this.backgroundLocation,
                ICON_X, ICON_Y, ICON_SIZE, ICON_SIZE);
    }
    
    @Override
    public void setRecipe(IRecipeLayout layout, Wrapper wrapper,
            IIngredients ingredients) {
        
        IGuiItemStackGroup stacks = layout.getItemStacks();
        stacks.init(IN_I, true, IN_X, IN_Y);
        stacks.set(ingredients);
    }
    
    @Override
    public IDrawable getBackground() {
        
        return this.background;
    }
    
    @Override
    public void drawExtras(Minecraft minecraft) {

        this.flame.draw(minecraft, FLAME_X, FLAME_Y);
    }
    
    @Override
    public IDrawable getIcon() {
        
        return this.icon;
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
    
    /** Recipe containing a fuel and it's minimum furnace level. */
    public static class Recipe {

        /** The fuel stack. */
        final ItemStack fuel;
        /** The minimum furnace level. */
        final String level;
        
        public Recipe(ItemStack fuel, String level) {
            
            this.fuel = fuel;
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
    
            ingredients.setInput(ItemStack.class, this.recipe.fuel);
        }
        
        @Override
        public void drawInfo(Minecraft minecraft, int width, int height,
                int mouseX, int mouseY) {
            
            minecraft.fontRendererObj.drawString("Min: " + this.recipe.level,
                    STRING_X, STRING_Y, GREY);
        }
    }
}

