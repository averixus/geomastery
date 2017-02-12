package com.jj.jjmod.blocks;

import java.util.Random;
import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.init.ModItems;
import com.jj.jjmod.utilities.BlockMaterial;
import com.jj.jjmod.utilities.IBiomeCheck;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeJungle;
import net.minecraft.world.biome.BiomeMushroomIsland;
import net.minecraft.world.biome.BiomePlains;
import net.minecraft.world.biome.BiomeSwamp;

/** Rice crop base block. */
public class BlockRiceBase extends BlockNew implements IBiomeCheck {
    
    public BlockRiceBase() {
        
        super(BlockMaterial.WATER_PLANT, "rice_base",
                null, 0.2F, ToolType.SICKLE);
        this.setDefaultState(this.blockState.getBaseState());        
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        return NULL_AABB;
    }
    
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        
        return ModItems.rice;
    }
    
    @Override
    public boolean doesSideBlockRendering(IBlockState state,
            IBlockAccess world, BlockPos pos, EnumFacing face) {
        
        BlockPos beside = pos.offset(face);
        Block blockBeside = world.getBlockState(beside).getBlock();
        
        if (blockBeside == Blocks.WATER ||
                blockBeside == Blocks.FLOWING_WATER) {
            
            return true;
        }
        
        return false;
    }
    
    @Override
    public void neighborChanged(IBlockState state, World world,
            BlockPos pos, Block block, BlockPos unusued) {
        
        if ((world.getBlockState(pos.up()).getBlock() != ModBlocks.riceTop) ||
                !this.canStay(world, pos)) {
            
            world.setBlockState(pos, Blocks.WATER.getDefaultState());
        }
    }
    
    @Override
    public boolean canRenderInLayer(IBlockState state,
            BlockRenderLayer layer) {
        
        return (layer == BlockRenderLayer.CUTOUT_MIPPED ||
                layer == BlockRenderLayer.TRANSLUCENT);
    }
    
    /** @return Whether this block can stay in the given position. */
    protected boolean canStay(World world, BlockPos pos) {
        
        BlockPos posBelow = pos.down();
        Block blockBelow = world.getBlockState(posBelow).getBlock();
        
        if (blockBelow != Blocks.GRASS && blockBelow != Blocks.DIRT &&
                blockBelow != Blocks.FARMLAND) {

            return false;
        }
        
        for (EnumFacing facingCheck : EnumFacing.Plane.HORIZONTAL) {
            
            BlockPos posCheck = pos.offset(facingCheck);
            IBlockState stateCheck = world.getBlockState(posCheck);
            Block blockCheck = stateCheck.getBlock();
            
            boolean sideSolid = stateCheck.isSideSolid(world, posCheck,
                    facingCheck.getOpposite());
            boolean validWater = (blockCheck == Blocks.WATER ||
                    blockCheck == Blocks.FLOWING_WATER) &&
                    stateCheck.getValue(BlockLiquid.LEVEL) == 0 &&
                    Blocks.WATER.modifyAcceleration(world, posCheck,
                    null, Vec3d.ZERO).equals(Vec3d.ZERO);
            boolean rice = blockCheck == ModBlocks.riceBase;
            
            if (!sideSolid && !validWater && !rice) {
            
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos,
            IBlockState state, Entity entity) {
        
        entity.motionX *= 0.4D;
        entity.motionZ *= 0.4D;
    }

    @Override
    public boolean isPermitted(Biome biome) {

        return biome instanceof BiomePlains || biome == Biomes.BEACH ||
                biome instanceof BiomeSwamp ||
                biome instanceof BiomeMushroomIsland ||
                biome instanceof BiomeJungle;
    }
}
