package com.jj.jjmod.gui;

import com.jj.jjmod.container.ContainerDrying;
import com.jj.jjmod.tileentities.TEDrying;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

/** Gui for Drying Rack */
public class GuiDrying extends GuiContainer {

    /** Text colour */
    private static final int FOREGROUND = 4210752;
    private static final String NAME = "Drying Rack";
    private final String texture;

    public GuiDrying(ContainerDrying container) {

        super(container);
        this.texture = "jjmod:textures/gui/drying_" +
                container.capability.getInventoryRows() + ".png";
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {

        int stringWidth = this.fontRendererObj.getStringWidth(NAME);
        int start = this.xSize / 2 - stringWidth / 2;

        this.fontRendererObj.drawString(NAME, start, 6, FOREGROUND);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float ticks, int mouseX,
            int mouseY) {

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager()
                .bindTexture(new ResourceLocation(this.texture));
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        int l = this.getDryProgress(24);
        this.drawTexturedModalRect(i + 79, j + 34, 176, 14, l + 1, 16);
    }

    /** Gets the scaled size of the drying progress rectangle.
     * @return Pixel length of drying progress rectangle. */
    private int getDryProgress(int pixels) {

        int i = ((ContainerDrying) this.inventorySlots).drying
                .getDrySpent();
        int j = ((ContainerDrying) this.inventorySlots).drying
                .getDryEach();
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }
}
