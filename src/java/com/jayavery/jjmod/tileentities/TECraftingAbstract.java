package com.jayavery.jjmod.tileentities;

import com.jayavery.jjmod.init.ModPackets;
import com.jayavery.jjmod.packets.CraftingPacketClient;
import com.jayavery.jjmod.utilities.IBuildingBlock;
import com.jayavery.jjmod.utilities.IMultipart;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;

/** Abstract superclass for crafting devices with weathering durability. */
public abstract class TECraftingAbstract<E extends Enum<E> & IMultipart>
        extends TEMultiAbstract<E> implements ITickable {

    /** Number of days durability remaining for this block. */
    protected int durability = 20;
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
    
    /** Has no inventory to drop. */
    @Override
    public void dropItems() {}
    
    /** Increments durability based on day, weather, and exposure. */
    @Override
    public void update() {
        
        if (this.world.isRemote) {
            
            return;
        }
        
        boolean update = false;
        
        if (this.world.getWorldTime() % 24000L == 0) {

            if (this.getExposure() == EnumExposure.EXPOSED) {
                
                this.durability--;
                update = true;
            }
        }
        
        if (this.world.isRaining() && !this.isRaining) {

            EnumExposure exposure = this.getExposure();
            
            if (exposure == EnumExposure.EXPOSED) {
            
                this.durability -= 3;
                
            } else if (exposure == EnumExposure.PARTIAL) {
                
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
    
    /** @return Whether this block is exposed to the sky,
     * not counting leaves as shelter. */
    protected EnumExposure getExposure() {
        
        BlockPos pos = this.pos.up();
        EnumExposure result = EnumExposure.EXPOSED;

        while (pos.getY() <= 256) {
            
            IBlockState state = this.world.getBlockState(pos);
            Block block = state.getBlock();
            int light = block.getLightOpacity(state, this.world, pos);
            
            if (block instanceof IBuildingBlock) {
                
                IBuildingBlock building = (IBuildingBlock) block;
                
                if (building.isShelter()) {
                    
                    result = EnumExposure.SHELTERED;
                    
                } else {
                    
                    result = EnumExposure.PARTIAL;
                }
                
                break;
                
            } else if (light != 0 && !(block instanceof BlockLeaves)) {
                
                result = EnumExposure.SHELTERED;
                break;
            }
            
            pos = pos.up();
        }

        return result;
    }
    
    /** Sends an update packet to the client for the durability bar. */
    protected void sendDurabilityPacket() {
        
        if (!this.world.isRemote) {
            
            ModPackets.NETWORK.sendToAll(new
                    CraftingPacketClient(this.durability, this.pos));
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
    public enum EnumExposure {
        
        SHELTERED, PARTIAL, EXPOSED;
    }
}
