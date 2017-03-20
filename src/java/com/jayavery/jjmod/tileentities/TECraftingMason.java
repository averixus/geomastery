package com.jayavery.jjmod.tileentities;

import com.jayavery.jjmod.blocks.BlockNew;
import com.jayavery.jjmod.init.ModBlocks;
import com.jayavery.jjmod.init.ModItems;
import com.jayavery.jjmod.tileentities.TECraftingMason.EnumPartMason;
import com.jayavery.jjmod.utilities.IMultipart;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** TileEntity for mason crafting block. */
public class TECraftingMason extends TECraftingAbstract<EnumPartMason> {

    @Override
    protected EnumPartMason partByOrdinal(int ordinal) {

        return EnumPartMason.values()[ordinal];
    }
    
    /** Enum defining parts of the whole mason structure. */
    public enum EnumPartMason implements IMultipart {

        FM("fm"), FL("fl"), BM("bm"), BR("br"), FR("fr");

        private final String name;

        private EnumPartMason(String name) {

            this.name = name;
        }

        @Override
        public String getName() {

            return this.name;
        }
        
        @Override
        public ItemStack getDrop() {
            
            if (this == FM) {
                    
                return new ItemStack(ModItems.craftingMason);
                
            } else {
                
                return ItemStack.EMPTY;
            }
        }
        
        @Override
        public BlockPos getMaster(BlockPos pos, EnumFacing facing) {
            
            switch (this) {
                
                case FL:
                    return pos.offset(facing.rotateY());
                case FR: 
                    return pos.offset(facing.rotateYCCW());
                case BR:
                    return pos.offset(facing.getOpposite())
                            .offset(facing.rotateYCCW());
                case BM:
                    return pos.offset(facing.getOpposite());
                case FM:
                default:
                    return pos;
            }
        }
        
        @Override
        public boolean shouldBreak(World world, BlockPos pos,
                EnumFacing facing) {
            
            boolean broken = false;
            Block block = ModBlocks.craftingMason;
            
            switch (this) {
                
                case FM: {

                    boolean brokenFL = world.getBlockState(pos.offset(facing
                            .rotateY().getOpposite())).getBlock() != block;
                    boolean brokenBM = world.getBlockState(pos.offset(facing))
                            .getBlock() != block;
                    broken = brokenFL || brokenBM;
                    break;
                }

                case FL: {

                    broken = world.getBlockState(pos.offset(facing.rotateY()))
                            .getBlock() != block;
                    break;
                }

                case BM: {

                    broken = world.getBlockState(pos.offset(facing.rotateY()))
                            .getBlock() != block;
                    break;
                }

                case BR: {

                    broken = world.getBlockState(pos.offset(facing
                            .getOpposite())).getBlock() != block;
                    break;
                }

                case FR: {

                    broken = world.getBlockState(pos.offset(facing.rotateY()
                            .getOpposite())).getBlock() != block;
                    break;
                }
            }
            
            return broken;
        }
        
        @Override
        public AxisAlignedBB getBoundingBox(EnumFacing facing) {
            
            int intFacing = facing.getHorizontalIndex();
            
            switch (this) {
                
                case BR:
                    return BlockNew.CORNER[(intFacing + 1) % 4];
                case BM:
                    return BlockNew.CORNER[intFacing % 4];
                case FR: 
                    return BlockNew.CORNER[(intFacing + 2) % 4];
                case FM: 
                    return BlockNew.CORNER[(intFacing + 3) % 4];
                case FL: 
                    return BlockNew.HALF[(intFacing + 3) % 4];
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
            
            if (this == FM) {
                
                BlockPos posFM = pos;
                BlockPos posFL = posFM.offset(facing.rotateY().getOpposite());
                BlockPos posBM = posFM.offset(facing);
                BlockPos posFR = posFM.offset(facing.rotateY());
                BlockPos posBR = posFR.offset(facing);

                // Check replaceable
                IBlockState stateFM = world.getBlockState(posFM);
                Block blockFM = stateFM.getBlock();
                boolean replaceableFM = blockFM.isReplaceable(world, posFM);

                IBlockState stateFL = world.getBlockState(posFL);
                Block blockFL = stateFL.getBlock();
                boolean replaceableFL = blockFL.isReplaceable(world, posFL);

                IBlockState stateBM = world.getBlockState(posBM);
                Block blockBM = stateBM.getBlock();
                boolean replaceableBM = blockBM.isReplaceable(world, posBM);

                IBlockState stateBR = world.getBlockState(posBR);
                Block blockBR = stateBR.getBlock();
                boolean replaceableBR = blockBR.isReplaceable(world, posBR);

                IBlockState stateFR = world.getBlockState(posFR);
                Block blockFR = stateFR.getBlock();
                boolean replaceableFR = blockFR.isReplaceable(world, posFR);

                if (!replaceableFM || !replaceableFL || !replaceableBM ||
                        !replaceableBR || !replaceableFR) {

                    // Place all
                    IBlockState placeState = ModBlocks
                            .craftingMason.getDefaultState();
    
                    world.setBlockState(posFM, placeState);
                    world.setBlockState(posFL, placeState);
                    world.setBlockState(posBM, placeState);
                    world.setBlockState(posBR, placeState);
                    world.setBlockState(posFR, placeState);
    
                    // Set up tileentities
                    ((TECraftingMason) world.getTileEntity(posFM))
                            .setState(facing, FM);
                    ((TECraftingMason) world.getTileEntity(posFL))
                            .setState(facing, FL);
                    ((TECraftingMason) world.getTileEntity(posBM))
                            .setState(facing, BM);
                    ((TECraftingMason) world.getTileEntity(posBR))
                            .setState(facing, BR);
                    ((TECraftingMason) world.getTileEntity(posFR))
                            .setState(facing, FR);
                    
                    return true;
                }
            }
            
            return false;
        }
    }
}
