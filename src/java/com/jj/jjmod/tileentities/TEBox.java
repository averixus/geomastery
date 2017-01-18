package com.jj.jjmod.tileentities;

import com.jj.jjmod.blocks.BlockBox;
import com.jj.jjmod.container.ContainerBox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class TEBox extends TileEntityChest {
    
    @Override
    public int getSizeInventory() {
        
        return 9;
    }
    
    @Override
    public Container createContainer(InventoryPlayer playerInv, EntityPlayer player) {
        
        return new ContainerBox(player, this.worldObj, this);
    }
    
    @Override
    public void checkForAdjacentChests() {}
    
    @Override
    public void update() {
        
        int i = this.pos.getX();
        int j = this.pos.getY();
        int k = this.pos.getZ();
        
        if (!this.worldObj.isRemote) {

            for (EntityPlayer entityplayer : this.worldObj
                    .getEntitiesWithinAABB(EntityPlayer.class,
                    new AxisAlignedBB((double)((float)i - 5.0F),
                    (double)((float)j - 5.0F), (double)((float)k - 5.0F),
                    (double)((float)(i + 1) + 5.0F),
                    (double)((float)(j + 1) + 5.0F),
                    (double)((float)(k + 1) + 5.0F)))) {
               
                if (entityplayer.openContainer instanceof ContainerBox) {
                   
                    IInventory iinventory =
                            ((ContainerBox)entityplayer.openContainer).boxInv;

                    if (iinventory == this) {
                       
                        ++this.numPlayersUsing;
                    }
                }
            }
        }
        
        this.prevLidAngle = this.lidAngle;
        
        if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F) {
            
            double d1 = (double)i + 0.5D;
            double d2 = (double)k + 0.5D;

            this.worldObj.playSound((EntityPlayer)null, d1, (double)j + 0.5D,
                    d2, SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.BLOCKS,
                    0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
        }
        
        if (this.numPlayersUsing == 0 && this.lidAngle > 0.0F ||
                this.numPlayersUsing > 0 && this.lidAngle < 1.0F) {
            
            float f2 = this.lidAngle;

            if (this.numPlayersUsing > 0) {
                
                this.lidAngle += 0.1F;
            }
            
            else {
                
                this.lidAngle -= 0.1F;
            }

            if (this.lidAngle > 1.0F) {
                
                this.lidAngle = 1.0F;
            }

            float f3 = 0.5F;

            if (this.lidAngle < 0.5F && f2 >= 0.5F) {
                
                double d3 = (double)i + 0.5D;
                double d0 = (double)k + 0.5D;

                this.worldObj.playSound(null, d3, (double)j + 0.5D, d0,
                        SoundEvents.BLOCK_CHEST_CLOSE, SoundCategory.BLOCKS,
                        0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
            }

            if (this.lidAngle < 0.0F) {
                
                this.lidAngle = 0.0F;
            }
        }
    }
    
    @Override
    public void closeInventory(EntityPlayer player) {
        
        if (!player.isSpectator() && this.getBlockType() instanceof BlockBox) {
            
            this.numPlayersUsing--;
            this.worldObj.addBlockEvent(this.pos, this.getBlockType(),
                    1, this.numPlayersUsing);
            this.worldObj.notifyNeighborsOfStateChange(this.pos,
                    this.getBlockType(), false);
        }
    }
}
