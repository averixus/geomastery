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

/** Abstract superclass for crafting devices with weathering. */
public abstract class TECraftingAbstract<E extends Enum<E> & IMultipart>
        extends TEMultiAbstract<E> implements ITickable {
    
    /** Maximum weather weathering this crafter has. */
    public static final int MAX_WEATHERING = 15;

    /** Number of days weathering remaining for this block. */
    protected int weathering = MAX_WEATHERING;
    /** Whether this block is currently being rained on. */
    protected boolean isRaining;
    
    /** Sets the weathering of this crafter. */
    public void setWeathering(int weathering) {

        this.weathering = weathering;
    }
    
    /** @return The weathering of this crafter. */
    public int getWeathering() {
        
        return this.weathering;
    }
    
    /** @return Whether this crafter has weathering. */
    public boolean hasWeathering() {
        
        return true;
    }
    
    /** Increments weathering based on day, conditions, and exposure. */
    @Override
    public void update() {
        
        if (this.world.isRemote) {
            
            return;
        }
        
        boolean update = false;
        EExposureLevel exposure = this.getExposure();
        
        if (this.world.getWorldTime() % 24000L == 0) {

            if (exposure == EExposureLevel.EXPOSED) {
                
                this.weathering--;
                update = true;
            }
        }
        
        if (this.world.isRaining() && !this.isRaining) {
            
            if (exposure == EExposureLevel.EXPOSED) {
            
                this.weathering -= 3;
                
            } else if (exposure == EExposureLevel.PARTIAL) {
                
                this.weathering--;
            }
            
            this.isRaining = true;
            update = true;
        }
        
        if (!this.world.isRaining() && this.isRaining) {

            this.isRaining = false;
        }

        if (this.weathering <= 0) {

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
    
    /** Sends an update packet to the client for the weathering bar. */
    protected void sendDurabilityPacket() {
        
        if (!this.world.isRemote) {
            
            Geomastery.NETWORK.sendToAll(new
                    CPacketCrafting(this.weathering, this.pos));
        }
    }
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {

        super.writeToNBT(compound);
        compound.setInteger("weathering", this.weathering);
        compound.setBoolean("isRaining", this.isRaining);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {

        super.readFromNBT(compound);
        this.weathering = compound.getInteger("weathering");
        this.isRaining = compound.getBoolean("isRaining");
    }
    
    /** Enum defining levels of exposure to weather. */
    public enum EExposureLevel {
        
        SHELTERED, PARTIAL, EXPOSED;
    }
}
