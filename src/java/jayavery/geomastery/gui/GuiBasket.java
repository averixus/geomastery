/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.gui;

import jayavery.geomastery.container.ContainerStorage.Basket;
import jayavery.geomastery.main.Geomastery;
import net.minecraft.util.ResourceLocation;

/** Gui for basket container. */
public class GuiBasket extends GuiContainerAbstract {
    
    /** Texture of this gui. */
    private final ResourceLocation texture;
    
    public GuiBasket(Basket container) {
        
        super(container, "basket");
        this.texture = new ResourceLocation(Geomastery.MODID, 
                "textures/gui/basket_" +
                container.capability.getInventoryRows() + ".png");
    }

    @Override
    protected ResourceLocation getTexture() {

        return this.texture;
    }
}
