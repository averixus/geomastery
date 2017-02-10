package com.jj.jjmod.gui;

import com.jj.jjmod.container.ContainerBox;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

/** Gui for Box */
public class GuiBox extends GuiContainer {
    
    /** Text colour */
    private static final int FOREGROUND = 4210752;
    private static final String NAME = "Box";
    private final String texture;
    
    public GuiBox(ContainerBox container) {
        
        super(container);
        this.texture = "jjmod:textures/gui/box_" +
                container.capability.getInventoryRows() + ".png";
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        
        int stringWidth = this.fontRendererObj.getStringWidth(NAME);
        int start = this.xSize / 2 - stringWidth / 2;

        this.fontRendererObj.drawString(NAME, start, 6, FOREGROUND);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float ticks,
            int mouseX, int mouseY) {
        
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager()
                .bindTexture(new ResourceLocation(this.texture));
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
    }
}
