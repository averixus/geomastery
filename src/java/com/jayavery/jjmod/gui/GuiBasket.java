package com.jayavery.jjmod.gui;

import com.jayavery.jjmod.container.ContainerBasket;
import net.minecraft.util.ResourceLocation;

/** Gui for basket container. */
public class GuiBasket extends GuiContainerAbstract {
    
    /** Texture of this gui. */
    private final ResourceLocation texture;
    
    public GuiBasket(ContainerBasket container) {
        
        super(container, "Basket");
        this.texture = new ResourceLocation("jjmod:textures/gui/basket_" +
                container.capability.getInventoryRows() + ".png");
    }

    @Override
    protected ResourceLocation getTexture() {

        return this.texture;
    }
}
