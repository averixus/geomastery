package com.jayavery.jjmod.blocks;

import java.util.List;
import com.jayavery.jjmod.utilities.BlockWeight;
import com.jayavery.jjmod.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Abstract superclass for weighted building blocks. */
public abstract class BlockBuilding extends BlockNew {

    public BlockBuilding(Material material, String name, CreativeTabs tab,
            float hardness, ToolType harvestTool) {
        
        super(material, name, tab, hardness, harvestTool);
    }
    
    /** Checks whether this block is valid in the given position.
     * Only called by {@code ItemBlock}s.
     * @return Whether the block can be placed. */
    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        
        return this.isValid(world, pos);
    }
    
    /* Checks whether this block is still in a valid position, breaks if not. */
    @Override
    public void neighborChanged(IBlockState state, World world,
            BlockPos pos, Block block, BlockPos unused) {
        
        if (!this.isValid(world, pos)) {
            
            world.destroyBlock(pos, true);
        }
    }

    /** @return Whether this position is valid for this block. Default
     * implementation requires the block below to be same weight or heavier,
     * or the same block (allows for multiblock structures).*/
    public boolean isValid(World world, BlockPos pos) {
        
        Block blockBelow = world.getBlockState(pos.down()).getBlock();
        BlockWeight weightBelow = BlockWeight.getWeight(blockBelow);
        return weightBelow.canSupport(this.getWeight());
    }
    
    /** @return The weight this block can support. */
    public abstract BlockWeight getWeight();
    
    /** @return Whether walls connect to this block from the given direction.
     * Default is always true. */
    public boolean shouldConnect(IBlockAccess world, IBlockState state,
            BlockPos pos, EnumFacing direction) {
        
        return true;
    }

    /** All implementations have to override this.
     * @return Actual state using position data. */
    @Override
    public abstract IBlockState getActualState(IBlockState state,
            IBlockAccess world, BlockPos pos);
    
    /** All implementations have to override this.
     * @return Highlight bounding box of complex-shaped block. */
    @Override
    public abstract AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos);
    
    /** All implementations have to override this.
     * Needed for custom block drops. */
    @Override
    public abstract List<ItemStack> getDrops(IBlockAccess world,
            BlockPos pos, IBlockState state, int fortune);

    /** All implementations have to override this.
     * @return BlockStateContainer of all needed properties. */
    @Override
    public abstract BlockStateContainer createBlockState();

    @Override
    public int getMetaFromState(IBlockState state) {
        
        return 0;
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta) {
        
        return this.getDefaultState();
    }

    @Override
    public BlockRenderLayer getBlockLayer() {

        return BlockRenderLayer.CUTOUT_MIPPED;
    }
}
