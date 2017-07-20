/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.tileentities;

import jayavery.geomastery.blocks.BlockFlatroof;
import jayavery.geomastery.main.Geomastery;
import jayavery.geomastery.packets.CPacketCrafting;
import jayavery.geomastery.utilities.IMultipart;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

/** Abstract superclass for crafting devices with weathering durability. */
public abstract class TECraftingAbstract<E extends Enum<E> & IMultipart>
        extends TEMultiAbstract<E> implements ITickable {
    
    /** Maximum weather durability this crafter has. */
    public static final int MAX_DURABILITY = 15;

    /** Number of days durability remaining for this block. */
    protected int durability = MAX_DURABILITY;
    /** Whether this block is currently being rained on. */
    protected boolean isRaining;
    
    /** Sets the durability of this crafter. */
    public void setDurability(int durability) {

        this.durability = durability;
    }
    
    /** @return The durability of this crafter. */
    public int getDurability() {
        
        return this.durability;
    }
    
    /** @return Whether this crafter has durability for weathering. */
    public boolean hasDurability() {
        
        return true;
    }
    
    /** Increments durability based on day, weather, and exposure. */
    @Override
    public void update() {
        
        if (this.world.isRemote) {
            
            return;
        }
        
        boolean update = false;
        EExposureLevel exposure = this.getExposure();
        
        if (this.world.getWorldTime() % 24000L == 0) {

            if (exposure == EExposureLevel.EXPOSED) {
                
                this.durability--;
                update = true;
            }
        }
        
        if (this.world.isRaining() && !this.isRaining) {
            
            if (exposure == EExposureLevel.EXPOSED) {
            
                this.durability -= 3;
                
            } else if (exposure == EExposureLevel.PARTIAL) {
                
                this.durability--;
            }
            
            this.isRaining = true;
            update = true;
        }
        
        if (!this.world.isRaining() && this.isRaining) {

            this.isRaining = false;
        }

        if (this.durability <= 0) {

            this.world.destroyBlock(this.pos, false);
        }
        
        if (update) {

            this.sendDurabilityPacket();
        }
    }
    
    /** @return The type of exposure this block is in. */
    protected EExposureLevel getExposure() {
        
        BlockPos pos = this.pos.up();
        EExposureLevel result = EExposureLevel.EXPOSED;

        while (pos.getY() <= 256) {
            
            IBlockState state = this.world.getBlockState(pos);
            Block block = state.getBlock();
            int light = block.getLightOpacity(state, this.world, pos);
            
            if (block instanceof BlockFlatroof) {
   
                result = EExposureLevel.PARTIAL;                
                break;
                
            } else if (light != 0 && !(block instanceof BlockLeaves)) {
                
                result = EExposureLevel.SHELTERED;
                break;
            }
            
            pos = pos.up();
        }

        return result;
    }
    
    /** Sends an update packet to the client for the durability bar. */
    protected void sendDurabilityPacket() {
        
        if (!this.world.isRemote) {
            
            Geomastery.NETWORK.sendToAll(new
                    CPacketCrafting(this.durability, this.pos));
        }
    }
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {

        super.writeToNBT(compound);
        compound.setInteger("durability", this.durability);
        compound.setBoolean("isRaining", this.isRaining);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {

        super.readFromNBT(compound);
        this.durability = compound.getInteger("durability");
        this.isRaining = compound.getBoolean("isRaining");
    }
    
    /** Enum defining levels of exposure to weather. */
    public enum EExposureLevel {
        
        SHELTERED, PARTIAL, EXPOSED;
    }
}
