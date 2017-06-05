package jayavery.geomastery.utilities;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Implemented for enums which define multipart structures. */
public interface IMultipart extends IStringSerializable {
    
    /** @return The item this part should drop from the given block. */
    public ItemStack getDrop();
    /** @return Whether this part should break based on neighbours. */
    public boolean shouldBreak(World world, BlockPos pos, EnumFacing facing);
    /** @return The position of the master for this structure. */
    public BlockPos getMaster(BlockPos pos, EnumFacing facing);
    /** @return The selection bounding box of this part. */
    public AxisAlignedBB getBoundingBox(EnumFacing facing);
    /** @return The collision bounding box of this part. */
    public AxisAlignedBB getCollisionBox(EnumFacing facing);
    /** Attempts to build the structure starting from this part.
     * @return Whether the structure was successfully built. */
    public boolean buildStructure(World world, BlockPos pos, EnumFacing facing);
}
