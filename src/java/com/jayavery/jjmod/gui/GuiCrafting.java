package com.jayavery.jjmod.gui;

import com.jayavery.jjmod.container.ContainerCrafting;
import net.minecraft.util.ResourceLocation;

/** Gui for crafting devices */
public class GuiCrafting extends GuiContainerAbstract {

    /** Texture for this gui. */
    private final ResourceLocation texture;

    public GuiCrafting(ContainerCrafting container, String name) {

        super(container, name);
        this.texture = new ResourceLocation("jjmod:textures/gui/crafting_" + 
                    ((ContainerCrafting) this.inventorySlots)
                    .capability.getInventoryRows() + ".png");
    }

    @Override
    protected ResourceLocation getTexture() {

        return this.texture;
    }
}
