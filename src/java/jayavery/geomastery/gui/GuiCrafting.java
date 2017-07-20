/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.gui;

import jayavery.geomastery.container.ContainerCrafting;
import jayavery.geomastery.main.Geomastery;
import jayavery.geomastery.tileentities.TECraftingAbstract;
import net.minecraft.util.ResourceLocation;

/** Gui for crafting devices */
public class GuiCrafting extends GuiContainerAbstract {

    /** Correctly case reference to the crafting container. */
    private final ContainerCrafting container;
    /** Texture for this gui. */
    private final ResourceLocation texture;

    public GuiCrafting(ContainerCrafting container, String name) {

        super(container, name);
        this.container = container;
        
        if (this.container.tile.hasDurability()) {
        
            this.texture = new ResourceLocation(Geomastery.MODID,
                    "textures/gui/craftingdurability_" + 
                    this.container.capability.getInventoryRows() + ".png");            
        } else {
            
             this.texture = new ResourceLocation(Geomastery.MODID,
                     "textures/gui/crafting_"
                     + this.container.capability.getInventoryRows() + ".png");
        }
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float ticks,
            int mouseX, int mouseY) {
        
        super.drawGuiContainerBackgroundLayer(ticks, mouseX, mouseY);
        
        if (this.container.tile.hasDurability()) {
            
            int height = Math.round(((TECraftingAbstract.MAX_DURABILITY -
                    (float) this.container.tile.getDurability()) /
                    TECraftingAbstract.MAX_DURABILITY) * DURABILITY_HEIGHT);
            this.drawTexturedModalRect(this.guiLeft + DURABILITY_X,
                    this.guiTop + DURABILITY_Y + height, DURABILITY_SOURCE_X,
                    height, DURABILITY_WIDTH, DURABILITY_HEIGHT);
        }
    }

    @Override
    protected ResourceLocation getTexture() {

        return this.texture;
    }
}
