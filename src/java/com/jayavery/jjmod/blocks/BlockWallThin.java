package com.jayavery.jjmod.blocks;

import java.util.List;
import java.util.Map;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jayavery.jjmod.utilities.BlockMaterial;
import com.jayavery.jjmod.utilities.BlockWeight;
import com.jayavery.jjmod.utilities.ToolType;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
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

/** Wall block with no height or thickness variation (implementation: pole). */
public class BlockWallThin extends BlockBuilding {
    
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
    
    public BlockWallThin(BlockMaterial material, String name,
            float hardness, ToolType toolType) {
                
        super(material, name, CreativeTabs.BUILDING_BLOCKS, hardness, toolType);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }
    
    @Override
    public BlockWeight getWeight() {
        
        return BlockWeight.LIGHT;
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
    public void addCollisionBoxToList(IBlockState state, World world,
            BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> list,
            @Nullable Entity entity, boolean unused) {
        
        state = this.getActualState(state, world, pos);
        
        addCollisionBoxToList(pos, entityBox, list, CENTRE_POST_THIN);
        
        if (state.getValue(NORTH)) {
            
            addCollisionBoxToList(pos, entityBox, list, BRANCH_NORTH_THIN);
        }
        
        if (state.getValue(EAST)) {
            
            addCollisionBoxToList(pos, entityBox, list, BRANCH_EAST_THIN);
        }
        
        if (state.getValue(SOUTH)) {
            
            addCollisionBoxToList(pos, entityBox, list, BRANCH_SOUTH_THIN);
        }
        
        if (state.getValue(WEST)) {
            
            addCollisionBoxToList(pos, entityBox, list, BRANCH_WEST_THIN);
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world,
            BlockPos pos) {

        return CENTRE_POST;
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos,
            IBlockState state, int fortune) {

        return Lists.newArrayList(new ItemStack(Item.getItemFromBlock(this)));
    }

    @Override
    public BlockStateContainer createBlockState() {

        return new BlockStateContainer(this, NORTH, EAST, SOUTH, WEST);
    }
}