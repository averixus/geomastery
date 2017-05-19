package com.jayavery.jjmod.gui;

import com.jayavery.jjmod.container.ContainerCompost;
import net.minecraft.util.ResourceLocation;

public class GuiCompost extends GuiContainerAbstract {
    
    /** Texture of this gui. */
    private final ResourceLocation texture;

    public GuiCompost(ContainerCompost container) {
        
        super(container, "Compost Heap");
        this.texture = new ResourceLocation("jjmod:textures/gui/compost_" +
                container.capability.getInventoryRows() + ".png");
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float ticks,
            int mouseX, int mouseY) {
        
        super.drawGuiContainerBackgroundLayer(ticks, mouseX, mouseY);
        
        // TODO draw image and bars
    }
    
    @Override
    protected ResourceLocation getTexture() {

        return this.texture;
    }
}
