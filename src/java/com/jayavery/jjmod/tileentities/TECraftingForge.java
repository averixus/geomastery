package com.jayavery.jjmod.tileentities;

import com.jayavery.jjmod.blocks.BlockNew;
import com.jayavery.jjmod.init.ModBlocks;
import com.jayavery.jjmod.init.ModItems;
import com.jayavery.jjmod.tileentities.TECraftingForge.EnumPartForge;
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

/** TileEntity for forge crafting block. */
public class TECraftingForge extends TECraftingAbstract<EnumPartForge> {

    @Override
    protected EnumPartForge partByOrdinal(int ordinal) {
        
        return EnumPartForge.values()[ordinal];
    }

    /** Enum defining parts of the whole forge structure. */
    public enum EnumPartForge implements IMultipart {

        FM("fm"), FL("fl"), BL("bl"), BM("bm"), BR("br"), FR("fr");

        private String name;

        private EnumPartForge(String name) {

            this.name = name;
        }
        
        @Override
        public String getName() {

            return this.name;
        }
        
        @Override
        public ItemStack getDrop() {
            
            if (this == FM) {
                
                return new ItemStack(ModItems.craftingForge);
                
            } else {
                
                return ItemStack.EMPTY;
            }
        }

        @Override
        public BlockPos getMaster(BlockPos pos, EnumFacing facing) {
            
            switch (this) {
                
                case FL:
                    return pos.offset(facing.rotateY());
                case BL:
                    return pos.offset(facing.getOpposite())
                            .offset(facing.rotateY());
                case BM:
                    return pos.offset(facing.getOpposite());
                case BR:
                    return pos.offset(facing.getOpposite())
                            .offset(facing.rotateYCCW(), 2);
                case FM:
                default:
                    return pos;
            }
        }
        
        @Override
        public boolean shouldBreak(World world, BlockPos pos,
                EnumFacing facing) {
            
            Block block = ModBlocks.craftingForge;
            Block below = world.getBlockState(pos.down()).getBlock();
            boolean broken = !(ModBlocks.LIGHT.contains(below) ||
                    ModBlocks.HEAVY.contains(below) || below == block);
            
            switch (this) {

                case FM: {

                    broken |= world.getBlockState(pos.offset(facing.rotateY()
                            .getOpposite())).getBlock() != block;
                    break;
                }

                case FL: {

                    broken |= world.getBlockState(pos.offset(facing))
                            .getBlock() != block;
                    break;
                }

                case BL: {

                    broken |= world.getBlockState(pos.offset(facing.rotateY()))
                            .getBlock() != block;
                    break;
                }

                case BM: {

                    broken |= world.getBlockState(pos.offset(facing.rotateY()))
                            .getBlock() != block;
                    break;
                }

                case BR: {

                    broken |= world.getBlockState(pos.offset(facing
                            .getOpposite())).getBlock() != block;
                    break;
                }

                case FR: {

                    broken |= world.getBlockState(pos.offset(facing.rotateY()
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
                case BM:
                    return BlockNew.HALF[intFacing % 4];
                
                case FR: 
                    return BlockNew.CORNER[(intFacing + 2) % 4];
                
                case FM: 
                    return BlockNew.CORNER[(intFacing + 3) % 4];
                    
                case BL: 
                case FL: 
                    return BlockNew.TEN;
                
                default:
                    return Block.FULL_BLOCK_AABB;
            }
        }
        
        @Override
        public AxisAlignedBB getCollisionBox(EnumFacing facing) {
            
            switch (this) {
                
                case FR:
                    return BlockNew.CENTRE_TWO;
                case FL:
                    return Block.NULL_AABB;
                default:
                    return this.getBoundingBox(facing);
            }
        }
        
        @Override
        public boolean buildStructure(World world, BlockPos pos,
                EnumFacing facing) {
            
            if (this == FM) {
                
                BlockPos posFM = pos;
                BlockPos posFL =
                        posFM.offset(facing.rotateY().getOpposite());
                BlockPos posBL =
                        posFL.offset(facing);
                BlockPos posBM =
                        posFM.offset(facing);
                BlockPos posBR = posBM.offset(facing.rotateY());
                BlockPos posFR = posBR.offset(facing.getOpposite());
                
                Block block = ModBlocks.craftingForge;
                BlockPos[] positions = {posFM, posFL, posBL,
                        posBM, posBR, posFR};
                boolean valid = true;
                
                for (BlockPos position : positions) {
                    
                    Block blockCheck = world.getBlockState(position).getBlock();
                    boolean replaceable = blockCheck
                            .isReplaceable(world, position);
                    
                    Block blockBelow = world.getBlockState(position.down())
                            .getBlock();
                    boolean foundation = ModBlocks.LIGHT.contains(blockBelow) ||
                            ModBlocks.HEAVY.contains(blockBelow) ||
                            blockBelow == block;
                    
                    if (!replaceable || !foundation) {
                        
                        valid = false;
                        break;
                    }
                }

                if (valid) {

                    // Place all
                    IBlockState placeState = block.getDefaultState();
    
                    world.setBlockState(posFM, placeState);
                    world.setBlockState(posFL, placeState);
                    world.setBlockState(posBL, placeState);
                    world.setBlockState(posBM, placeState);
                    world.setBlockState(posBR, placeState);
                    world.setBlockState(posFR, placeState);
    
                    // Set up tileentities
                    ((TECraftingForge) world.getTileEntity(posFM))
                            .setState(facing, FM);
                    ((TECraftingForge) world.getTileEntity(posFL))
                            .setState(facing, FL);
                    ((TECraftingForge) world.getTileEntity(posBL))
                            .setState(facing, BL);
                    ((TECraftingForge) world.getTileEntity(posBM))
                            .setState(facing, BM);
                    ((TECraftingForge) world.getTileEntity(posBR))
                            .setState(facing, BR);
                    ((TECraftingForge) world.getTileEntity(posFR))
                            .setState(facing, FR);
                    
                    return true;
                }
            }
            
            return false;
        }
    }
}
