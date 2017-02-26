package com.jayavery.jjmod.container;

import com.jayavery.jjmod.container.slots.SlotFurnaceFuel;
import com.jayavery.jjmod.container.slots.SlotFurnaceInput;
import com.jayavery.jjmod.container.slots.SlotFurnaceOutput;
import com.jayavery.jjmod.tileentities.TEFurnaceAbstract;
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
            TEFurnaceAbstract furnace, BlockPos pos) {
        
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
