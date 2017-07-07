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
import mezz.jei.api.recipe.BlankRecipeCategory;
import net.minecraft.util.ResourceLocation;

public class GeoCompostCategory extends BlankRecipeCategory<CompostRecipe.Wrapper> {
    
    private final IDrawable background;
    private final String name = "Compost Heap";
    private final String id = "geomastery:compost";
    
    public GeoCompostCategory() {
        
        ResourceLocation res = new ResourceLocation(Geomastery.MODID, "textures/gui/compost_0.png");
        this.background = GeoJei.guiHelper.createDrawable(res, 24, 16, 131, 47);
    }
    
    @Override
    public void setRecipe(IRecipeLayout layout, CompostRecipe.Wrapper wrapper, IIngredients ingredients) {
        
        IGuiItemStackGroup stacks = layout.getItemStacks();
        stacks.init(0, true, 0, 10);
        stacks.init(1, false, 109, 10);
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
        
        return Geomastery.NAME;
    }

}
