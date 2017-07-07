/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.compat;

import jayavery.geomastery.main.Geomastery;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class GeoDryingCategory extends BlankRecipeCategory<DryingRecipe.Wrapper> {

    private final IDrawable background;
    private final IDrawableAnimated arrow;
    private final String name = "Drying Rack";
    private final String id = "geomastery:drying";
    
    public GeoDryingCategory() {
        
        ResourceLocation res = new ResourceLocation(Geomastery.MODID, "textures/gui/drying_0.png");
        this.background = GeoJei.guiHelper.createDrawable(res, 48, 34, 82, 18);
        
        IDrawableStatic arrowStatic = GeoJei.guiHelper.createDrawable(res, 176, 14, 24, 17);
        this.arrow = GeoJei.guiHelper.createAnimatedDrawable(arrowStatic, 200, IDrawableAnimated.StartDirection.LEFT, false);
    }
    
    @Override
    public IDrawable getBackground() {
        
        return this.background;
    }
    
    @Override
    public void drawExtras(Minecraft minecraft) {
        
        this.arrow.draw(minecraft, 29, 0);
    }
    
    @Override
    public void setRecipe(IRecipeLayout layout, DryingRecipe.Wrapper wrapper, IIngredients ingredients) {
        
        IGuiItemStackGroup stacks = layout.getItemStacks();
        stacks.init(0, true, 0, 0);
        stacks.init(1, false, 64, 0);
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
