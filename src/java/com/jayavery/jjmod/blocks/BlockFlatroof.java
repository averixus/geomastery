package com.jayavery.jjmod.blocks;

import java.util.List;
import com.google.common.collect.Lists;
import com.jayavery.jjmod.utilities.BlockMaterial;
import com.jayavery.jjmod.utilities.BlockWeight;
import com.jayavery.jjmod.utilities.ToolType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Flat breakable roof block. */
public class BlockFlatroof extends BlockBuilding {

    public BlockFlatroof(String name, float hardness, ToolType harvestTool) {
        
        super(BlockMaterial.WOOD_HANDHARVESTABLE, name,
                CreativeTabs.BUILDING_BLOCKS, hardness, harvestTool);
    }
    
    @Override
    public BlockWeight getWeight() {
        
        return BlockWeight.NONE;
    }
    
    @Override
    public boolean shouldConnect(IBlockAccess world, IBlockState state,
            BlockPos pos, EnumFacing direcion) {
        
        return false;
    }
    
    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos,
            IBlockState state, int fortune) {
        
        return Lists.newArrayList(new ItemStack(Item.getItemFromBlock(this)));
    }

    @Override
    public boolean isValid(World world, BlockPos pos) {
                        
        if (BlockWeight.getWeight(world.getBlockState(pos.down()).getBlock())
                .canSupport(this.getWeight())) {
            
            return true;
        }
        
        for (EnumFacing facing : EnumFacing.HORIZONTALS) {
            
            int distance = 1;
            
            while (distance <= 2) {
                
                BlockPos offset = pos.offset(facing, distance);
                
                if (this.isValidSupport(world, offset)) {
                    
                    return true;
                }
                
                if (!(world.getBlockState(offset).getBlock()
                        instanceof BlockFlatroof)) {
                    
                    break;
                }
                
                distance++;
            }
        }
        
        return false;
    }

    /** Breaks the block if an entity tries to walk on it. */
    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos,
            IBlockState state, Entity entity) {
        
        if (entity instanceof EntityLivingBase) {
            
            EntityLivingBase living = (EntityLivingBase) entity;
            BlockPos feetPos = new BlockPos(living.posX,
                    living.getEntityBoundingBox().minY + 0.5D, living.posZ);

            if (feetPos.equals(pos) && !BlockWeight.getWeight(world
                    .getBlockState(pos.down()).getBlock())
                    .canSupport(this.getWeight())) {
            
                world.destroyBlock(pos, true);
            }
        }
    }
    
    /** @return Whether the given position is a valid supported roof block. */
    protected boolean isValidSupport(World world, BlockPos pos) {
        
        boolean isRoof = world.getBlockState(pos).getBlock()
                instanceof BlockFlatroof;
        boolean isSupported = BlockWeight.getWeight(world.getBlockState(pos
                .down()).getBlock()).canSupport(this.getWeight());
        return isRoof && isSupported;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        return TWO;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        return NULL_AABB;
    }
    
    @Override
    public IBlockState getActualState(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        return state;
    }
    
    @Override
    public BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this);
    }
}