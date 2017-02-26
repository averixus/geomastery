package com.jj.jjmod.blocks;

import java.util.List;
import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.utilities.BlockMaterial;
import com.jj.jjmod.utilities.IBuildingBlock;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Stairs block with no connections or corners. */
public class BlockStairsSimple extends BlockNew {
    
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public BlockStairsSimple(String name, float hardness,
            ToolType harvestTool) {
        
        super(BlockMaterial.WOOD_FURNITURE, name, CreativeTabs.BUILDING_BLOCKS,
                hardness, harvestTool);
    }
    
    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        
        return this.hasValidConnections(world, pos) &&
                this.hasFoundation(world, pos);
    }
    
    /** @return Whether this block has a valid
     * foundation below the given position. */
    private boolean hasFoundation(World world, BlockPos pos) {
        
        Block block = world.getBlockState(pos.down()).getBlock();
        
        boolean natural = false;
        boolean built = false;
        
        natural = ModBlocks.LIGHT.contains(block) ||
                ModBlocks.HEAVY.contains(block);
        
        if (block instanceof IBuildingBlock) {
            
            IBuildingBlock builtBlock = (IBuildingBlock) block;
            built = builtBlock.isLight() || builtBlock.isHeavy();
        }
        
        return natural || built;
    }
    
    /** Checks whether this block has no adjacent
     * same blocks at the given position.
     * @return Whether the given position is valid. */
    private boolean hasValidConnections(World world, BlockPos pos) {
        
        for (EnumFacing direction : EnumFacing.HORIZONTALS) {
            
            Block connect = world.getBlockState(pos
                    .offset(direction)).getBlock();
            
            if (connect == this) {
                
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos,
            EnumFacing side, float x, float y, float z,
            int meta, EntityLivingBase placer) {
        
        return this.getDefaultState().withProperty(FACING,
                placer.getHorizontalFacing());
    }
    
    @Override
    public void addCollisionBoxToList(IBlockState state, World world,
            BlockPos pos, AxisAlignedBB entityBox,
            List<AxisAlignedBB> list, Entity entity, boolean unused) {
                
        int facing = (state.getValue(FACING).getHorizontalIndex() + 1) % 4;
        
        for (AxisAlignedBB box : STAIRS_STRAIGHT[facing]) {
            
            addCollisionBoxToList(pos, entityBox, list, box);
        }
    }
    
    @Override
    protected BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta) {
        
        IBlockState state = this.getDefaultState();
        EnumFacing facing = EnumFacing.getHorizontal(meta);        
        state = state.withProperty(FACING, facing);
        return state;
    }
    
    @Override
    public int getMetaFromState(IBlockState state) {
        
        int i = state.getValue(FACING).getHorizontalIndex();
        return i;
    }
}
