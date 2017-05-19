package com.jayavery.jjmod.container;

import com.jayavery.jjmod.blocks.BlockComplexAbstract;
import com.jayavery.jjmod.container.slots.SlotCompostInput;
import com.jayavery.jjmod.container.slots.SlotCompostOutput;
import com.jayavery.jjmod.tileentities.TECompost;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ContainerCompost extends ContainerAbstract {
    
    /** Drying rack TileEntity of this container. */
    public final TECompost compost;
    /** Position of this container. */
    private BlockPos pos;
    
    public ContainerCompost(EntityPlayer player, World world,
            BlockPos pos, TECompost compost) {
        
        super(player, world);
        this.compost = compost;
        this.pos = pos;
        
        this.addSlotToContainer(new SlotCompostInput(compost, 10, 10));
        this.addSlotToContainer(new SlotCompostOutput(compost, 100, 100));
        
        this.buildHotbar();
        this.buildInvgrid();
        
        //TODO construct slots
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {

        boolean correctBlock = this.world.getBlockState(this.pos)
                .getBlock() instanceof BlockComplexAbstract;

        if (correctBlock) {

            return player.getDistanceSq(this.pos.getX() + 0.5,
                    this.pos.getY() + 0.5, this.pos.getZ() + 0.5) <= 64;
        }

        return false;
    }
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        
        // TODO transfer stack slots
        return ItemStack.EMPTY;
    }
}
