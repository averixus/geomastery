/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.compat;

import java.util.List;
import jayavery.geomastery.main.Geomastery;
import mezz.jei.api.gui.ICraftingGridHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GeoInventoryCategory extends BlankRecipeCategory<IRecipeWrapper> {

    private final IDrawable background;
    private final IDrawable icon;
    private final String name = "Inventory";
    private final String id = "geomastery:inventory";
    private final ICraftingGridHelper gridHelper;
    
    public GeoInventoryCategory() {
        
        ResourceLocation res = new ResourceLocation(Geomastery.MODID, "textures/gui/inventory_0.png");
        this.background = GeoJei.guiHelper.createDrawable(res, 79, 17, 92, 36);
        this.icon = GeoJei.guiHelper.createDrawable(new ResourceLocation(Geomastery.MODID, "textures/logo_small.jpg"), 0, 0, 14, 14, 14, 14);
        this.gridHelper = GeoJei.guiHelper.createCraftingGridHelper(0, 1);
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
    
    @Override
    public IDrawable getBackground() {
        
        return this.background;
    }
    
    @Override
    public IDrawable getIcon() {
        
        return this.icon;
    }
    
    @Override
    public void setRecipe(IRecipeLayout layout, IRecipeWrapper wrapper, IIngredients ingredients) {
        
        IGuiItemStackGroup stacks = layout.getItemStacks();        
        stacks.init(6, false, 74, 10);
        
        for (int y = 0; y < 2; y++) {
            
            for (int x = 0; x < 3; x++) {
                
                int index = x + (y * 3);
                stacks.init(index, true, x * 18, y * 18);
            }
        }
        
        stacks.set(ingredients);
        
    }
}
