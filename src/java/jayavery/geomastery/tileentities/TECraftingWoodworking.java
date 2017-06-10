package jayavery.geomastery.tileentities;

import jayavery.geomastery.blocks.BlockBuilding;
import jayavery.geomastery.blocks.BlockNew;
import jayavery.geomastery.main.GeoBlocks;
import jayavery.geomastery.main.GeoItems;
import jayavery.geomastery.tileentities.TECraftingWoodworking.EnumPartWoodworking;
import jayavery.geomastery.utilities.BlockWeight;
import jayavery.geomastery.utilities.IMultipart;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** TileEntity for woodworking crafting block. */
public class TECraftingWoodworking extends
        TECraftingAbstract<EnumPartWoodworking> {

    @Override
    protected EnumPartWoodworking partByOrdinal(int ordinal) {
        
        return EnumPartWoodworking.values()[ordinal];
    }
    
    /** Enum defining parts of the whole woodworking structure. */
    public enum EnumPartWoodworking implements IMultipart {

        FM("fm"), FL("fl"), BL("bl"), BM("bm"), BR("br"), FR("fr");

        private final String name;

        private EnumPartWoodworking(String name) {

            this.name = name;
        }

        @Override
        public String getName() {

            return this.name;
        }
        
        @Override
        public ItemStack getDrop() {
            
            if (this == FM) {
                
                return new ItemStack(GeoItems.CRAFTING_WOODWORKING);
                
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
                case BL:
                    return pos.offset(facing.getOpposite())
                            .offset(facing.rotateY());
                case BM:
                    return pos.offset(facing.getOpposite());
                case BR:
                    return pos.offset(facing.getOpposite())
                            .offset(facing.rotateYCCW());
                case FM:
                default:
                    return pos;
            }
        }
        
        @Override
        public boolean shouldBreak(World world, BlockPos pos,
                EnumFacing facing) {
            
            BlockBuilding block = GeoBlocks.CRAFTING_WOODWORKING;
            Block below = world.getBlockState(pos.down()).getBlock();
            boolean broken = !BlockWeight.getWeight(below)
                    .canSupport(block.getWeight());
            
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
                    return BlockNew.HALF[(intFacing + 3) % 4];
                case FR: 
                    return BlockNew.CORNER[(intFacing + 2) % 4];
                case FM: 
                    return BlockNew.HALF[intFacing];
                case FL: 
                    return BlockNew.TWELVE;
                case BM: 
                case BL: 
                default:
                    return Block.FULL_BLOCK_AABB;
            }
        }
        
        @Override
        public AxisAlignedBB getCollisionBox(EnumFacing facing) {
            
            switch (this) {
                
                case FL:
                    return BlockNew.TWELVE;
                case BM:
                case BL:
                    return Block.FULL_BLOCK_AABB;
                case BR:
                case FR:
                case FM:
                default:
                    return Block.NULL_AABB;
            }
        }
        
        @Override
        public boolean buildStructure(World world, BlockPos pos,
                EnumFacing facing) {
            
            if (this == FM) {
                
                BlockPos posFM = pos;
                BlockPos posFL = posFM.offset(facing.rotateY().getOpposite());
                BlockPos posBL = posFL.offset(facing);
                BlockPos posBM = posFM.offset(facing);
                BlockPos posBR = posBM.offset(facing.rotateY());
                BlockPos posFR = posBR.offset(facing.getOpposite());
                
                BlockBuilding block = GeoBlocks.CRAFTING_WOODWORKING;
                BlockPos[] positions = {posFM, posFL, posBL,
                        posBM, posBR, posFR};
                boolean valid = true;
                
                for (BlockPos position : positions) {
                    
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
                    ((TECraftingWoodworking) world.getTileEntity(posFM))
                            .setState(facing, FM);
                    ((TECraftingWoodworking) world.getTileEntity(posFL))
                            .setState(facing, FL);
                    ((TECraftingWoodworking) world.getTileEntity(posBL))
                            .setState(facing, BL);
                    ((TECraftingWoodworking) world.getTileEntity(posBM))
                            .setState(facing, BM);
                    ((TECraftingWoodworking) world.getTileEntity(posBR))
                            .setState(facing, BR);
                    ((TECraftingWoodworking) world.getTileEntity(posFR))
                            .setState(facing, FR);
                    
                    return true;
                }
            }
            
            return false;
        }
    }
}
