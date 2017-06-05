package jayavery.geomastery.tileentities;

import jayavery.geomastery.blocks.BlockMultiCrafting;
import jayavery.geomastery.utilities.IMultipart;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

/** Abstract superclass for multi part structure tile entities. */
public abstract class TEMultiAbstract<E extends Enum<E> & IMultipart>
        extends TEContainerAbstract {

    /** EnumFacing of this structure. */
    protected EnumFacing facing;
    /** Enum part of this block. */
    protected E part;
    
    /** Sets the given facing and part for this block. */
    public void setState(EnumFacing facing, E part) {
        
        this.facing = facing;
        this.part = part;
    }
    
    /** @return The enum facing of this block. */
    public EnumFacing getFacing() {
        
        return this.facing;
    }
    
    /** @return The part of this block. */
    public E getPart() {
        
        return this.part;
    }
    
    /** @return The item dropped from this block. */
    public ItemStack getDrop() {
        
        return this.part == null ? ItemStack.EMPTY : this.part.getDrop();
    }
    
    /** @return Whether this block should be broken based on neighbours. */
    public boolean shouldBreak() {
        
        return this.part == null ? false :
            this.part.shouldBreak(this.world, this.pos, this.facing);
    }
    
    /** @return Collision bounding box of this part. */
    public AxisAlignedBB getCollisionBox() {
        
        return this.part == null ? Block.FULL_BLOCK_AABB :
            this.part.getCollisionBox(this.facing);
    }
    
    /** @return Selection bounding box of this part. */
    public AxisAlignedBB getBoundingBox() {
        
        return this.part == null ? Block.FULL_BLOCK_AABB :
            this.part.getBoundingBox(this.facing);
    }
    
    /** Apply state information for this block.
     * @return The IBlockState with tile entity information added. */
    public IBlockState applyActualState(IBlockState state,
            PropertyEnum<E> partProperty) {
        
        state = this.part == null ? state :
                state.withProperty(partProperty, this.part);
        state = this.facing == null ? state :
                state.withProperty(BlockMultiCrafting.FACING, this.facing);
        return state;
    }
    
    /** @return BlockPos of the master block for this structure. */
    public BlockPos getMaster() {
        
        return this.part.getMaster(this.pos, this.facing);
    }
    
    /** To implement values() for generic type, only used for NBT tags. */
    protected abstract E partByOrdinal(int ordinal);
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {

        super.writeToNBT(compound);
        compound.setInteger("facing", this.facing == null ? 0 :
                this.facing.getHorizontalIndex());
        compound.setInteger("part", this.part == null ? 0 :
                this.part.ordinal());
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {

        super.readFromNBT(compound);
        this.facing = EnumFacing.getHorizontal(compound.getInteger("facing"));
        this.part = this.partByOrdinal(compound.getInteger("part"));
    }

    /** Required to update rendering on the Client. */
    @Override
    public NBTTagCompound getUpdateTag() {

        return this.writeToNBT(new NBTTagCompound());
    }

    /** Required to update rendering on the Client. */
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {

        return new SPacketUpdateTileEntity(this.getPos(), 0,
                this.writeToNBT(new NBTTagCompound()));
    }

    /** Required to update rendering on the Client. */
    @Override
    public void onDataPacket(NetworkManager net,
            SPacketUpdateTileEntity packet) {

        this.readFromNBT(packet.getNbtCompound());
    }
}
