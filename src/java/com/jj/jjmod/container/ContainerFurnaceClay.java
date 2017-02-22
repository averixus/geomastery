package com.jj.jjmod.container;

import com.jj.jjmod.container.slots.SlotFurnaceFuel;
import com.jj.jjmod.container.slots.SlotFurnaceInput;
import com.jj.jjmod.container.slots.SlotFurnaceOutput;
import com.jj.jjmod.tileentities.TEFurnaceAbstract;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Container for clay furnace. */
public class ContainerFurnaceClay extends ContainerFurnaceAbstract {
    
    /** X-position of the start of input slots. */
    private static final int INPUT_X = 74;
    /** X-position of the start of output slots. */
    private static final int OUTPUT_X = 123;
    /** Y-position of the start of output slots. */
    private static final int OUTPUT_Y = 26;

    public ContainerFurnaceClay(EntityPlayer player, World world,
            TEFurnaceAbstract furnace, BlockPos pos) {
        
        super(player, world, furnace, pos);
        this.fireX = 75;
        this.arrowX = 94;
    }

    @Override
    protected void buildInput() {

        for (int i = 0; i < this.size; i++) {
            
            this.addSlotToContainer(new SlotFurnaceInput(this.furnace, i,
                    INPUT_X - (i * SLOT_SIZE), INPUT_Y));
        }
    }

    @Override
    protected void buildFuel() {

        for (int i = 0; i < this.size; i++) {
            
            this.addSlotToContainer(new SlotFurnaceFuel(this.furnace, i,
                    INPUT_X - (i * SLOT_SIZE), FUEL_Y));
        }
    }

    @Override
    protected void buildOutput() {

        for (int x = 0; x < 2; x++) {
            
            for (int y = 0; y < 2; y++) {
                
                this.addSlotToContainer(new SlotFurnaceOutput(this.furnace,
                        ((2 * y) + x), OUTPUT_X + (x * SLOT_SIZE),
                        OUTPUT_Y + (y * SLOT_SIZE)));
            }
        }  
    }
}
