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

public class FuelRecipeWrapper extends BlankRecipeWrapper {

    private final ItemStack fuel;
    private final int time;
    private final String level;
    private final IDrawableAnimated flame;
    
    public FuelRecipeWrapper(Pair<Entry<ItemStack, Integer>, String> pair) {
        
        this.fuel = pair.getLeft().getKey();
        this.time = pair.getLeft().getValue();
        this.level = pair.getRight();
        ResourceLocation furnaceBackgroundLocation = new ResourceLocation("minecraft", "textures/gui/container/furnace.png");
        IDrawableStatic flameDrawable = GeoJei.guiHelper.createDrawable(furnaceBackgroundLocation, 176, 0, 14, 14);
        this.flame = GeoJei.guiHelper.createAnimatedDrawable(flameDrawable, 200, IDrawableAnimated.StartDirection.TOP, true);
    }
    
    @Override
    public void getIngredients(IIngredients ingredients) {

        ingredients.setInput(ItemStack.class, this.fuel);
    }
    
    @Override
    public void drawInfo(Minecraft minecraft, int width, int height, int mouseX, int mouseY) {
        
        this.flame.draw(minecraft, 2, 0);
        minecraft.fontRendererObj.drawString("Minimum furnace level: " + this.level, 24, 8, Color.gray.getRGB());
        minecraft.fontRendererObj.drawString("Base burn time: " + (this.time / 20) + "s", 24, 18, Color.gray.getRGB());
    }
}
