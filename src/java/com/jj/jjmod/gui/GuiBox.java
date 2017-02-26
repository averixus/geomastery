package com.jj.jjmod.gui;

import com.jj.jjmod.container.ContainerBox;
import net.minecraft.util.ResourceLocation;

/** Gui for box container. */
public class GuiBox extends GuiContainerAbstract {
    
    /** Texture of this gui. */
    private final ResourceLocation texture;
    
    public GuiBox(ContainerBox container) {
        
        super(container, "Box");
        this.texture = new ResourceLocation("jjmod:textures/gui/box_" +
                container.capability.getInventoryRows() + ".png");
    }

    @Override
    protected ResourceLocation getTexture() {

        return this.texture;
    }
}
