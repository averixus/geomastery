package com.jayavery.jjmod.blocks;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jayavery.jjmod.utilities.BlockWeight;
import com.jayavery.jjmod.utilities.IDoublingBlock;
import com.jayavery.jjmod.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Rough heaping wall block. */
public class BlockWallRough extends BlockBuilding implements IDoublingBlock {

    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");
    
    /** Convenience map of EnumFacing to connection properties. */
    public static final Map<EnumFacing, PropertyBool>
            directionProperties = Maps.newHashMap();
    static {
        directionProperties.put(EnumFacing.NORTH, NORTH);
        directionProperties.put(EnumFacing.EAST, EAST);
        directionProperties.put(EnumFacing.SOUTH, SOUTH);
        directionProperties.put(EnumFacing.WEST, WEST);
    }
    
    /** The item this block drops. */
    protected final Supplier<Item> item;
    /** Whether this block is double. */
    protected final boolean isDouble;
    
    public BlockWallRough(Material material, String name, float hardness,
            ToolType harvestTool, boolean isDouble, Supplier<Item> item) {
        
        super(material, name, CreativeTabs.BUILDING_BLOCKS,
                hardness, harvestTool);
        this.item = item;
        this.isDouble = isDouble;
    }
    
    @Override
    public boolean isValid(World world, BlockPos pos) {
        
        IBlockState state = world.getBlockState(pos.down());
        Block block = state.getBlock();
        
        if (this.isDouble()) {
            
            return super.isValid(world, pos) &&
                    !(block instanceof BlockWallRough);
            
        } else {
                        
            if (block instanceof BlockWallRough) {
                
                return super.isValid(world, pos) &&
                        ((BlockWallRough) block).isDouble();
            }
            
            return super.isValid(world, pos);
        }
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
        
        for (EnumFacing direction : directionProperties.keySet()) {
            
            state = state.withProperty(directionProperties.get(direction),
                    this.hasConnection(world, pos, direction));
        }
        
        return state;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        if (this.isDouble()) {
            
            return FULL_BLOCK_AABB;
            
        } else {
            
            return CENTRE_POST;
        }
    }
    
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn,
            BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> list,
            @Nullable Entity entity, boolean unused) {
        
        state = this.getActualState(state, worldIn, pos);
        
        if (this.isDouble()) {
            
            addCollisionBoxToList(pos, entityBox, list, FULL_BLOCK_AABB);
            return;
        }
            
        addCollisionBoxToList(pos, entityBox, list, CENTRE_POST);
        
        if (state.getValue(NORTH)) {
            
            addCollisionBoxToList(pos, entityBox, list, BRANCH_NORTH);
        }
        
        if (state.getValue(EAST)) {
            
            addCollisionBoxToList(pos, entityBox, list, BRANCH_EAST);
        }
        
        if (state.getValue(SOUTH)) {
            
            addCollisionBoxToList(pos, entityBox, list, BRANCH_SOUTH);
        }
        
        if (state.getValue(WEST)) {
            
            addCollisionBoxToList(pos, entityBox, list, BRANCH_WEST);
        }
    }
    
    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos,
            IBlockState state, int fortune) {

        return Lists.newArrayList(new ItemStack(this.item.get(),
                this.isDouble() ? 2 : 1));
    }

    @Override
    public BlockStateContainer createBlockState() {

        return new BlockStateContainer(this, NORTH, EAST, SOUTH, WEST);
    }
}
