package com.jayavery.jjmod.gui;

import com.jayavery.jjmod.container.ContainerCrafting;
import net.minecraft.util.ResourceLocation;

/** Gui for crafting devices */
public class GuiCrafting extends GuiContainerAbstract {

    /** Correctly case reference to the crafting container. */
    private final ContainerCrafting container;
    /** Texture for this gui. */
    private final ResourceLocation texture;

    public GuiCrafting(ContainerCrafting container, String name) {

        super(container, name);
        this.container = container;
        
        if (this.container.tile.hasDurability()) {
        
            this.texture = new ResourceLocation(
                    "jjmod:textures/gui/craftingdurability_" + 
                    this.container.capability.getInventoryRows() + ".png");            
        } else {
            
             this.texture = new ResourceLocation("jjmod:textures/gui/crafting_"
                     + this.container.capability.getInventoryRows() + ".png");
        }
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float ticks,
            int mouseX, int mouseY) {
        
        super.drawGuiContainerBackgroundLayer(ticks, mouseX, mouseY);
        
        if (this.container.tile.hasDurability()) {
            
            int height = Math.round(((15 -
                    (float) this.container.tile.getDurability()) / 15)
                    * DURABILITY_HEIGHT);
            this.drawTexturedModalRect(this.guiLeft + DURABILITY_X,
                    this.guiTop + DURABILITY_Y + height, DURABILITY_SOURCE_X,
                    height, DURABILITY_WIDTH, DURABILITY_HEIGHT);
        }
    }

    @Override
    protected ResourceLocation getTexture() {

        return this.texture;
    }
}
