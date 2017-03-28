package com.jayavery.jjmod.tileentities;

import com.jayavery.jjmod.blocks.BlockNew;
import com.jayavery.jjmod.init.ModBlocks;
import com.jayavery.jjmod.init.ModItems;
import com.jayavery.jjmod.tileentities.TECraftingSawpit.EnumPartSawpit;
import com.jayavery.jjmod.utilities.IBuildingBlock;
import com.jayavery.jjmod.utilities.IMultipart;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** TileEntity for sawpit crafting block. */
public class TECraftingSawpit extends TECraftingAbstract<EnumPartSawpit> {
    
    @Override
    protected EnumPartSawpit partByOrdinal(int ordinal) {
        
        return EnumPartSawpit.values()[ordinal];
    }
    
    @Override
    public boolean hasDurability() {
        
        return false;
    }
    
    @Override
    public void update() {}
    
    /** Enum defining parts of the whole sawpit structure. */
    public enum EnumPartSawpit implements IMultipart {
        
        B1 ("b1"), B2("b2"), B3("b3"),
        B4("b4"), B5("b5"), M1 ("m1"),
        M2("m2"), M3("m3"), M4("m4"),
        M5("m5"), T1 ("t1"), T2("t2"),
        T3("t3"), T4("t4"), T5("t5"), T6("t6");
        
        private final String name;
                
        private EnumPartSawpit(String name) {
            
            this.name = name;
        }

        @Override
        public String getName() {

            return this.name;
        }
        
        @Override
        public ItemStack getDrop() {
            
            if (this == T1) {
                
                return new ItemStack(ModItems.craftingSawpit);
                
            } else {
                
                return ItemStack.EMPTY;
            }
        }
        
        @Override
        public BlockPos getMaster(BlockPos pos, EnumFacing facing) {
            
            switch (this) {
                
                case T2:
                    return pos.offset(facing.rotateYCCW());
                case T3:
                    return pos.offset(facing.rotateYCCW(), 2);
                case T4:
                    return pos.offset(facing.rotateYCCW(), 3);
                case T5:
                    return pos.offset(facing.rotateYCCW(), 4);
                case T6:
                    return pos.offset(facing.rotateYCCW()).offset(facing);
                case M1:
                    return pos.up();
                case M2:
                    return pos.up().offset(facing.rotateYCCW());
                case M3:
                    return pos.up().offset(facing.rotateYCCW(), 2);
                case M4:
                    return pos.up().offset(facing.rotateYCCW(), 3);
                case M5:
                    return pos.up().offset(facing.rotateYCCW(), 4);
                case B1:
                    return pos.up(2);
                case B2:
                    return pos.up(2).offset(facing.rotateYCCW());
                case B3:
                    return pos.up(2).offset(facing.rotateYCCW(), 2);
                case B4:
                    return pos.up(2).offset(facing.rotateYCCW(), 3);
                case B5:
                    return pos.up(2).offset(facing.rotateYCCW(), 4);
                case T1:
                default:
                    return pos;    
            }
        }
        
        @Override
        public boolean shouldBreak(World world, BlockPos pos,
                EnumFacing facing) {
            
            boolean broken = false;
            Block block = ModBlocks.craftingSawpit;
            
            switch (this) {
                
                case B1: {
                    
                    broken = world.getBlockState(pos.offset(facing.rotateY()))
                            .getBlock() != block;
                    break;
                }
                
                case B2: {
                    
                    boolean brokenB3 = world.getBlockState(pos
                            .offset(facing.rotateY())).getBlock() != block;
                    boolean brokenB1 = world.getBlockState(pos
                            .offset(facing.rotateYCCW())).getBlock() != block;
                    
                    broken = brokenB3 || brokenB1;
                    break;
                }
                
                case B3: {
                    
                    broken = world.getBlockState(pos.offset(facing.rotateY()))
                            .getBlock() != block;
                    break;
                }
                
                case B4: {
                    
                    broken = world.getBlockState(pos.offset(facing.rotateY()))
                            .getBlock() != block;
                    break;
                }
                
                case B5: {
                    
                    broken = world.getBlockState(pos.up()).getBlock() != block;
                    break;
                }
                
                case M5: {
                    
                    boolean brokenM4 = world.getBlockState(pos
                            .offset(facing.rotateYCCW())).getBlock() != block;
                    boolean brokenB5 = world.getBlockState(pos
                            .down()).getBlock() != block;
                    
                    broken = brokenM4 || brokenB5;
                    break;
                }
                
                case M4: {
                    
                    broken = world.getBlockState(pos.offset(facing
                            .rotateYCCW())).getBlock() != block;
                    break;
                }
                
                case M3: {
                    
                    broken = world.getBlockState(pos.offset(facing
                            .rotateYCCW())).getBlock() != block;
                    break;
                }
                
                case M2: {
                    
                    broken = world.getBlockState(pos.offset(facing
                            .rotateYCCW())).getBlock() != block;
                    break;
                }
                
                case M1: {
                    
                    boolean brokenT1 = world.getBlockState(pos.up())
                            .getBlock() != block;
                    boolean brokenB1 = world.getBlockState(pos.down())
                            .getBlock() != block;
                    
                    broken = brokenT1 || brokenB1;
                    break;
                }
                
                case T1: {
                    
                    boolean brokenT6 = world.getBlockState(pos.offset(facing))
                            .getBlock() != block;
                    boolean brokenT2 = world.getBlockState(pos
                            .offset(facing.rotateY())).getBlock() != block;
                    boolean brokenM1 = world.getBlockState(pos
                            .down()).getBlock() != block;
                    
                    IBlockState frontSupport = world.getBlockState(pos
                            .offset(facing.getOpposite()).down());
                    Block fsBlock = frontSupport.getBlock();
                    boolean fsValid = false;
                    
                    if (fsBlock instanceof IBuildingBlock) {
                        
                        IBuildingBlock building = (IBuildingBlock) fsBlock;
                        
                        fsValid = building.isDouble() && building.isHeavy();
                        
                    } else if (ModBlocks.HEAVY.contains(fsBlock)) {
                        
                        fsValid = true;
                    }
                    
                    IBlockState backSupport = world.getBlockState(pos
                            .offset(facing).down());
                    Block bsBlock = backSupport.getBlock();
                    boolean bsValid = false;
                    
                    if (bsBlock instanceof IBuildingBlock) {
                        
                        IBuildingBlock building = (IBuildingBlock) bsBlock;
                        bsValid = building.isDouble() && building.isHeavy();
                        
                    } else if (ModBlocks.HEAVY.contains(bsBlock)) {
                        
                        bsValid = true;
                    }
                    
                    broken = brokenT2 || brokenM1 ||
                            brokenT6 || !fsValid || !bsValid;
                    break;
                }
                
                case T2: {
                    
                    broken = world.getBlockState(pos.offset(facing.rotateY()))
                            .getBlock() != block;
                    break;
                }
                
                case T3: {
                    
                    broken = world.getBlockState(pos.offset(facing.rotateY()))
                            .getBlock() != block;                
                    break;
                }
                
                case T4: {
                    
                    broken = world.getBlockState(pos.offset(facing.rotateY()))
                            .getBlock() != block;
                    break;
                }
                
                case T5: {
                    
                    boolean brokenT4 = world.getBlockState(pos
                            .offset(facing.rotateYCCW())).getBlock() != block;
                    boolean brokenM5 = world.getBlockState(pos
                            .down()).getBlock() != block;
                    
                    IBlockState frontSupport = world.getBlockState(pos
                            .offset(facing.getOpposite()).down());
                    Block fsBlock = frontSupport.getBlock();
                    boolean fsValid = false;
                    
                    if (fsBlock instanceof IBuildingBlock) {
                        
                        IBuildingBlock building = (IBuildingBlock) fsBlock;
                        
                        fsValid = building.isDouble() && building.isHeavy();
                        
                    } else if (ModBlocks.HEAVY.contains(fsBlock)) {
                        
                        fsValid = true;
                    }
                    
                    IBlockState backSupport = world.getBlockState(pos
                            .offset(facing).down());
                    Block bsBlock = backSupport.getBlock();
                    boolean bsValid = false;
                    
                    if (bsBlock instanceof IBuildingBlock) {
                        
                        IBuildingBlock building = (IBuildingBlock) bsBlock;
                        bsValid = building.isDouble() && building.isHeavy();
                        
                    } else if (ModBlocks.HEAVY.contains(bsBlock)) {
                        
                        bsValid = true;
                    }
                    
                    broken = brokenT4 || brokenM5 || !fsValid || !bsValid;
                    break;
                }
                
                case T6: {
                    
                    broken = world.getBlockState(pos.offset(facing
                            .getOpposite())).getBlock() != block;
                }
            }
            
            return broken;
        }
        
        @Override
        public AxisAlignedBB getBoundingBox(EnumFacing facing) {
            
            int axis = facing.getHorizontalIndex() % 2;
                
            switch (this) {
                
                case T1: case T2: case T3: case T4: case T5:
                    return BlockNew.CENTRE_HALF_LOW[axis];
                case T6:
                    return BlockNew.EIGHT;
                case B1: case B2: case B3: case B4: case B5:
                case M1: case M2: case M3: case M4: case M5:
                default:
                    return Block.FULL_BLOCK_AABB;
            }
        }
        
        @Override
        public AxisAlignedBB getCollisionBox(EnumFacing facing) {
            
            switch (this) {
                
                case T1: case T2: case T3: case T4: case T5:
                    return this.getBoundingBox(facing);
                case T6:
                    return BlockNew.EIGHT;
                case B1: case B2: case B3: case B4: case B5:
                case M1: case M2: case M3: case M4: case M5:
                default:
                    return Block.NULL_AABB;
            }
        }
        
        @Override
        public boolean buildStructure(World world, BlockPos pos,
                EnumFacing facing) {
            
            if (this == T6) {
                
                BlockPos posT6 = pos;
                BlockPos posT1 = posT6.offset(facing.getOpposite());
                BlockPos posT2 = posT1.offset(facing.rotateY());
                BlockPos posT3 = posT2.offset(facing.rotateY());
                BlockPos posT4 = posT3.offset(facing.rotateY());
                BlockPos posT5 = posT4.offset(facing.rotateY());
                BlockPos posM1 = posT1.down();
                BlockPos posM2 = posT2.down();
                BlockPos posM3 = posT3.down();
                BlockPos posM4 = posT4.down();
                BlockPos posM5 = posT5.down();
                BlockPos posB1 = posM1.down();
                BlockPos posB2 = posM2.down();
                BlockPos posB3 = posM3.down();
                BlockPos posB4 = posM4.down();
                BlockPos posB5 = posM5.down();
                
         /*       BlockPos posB3 = pos;
                BlockPos posB1 = posB3.offset(facing.rotateYCCW(), 2);
                BlockPos posB2 = posB1.offset(facing.rotateY());
                BlockPos posB4 = posB3.offset(facing.rotateY());
                BlockPos posB5 = posB4.offset(facing.rotateY());
                BlockPos posM1 = posB1.up();
                BlockPos posM2 = posB2.up();
                BlockPos posM3 = posB3.up();
                BlockPos posM4 = posB4.up();
                BlockPos posM5 = posB5.up();
                BlockPos posT1 = posM1.up();
                BlockPos posT2 = posM2.up();
                BlockPos posT3 = posM3.up();
                BlockPos posT4 = posM4.up();
                BlockPos posT5 = posM5.up();
                BlockPos posT6 = posT1.offset(facing);*/
                
                BlockPos[] allPositions = {posB1, posB2, posB3, posB4, posB5,
                        posM1, posM2, posM3, posM4, posM5, posT1, posT2, posT3,
                        posT4, posT5, posT6};
                
                BlockPos supportFL = posM1.offset(facing.getOpposite());
                BlockPos supportBL = posM1.offset(facing);
                BlockPos supportFR = posM5.offset(facing.getOpposite());
                BlockPos supportBR = posM5.offset(facing);
                BlockPos[] allSupports = {supportFL, supportBL,
                        supportFR, supportBR};
                
                // Check supports
                for (BlockPos support : allSupports) {
                    
                    IBlockState state = world.getBlockState(support);
                    Block block = state.getBlock();
                    
                    if (block instanceof IBuildingBlock) {
                        
                        IBuildingBlock building = (IBuildingBlock) block;
                        
                        if (!building.isDouble() || !building.isHeavy()) {
                            
                            return false;
                        }
                        
                    } else if (!ModBlocks.HEAVY.contains(block)) {
                        
                        return false;
                    }
                }
                
                // Check replaceable
                for (BlockPos aPos : allPositions) {
                    
                    IBlockState state = world.getBlockState(aPos);
                    Block block = state.getBlock();
                    
                    if (!block.isReplaceable(world, aPos)) {
                        
                        return false;
                    }
                }
                
                // Place all
                IBlockState placeState = ModBlocks
                        .craftingSawpit.getDefaultState();

                world.setBlockState(posB1, placeState);
                world.setBlockState(posB2, placeState);
                world.setBlockState(posB3, placeState);
                world.setBlockState(posB4, placeState);
                world.setBlockState(posB5, placeState);
                world.setBlockState(posM5, placeState);
                world.setBlockState(posM4, placeState);
                world.setBlockState(posM3, placeState);
                world.setBlockState(posM2, placeState);
                world.setBlockState(posM1, placeState);
                world.setBlockState(posT1, placeState);
                world.setBlockState(posT2, placeState);
                world.setBlockState(posT3, placeState);
                world.setBlockState(posT4, placeState);
                world.setBlockState(posT5, placeState);
                world.setBlockState(posT6, placeState);
                
                // Set up tileentities
                ((TECraftingSawpit) world.getTileEntity(posB1))
                        .setState(facing, B1);
                ((TECraftingSawpit) world.getTileEntity(posB2))
                        .setState(facing, B2);
                ((TECraftingSawpit) world.getTileEntity(posB3))
                        .setState(facing, B3);
                ((TECraftingSawpit) world.getTileEntity(posB4))
                        .setState(facing, B4);
                ((TECraftingSawpit) world.getTileEntity(posB5))
                        .setState(facing, B5);
                ((TECraftingSawpit) world.getTileEntity(posM5))
                        .setState(facing, M5);
                ((TECraftingSawpit) world.getTileEntity(posM4))
                        .setState(facing, M4);
                ((TECraftingSawpit) world.getTileEntity(posM3))
                        .setState(facing, M3);
                ((TECraftingSawpit) world.getTileEntity(posM2))
                        .setState(facing, M2);
                ((TECraftingSawpit) world.getTileEntity(posM1))
                        .setState(facing, M1);
                ((TECraftingSawpit) world.getTileEntity(posT1))
                        .setState(facing, T1);
                ((TECraftingSawpit) world.getTileEntity(posT2))
                        .setState(facing, T2);
                ((TECraftingSawpit) world.getTileEntity(posT3))
                        .setState(facing, T3);
                ((TECraftingSawpit) world.getTileEntity(posT4))
                        .setState(facing, T4);
                ((TECraftingSawpit) world.getTileEntity(posT5))
                        .setState(facing, T5);
                ((TECraftingSawpit) world.getTileEntity(posT6))
                .setState(facing, T6);
           
                return true;
            }
            
            return false;
        }
    }
}
