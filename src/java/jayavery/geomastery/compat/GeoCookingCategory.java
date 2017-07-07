/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.compat;

import jayavery.geomastery.main.Geomastery;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.config.Constants;
import mezz.jei.plugins.vanilla.furnace.FurnaceRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class GeoCookingCategory extends FurnaceRecipeCategory<CookingRecipe.Wrapper> {

    private final String name;
    private final String id;
    private final IDrawable background;
    
    public GeoCookingCategory(String name, String id) {
        
        super(GeoJei.guiHelper);
        this.name = name;
        this.id = id;
        this.background = GeoJei.guiHelper.createDrawable(new ResourceLocation(Geomastery.MODID, "textures/gui/furnace1_0.png"), 54, 16, 83, 54);
    }
    
    @Override
    public IDrawable getBackground() {
        
        return this.background;
    }
    
    @Override
    public void drawExtras(Minecraft minecraft) {
        
        this.flame.draw(minecraft, 2, 20);
        this.arrow.draw(minecraft, 24, 18);
    }
    
    @Override
    public void setRecipe(IRecipeLayout layout, CookingRecipe.Wrapper wrapper, IIngredients ingredients) {
        
        IGuiItemStackGroup stacks = layout.getItemStacks();
        stacks.init(0, true, 0, 0);
        stacks.init(2, false, 60, 18);
        stacks.set(ingredients);
    }
    
    @Override
    public String getUid() {
        
        return this.id;
    }
    
    @Override
    public String getTitle() {
        
        return this.name;
    }
    
    @Override
    public String getModName() {
        
        return Geomastery.NAME;
    }
}
