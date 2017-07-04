/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.container;

import jayavery.geomastery.container.slots.SlotFurnaceFuel;
import jayavery.geomastery.container.slots.SlotFurnaceInput;
import jayavery.geomastery.container.slots.SlotFurnaceOutput;
import jayavery.geomastery.tileentities.TEFurnaceAbstract;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Container for single-slot furnaces. */
public class ContainerFurnaceSingle extends ContainerFurnaceAbstract {

    /** X-position of start of input slots. */
    private static final int INPUT_X = 56;
    /** X-position of start of output slots. */
    private static final int OUTPUT_X = 116;
    /** Y-position of start of output slots. */
    private static final int OUTPUT_Y = 35;
    
    public ContainerFurnaceSingle(EntityPlayer player, World world,
            TEFurnaceAbstract<?> furnace, BlockPos pos) {
        
        super(player, world, furnace, pos);
        this.fireX = 56;
        this.arrowX = 79;
    }

    @Override
    protected void buildInput() {

         this.addSlotToContainer(new SlotFurnaceInput(this.furnace, 0,
                 INPUT_X, INPUT_Y));
    }

    @Override
    protected void buildFuel() {

        this.addSlotToContainer(new SlotFurnaceFuel(this.furnace, 0,
                INPUT_X, FUEL_Y));
    }

    @Override
    protected void buildOutput() {

        this.addSlotToContainer(new SlotFurnaceOutput(this.furnace, 0,
                OUTPUT_X, OUTPUT_Y));
    }
}
