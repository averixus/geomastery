/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.compat.jei;

import java.util.List;
import jayavery.geomastery.main.Geomastery;
import jayavery.geomastery.main.GuiHandler.GuiList;
import mezz.jei.api.gui.ICraftingGridHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

/** Category for Geomastery inventory crafting recipes. */
public class GeoInventoryCategory
        extends BlankRecipeCategory<GeoCraftingCategory.Wrapper> {

    /** Location of background image. */
    private static final ResourceLocation BG_RES = new ResourceLocation(
            Geomastery.MODID, "textures/gui/inventory_0.png");
    /** X position of background image. */
    private static final int BG_X = 79;
    /** Y position of background image. */
    private static final int BG_Y = 17;
    /** Width of background image. */
    private static final int BG_WIDTH = 92;
    /** Height of background image. */
    private static final int BG_HEIGHT = 36;
    
    /** Location of icon image. */
    private static final ResourceLocation ICON_RES = new ResourceLocation(
            Geomastery.MODID, "textures/logo_small.jpg");
    /** Start position of icon image. */
    private static final int ICON_START = 0;
    /** Size of icon image. */
    private static final int ICON_SIZE = 14;
    
    /** Recipe tab unlocalised name. */
    private final String name = Geomastery.MODID + ":container.inventory";
    /** Internal unique id. */
    private final String uid = Geomastery.MODID + ":inventory";
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
        
        return this.uid;
    }
    
    @Override
    public String getTitle() {
        
        return I18n.translateToLocal(this.name);
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
