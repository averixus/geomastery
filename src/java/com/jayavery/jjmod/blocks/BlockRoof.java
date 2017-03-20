package com.jayavery.jjmod.blocks;

import com.jayavery.jjmod.utilities.BlockMaterial;
import com.jayavery.jjmod.utilities.IBuildingBlock;
import com.jayavery.jjmod.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRoof extends BlockNew implements IBuildingBlock {

    public BlockRoof(String name, float hardness, ToolType harvestTool) {
        
        super(BlockMaterial.WOOD_HANDHARVESTABLE, name,
                CreativeTabs.BUILDING_BLOCKS, hardness, harvestTool);
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
                        
        if (world.getBlockState(pos.down()).getBlock()
                instanceof IBuildingBlock) {
            
            return true;
        }
        
        for (EnumFacing facing : EnumFacing.HORIZONTALS) {
            
            int distance = 1;
            
            while (distance <= 2) {
                
                BlockPos offset = pos.offset(facing, distance);
                
                if (this.isValidSupport(world, offset)) {
                    
                    return true;
                }
                
                if (!(world.getBlockState(pos).getBlock()
                        instanceof BlockRoof)) {
                    
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

            if (feetPos.equals(pos)) {
            
                world.destroyBlock(pos, true);
            }
        }
    }
    
    /** @return Whether the given position is a valid supported thatch block. */
    protected boolean isValidSupport(World world, BlockPos pos) {
        
        boolean isThatch = world.getBlockState(pos).getBlock()
                instanceof BlockRoof;
        boolean isSupported = world.getBlockState(pos.down()).getBlock()
                instanceof IBuildingBlock;
        return isThatch && isSupported;
    }
    
    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos,
            Block block, BlockPos unused) {
        
        if (!this.canPlaceBlockAt(world, pos)) {
            
            world.destroyBlock(pos, true);
        }
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
    public BlockRenderLayer getBlockLayer() {

        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public boolean isLight() {

        return false;
    }

    @Override
    public boolean isHeavy() {

        return false;
    }

    @Override
    public boolean isDouble() {

        return false;
    }

    @Override
    public boolean supportsBeam() {

        return false;
    }

    @Override
    public boolean isShelter() {

        return false;
    }
}