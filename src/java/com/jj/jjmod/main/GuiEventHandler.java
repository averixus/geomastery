package com.jj.jjmod.main;

import com.jj.jjmod.capabilities.ICapPlayer;
import com.jj.jjmod.container.ContainerInventory;
import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.init.ModCaps;
import com.jj.jjmod.utilities.FoodType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
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
    
    /** Alters HUD rendering. */
    @SubscribeEvent
    public void renderGameOverlay(RenderGameOverlayEvent.Pre event) {
        
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.player;
        
        // Draw the temperature icon opposite the offhand
        if (event.getType() == ElementType.HOTBAR) {
        
            EnumHandSide hand = player.getPrimaryHand();
            ResourceLocation icon = player.getCapability(ModCaps
                    .CAP_PLAYER, null).getTempIcon();
            
            int x = event.getResolution().getScaledWidth() / 2;
            x = hand == EnumHandSide.LEFT ? x - 114 : x + 96;
            int y = event.getResolution().getScaledHeight() - 21;
            
            mc.getTextureManager().bindTexture(icon);
            Gui.drawModalRectWithCustomSizedTexture(x, y,
                    0, 0, 18, 18, 18, 18);
        }
        
        // Draw food bars for each type
        if (event.getType() == ElementType.FOOD) {

            GlStateManager.enableBlend();
            int left = event.getResolution().getScaledWidth() / 2 + 91;
            int top = event.getResolution().getScaledHeight() - 39;

            ICapPlayer playerCap = player.getCapability(ModCaps
                    .CAP_PLAYER, null);
            
            int carbsHunger = playerCap.foodLevel(FoodType.CARBS);
            Minecraft.getMinecraft().getTextureManager()
                    .bindTexture(carbsTexture);
            for (int i = 0; i < 10; i++) {
                
                int idx = i * 2 + 1;
                int x = left - i * 8 - 9;
                
                Gui.drawModalRectWithCustomSizedTexture(x, top,
                        0, 0, 9, 9, 27, 9);
                
                if (idx < carbsHunger) {
                    
                    Gui.drawModalRectWithCustomSizedTexture(x, top,
                            9, 0, 9, 9, 27, 9);
                    
                } else if (idx == carbsHunger) {
                    
                    Gui.drawModalRectWithCustomSizedTexture(x, top,
                            18, 0, 9, 9, 27, 9);
                }
            }
            
            int fruitvegHunger = playerCap.foodLevel(FoodType.FRUITVEG);
            Minecraft.getMinecraft().getTextureManager()
                    .bindTexture(fruitvegTexture);
            top -= 10;
            
            for (int i = 0; i < 10; i++) {
                
                int idx = i * 2 + 1;
                int x = left - i * 8 - 9;
                
                Gui.drawModalRectWithCustomSizedTexture(x, top,
                        0, 0, 9, 9, 27, 9);
                
                if (idx < fruitvegHunger) {
                    
                    Gui.drawModalRectWithCustomSizedTexture(x, top,
                            9, 0, 9, 9, 27, 9);
                    
                } else if (idx == fruitvegHunger) {
                    
                    Gui.drawModalRectWithCustomSizedTexture(x, top,
                            18, 0, 9, 9, 27, 9);
                }
            }
            
            int proteinHunger = playerCap.foodLevel(FoodType.PROTEIN);
            Minecraft.getMinecraft().getTextureManager()
                    .bindTexture(proteinTexture);
            top -= 10;
            
            for (int i = 0; i < 10; i++) {
                
                int idx = i * 2 + 1;
                int x = left - i * 8 - 9;
                
                Gui.drawModalRectWithCustomSizedTexture(x, top,
                        0, 0, 9, 9, 27, 9);
                
                if (idx < proteinHunger) {
                    
                    Gui.drawModalRectWithCustomSizedTexture(x, top,
                            9, 0, 9, 9, 27, 9);
                    
                } else if (idx == proteinHunger) {
                    
                    Gui.drawModalRectWithCustomSizedTexture(x, top,
                            18, 0, 9, 9, 27, 9);
                }
            }
            
            event.setCanceled(true);
        }
    }
    
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

            event.setGui(new com.jj.jjmod.gui
                    .GuiInventory((ContainerInventory)
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
}