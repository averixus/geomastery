/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.tileentities;

import jayavery.geomastery.main.Geomastery;
import jayavery.geomastery.packets.CPacketLid;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.items.ItemStackHandler;

/** Abstract superclass for storage TE. */
public abstract class TEStorage extends TileEntity implements ITickable {
    
    /** Y co-ordinate for inventory in container. */
    protected int containerY;
    /** The storage inventory. */
    protected NonNullList<ItemStack> inv;
    /** The current lid angle. */
    public float lidAngle;
    /** The last tick lid angle. */
    public float prevLidAngle;
    /** The number of players viewing this storage. */
    public int users;
    
    public TEStorage(int slots, int containerY) {
        
        this.inv = NonNullList.withSize(slots, ItemStack.EMPTY);
        this.containerY = containerY;
    }
    
    /** Sets this storage lid angles. */
    public void setAngles(float lidAngle, float prevLidAngle) {
        
        this.lidAngle = lidAngle;
        this.prevLidAngle = prevLidAngle;
    }
    
    /** Sets the given slot of this inventory. */
    public void setInventory(ItemStack stack, int index) {
        
        this.inv.set(index, stack);
        this.markDirty();
    }
    
    /** @return The given slot of this inventory. */
    public ItemStack getInventory(int index) {
        
        return this.inv.get(index);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        
        super.readFromNBT(compound);
        
        for (int i = 0; i < this.inv.size(); i++) {
            
            this.setInventory(new ItemStack(compound
                    .getCompoundTag("inv" + i)), i);
        }
    }
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        
        super.writeToNBT(compound);

        for (int i = 0; i < this.inv.size(); i++) {
            
            compound.setTag("inv" + i, this.getInventory(i)
                    .writeToNBT(new NBTTagCompound()));
        }

        return compound;
    }
    
    /** @return The number of slots in this inventory. */
    public int getSlots() {
        
        return this.inv.size();
    }
    
    /** @return The number of rows in this inventory. */
    public int getRows() {
        
        return this.inv.size() / 9;
    }
    
    /** Updates animation and sound states. */
    @Override
    public void update() {
        
        if (this.world.isRemote) {

            return;
        }
                
        float startLidAngle = this.lidAngle;
        float startPrevAngle = this.prevLidAngle;
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
        
        if (this.lidAngle != startLidAngle ||
                this.prevLidAngle != startPrevAngle) {
            
            this.sendAnglePacket();
        }
    }
    
    /** Gets the container Y co-ordinate for this inventory. */
    public int getInvY() {
        
        return this.containerY;
    }
    
    /** Removes a user. */
    public void open() {
        
        this.users++;
    }
    
    /** Adds a user. */
    public void close() {
        
        this.users--;
    }
    
    /** Sends an packet to update the lid angle on the Client. */
    private void sendAnglePacket() {
        
        if (!this.world.isRemote) {

            Geomastery.NETWORK.sendToAll(new CPacketLid(this.lidAngle,
                    this.prevLidAngle, this.pos));
        }
    }
    
    /** Basket implementation. */
    public static class Basket extends TEStorage {
        
        public Basket() {
            
            super(9, 36);
        }
        
        @Override
        public void update() {}
    }
    
    /** Box implementation. */
    public static class Box extends TEStorage {
        
        public Box() {
            
            super(18, 26);
        }
    }
    
    /** Chest implementation. */
    public static class Chest extends TEStorage {
        
        public Chest() {
            
            super(27, 18);
        }
    }
}
