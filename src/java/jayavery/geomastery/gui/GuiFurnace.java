/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.gui;

import jayavery.geomastery.container.ContainerFurnaceAbstract;
import jayavery.geomastery.main.Geomastery;
import net.minecraft.util.ResourceLocation;

/** Gui for furnace devices */
public class GuiFurnace extends GuiContainerAbstract {

    /** Correctly cast reference to the furnace container. */
    private final ContainerFurnaceAbstract container;
    /** Texture of this gui. */
    private final ResourceLocation texture;

    public GuiFurnace(ContainerFurnaceAbstract container, String name) {

        super(container, name);
        this.container = container;
        this.texture = new ResourceLocation(Geomastery.MODID,
                "textures/gui/furnace" + this.container.size + "_" +
                this.container.capability.getInventoryRows() + ".png");
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float ticks, int mouseX,
            int mouseY) {
        
        super.drawGuiContainerBackgroundLayer(ticks, mouseX, mouseY);

        if (this.container.furnace.isUsingFuel()) {
            
            int fuelEach = this.container.furnace.getFuelEach();
            fuelEach = fuelEach == 0 ? 200 : fuelEach;
            int fuelLeft = this.container.furnace.getFuelLeft();
            int flameHeight = fuelLeft * FLAME_HEIGHT / fuelEach;
            int flameSourceY = FLAME_SOURCE_Y - flameHeight;
            
            this.drawTexturedModalRect(this.guiLeft + this.container.fireX,
                    this.guiTop + this.container.fireY + flameSourceY,
                    FLAME_SOURCE_X, flameSourceY, FLAME_WIDTH, flameHeight + 1);
        }

        int cookSpent = this.container.furnace.getCookSpent();
        int cookEach = this.container.furnace.getCookEach();
        int arrowLength = cookSpent != 0 && cookEach != 0 ?
                cookSpent * ARROW_LENGTH / cookEach : 0;
        this.drawTexturedModalRect(this.guiLeft + this.container.arrowX,
                this.guiTop + this.container.arrowY, ARROW_SOURCE_X,
                ARROW_SOURCE_Y, arrowLength + 1, ARROW_HEIGHT);
    }

    @Override
    protected ResourceLocation getTexture() {

        return this.texture;
    }
}
