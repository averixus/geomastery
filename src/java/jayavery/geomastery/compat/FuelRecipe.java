/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.compat;

import java.awt.Color;
import java.util.Map.Entry;
import org.apache.commons.lang3.tuple.Pair;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class FuelRecipe {

    final ItemStack fuel;
    final String level;
    final IDrawableAnimated flame;
    
    public FuelRecipe(ItemStack fuel, String level) {
        
        this.fuel = fuel;
        this.level = level;
        ResourceLocation furnaceBackgroundLocation = new ResourceLocation("minecraft", "textures/gui/container/furnace.png");
        IDrawableStatic flameDrawable = GeoJei.guiHelper.createDrawable(furnaceBackgroundLocation, 176, 0, 14, 14);
        this.flame = GeoJei.guiHelper.createAnimatedDrawable(flameDrawable, 200, IDrawableAnimated.StartDirection.TOP, true);
    }
    
    public static class Wrapper extends BlankRecipeWrapper {
        
        private final FuelRecipe recipe;
        
        public Wrapper(FuelRecipe recipe) {
            
            this.recipe = recipe;
        }
    
        @Override
        public void getIngredients(IIngredients ingredients) {
    
            ingredients.setInput(ItemStack.class, this.recipe.fuel);
        }
        
        @Override
        public void drawInfo(Minecraft minecraft, int width, int height, int mouseX, int mouseY) {
            
            this.recipe.flame.draw(minecraft, 2, 0);
            minecraft.fontRendererObj.drawString("Min: " + this.recipe.level, 24, 4, Color.gray.getRGB());
        }
    }
}
