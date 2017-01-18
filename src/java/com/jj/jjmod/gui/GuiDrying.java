package com.jj.jjmod.gui;

import com.jj.jjmod.container.ContainerDrying;
import com.jj.jjmod.tileentities.TEDrying;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiDrying extends GuiContainer {

    public static final int FOREGROUND = 4210752;
    public static final String NAME = "Drying Rack";
    
    public final String TEXTURE;

    public GuiDrying(ContainerDrying container) {

        super(container);
        this.TEXTURE = "jjmod:textures/gui/drying_" +
                container.capInv.getInventoryRows() + ".png";
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {

        int stringWidth = this.fontRendererObj.getStringWidth(this.NAME);
        int start = this.xSize / 2 - stringWidth / 2;

        this.fontRendererObj.drawString(this.NAME, start, 6, FOREGROUND);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float ticks, int mouseX,
            int mouseY) {

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager()
                .bindTexture(new ResourceLocation(this.TEXTURE));
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        int l = this.getDryProgress(24);
        this.drawTexturedModalRect(i + 79, j + 34, 176, 14, l + 1, 16);
    }

    private int getDryProgress(int pixels) {

        int i = ((TEDrying) ((ContainerDrying) this.inventorySlots).dryingInv)
                .getField(0);
        int j = ((TEDrying) ((ContainerDrying) this.inventorySlots).dryingInv)
                .getField(1);
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }
}
