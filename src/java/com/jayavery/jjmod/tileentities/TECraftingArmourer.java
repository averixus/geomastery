package com.jayavery.jjmod.tileentities;

import com.jayavery.jjmod.blocks.BlockNew;
import com.jayavery.jjmod.init.ModBlocks;
import com.jayavery.jjmod.init.ModItems;
import com.jayavery.jjmod.tileentities.TECraftingArmourer.EnumPartArmourer;
import com.jayavery.jjmod.utilities.IMultipart;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** TileEntity for armourer crafting block. */
public class TECraftingArmourer extends TECraftingAbstract<EnumPartArmourer> {
    
    @Override
    protected EnumPartArmourer partByOrdinal(int ordinal) {

        return EnumPartArmourer.values()[ordinal];
    }
    
    /** Enum defining parts of the whole Armourer structure. */
    public enum EnumPartArmourer implements IMultipart {
        
        T("t"), L("l"), M("m"), R("r");
        
        private final String name;
        
        private EnumPartArmourer(String name) {
            
            this.name = name;
        }
        
        @Override
        public String getName() {
            
            return this.name;
        }

        @Override
        public ItemStack getDrop() {

            if (this == T) {
                
                return new ItemStack(ModItems.craftingArmourer);
                
            } else {
                
                return ItemStack.EMPTY;
            }
        }
        
        @Override
        public BlockPos getMaster(BlockPos pos, EnumFacing facing) {
            
            switch (this) {
                
                case L:
                    return pos.up();
                case M:
                    return pos.offset(facing.rotateYCCW()).up();
                case R:
                    return pos.offset(facing.rotateYCCW(), 2).up();
                case T:
                default:
                    return pos;
            }
        }
        
        @Override
        public boolean shouldBreak(World world, BlockPos pos,
                EnumFacing facing) {
            
            boolean broken = false;
            Block block = ModBlocks.craftingArmourer;
            
            switch (this) {
                
                case T: {
                    
                    broken = world.getBlockState(pos.down())
                            .getBlock() != block;
                    break;
                }
                
                case L: {
                    
                    boolean brokenT = world.getBlockState(pos.up())
                            .getBlock() != block;
                    boolean brokenM = world.getBlockState(pos
                            .offset(facing.rotateY())).getBlock() != block;
                    
                    broken = brokenM || brokenT;
                    break;
                }
                
                case M: {
                    
                    boolean brokenL = world.getBlockState(pos
                            .offset(facing.rotateYCCW())).getBlock() != block;
                    boolean brokenR = world.getBlockState(pos
                            .offset(facing.rotateY())).getBlock() != block;
                    
                    broken = brokenL || brokenR;
                    break;
                }
                
                case R: {
                    
                    broken = world.getBlockState(pos
                            .offset(facing.rotateYCCW()))
                            .getBlock() != block;
                    break;
                }
            }
            
            return broken;
        }
        
        @Override
        public AxisAlignedBB getBoundingBox(EnumFacing facing) {
            
            int intFacing = facing.getHorizontalIndex();
            
            switch (this) {
                
                case R: 
                    return BlockNew.TWELVE;
                case L: 
                    return BlockNew.HALF[(intFacing + 1) % 4];
                case T: 
                    return BlockNew.CORNER[(intFacing + 2) % 4];
                case M: 
                default: 
                    return Block.FULL_BLOCK_AABB;
            }
        }
        
        @Override
        public AxisAlignedBB getCollisionBox(EnumFacing facing) {
            
            switch (this) {
                
                case R: 
                    return BlockNew.TWELVE;
                case M: 
                    return BlockNew.FOURTEEN;
                case L: 
                case T: 
                    return Block.NULL_AABB;
                default: 
                    return Block.FULL_BLOCK_AABB;
            }
        }
        
        @Override
        public boolean buildStructure(World world, BlockPos pos,
                EnumFacing facing) {
            
            if (this == M) {
                
                BlockPos posM = pos;
                BlockPos posL = posM.offset(facing.rotateYCCW());
                BlockPos posT = posL.up();
                BlockPos posR = posM.offset(facing.rotateY());
                
                // Check replaceable
                IBlockState stateT = world.getBlockState(posT);
                Block blockT = stateT.getBlock();
                boolean replaceableT = blockT.isReplaceable(world, posT);
                
                IBlockState stateL = world.getBlockState(posL);
                Block blockL = stateL.getBlock();
                boolean replaceableL = blockL.isReplaceable(world, posL);
                
                IBlockState stateM = world.getBlockState(posM);
                Block blockM = stateM.getBlock();
                boolean replaceableM = blockM.isReplaceable(world, posM);
                
                IBlockState stateR = world.getBlockState(posR);
                Block blockR = stateR.getBlock();
                boolean replaceableR = blockR.isReplaceable(world, posR);
                
                if (replaceableT && replaceableL &&
                        replaceableM && replaceableR) {
                
                    // Place all
                    IBlockState placeState = ModBlocks
                            .craftingArmourer.getDefaultState();
                    
                    world.setBlockState(posT, placeState);
                    world.setBlockState(posL, placeState);
                    world.setBlockState(posM, placeState);
                    world.setBlockState(posR, placeState);
                    
                    // Set up tileentities
                    ((TECraftingArmourer) world.getTileEntity(posT))
                            .setState(facing, T);
                    ((TECraftingArmourer) world.getTileEntity(posL))
                            .setState(facing, L);
                    ((TECraftingArmourer) world.getTileEntity(posM))
                            .setState(facing, M);
                    ((TECraftingArmourer) world.getTileEntity(posR))
                            .setState(facing, R);
                    
                    return true;
                }
            }
            
            return false;
        }
    }
}
