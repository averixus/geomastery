/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.compat;

import jayavery.geomastery.main.Geomastery;
import mezz.jei.api.IGuiHelper;
import mezz.jei.plugins.vanilla.crafting.CraftingRecipeCategory;

public class GeoCraftingRecipeCategory extends CraftingRecipeCategory {

    private final String id;
    private final String name;
    
    public GeoCraftingRecipeCategory(IGuiHelper guiHelper, String id, String name) {
        
        super(guiHelper);
        this.id = id;
        this.name = name;
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
