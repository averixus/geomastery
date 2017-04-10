package com.jayavery.jjmod.items;

import com.jayavery.jjmod.container.ContainerInventory;
import com.jayavery.jjmod.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/** Rice seed item. */
public class ItemRice extends ItemBlockplacer {
        
    public ItemRice() {
        
        super("rice", 12, CreativeTabs.MATERIALS, SoundType.PLANT);
    }
    
    /** Attempts to plant a rice crop. */
    @Override
    protected boolean place(World world, BlockPos targetPos,
            EnumFacing targetSide, EnumFacing placeFacing, ItemStack stack) {
        
        // Check positions are allowed
        BlockPos target = targetPos.offset(targetSide);
        IBlockState stateTarget = world.getBlockState(target);
        Block blockTarget = stateTarget.getBlock();
        boolean okTarget = (blockTarget == Blocks.WATER ||
                blockTarget == Blocks.FLOWING_WATER) &&
                (stateTarget.getValue(BlockLiquid.LEVEL) == 0);
        
        BlockPos above = target.up();
        IBlockState stateAbove = world.getBlockState(above);
        Block blockAbove = stateAbove.getBlock();
        boolean okAbove = blockAbove.isReplaceable(world, above) &&
                blockAbove != Blocks.WATER;
        
        if (!okTarget || !okAbove) {
            
            return false;
        }
        
        // Check surroundings are allowed
        for (EnumFacing facingCheck : EnumFacing.Plane.HORIZONTAL) {
            
            BlockPos posCheck = target.offset(facingCheck);
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
        
        // Place crops
        world.setBlockState(target, ModBlocks.riceBase.getDefaultState());
        world.setBlockState(above, ModBlocks.riceTop.getDefaultState());
        
        return true;
    }
}