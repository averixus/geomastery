/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.gui;

import jayavery.geomastery.container.ContainerCompost;
import jayavery.geomastery.main.Geomastery;
import jayavery.geomastery.tileentities.TECompostheap;
import jayavery.geomastery.utilities.Lang;
import net.minecraft.util.ResourceLocation;

/** Gui for compost heap. */
public class GuiCompost extends GuiContainerAbstract {
    
    /** Width of input fullness bar. */
    private static final int INPUT_WIDTH = 13;
    /** Height of input fullness bar. */
    private static final int INPUT_HEIGHT = 40;
    /** X-position of input fullness bar. */
    private static final int INPUT_X = 81;
    /** Y-position of input fullness bar. */
    private static final int INPUT_Y = 17;
    /** X-position of source of input fullness bar. */
    private static final int INPUT_SOURCE_X = 176;
    /** Y-position of source of input fullness bar. */
    private static final int INPUT_SOURCE_Y = 65;
    /** Width of progress arrow. */
    private static final int PROGRESS_WIDTH = 24;
    /** Height of progress arrow. */
    private static final int PROGRESS_HEIGHT = 17;
    /** X-position of progress arrow. */
    private static final int PROGRESS_X = 100;
    /** Y-position of progress arrow. */
    private static final int PROGRESS_Y = 26;
    /** X-position of source of progress arrow. */
    private static final int PROGRESS_SOURCE_X = 176;
    /** Y-position of source of progress arrow. */
    private static final int PROGRESS_SOURCE_Y = 8;
    /** Width of balance marker. */
    private static final int BALANCE_WIDTH = 13;
    /** Height of balance marker. */
    private static final int BALANCE_HEIGHT = 8;
    /** X-position of balance bar centre. */
    private static final int BALANCE_X = 81;
    /** Y-position of balance bar centre. */
    private static final int BALANCE_Y = 72;
    /** X-position of source of balance marker. */
    private static final int BALANCE_SOURCE_X = 176;
    /** Y-position of source of balance marker. */
    private static final int BALANCE_SOURCE_Y = 0;

    /** Texture of this gui. */
    private final ResourceLocation texture;

    public GuiCompost(ContainerCompost container) {
        
        super(container, Lang.CONTAINER_COMPOSTHEAP);
        this.texture = new ResourceLocation(Geomastery.MODID, 
                "textures/gui/compost_" +
                container.capability.getInventoryRows() + ".png");
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float ticks,
            int mouseX, int mouseY) {
        
        super.drawGuiContainerBackgroundLayer(ticks, mouseX, mouseY);
        
        TECompostheap compost = ((ContainerCompost) this.inventorySlots)
                .compost;
        
        int input = compost.getInput();
        int inputHeight = (int) (input *
                ((float) INPUT_HEIGHT / TECompostheap.MAX_INPUT));
        int inputSourceY = INPUT_SOURCE_Y - inputHeight;
        this.drawTexturedModalRect(this.guiLeft + INPUT_X,
                this.guiTop + INPUT_Y + (INPUT_HEIGHT - inputHeight),
                INPUT_SOURCE_X, inputSourceY, INPUT_WIDTH, inputHeight);
        
        int progress = compost.getCompostSpent();
        int progressWidth = (int) (((float) progress /
                TECompostheap.COMPOST_EACH) * PROGRESS_WIDTH);
        this.drawTexturedModalRect(this.guiLeft + PROGRESS_X,
                this.guiTop + PROGRESS_Y, PROGRESS_SOURCE_X, PROGRESS_SOURCE_Y,
                progressWidth, PROGRESS_HEIGHT);

        int balance = compost.getBalance();
        int balanceX = balance + BALANCE_X;
        this.drawTexturedModalRect(this.guiLeft + balanceX,
                this.guiTop + BALANCE_Y, BALANCE_SOURCE_X, BALANCE_SOURCE_Y,
                BALANCE_WIDTH, BALANCE_HEIGHT);
    }
    
    @Override
    protected ResourceLocation getTexture() {

        return this.texture;
    }
}
