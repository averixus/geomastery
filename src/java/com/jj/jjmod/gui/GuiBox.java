package com.jj.jjmod.gui;

import com.jj.jjmod.container.ContainerBox;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

/** Gui for Box */
public class GuiBox extends GuiContainerAbstract {
    
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
