package com.jj.jjmod.gui;

import com.jj.jjmod.container.ContainerFurnace;
import com.jj.jjmod.tileentities.TEFurnaceAbstract;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/** Gui for Furnace devices */
public class GuiFurnace extends GuiContainer {

    /** Text colour */
    private static final int FOREGROUND = 4210752;
    private final String name;
    private final String texture;

    public GuiFurnace(EntityPlayer player, World world, IInventory furnaceInv,
            String name) {

        super(new ContainerFurnace(player, world, furnaceInv));
        this.texture = "jjmod:textures/gui/furnace_" +
                ((ContainerFurnace) this.inventorySlots).capability
                .getInventoryRows() + ".png";
        this.name = name;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {

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

    /** Gets the scaled size of the cook progress rectangle.
     * @return Pixel length of the cook progress rectangle. */
    private int getCookProgress(int pixels) {

        int i = ((TEFurnaceAbstract) ((ContainerFurnace) this.inventorySlots)
                .furnaceInv).getField(2);
        int j = ((TEFurnaceAbstract) ((ContainerFurnace) this.inventorySlots)
                .furnaceInv).getField(3);
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }

    /** Gets the scaled size of the flame progress rectangle.
     * @return Pixel height of the flame progress rectangle. */
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
