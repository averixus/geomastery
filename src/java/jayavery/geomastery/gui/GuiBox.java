/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.gui;

import jayavery.geomastery.container.ContainerStorage.Box;
import jayavery.geomastery.main.Geomastery;
import net.minecraft.util.ResourceLocation;

/** Gui for box container. */
public class GuiBox extends GuiContainerAbstract {
    
    /** Texture of this gui. */
    private final ResourceLocation texture;
    
    public GuiBox(Box container) {
        
        super(container, "box");
        this.texture = new ResourceLocation(Geomastery.MODID,
                "textures/gui/box_" +
                container.capability.getInventoryRows() + ".png");
    }

    @Override
    protected ResourceLocation getTexture() {
        
        return this.texture;
    }
}
