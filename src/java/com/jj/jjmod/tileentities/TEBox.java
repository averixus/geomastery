package com.jj.jjmod.tileentities;

import java.util.List;
import com.jj.jjmod.blocks.BlockBox;
import com.jj.jjmod.container.ContainerBox;
import com.jj.jjmod.init.ModCapabilities;
import com.jj.jjmod.init.ModItems;
import com.jj.jjmod.items.ItemEdibleDecayable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;

/** TileEntity for Box block. Closely adapted from vanilla. */
public class TEBox extends TileEntityChest {
    
    @Override
    public int getSizeInventory() {
        
        return 9;
    }
    
    @Override
    public Container createContainer(InventoryPlayer playerInv,
            EntityPlayer player) {
        
        return new ContainerBox(player, this.world, this);
    }
    
    @Override
    public void checkForAdjacentChests() {}
    
    @Override
    public void update() {
        
        int i = this.pos.getX();
        int j = this.pos.getY();
        int k = this.pos.getZ();
        
        if (!this.world.isRemote) {

            for (EntityPlayer entityplayer : this.world
                    .getEntitiesWithinAABB(EntityPlayer.class,
                    new AxisAlignedBB(i - 5.0F,
                    j - 5.0F, k - 5.0F,
                    i + 1 + 5.0F,
                    j + 1 + 5.0F,
                    k + 1 + 5.0F))) {
               
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
            
            double d1 = i + 0.5D;
            double d2 = k + 0.5D;

            this.world.playSound((EntityPlayer)null, d1, j + 0.5D,
                    d2, SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.BLOCKS,
                    0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
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

            if (this.lidAngle < 0.5F && f2 >= 0.5F) {
                
                double d3 = i + 0.5D;
                double d0 = k + 0.5D;

                this.world.playSound(null, d3, j + 0.5D, d0,
                        SoundEvents.BLOCK_CHEST_CLOSE, SoundCategory.BLOCKS,
                        0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
            }

            if (this.lidAngle < 0.0F) {
                
                this.lidAngle = 0.0F;
            }
        }
        
        List<ItemStack> inventory = this.getItems();
        
        for (int l = 0; l < this.getSizeInventory(); l++) {
            
            ItemStack stack = inventory.get(l);
            
            if (stack.getItem() instanceof ItemEdibleDecayable) {
                
                if (stack.getCapability(ModCapabilities.CAP_DECAY, null)
                        .updateAndRot()) {
                    
                    this.setInventorySlotContents(l,
                            new ItemStack(ModItems.rot));
                }
            }
        }
    }
    
    @Override
    public void closeInventory(EntityPlayer player) {
        
        if (!player.isSpectator() && this.getBlockType() instanceof BlockBox) {
            
            this.numPlayersUsing--;
            this.world.addBlockEvent(this.pos, this.getBlockType(),
                    1, this.numPlayersUsing);
            this.world.notifyNeighborsOfStateChange(this.pos,
                    this.getBlockType(), false);
        }
    }
}
