package jayavery.geomastery.tileentities;

import jayavery.geomastery.blocks.BlockBuilding;
import jayavery.geomastery.blocks.BlockNew;
import jayavery.geomastery.main.GeoBlocks;
import jayavery.geomastery.main.GeoItems;
import jayavery.geomastery.tileentities.TECraftingSawpit.EnumPartSawpit;
import jayavery.geomastery.utilities.BlockWeight;
import jayavery.geomastery.utilities.IMultipart;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
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
        
        FL("fl"), L("l"), M("m"), R("r"), FR("fr"), F("f");
        
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
            
            if (this == F) {
                
                return new ItemStack(GeoItems.craftingSawpit);
                
            } else {
                
                return ItemStack.EMPTY;
            }
        }
        
        @Override
        public BlockPos getMaster(BlockPos pos, EnumFacing facing) {
            
            switch (this) {
                
                case FL:
                    return pos.offset(facing.getOpposite());
                case L:
                    return pos.offset(facing.rotateYCCW())
                            .offset(facing.getOpposite());
                case M:
                    return pos.offset(facing.rotateYCCW(), 2)
                            .offset(facing.getOpposite());
                case R:
                    return pos.offset(facing.rotateYCCW(), 3)
                            .offset(facing.getOpposite());
                case FR:
                    return pos.offset(facing.rotateYCCW(), 4)
                            .offset(facing.getOpposite());
                case F:
                default:
                    return pos; 
            }
        }
        
        @Override
        public boolean shouldBreak(World world, BlockPos pos,
                EnumFacing facing) {
            
            boolean broken = false;
            BlockBuilding block = GeoBlocks.craftingSawpit;
            
            switch (this) {
                
                case F: {
                    
                    broken = world.getBlockState(pos.offset(facing)).getBlock()
                            != block;
                    break;
                }
                
                case FL: {
                    
                    boolean brokenL = world.getBlockState(pos.
                            offset(facing.rotateY())).getBlock() != block;
                    boolean brokenF = world.getBlockState(pos.offset(facing
                            .getOpposite())).getBlock() != block;
                    
                    IBlockState frontSupport = world.getBlockState(pos
                            .offset(facing.getOpposite()).down());
                    Block fsBlock = frontSupport.getBlock();
                    boolean fsValid = BlockWeight.getWeight(fsBlock)
                            .canSupport(block.getWeight());
                    
                    IBlockState backSupport = world.getBlockState(pos
                            .offset(facing).down());
                    Block bsBlock = backSupport.getBlock();
                    boolean bsValid = BlockWeight.getWeight(bsBlock)
                            .canSupport(block.getWeight());
                    
                    boolean hasAir = world.isAirBlock(pos.down()) &&
                            world.isAirBlock(pos.down(2));
                    
                    broken = brokenL || brokenF || !fsValid ||
                            !bsValid || !hasAir;
                    break;
                }
                
                case L: {
                    
                    boolean brokenFL = world.getBlockState(pos
                            .offset(facing.rotateYCCW())).getBlock() != block;
                    boolean brokenM = world.getBlockState(pos
                            .offset(facing.rotateY())).getBlock() != block;
                    boolean hasAir = world.isAirBlock(pos.down()) &&
                            world.isAirBlock(pos.down(2));
                    broken = brokenFL || brokenM || !hasAir;
                    break;
                }
                
                case M: {
                    
                    boolean brokenL = world.getBlockState(pos
                            .offset(facing.rotateYCCW())).getBlock() != block;
                    boolean brokenR = world.getBlockState(pos
                            .offset(facing.rotateY())).getBlock() != block; 
                    boolean hasAir = world.isAirBlock(pos.down()) &&
                            world.isAirBlock(pos.down(2));
                    broken = brokenL || brokenR || !hasAir;
                    break;
                }
                
                case R: {
                    
                    boolean brokenM = world.getBlockState(pos
                            .offset(facing.rotateYCCW())).getBlock() != block;
                    boolean brokenFR = world.getBlockState(pos
                            .offset(facing.rotateY())).getBlock() != block;
                    boolean hasAir = world.isAirBlock(pos.down()) &&
                            world.isAirBlock(pos.down(2));
                    broken = brokenM || brokenFR || !hasAir;
                    break;
                }
                
                case FR: {
                    
                    boolean brokenR = world.getBlockState(pos.offset(facing
                            .rotateYCCW())).getBlock() != block;
                    
                    IBlockState frontSupport = world.getBlockState(pos
                            .offset(facing.getOpposite()).down());
                    Block fsBlock = frontSupport.getBlock();
                    boolean fsValid = BlockWeight.getWeight(fsBlock)
                            .canSupport(block.getWeight());
                    
                    IBlockState backSupport = world.getBlockState(pos
                            .offset(facing).down());
                    Block bsBlock = backSupport.getBlock();
                    boolean bsValid = BlockWeight.getWeight(bsBlock)
                            .canSupport(block.getWeight());
                    
                    boolean hasAir = world.isAirBlock(pos.down()) &&
                            world.isAirBlock(pos.down(2));
                    
                    broken = brokenR || !fsValid || !bsValid || !hasAir;
                    break;
                }
            }
            
            return broken;
        }
        
        @Override
        public AxisAlignedBB getBoundingBox(EnumFacing facing) {
            
            int axis = facing.getHorizontalIndex() % 2;
                
            switch (this) {
                
                case FL: case L: case M: case R: case FR:
                    return BlockNew.CENTRE_HALF_LOW[axis];
                case F:
                default:
                    return BlockNew.EIGHT;
            }
        }
        
        @Override
        public AxisAlignedBB getCollisionBox(EnumFacing facing) {
            
            return this.getBoundingBox(facing);
        }
        
        @Override
        public boolean buildStructure(World world, BlockPos pos,
                EnumFacing facing) {
            
            if (this == F) {
                
                BlockBuilding placeBlock = GeoBlocks.craftingSawpit;
                IBlockState placeState = placeBlock.getDefaultState();
                
                BlockPos posF = pos;
                BlockPos posFL = posF.offset(facing);
                BlockPos posL = posFL.offset(facing.rotateY());
                BlockPos posM = posL.offset(facing.rotateY());
                BlockPos posR = posM.offset(facing.rotateY());
                BlockPos posFR = posR.offset(facing.rotateY());
                
                BlockPos[] allPositions = {posFL, posL, posM,
                        posR, posFR, posF};
                
                BlockPos supportFL = posFL.offset(facing.getOpposite()).down();
                BlockPos supportBL = posFL.offset(facing).down();
                BlockPos supportFR = posFR.offset(facing.getOpposite()).down();
                BlockPos supportBR = posFR.offset(facing).down();
                BlockPos[] allSupports = {supportFL, supportBL,
                        supportFR, supportBR};
                
                BlockPos[] allAir = {posFL.down(), posFL.down(2), posL.down(),
                        posL.down(2), posM.down(), posM.down(2), posR.down(),
                        posR.down(2), posFR.down(), posFR.down(2)};
                
                // Check supports
                for (BlockPos support : allSupports) {
                    
                    IBlockState state = world.getBlockState(support);
                    Block block = state.getBlock();
                    
                    if (!BlockWeight.getWeight(block)
                            .canSupport(placeBlock.getWeight())) {
                        
                        return false;
                    }
                }
                
                // Check air
                for (BlockPos air : allAir) {
                    
                    if (!world.isAirBlock(air)) {
                        
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
                world.setBlockState(posF, placeState);
                world.setBlockState(posFL, placeState);
                world.setBlockState(posL, placeState);
                world.setBlockState(posM, placeState);
                world.setBlockState(posR, placeState);
                world.setBlockState(posFR, placeState);
                
                // Set up tileentities
                ((TECraftingSawpit) world.getTileEntity(posF))
                        .setState(facing, F);
                ((TECraftingSawpit) world.getTileEntity(posFL))
                        .setState(facing, FL);
                ((TECraftingSawpit) world.getTileEntity(posL))
                        .setState(facing, L);
                ((TECraftingSawpit) world.getTileEntity(posM))
                        .setState(facing, M);
                ((TECraftingSawpit) world.getTileEntity(posR))
                        .setState(facing, R);
                ((TECraftingSawpit) world.getTileEntity(posFR))
                        .setState(facing, FR);
           
                return true;
            }
            
            return false;
        }
    }
}
