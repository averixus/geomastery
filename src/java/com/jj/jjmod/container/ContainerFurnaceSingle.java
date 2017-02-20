package com.jj.jjmod.container;

import com.jj.jjmod.container.slots.SlotFurnaceFuel;
import com.jj.jjmod.container.slots.SlotFurnaceInput;
import com.jj.jjmod.container.slots.SlotFurnaceOutput;
import com.jj.jjmod.tileentities.TEFurnaceAbstract;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ContainerFurnaceSingle extends ContainerFurnaceAbstract {

    // Co-ordinate constants
    private static final int INPUT_X = 56;
    private static final int OUTPUT_X = 116;
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
