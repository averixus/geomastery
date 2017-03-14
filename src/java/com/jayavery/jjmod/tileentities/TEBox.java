package com.jayavery.jjmod.tileentities;

import com.jayavery.jjmod.blocks.BlockBox;
import com.jayavery.jjmod.container.ContainerBox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

/** TileEntity for box block. */
public class TEBox extends TileEntity implements ITickable {
    
    /** The box inventory. */
    private final ItemStackHandler inv = new ItemStackHandler(18);
    /** The current lid angle. */
    public float lidAngle;
    /** The last tick lid angle. */
    public float prevLidAngle;
    /** The number of players viewing this box. */
    public int users;
    
    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ?
                (T) this.inv : super.getCapability(capability, facing);
    }
    
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ?
                true : super.hasCapability(capability, facing);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        
        super.readFromNBT(compound);
        this.inv.deserializeNBT(compound.getCompoundTag("inventory"));
    }
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        
        super.writeToNBT(compound);
        compound.setTag("inventory", this.inv.serializeNBT());
        return compound;
    }
    
    /** Updates animation and sound states. */
    @Override
    public void update() {
        
        this.prevLidAngle = this.lidAngle;
        
        // Play open sound
        if (this.users > 0 && this.lidAngle == 0.0F) {

            this.world.playSound(null, this.pos, SoundEvents.BLOCK_CHEST_OPEN,
                    SoundCategory.BLOCKS, 0.5F,
                    this.world.rand.nextFloat() * 0.1F + 0.9F);
        }
        
        // Continue closing when unused
        if (this.users == 0 && this.lidAngle > 0) {

            this.lidAngle -= 0.1F;
            
            // Play close sound
            if (this.lidAngle < 0.5F && this.prevLidAngle >= 0.5F) {

                this.world.playSound(null, this.pos,
                        SoundEvents.BLOCK_CHEST_CLOSE, SoundCategory.BLOCKS,
                        0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
            }
            
            // Prevent overshoot
            if (this.lidAngle < 0) {
                
                this.lidAngle = 0;
            }
        }
        
        // Continue opening when used
        if (this.users > 0 && this.lidAngle < 1) {

            this.lidAngle += 0.1F;
            
            // Prevent overshoot
            if (this.lidAngle > 1) {
                
                this.lidAngle = 1;
            }
        }
    }
    
    /** Removes a user. */
    public void open() {
        
        this.users++;
    }
    
    /** Adds a user. */
    public void close() {
        
        this.users--;
    }
}
