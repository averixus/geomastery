/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.main;

import jayavery.geomastery.capabilities.ICapPlayer;
import jayavery.geomastery.container.ContainerInventory;
import jayavery.geomastery.utilities.EBlockWeight;
import jayavery.geomastery.utilities.EFoodType;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent.OverlayType;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/** Handler for GUI and player input related events. */
public class GuiEvents {
    
    /** Texture for the carbs food icons. */
    private static final ResourceLocation CARBS_TEXTURE = new ResourceLocation(Geomastery.MODID, "textures/gui/carbs.png");
    /** Texture for the protein food icons. */
    private static final ResourceLocation PROTEIN_TEXTURE = new ResourceLocation(Geomastery.MODID, "textures/gui/protein.png");
    /** Texture for the fruitveg food icons. */
    private static final ResourceLocation FRUITVEG_TEXTURE = new ResourceLocation(Geomastery.MODID, "textures/gui/fruitveg.png");
    /** Texture for the backpack slot icon. */
    private static final ResourceLocation BACKPACK_TEXTURE = new ResourceLocation(Geomastery.MODID, "gui/backpack_slot");
    /** Texture for the yoke slot icon. */
    private static final ResourceLocation YOKE_SLOT = new ResourceLocation(Geomastery.MODID, "gui/yoke_slot");
    /** Texture for the vanilla icons. */
    private static final ResourceLocation ICONS = new ResourceLocation("textures/gui/icons.png");
    /** Texture for tar fluid. */
    private static final ResourceLocation TAR = new ResourceLocation(Geomastery.MODID, "textures/blocks/liquids/tar_overlay.png");
    
    /** X offset of temperature icon for left-handed player. */
    private static final int TEMP_OFFSET_X_LEFT = -114;
    /** X offset of temperature icon for right-handed player. */
    private static final int TEMP_OFFSET_X_RIGHT = 96;
    /** Y offset of temperature icon. */
    private static final int TEMP_OFFSET_Y = -21;
    /** Size of temperature icon. */
    private static final int TEMP_ICON_SIZE = 18;
    
    /** Spacing of standard style ICONS. */
    private static final int ICON_SPACING = 8;
    /** Size of standard style ICONS. */
    private static final int ICON_SIZE = 9;
    
    /** Y offset of air bar. */
    private static final int AIR_OFFSET_Y = -49;
    /** X offset of air bar. */
    private static final int AIR_OFFSET_X = -91;
    /** X position of full air icon in texture. */
    private static final int AIR_ICON_X_FULL = 16;
    /** X position of partial air icon in texture. */
    private static final int AIR_ICON_X_PARTIAL = 25;
    /** Y position of air icons in texture. */
    private static final int AIR_ICON_Y = 18;
    
    /** X offset of food icons. */
    private static final int FOOD_OFFSET_X = 82;
    /** Y offset of food icons. */
    private static final int FOOD_OFFSET_Y = -39;
    /** X size of food icons. */
    private static final int FOOD_SIZE_X = 27;
    /** Y size of food icons. */
    private static final int FOOD_SIZE_Y = 9;
    /** X position of background food icon in texture. */
    private static final int FOOD_BACKGROUND_X = 0;
    /** X position of full food icon in texture. */
    private static final int FOOD_FULL_X = 9;
    /** X position of partial food icon in texture. */
    private static final int FOOD_PARTIAL_X = 18;
    /** Vertical spacing between rows of food icons. */
    private static final int FOOD_SPACING = -10;
    
    /** Registers ICONS for container slot backgrounds. */
    @SubscribeEvent
    public void textureStich(TextureStitchEvent.Pre event) {

        event.getMap().registerSprite(BACKPACK_TEXTURE);
        event.getMap().registerSprite(YOKE_SLOT);
    }
    
    /** Alters which vanilla Gui is opened. */
    @SubscribeEvent
    public void guiOpen(GuiOpenEvent event) {

        EntityPlayer player = Minecraft.getMinecraft().player;

        if (event.getGui() instanceof
                net.minecraft.client.gui.inventory.GuiInventory &&
                player.inventoryContainer instanceof ContainerInventory) {
        
            event.setGui(new jayavery.geomastery.gui.GuiInventory(
                    (ContainerInventory) player.inventoryContainer));
        }
    }
    
    /** Adds block weight to non BlockBuildingAbstract tooltips if config. */
    @SubscribeEvent
    public void addTooltips(ItemTooltipEvent event) {
        
        if (GeoConfig.buildTooltips) {
            
            Item item = event.getItemStack().getItem();
            Block block = Block.getBlockFromItem(item);
            
            if (item instanceof ItemBlock) {
            
                event.getToolTip().add(I18n.format(EBlockWeight
                        .getWeight(block.getDefaultState()).supports()));
            }
        }
    }
    
    /** Adds temperature information to debug screen. */
    @SubscribeEvent
    public void renderDebug(RenderGameOverlayEvent.Text event) {
        
        if (Minecraft.getMinecraft().gameSettings.showDebugInfo) {
            
            EntityPlayer player = Geomastery.proxy.getClientPlayer();
            event.getRight().add("");
            event.getRight().addAll(player.getCapability(GeoCaps.CAP_PLAYER,
                    null).getDebug());
        }
    }
    
    /** Replaces water overlay on tar. */
    @SubscribeEvent
    public void renderWaterOverlay(RenderBlockOverlayEvent event) {
        
        EntityPlayer player = event.getPlayer();
        
        if (event.getOverlayType() == OverlayType.WATER &&
                player.world.getBlockState(event.getBlockPos())
                .getBlock() == GeoBlocks.tar) {
            
            Minecraft.getMinecraft().getTextureManager().bindTexture(TAR);
            Tessellator tess = Tessellator.getInstance();
            VertexBuffer vert = tess.getBuffer();
            float f = player.getBrightness(event.getRenderPartialTicks());
            GlStateManager.color(f, f, f, 0.5F);
            GlStateManager.enableBlend();
            GlStateManager.pushMatrix();
            float f7 = -player.rotationYaw / 64.0F;
            float f8 = player.rotationPitch / 64.0F;
            vert.begin(7, DefaultVertexFormats.POSITION_TEX);
            vert.pos(-1.0D, -1.0D, -0.5D).tex(4.0F + f7, 4.0F + f8).endVertex();
            vert.pos(1.0D, -1.0D, -0.5D).tex(0.0F + f7, 4.0F + f8).endVertex();
            vert.pos(1.0D, 1.0D, -0.5D).tex(0.0F + f7, 0.0F + f8).endVertex();
            vert.pos(-1.0D, 1.0D, -0.5D).tex(4.0F + f7, 0.0F + f8).endVertex();
            tess.draw();
            GlStateManager.popMatrix();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 0.1F);
            GlStateManager.disableBlend();
            event.setCanceled(true);
        }
    }
    
    /** Alters HUD rendering. */
    @SubscribeEvent
    public void renderGameOverlay(RenderGameOverlayEvent.Pre event) {
        
        EntityPlayer player = Geomastery.proxy.getClientPlayer();
        int centreX = event.getResolution().getScaledWidth() / 2;
        int resHeight = event.getResolution().getScaledHeight();
        
        if (event.getType() == ElementType.HOTBAR && GeoConfig.temperature) {
            
            drawTempIcon(player, centreX, resHeight);
        }
        
        if (event.getType() == ElementType.AIR) {

            if (player.isInsideOfMaterial(Material.WATER)) {
                
                drawAirBubbles(player, centreX, resHeight);
            }
            
            event.setCanceled(true);
        }
        
        if (event.getType() == ElementType.FOOD) {

            drawFoodBars(player, centreX, resHeight);
            event.setCanceled(true);
        }
    }
    
    /** Draws the temperature icon opposite the player's offhand. */
    private static void drawTempIcon(EntityPlayer player,
            int centreX, int resHeight) {
        
        EnumHandSide hand = player.getPrimaryHand();
        ResourceLocation icon = player.getCapability(GeoCaps
                .CAP_PLAYER, null).getTempIcon();
        
        int x = hand == EnumHandSide.LEFT ?
                centreX + TEMP_OFFSET_X_LEFT : centreX + TEMP_OFFSET_X_RIGHT;
        int y = resHeight + TEMP_OFFSET_Y;
        
        Minecraft.getMinecraft().getTextureManager().bindTexture(icon);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0,
                TEMP_ICON_SIZE, TEMP_ICON_SIZE, TEMP_ICON_SIZE, TEMP_ICON_SIZE);
    }
    
    /** Draws the player's air bubbles above health bar. */
    private static void drawAirBubbles(EntityPlayer player,
            int centreX, int resHeight) {
        
        Minecraft.getMinecraft().getTextureManager().bindTexture(ICONS);
        GlStateManager.enableBlend();
        int left = centreX + AIR_OFFSET_X;
        int top = resHeight + AIR_OFFSET_Y;
        
        int air = player.getAir();
        int full = MathHelper.ceil((air - 2) * 10.0D / 300.0D);
        int partial = MathHelper.ceil(air * 10.0D / 300.0D) - full;
        
        for (int i = 0; i < full + partial; i++) {
            
            new Gui().drawTexturedModalRect(left + i * ICON_SPACING,
                    top, (i < full ? AIR_ICON_X_FULL : AIR_ICON_X_PARTIAL),
                    AIR_ICON_Y, ICON_SIZE, ICON_SIZE);
        }
        
        GlStateManager.disableBlend();
    }
    
    /** Draws the player's three food bars. */
    private static void drawFoodBars(EntityPlayer player,
            int centreX, int resHeight) {
        
        GlStateManager.enableBlend();
        int left = centreX + FOOD_OFFSET_X;
        int top = resHeight + FOOD_OFFSET_Y;
        ICapPlayer playerCap = player.getCapability(GeoCaps.CAP_PLAYER, null);
        
        int carbsHunger = playerCap.foodLevel(EFoodType.CARBS);
        Minecraft.getMinecraft().getTextureManager().bindTexture(CARBS_TEXTURE);
        
        for (int i = 0; i < 10; i++) {
            
            int idx = i * 2 + 1;
            int x = left - i * ICON_SPACING;
            
            Gui.drawModalRectWithCustomSizedTexture(x, top, FOOD_BACKGROUND_X,
                    0, ICON_SIZE, ICON_SIZE, FOOD_SIZE_X, FOOD_SIZE_Y);
            
            if (idx < carbsHunger) {
                
                Gui.drawModalRectWithCustomSizedTexture(x, top, FOOD_FULL_X, 0,
                        ICON_SIZE, ICON_SIZE, FOOD_SIZE_X, FOOD_SIZE_Y);
                
            } else if (idx == carbsHunger) {
                
                Gui.drawModalRectWithCustomSizedTexture(x, top, FOOD_PARTIAL_X,
                        0, ICON_SIZE, ICON_SIZE, FOOD_SIZE_X, FOOD_SIZE_Y);
            }
        }
        
        int fruitvegHunger = playerCap.foodLevel(EFoodType.FRUITVEG);
        Minecraft.getMinecraft().getTextureManager()
                .bindTexture(FRUITVEG_TEXTURE);
        top += FOOD_SPACING;
        
        for (int i = 0; i < 10; i++) {
            
            int idx = i * 2 + 1;
            int x = left - i * ICON_SPACING;
            
            Gui.drawModalRectWithCustomSizedTexture(x, top, FOOD_BACKGROUND_X,
                    0, ICON_SIZE, ICON_SIZE, FOOD_SIZE_X, FOOD_SIZE_Y);
            
            if (idx < fruitvegHunger) {
                
                Gui.drawModalRectWithCustomSizedTexture(x, top, FOOD_FULL_X, 0,
                        ICON_SIZE, ICON_SIZE, FOOD_SIZE_X, FOOD_SIZE_Y);
                
            } else if (idx == fruitvegHunger) {
                
                Gui.drawModalRectWithCustomSizedTexture(x, top, FOOD_PARTIAL_X,
                        0, ICON_SIZE, ICON_SIZE, FOOD_SIZE_X, FOOD_SIZE_Y);
            }
        }
        
        int proteinHunger = playerCap.foodLevel(EFoodType.PROTEIN);
        Minecraft.getMinecraft().getTextureManager()
                .bindTexture(PROTEIN_TEXTURE);
        top += FOOD_SPACING;
        
        for (int i = 0; i < 10; i++) {
            
            int idx = i * 2 + 1;
            int x = left - i * ICON_SPACING;
            
            Gui.drawModalRectWithCustomSizedTexture(x, top, FOOD_BACKGROUND_X,
                    0, ICON_SIZE, ICON_SIZE, FOOD_SIZE_X, FOOD_SIZE_Y);
            
            if (idx < proteinHunger) {
                
                Gui.drawModalRectWithCustomSizedTexture(x, top, FOOD_FULL_X, 0,
                        ICON_SIZE, ICON_SIZE, FOOD_SIZE_X, FOOD_SIZE_Y);
                
            } else if (idx == proteinHunger) {
                
                Gui.drawModalRectWithCustomSizedTexture(x, top, FOOD_PARTIAL_X,
                        0, ICON_SIZE, ICON_SIZE, FOOD_SIZE_X, FOOD_SIZE_Y);
            }
        }
    }
}
