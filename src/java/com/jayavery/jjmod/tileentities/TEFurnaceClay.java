package com.jayavery.jjmod.tileentities;

import com.jayavery.jjmod.blocks.BlockBuilding;
import com.jayavery.jjmod.blocks.BlockNew;
import com.jayavery.jjmod.init.ModBlocks;
import com.jayavery.jjmod.init.ModItems;
import com.jayavery.jjmod.init.ModRecipes;
import com.jayavery.jjmod.tileentities.TEFurnaceClay.EnumPartClay;
import com.jayavery.jjmod.utilities.BlockWeight;
import com.jayavery.jjmod.utilities.IMultipart;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** TileEntity for clay furnace. */
public class TEFurnaceClay extends TEFurnaceAbstract<EnumPartClay> {

    public TEFurnaceClay() {

        super(ModRecipes.CLAY, 4);
    }
    
    @Override
    protected EnumPartClay partByOrdinal(int ordinal) {

        return EnumPartClay.values()[ordinal];
    }

    /** Enum defining parts of the clay furnace structure. */
    public enum EnumPartClay implements IMultipart {

        BL("bl"), BR("br"), TL("tl"), TR("tr");

        private final String name;

        private EnumPartClay(String name) {

            this.name = name;
        }

        @Override
        public String getName() {

            return this.name;
        }
        
        @Override
        public ItemStack getDrop() {
            
            if (this == BL) {
                
                return new ItemStack(ModItems.furnaceClay);
                
            } else {
                
                return ItemStack.EMPTY;
            }
        }
        
        @Override
        public BlockPos getMaster(BlockPos pos, EnumFacing facing) {
            
            switch (this) {

                case BR: 
                    return pos.offset(facing.rotateY().getOpposite());
                case TL: 
                    return pos.down();
                case TR: 
                    return pos.offset(facing.rotateY().getOpposite()).down();
                case BL:
                default: 
                    return pos;
            }
        }
        
        @Override
        public boolean shouldBreak(World world, BlockPos pos,
                EnumFacing facing) {
            
            BlockBuilding block = ModBlocks.furnaceClay;
            Block below = world.getBlockState(pos.down()).getBlock();
            boolean broken = !BlockWeight.getWeight(below)
                    .canSupport(block.getWeight()) && below != block;
            
            switch (this) {
                
                case BL: {

                    broken |= world.getBlockState(pos.offset(facing.rotateY()))
                            .getBlock() != block;
                    break;
                }

                case BR: {

                    broken |= world.getBlockState(pos.up()).getBlock() != block;
                    break;
                }

                case TR: {

                    broken |= world.getBlockState(pos.offset(facing.rotateY()
                            .getOpposite())).getBlock() != block;
                    break;
                }

                case TL: {

                    broken |= world.getBlockState(pos.down()).getBlock()
                            != block;
                    break;
                }
            }
            
            return broken;
        }
        
        @Override
        public AxisAlignedBB getBoundingBox(EnumFacing facing) {
            
            switch (this) {

                case TR: 
                case TL: 
                    return BlockNew.EIGHT;
                    
                case BR: 
                case BL:
                default: 
                    return Block.FULL_BLOCK_AABB;
            }
        }
        
        @Override
        public AxisAlignedBB getCollisionBox(EnumFacing facing) {
            
            return this.getBoundingBox(facing);
        }
        
        @Override
        public boolean buildStructure(World world, BlockPos pos,
                EnumFacing facing) {
            
            if (this == BL) {
                
                BlockPos posBL = pos;
                BlockPos posBR = posBL.offset(facing.rotateY());
                BlockPos posTL = posBL.up();
                BlockPos posTR = posBR.up();
                
                BlockBuilding block = ModBlocks.furnaceClay;
                BlockPos[] basePositions = {posBL, posBR};
                BlockPos[] upperPositions = {posTL, posTR};
                boolean valid = true;
                
                for (BlockPos position : basePositions) {
                    
                    Block blockCheck = world.getBlockState(position).getBlock();
                    boolean replaceable = blockCheck
                            .isReplaceable(world, position);
                    
                    Block blockBelow = world.getBlockState(position.down())
                            .getBlock();
                    boolean foundation = BlockWeight.getWeight(blockBelow)
                            .canSupport(block.getWeight());
                    
                    if (!replaceable || !foundation) {
                        
                        valid = false;
                        break;
                    }
                }
                
                for (BlockPos position : upperPositions) {
                    
                    Block blockCheck = world.getBlockState(position).getBlock();
                    boolean replaceable = blockCheck
                            .isReplaceable(world, position);
                    
                    if (!replaceable) {
                        
                        valid = false;
                        break;
                    }
                }

                if (valid) {

                    // Place all
                    IBlockState placeState = block.getDefaultState();
    
                    world.setBlockState(posBL, placeState);
                    world.setBlockState(posBR, placeState);
                    world.setBlockState(posTR, placeState);
                    world.setBlockState(posTL, placeState);
    
                    // Set up tileentities
                    ((TEFurnaceClay) world.getTileEntity(posBL))
                            .setState(facing, BL);
                    ((TEFurnaceClay) world.getTileEntity(posBR))
                            .setState(facing, BR);
                    ((TEFurnaceClay) world.getTileEntity(posTL))
                            .setState(facing, TL);
                    ((TEFurnaceClay) world.getTileEntity(posTR))
                            .setState(facing, TR);
                    
                    return true;
                }
            }
            
            return false;
        }
    }
}
