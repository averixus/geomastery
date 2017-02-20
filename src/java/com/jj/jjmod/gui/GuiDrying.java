package com.jj.jjmod.gui;

import com.jj.jjmod.container.ContainerDrying;
import com.jj.jjmod.tileentities.TEDrying;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

/** Gui for Drying Rack */
public class GuiDrying extends GuiContainerAbstract {

    private static final int ARROW_X = 78;
    private static final int ARROW_Y = 34;
    
    private final ResourceLocation texture;

    public GuiDrying(ContainerDrying container) {

        super(container, "Drying Rack");
        this.texture = new ResourceLocation("jjmod:textures/gui/drying_" +
                container.capability.getInventoryRows() + ".png");
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float ticks, int mouseX,
            int mouseY) {

        super.drawGuiContainerBackgroundLayer(ticks, mouseX, mouseY);
        
        int drySpent = ((ContainerDrying) this.inventorySlots).drying.getDrySpent();
        int dryEach = ((ContainerDrying) this.inventorySlots).drying.getDryEach();
        int arrowLength = dryEach != 0 && drySpent != 0 ?
                drySpent * ARROW_LENGTH / dryEach : 0;
        
        this.drawTexturedModalRect(this.guiLeft + ARROW_X,
                this.guiTop + ARROW_Y, ARROW_SOURCE_X, ARROW_SOURCE_Y,
                arrowLength + 1, ARROW_HEIGHT);
    }

    @Override
    protected ResourceLocation getTexture() {

        return this.texture;
    }
}
