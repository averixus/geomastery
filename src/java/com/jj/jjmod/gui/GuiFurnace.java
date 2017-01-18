package com.jj.jjmod.gui;

import com.jj.jjmod.container.ContainerFurnace;
import com.jj.jjmod.tileentities.TEFurnaceAbstract;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GuiFurnace extends GuiContainer {

    public static final int FOREGROUND = 4210752;
    
    public final String TEXTURE;
    public final String NAME;

    public GuiFurnace(EntityPlayer player, World world, IInventory furnaceInv,
            String name) {

        super(new ContainerFurnace(player, world, furnaceInv));
        this.TEXTURE = "jjmod:textures/gui/furnace_" +
                ((ContainerFurnace) this.inventorySlots).capInv
                .getInventoryRows() + ".png";
        this.NAME = name;
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

        if (((TEFurnaceAbstract) ((ContainerFurnace) this.inventorySlots)
                .furnaceInv).isBurning()) {
            
            int k = this.getBurnLeft(13);
            this.drawTexturedModalRect(i + 56, j + 36 + 12 - k, 176,
                    12 - k, 14, k + 1);
        }

        int l = this.getCookProgress(24);
        this.drawTexturedModalRect(i + 79, j + 34, 176, 14, l + 1, 16);
    }

    private int getCookProgress(int pixels) {

        int i = ((TEFurnaceAbstract) ((ContainerFurnace) this.inventorySlots)
                .furnaceInv).getField(2);
        int j = ((TEFurnaceAbstract) ((ContainerFurnace) this.inventorySlots)
                .furnaceInv).getField(3);
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }

    private int getBurnLeft(int pixels) {

        int i = ((TEFurnaceAbstract) ((ContainerFurnace) this.inventorySlots)
                .furnaceInv).getField(1);

        if (i == 0) {
            
            i = 200;
        }

        return ((TEFurnaceAbstract) ((ContainerFurnace) this.inventorySlots)
                .furnaceInv).getField(0) * pixels / i;
    }
}
