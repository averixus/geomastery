/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.compat;

import java.awt.Color;
import java.util.List;
import java.util.Map.Entry;
import com.google.common.collect.Lists;
import jayavery.geomastery.crafting.CompostManager.CompostType;
import jayavery.geomastery.main.GeoItems;
import jayavery.geomastery.main.Geomastery;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class CompostRecipe {
    
    final static List<List<ItemStack>> outputs = Lists.newArrayList();
    static { 
        NonNullList<ItemStack> single = NonNullList.create();
        GeoItems.COMPOST.getSubItems(GeoItems.COMPOST, CreativeTabs.MATERIALS, single);
        outputs.add(single);
    }
    
    final ItemStack input;
    final CompostType type;
    final IDrawableAnimated fill;
    final IDrawableAnimated arrow;
    
    public CompostRecipe(Entry<ItemStack, CompostType> entry) {
        
        this.input = entry.getKey();
        this.type = entry.getValue();
        ResourceLocation res = new ResourceLocation(Geomastery.MODID, "textures/gui/compost_0.png");
        IDrawableStatic fillDrawable = GeoJei.guiHelper.createDrawable(res, 176, 25, 13, 40);
        this.fill = GeoJei.guiHelper.createAnimatedDrawable(fillDrawable, 200, IDrawableAnimated.StartDirection.BOTTOM, false);
        IDrawableStatic arrowDrawable = GeoJei.guiHelper.createDrawable(res, 176, 8, 24, 17);
        this.arrow = GeoJei.guiHelper.createAnimatedDrawable(arrowDrawable, 400, IDrawableAnimated.StartDirection.LEFT, false);
    }

    public static class Wrapper extends BlankRecipeWrapper {
        
        private final CompostRecipe recipe;
        
        public Wrapper(CompostRecipe recipe) {
            
            this.recipe = recipe;
        }
        
        @Override
        public void getIngredients(IIngredients ingredients) {
            
            ingredients.setInput(ItemStack.class, this.recipe.input);
            ingredients.setOutputLists(ItemStack.class, outputs);
        }
        
        @Override
        public void drawInfo(Minecraft minecraft, int width, int height, int mouseX, int mouseY) {
            
            this.recipe.fill.draw(minecraft, 57, 1);
            this.recipe.arrow.draw(minecraft, 25, 10);
            this.recipe.arrow.draw(minecraft, 76, 10);
            minecraft.fontRendererObj.drawString("Type: " + this.recipe.type, 2, 33, this.recipe.type.getColour().getRGB());
        }
    }
}
