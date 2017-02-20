package com.jj.jjmod.items;

import com.jj.jjmod.container.ContainerInventory;
import com.jj.jjmod.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
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
public class ItemRice extends ItemJj {
        
    public ItemRice() {
        
        super("rice", 1);
    }
    
    /** Attempts to plant a rice crop. */
    @Override
    public EnumActionResult onItemUse(EntityPlayer player,
            World world, BlockPos pos, EnumHand hand, EnumFacing side,
            float x, float y, float z) {
                        
        if (world.isRemote) {
            
            return EnumActionResult.SUCCESS;
        }
        
        ItemStack stack = player.getHeldItem(hand);
        
        // Check positions are allowed
        BlockPos target = pos.offset(side);
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
            
            return EnumActionResult.FAIL;
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
            
                return EnumActionResult.FAIL;
            }
        }
        
        // Place crops
        world.setBlockState(target, ModBlocks.riceBase.getDefaultState());
        world.setBlockState(above, ModBlocks.riceTop.getDefaultState());
        
        if (!player.capabilities.isCreativeMode) {
            
            stack.shrink(1);
            ContainerInventory.updateHand(player, hand);
        }
        
        return EnumActionResult.SUCCESS;
    }
}