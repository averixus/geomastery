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
public class GuiCrafting extends GuiContainer {

    /** Text colour */
    private static final int FOREGROUND = 4210752;
    private final String name;
    private final String texture;

    public GuiCrafting(EntityPlayer player, World world, BlockPos pos,
            CraftingManager craftManager, String name) {

        super(new ContainerCrafting(player, world, pos, craftManager));
        this.texture = "jjmod:textures/gui/crafting_" + 
                    ((ContainerCrafting) this.inventorySlots)
                    .capability.getInventoryRows() + ".png";
        this.name = name;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {

        int stringWidth = this.fontRendererObj.getStringWidth(this.name);
        int start = this.xSize / 2 - stringWidth / 2;
        this.fontRendererObj.drawString(this.name, start, 6, FOREGROUND);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float ticks, int mouseX,
            int mouseY) {

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager()
                .bindTexture(new ResourceLocation(this.texture));
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
    }
}
