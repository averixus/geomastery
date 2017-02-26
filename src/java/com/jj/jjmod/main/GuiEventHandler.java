package com.jj.jjmod.main;

import com.jj.jjmod.capabilities.ICapPlayer;
import com.jj.jjmod.container.ContainerInventory;
import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.init.ModCaps;
import com.jj.jjmod.utilities.FoodType;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

/** Handler for Gui and player input related events. */
public class GuiEventHandler {
    
    /** Texture for the carbs food icons. */
    private static final ResourceLocation carbsTexture =
            new ResourceLocation("jjmod:textures/gui/carbs.png");
    /** Texture for the protein food icons. */
    private static final ResourceLocation proteinTexture =
            new ResourceLocation("jjmod:textures/gui/protein.png");
    /** Texture for the fruitveg food icons. */
    private static final ResourceLocation fruitvegTexture =
            new ResourceLocation("jjmod:textures/gui/fruitveg.png");
    /** Texture for the backpack slot icon. */
    private static final ResourceLocation backpackTexture = new
            ResourceLocation("jjmod:gui/backpack_slot");
    /** Texture for the yoke slot icon. */
    private static final ResourceLocation yokeTexture = new
            ResourceLocation("jjmod:gui/yoke_slot");
    /** Texture for the vanilla icons. */
    private static final ResourceLocation icons = new
            ResourceLocation("textures/gui/icons.png");
    
    /** X offset of temperature icon for left-handed player. */
    private static final int tempOffsetXLeft = -114;
    /** X offset of temperature icon for right-handed player. */
    private static final int tempOffsetXRight = 96;
    /** Y offset of temperature icon. */
    private static final int tempOffsetY = -21;
    /** Size of temperature icon. */
    private static final int tempIconSize = 18;
    
    /** Spacing of standard style icons. */
    private static final int iconSpacing = 8;
    /** Size of standard style icons. */
    private static final int iconSize = 9;
    
    /** Y offset of air bar. */
    private static final int airOffsetY = -49;
    /** X offset of air bar. */
    private static final int airOffsetX = -91;
    /** X position of full air icon in texture. */
    private static final int airIconXFull = 16;
    /** X position of partial air icon in texture. */
    private static final int airIconXPartial = 25;
    /** Y position of air icons in texture. */
    private static final int airIconY = 18;
    
    /** X offset of food icons. */
    private static final int foodOffsetX = 82;
    /** Y offset of food icons. */
    private static final int foodOffsetY = -39;
    /** X size of food icons textures. */
    private static final int foodSizeX = 27;
    /** Y size of food icons textures. */
    private static final int foodSizeY = 9;
    /** X position of background food icon in texture. */
    private static final int foodBackgroundX = 0;
    /** X position of full food icon in texture. */
    private static final int foodFullX = 9;
    /** X position of partial food icon in texture. */
    private static final int foodPartialX = 18;
    /** Vertical spacing between rows of food icons. */
    private static final int foodSpacing = -10;
    
    /** Registers icons for container slot backgrounds. */
    @SubscribeEvent
    public void textureStich(TextureStitchEvent.Pre event) {

        event.getMap().registerSprite(backpackTexture);
        event.getMap().registerSprite(yokeTexture);
    }
    
    /** Alters which vanilla Gui is opened. */
    @SubscribeEvent
    public void guiOpen(GuiOpenEvent event) {

        EntityPlayer player = Minecraft.getMinecraft().player;

        if (event.getGui() instanceof
                net.minecraft.client.gui.inventory.GuiInventory &&
                player.inventoryContainer instanceof ContainerInventory) {

            event.setGui(new com.jj.jjmod.gui.GuiInventory((ContainerInventory)
                    player.inventoryContainer));
        }
    }
    
    /** Alters behaviour when keys are pressed. */
    @SubscribeEvent
    public void keyInput(KeyInputEvent event) {

        Minecraft mc = Minecraft.getMinecraft();
        
        if (!mc.player.isSpectator() &&
                !mc.player.capabilities.isCreativeMode &&
                mc.gameSettings.keyBindSwapHands.isPressed()) {
           
            ContainerInventory inv =
                    (ContainerInventory) mc.player.inventoryContainer;
            
            if (!mc.player.inventory.offHandInventory.get(0).isEmpty() &&
                    ModBlocks.OFFHAND_ONLY.contains(mc.player.inventory
                    .offHandInventory.get(0).getItem())) {
                
                return;
            }

            inv.swapHands();
        }
    }
    
    /** Alters HUD rendering. */
    @SubscribeEvent
    public void renderGameOverlay(RenderGameOverlayEvent.Pre event) {
        
        EntityPlayer player = Minecraft.getMinecraft().player;
        int centreX = event.getResolution().getScaledWidth() / 2;
        int resHeight = event.getResolution().getScaledHeight();
        
        if (event.getType() == ElementType.HOTBAR) {
            
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
        ResourceLocation icon = player.getCapability(ModCaps
                .CAP_PLAYER, null).getTempIcon();
        
        int x = hand == EnumHandSide.LEFT ?
                centreX + tempOffsetXLeft : centreX + tempOffsetXRight;
        int y = resHeight + tempOffsetY;
        
        Minecraft.getMinecraft().getTextureManager().bindTexture(icon);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0,
                tempIconSize, tempIconSize, tempIconSize, tempIconSize);
    }
    
    /** Draws the player's air bubbles above health bar. */
    private static void drawAirBubbles(EntityPlayer player,
            int centreX, int resHeight) {
        
        Minecraft.getMinecraft().getTextureManager()
                .bindTexture(icons);
        GlStateManager.enableBlend();
        int left = centreX + airOffsetX;
        int top = resHeight + airOffsetY;
        
        int air = player.getAir();
        int full = MathHelper.ceil((air - 2) * 10.0D / 300.0D);
        int partial = MathHelper.ceil(air * 10.0D / 300.0D) - full;
        
        for (int i = 0; i < full + partial; i++) {
            
            new Gui().drawTexturedModalRect(left + i * iconSpacing,
                    top, (i < full ? airIconXFull : airIconXPartial),
                    airIconY, iconSize, iconSize);
        }
        
        GlStateManager.disableBlend();
    }
    
    /** Draws the player's three food bars. */
    private static void drawFoodBars(EntityPlayer player,
            int centreX, int resHeight) {
        
        GlStateManager.enableBlend();
        int left = centreX + foodOffsetX;
        int top = resHeight + foodOffsetY;
        ICapPlayer playerCap = player.getCapability(ModCaps
                .CAP_PLAYER, null);
        
        int carbsHunger = playerCap.foodLevel(FoodType.CARBS);
        Minecraft.getMinecraft().getTextureManager()
                .bindTexture(carbsTexture);
        
        for (int i = 0; i < 10; i++) {
            
            int idx = i * 2 + 1;
            int x = left - i * iconSpacing;
            
            Gui.drawModalRectWithCustomSizedTexture(x, top,
                    foodBackgroundX, 0, iconSize, iconSize,
                    foodSizeX, foodSizeY);
            
            if (idx < carbsHunger) {
                
                Gui.drawModalRectWithCustomSizedTexture(x, top,
                        foodFullX, 0, iconSize, iconSize,
                        foodSizeX, foodSizeY);
                
            } else if (idx == carbsHunger) {
                
                Gui.drawModalRectWithCustomSizedTexture(x, top,
                        foodPartialX, 0, iconSize, iconSize,
                        foodSizeX, foodSizeY);
            }
        }
        
        int fruitvegHunger = playerCap.foodLevel(FoodType.FRUITVEG);
        Minecraft.getMinecraft().getTextureManager()
                .bindTexture(fruitvegTexture);
        top += foodSpacing;
        
        for (int i = 0; i < 10; i++) {
            
            int idx = i * 2 + 1;
            int x = left - i * iconSpacing;
            
            Gui.drawModalRectWithCustomSizedTexture(x, top,
                    foodBackgroundX, 0, iconSize, iconSize,
                    foodSizeX, foodSizeY);
            
            if (idx < fruitvegHunger) {
                
                Gui.drawModalRectWithCustomSizedTexture(x, top,
                        foodFullX, 0, iconSize, iconSize,
                        foodSizeX, foodSizeY);
                
            } else if (idx == fruitvegHunger) {
                
                Gui.drawModalRectWithCustomSizedTexture(x, top,
                        foodPartialX, 0, iconSize, iconSize,
                        foodSizeX, foodSizeY);
            }
        }
        
        int proteinHunger = playerCap.foodLevel(FoodType.PROTEIN);
        Minecraft.getMinecraft().getTextureManager()
                .bindTexture(proteinTexture);
        top += foodSpacing;
        
        for (int i = 0; i < 10; i++) {
            
            int idx = i * 2 + 1;
            int x = left - i * iconSpacing;
            
            Gui.drawModalRectWithCustomSizedTexture(x, top,
                    foodBackgroundX, 0, iconSize, iconSize,
                    foodSizeX, foodSizeY);
            
            if (idx < proteinHunger) {
                
                Gui.drawModalRectWithCustomSizedTexture(x, top,
                        foodFullX, 0, iconSize, iconSize,
                        foodSizeX, foodSizeY);
                
            } else if (idx == proteinHunger) {
                
                Gui.drawModalRectWithCustomSizedTexture(x, top,
                        foodPartialX, 0, iconSize, iconSize,
                        foodSizeX, foodSizeY);
            }
        }
    }
}