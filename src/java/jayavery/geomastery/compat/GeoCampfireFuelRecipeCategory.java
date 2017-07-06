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
import mezz.jei.config.Constants;
import mezz.jei.plugins.vanilla.furnace.FurnaceRecipeCategory;
import net.minecraft.util.ResourceLocation;

public class GeoCampfireFuelRecipeCategory extends FurnaceRecipeCategory<FuelRecipeWrapper> {

    private final IDrawable background;
    private final IDrawable flame;
    private final String name;
    private final String id;
    
    public GeoCampfireFuelRecipeCategory(String name, String id) {
        
        super(GeoJei.guiHelper);
        this.name = name;
        this.id = id;
        this.background = GeoJei.guiHelper.createDrawable(this.backgroundLocation, 55, 38, 18, 32, 0, 0, 0, 80);
        ResourceLocation recipeBackgroundResource = new ResourceLocation(Constants.RESOURCE_DOMAIN, Constants.TEXTURE_RECIPE_BACKGROUND_PATH);
        this.flame = GeoJei.guiHelper.createDrawable(recipeBackgroundResource, 215, 0, 14, 14);
    }
    
    @Override
    public void setRecipe(IRecipeLayout layout, FuelRecipeWrapper wrapper, IIngredients ingredients) {
        
        IGuiItemStackGroup stacks = layout.getItemStacks();
        stacks.init(0, true, 0, 14);
        stacks.set(ingredients);
    }
    
    @Override
    public IDrawable getBackground() {
        
        return this.background;
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
        
        return Geomastery.MODID;
    }
}

