package com.jj.jjmod.blocks;

import java.util.List;
import com.jj.jjmod.utilities.BlockMaterial;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockStairsSimple extends BlockNew {
    
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    protected static final AxisAlignedBB BOTTOM = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
    protected static final AxisAlignedBB WEST = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 0.5D, 1.0D, 1.0D);
    protected static final AxisAlignedBB EAST = new AxisAlignedBB(0.5D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB NORTH = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 0.5D);
    protected static final AxisAlignedBB SOUTH = new AxisAlignedBB(0.0D, 0.5D, 0.5D, 1.0D, 1.0D, 1.0D);

    public BlockStairsSimple(String name, float hardness, ToolType harvestTool) {
        
        super(BlockMaterial.WOOD_FURNITURE, name, CreativeTabs.BUILDING_BLOCKS, hardness, harvestTool);
    }
    
    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        
        for (EnumFacing direction : EnumFacing.HORIZONTALS) {
            
            Block block = world.getBlockState(pos.offset(direction)).getBlock();
            
            if (block == this) {
                
                return false;
            }
        }

        return true;
    }
    
    @Override
    public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing side, float x, float y, float z, int meta, EntityLivingBase placer) {
        
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
    }
    
    @SuppressWarnings("incomplete-switch")
    @Override
    public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> list, Entity entity) {
                
        addCollisionBoxToList(pos, entityBox, list, BOTTOM);
        
        EnumFacing facing = state.getValue(FACING);
        
        switch (facing) {
            
            case NORTH : {
                
                addCollisionBoxToList(pos, entityBox, list, NORTH);
                break;
            }
            
            case EAST : {
                
                addCollisionBoxToList(pos, entityBox, list, EAST);
                break;
            }
            
            case SOUTH : {
                
                addCollisionBoxToList(pos, entityBox, list, SOUTH);
                break;
            }
            
            case WEST : {
                
                addCollisionBoxToList(pos, entityBox, list, WEST);
                break;
            }
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
