package com.jj.jjmod.gui;

import com.jj.jjmod.crafting.CraftingManager;
import com.jj.jjmod.container.ContainerCrafting;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Gui for Crafting devices */
public class GuiCrafting extends GuiContainerAbstract {

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
