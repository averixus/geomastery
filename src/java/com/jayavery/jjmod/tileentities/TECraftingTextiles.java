package com.jayavery.jjmod.tileentities;

import com.jayavery.jjmod.blocks.BlockCraftingTextiles;
import com.jayavery.jjmod.blocks.BlockNew;
import com.jayavery.jjmod.init.ModBlocks;
import com.jayavery.jjmod.init.ModItems;
import com.jayavery.jjmod.tileentities.TECraftingArmourer.EnumPartArmourer;
import com.jayavery.jjmod.tileentities.TECraftingTextiles.EnumPartTextiles;
import com.jayavery.jjmod.utilities.IMultipart;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TECraftingTextiles extends TECraftingAbstract<EnumPartTextiles> {

    @Override
    protected EnumPartTextiles partByOrdinal(int ordinal) {
        
        return EnumPartTextiles.values()[ordinal];
    }
    
    /** Enum defining parts of the whole Textiles structure. */
    public enum EnumPartTextiles implements IMultipart {

        FRONT("front"), BACK("back");

        private final String name;

        private EnumPartTextiles(String name) {

            this.name = name;
        }

        @Override
        public String getName() {

            return this.name;
        }
        
        @Override
        public ItemStack getDrop() {
            
            if (this == FRONT) {
                
                return new ItemStack(ModItems.craftingTextiles);
                
            } else {
                
                return ItemStack.EMPTY;
            }
        }
        
        @Override
        public BlockPos getMaster(BlockPos pos, EnumFacing facing) {
            
            if (this == FRONT) {
                
                return pos;
                
            } else {
                
                return pos.offset(facing.getOpposite());
            }
        }
        
        @Override
        public boolean shouldBreak(World world, BlockPos pos,
                EnumFacing facing) {
            
            boolean broken = false;
            Block block = ModBlocks.craftingTextiles;
            
            if (this == FRONT) {

                broken = world.getBlockState(pos.offset(facing)).getBlock()
                        != block;
                
            } else {

                broken = world.getBlockState(pos.offset(facing.getOpposite()))
                        .getBlock() != block;
            }
            
            return broken;
        }
        
        @Override
        public AxisAlignedBB getBoundingBox(EnumFacing facing) {
                        
            return this == BACK ? Block.FULL_BLOCK_AABB :
                BlockNew.HALF[facing.getHorizontalIndex()];
        }
        
        @Override
        public AxisAlignedBB getCollisionBox(EnumFacing facing) {
            
            return this == BACK ? Block.FULL_BLOCK_AABB : Block.NULL_AABB;
        }
        
        @Override
        public boolean buildStructure(World world, BlockPos pos,
                EnumFacing facing) {
            
            if (this == FRONT) {
                
                BlockPos frontPos = pos;
                BlockPos backPos = frontPos.offset(facing);

                // Check replaceable
                IBlockState frontState = world.getBlockState(frontPos);
                Block frontBlock = frontState.getBlock();
                boolean frontReplaceable = frontBlock
                        .isReplaceable(world, frontPos);

                IBlockState backState = world.getBlockState(backPos);
                Block backBlock = backState.getBlock();
                boolean backReplaceable = backBlock
                        .isReplaceable(world, backPos);

                if (frontReplaceable && backReplaceable) {

                    // Place all blocks
                    IBlockState placeState = ModBlocks
                            .craftingTextiles.getDefaultState();
                    
                    world.setBlockState(backPos, placeState);
                    world.setBlockState(frontPos, placeState);
                    
                    ((TECraftingTextiles) world.getTileEntity(backPos))
                            .setState(facing, BACK);
                    ((TECraftingTextiles) world.getTileEntity(frontPos))
                            .setState(facing, FRONT);
                    
                    return true;
                }
            }
            
            return false;
        }
    }
}
