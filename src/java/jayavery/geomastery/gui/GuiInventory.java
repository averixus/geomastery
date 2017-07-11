/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.gui;

import jayavery.geomastery.container.ContainerInventory;
import net.minecraft.util.ResourceLocation;

/** Gui for the player inventory */
public class GuiInventory extends GuiContainerAbstract {
    
    /** X-position of player entity. */
    private static final int PLAYER_X = 51;
    /** Y-position of player entity. */
    private static final int PLAYER_Y = 75;
    /** Scale of player entity. */
    private static final int PLAYER_SCALE = 30;
    /** Y offset of player entity. */
    private static final int Y_OFFSET = -50;
    /** X-position of name. */
    private static final int TEXT_X = 97;

    public GuiInventory(ContainerInventory container) {

        super(container, "inventory");
        this.allowUserInput = true;
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        
        this.fontRendererObj.drawString(this.name, TEXT_X, 6, TEXT_COLOUR);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float ticks,
            int mouseX, int mouseY) {

        super.drawGuiContainerBackgroundLayer(ticks, mouseX, mouseY);
        drawEntityOnScreen(this.guiLeft + PLAYER_X, this.guiTop + PLAYER_Y,
                PLAYER_SCALE, this.xSize + PLAYER_X - mouseX,
                this.ySize + PLAYER_Y + Y_OFFSET - mouseY, this.mc.player);
    }

    @Override
    protected ResourceLocation getTexture() {

        return ((ContainerInventory) this.inventorySlots).getBackground();
    }
}
