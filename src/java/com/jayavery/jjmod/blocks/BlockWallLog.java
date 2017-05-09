package com.jayavery.jjmod.blocks;

import java.util.List;
import com.google.common.collect.Lists;
import com.jayavery.jjmod.init.ModItems;
import com.jayavery.jjmod.utilities.BlockMaterial;
import com.jayavery.jjmod.utilities.BlockWeight;
import com.jayavery.jjmod.utilities.IDoublingBlock;
import com.jayavery.jjmod.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Crossing log wall block. */
public class BlockWallLog extends BlockBuilding implements IDoublingBlock {
    
    public static final PropertyEnum<EnumStraight> STRAIGHT =
            PropertyEnum.create("straight", EnumStraight.class);
    
    /* Whether this block is double. */
    protected final boolean isDouble;
        
    public BlockWallLog(String name, float hardness, boolean isDouble) {
        
        super(BlockMaterial.WOOD_FURNITURE, name, CreativeTabs.BUILDING_BLOCKS,
                hardness, ToolType.AXE);
        this.isDouble = isDouble;
    }
    
    @Override
    public BlockWeight getWeight() {
        
        return BlockWeight.MEDIUM;
    }
    
    @Override
    public boolean shouldDouble(IBlockState state, EnumFacing side) {
        
        return side != EnumFacing.UP;
    }
    
    @Override
    public boolean isDouble() {
        
        return this.isDouble;
    }
    
    protected boolean hasConnection(IBlockAccess world,
            BlockPos pos, EnumFacing direction) {
        
        IBlockState state = world.getBlockState(pos.offset(direction));
        Block block = state.getBlock();
        
        if (!(block instanceof BlockBuilding)) {
        
            return BlockWeight.getWeight(block) != BlockWeight.NONE;
        }
        
        BlockBuilding building = (BlockBuilding) block;
        return building.shouldConnect(world, state, pos,
                direction.getOpposite());
    }
    
    @Override
    public IBlockState getActualState(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        boolean north = this.hasConnection(world, pos, EnumFacing.NORTH);
        boolean east = this.hasConnection(world, pos, EnumFacing.EAST);
        boolean south = this.hasConnection(world, pos, EnumFacing.SOUTH);
        boolean west = this.hasConnection(world, pos, EnumFacing.WEST);        
        
        state = state.withProperty(STRAIGHT, EnumStraight
                .get(north, east, south, west));
        
        return state;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
                
        if (this.isDouble()) {
            
            return FULL_BLOCK_AABB;

        } else {
            
            switch (this.getActualState(state, world, pos).getValue(STRAIGHT)) {
                
                case NS:
                    return CENTRE_HALF[1];
                    
                case EW:
                    return CENTRE_HALF[0];
                    
                default:
                    return FULL_BLOCK_AABB;
            }           
        }
    }
    
    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos,
            IBlockState state, int fortune) {

        return Lists.newArrayList(new ItemStack(ModItems.wallLog,
                this.isDouble() ? 2 : 1));
    }

    @Override
    public BlockStateContainer createBlockState() {

        return new BlockStateContainer(this, STRAIGHT);
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta) {
        
        return this.getDefaultState();
    }
    
    @Override
    public int getMetaFromState(IBlockState state) {
        
        return 0;
    }

    /** Enum defining possible straight directions of walls. */
    public enum EnumStraight implements IStringSerializable {
        
        NO("no"), NS("ns"), EW("ew");
        
        private String name;
        
        private EnumStraight(String name) {
            
            this.name = name;
        }

        @Override
        public String getName() {

            return this.name;
        }
        
        /** @return The EnumStraight according to the given properties. */
        public static EnumStraight get(boolean north, boolean east,
                boolean south, boolean west) {
            
            if ((north || south) && !east && !west) {
                
                return NS;
                
            } else if ((east || west) && !north && !south) {
                
                return EW;
                
            } else {
                
                return NO;                
            }
        }
    }
}
