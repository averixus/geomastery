package com.jj.jjmod.gui;

import com.jj.jjmod.crafting.CraftingManager;
import com.jj.jjmod.container.ContainerCrafting;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GuiCrafting extends GuiContainer {

    public static final int FOREGROUND = 4210752;

    public final String NAME;
    public final String TEXTURE;

    public GuiCrafting(EntityPlayer player, World world, BlockPos pos,
            CraftingManager craftManager, String name) {

        super(new ContainerCrafting(player, world, pos, craftManager));
        this.TEXTURE = "jjmod:textures/gui/crafting_" + 
                    ((ContainerCrafting) this.inventorySlots)
                    .capInv.getInventoryRows() + ".png";
        this.NAME = name;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {

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
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
    }
}
