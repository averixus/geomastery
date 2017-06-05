package jayavery.geomastery.tileentities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** TilEntity for storing crop fertilisation level. */
public class TECrop extends TileEntity {

    /** Current fertility level of this crop. */
    private int fertility = 0;
   
    /** Apply the given fertility to this crop.
     * @return Whether the fertiliser was applied successfully. */
    public boolean applyFertiliser(int applied) {
                    
        if (applied <= this.fertility) {
            
            return false;
            
        } else {
        
            this.fertility = applied;
            return true;
        }
    }
    
    /** @return The amount the growth rate and yield are multiplied by. */
    public float getMultiplier() {
        
        return 1.0F + (0.1F * this.fertility);
    }
    
    /** Stop the TE from being removed when the crop grows. */
    @Override
    public boolean shouldRefresh(World world, BlockPos pos,
            IBlockState oldState, IBlockState newState) {
        
        return oldState.getBlock() != newState.getBlock();
    }

    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        
        super.writeToNBT(compound);
        compound.setInteger("fertility", this.fertility);
        return compound;
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        
        super.readFromNBT(compound);
        this.fertility = compound.getInteger("fertility");
    }
}
