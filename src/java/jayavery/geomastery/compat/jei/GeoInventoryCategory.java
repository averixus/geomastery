/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.compat.jei;

import jayavery.geomastery.main.Geomastery;
import jayavery.geomastery.utilities.Lang;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

/** Category for Geomastery inventory crafting recipes. */
public class GeoInventoryCategory
        extends BlankRecipeCategory<GeoCraftingCategory.Wrapper> {

    /** Location of background image. */
    private static final ResourceLocation BG_RES = new ResourceLocation(Geomastery.MODID, "textures/gui/inventory_0.png");
    /** X position of background image. */
    private static final int BG_X = 79;
    /** Y position of background image. */
    private static final int BG_Y = 17;
    /** Width of background image. */
    private static final int BG_WIDTH = 92;
    /** Height of background image. */
    private static final int BG_HEIGHT = 36;
    
    /** Location of icon image. */
    private static final ResourceLocation ICON_RES = new ResourceLocation(Geomastery.MODID, "textures/logo_small.jpg");
    /** Start position of icon image. */
    private static final int ICON_START = 0;
    /** Size of icon image. */
    private static final int ICON_SIZE = 14;
    
    /** Index of output slot. */
    private static final int OUTPUT_I = 6;
    /** X position of output slot. */
    private static final int OUTPUT_X = 74;
    /** Y position of output slot. */
    private static final int OUTPUT_Y = 10;
    /** Number of crafting grid rows. */
    private static final int CRAFT_ROWS = 2;
    /** Number of crafting grid columns. */
    private static final int CRAFT_COLS = 3;
    /** Size of crafting slot. */
    private static final int SLOT_SIZE = 18;
    

    /** Background image. */
    private final IDrawable background;
    /** Recipe tab icon. */
    private final IDrawable icon;
    
    public GeoInventoryCategory() {
        
        this.background = GeoJei.guiHelper.createDrawable(BG_RES,
                BG_X, BG_Y, BG_WIDTH, BG_HEIGHT);
        this.icon = GeoJei.guiHelper.createDrawable(ICON_RES, ICON_START,
                ICON_START, ICON_SIZE, ICON_SIZE, ICON_SIZE, ICON_SIZE);
    }
    
    @Override
    public String getUid() {
        
        return Lang.CONTAINER_INVENTORY;
    }
    
    @Override
    public String getTitle() {
        
        return I18n.format(Lang.CONTAINER_INVENTORY);
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
    public void setRecipe(IRecipeLayout layout,
            GeoCraftingCategory.Wrapper wrapper, IIngredients ingredients) {
        
        IGuiItemStackGroup stacks = layout.getItemStacks();        
        stacks.init(OUTPUT_I, false, OUTPUT_X, OUTPUT_Y);
        
        for (int y = 0; y < CRAFT_ROWS; y++) {
            
            for (int x = 0; x < CRAFT_COLS; x++) {
                
                int index = x + (y * CRAFT_COLS);
                stacks.init(index, true, x * SLOT_SIZE, y * SLOT_SIZE);
            }
        }
        
        stacks.set(ingredients);
    }
}
