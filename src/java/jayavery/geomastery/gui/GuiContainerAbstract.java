package jayavery.geomastery.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

/** Abstract superclass for gui containers. */
public abstract class GuiContainerAbstract extends GuiContainer {
  
    /** Name text colour. */
    protected static final int TEXT_COLOUR = 4210752;
    /** X-position of centre of name. */
    protected static final int NAME_X = 97;
    /** Y-position of centre of name. */
    protected static final int NAME_Y = 8;
    /** X-position of source of furnace progress arrow. */
    protected static final int ARROW_SOURCE_X = 176;
    /** Y-position of source of furnace progress arrow. */
    protected static final int ARROW_SOURCE_Y = 14;
    /** Height of furnace progress arrow. */
    protected static final int ARROW_HEIGHT = 16;
    /** Length of furnace progress arrow. */
    protected static final int ARROW_LENGTH = 24;
    /** X-position of source of progress flame. */
    protected static final int FLAME_SOURCE_X = 176;
    /** Y-position of source of progress flame. */
    protected static final int FLAME_SOURCE_Y = 12;
    /** Height of progress flame. */
    protected static final int FLAME_HEIGHT = 13;
    /** Width of progress flame. */
    protected static final int FLAME_WIDTH = 14;
    /** X-position of crafting durability bar. */
    protected static final int DURABILITY_X = 9;
    /** Y-position of crafting durability bar. */
    protected static final int DURABILITY_Y = 17;
    /** X-position of source of crafting durability bar. */
    protected static final int DURABILITY_SOURCE_X = 176;
    /** Width of crafting durability bar. */
    protected static final int DURABILITY_WIDTH = 5;
    /** Height of crafting durability bar. */
    protected static final int DURABILITY_HEIGHT = 52;
    
    /** Name of this container. */
    protected final String name;
    
    public GuiContainerAbstract(Container container, String name) {
        
        super(container);
        this.name = name;
    }
    
    /** @return The texture for this container. */
    protected abstract ResourceLocation getTexture();
    
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        
        int stringWidth = this.fontRendererObj.getStringWidth(this.name);
        int start = this.xSize / 2 - stringWidth / 2;
        this.fontRendererObj.drawString(this.name, start, 6, TEXT_COLOUR);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float ticks,
            int mouseX, int mouseY) {
        
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(this.getTexture());
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0,
                this.xSize, this.ySize);
    }

    /** Utility method to draw the player, copied from vanilla */
    protected static void drawEntityOnScreen(int posX, int posY, int scale,
            float mouseX, float mouseY, EntityLivingBase ent) {

        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate(posX, posY, 50.0F);
        GlStateManager.scale((-scale), scale,
                scale);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        float f = ent.renderYawOffset;
        float f1 = ent.rotationYaw;
        float f2 = ent.rotationPitch;
        float f3 = ent.prevRotationYawHead;
        float f4 = ent.rotationYawHead;
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(
                -((float) Math.atan(mouseY / 40.0F)) * 20.0F,
                1.0F, 0.0F, 0.0F);
        ent.renderYawOffset =
                (float) Math.atan(mouseX / 40.0F) * 20.0F;
        ent.rotationYaw =
                (float) Math.atan(mouseX / 40.0F) * 40.0F;
        ent.rotationPitch =
                -((float) Math.atan(mouseY / 40.0F)) * 20.0F;
        ent.rotationYawHead = ent.rotationYaw;
        ent.prevRotationYawHead = ent.rotationYaw;
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager rendermanager =
                Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);
        rendermanager.doRenderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F,
                false);
        rendermanager.setRenderShadow(true);
        ent.renderYawOffset = f;
        ent.rotationYaw = f1;
        ent.rotationPitch = f2;
        ent.prevRotationYawHead = f3;
        ent.rotationYawHead = f4;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
}
