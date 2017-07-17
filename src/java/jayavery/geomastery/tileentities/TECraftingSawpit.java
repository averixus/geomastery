/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.tileentities;

import java.util.Map;
import java.util.Map.Entry;
import com.google.common.collect.Maps;
import jayavery.geomastery.blocks.BlockContainerMulti;
import jayavery.geomastery.blocks.BlockNew;
import jayavery.geomastery.blocks.BlockSawpit;
import jayavery.geomastery.main.GeoBlocks;
import jayavery.geomastery.tileentities.TECraftingArmourer.EPartArmourer;
import jayavery.geomastery.tileentities.TECraftingSawpit.EPartSawpit;
import jayavery.geomastery.utilities.IMultipart;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** TileEntity for sawpit crafting block. */
public class TECraftingSawpit extends TECraftingAbstract<EPartSawpit> {
    
    @Override
    protected EPartSawpit partByOrdinal(int ordinal) {
        
        return EPartSawpit.values()[ordinal];
    }
    
    @Override
    public boolean hasDurability() {
        
        return false;
    }
    
    @Override
    public void update() {}
    
    /** Enum defining parts of the whole sawpit structure. */
    public enum EPartSawpit implements IMultipart {
        
        FL("fl"), L("l"), M("m"), R("r"), FR("fr"), F("f");
        
        private final String name;
                
        private EPartSawpit(String name) {
            
            this.name = name;
        }
        
        @Override
        public boolean needsSupport() {
            
            return this == F;
        }

        @Override
        public String getName() {

            return this.name;
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
            BlockSawpit block = GeoBlocks.CRAFTING_SAWPIT;
            
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
                    boolean fsValid = block.isSupport(world,
                            pos.offset(facing.getOpposite()).down(), null);
                    boolean bsValid = block.isSupport(world,
                            pos.offset(facing).down(), null);
                    boolean hasSpace = block.isSpace(world, pos.down(), null) &&
                            block.isSpace(world, pos.down(2), null);
                    
                    broken = brokenL || brokenF || !fsValid ||
                            !bsValid || !hasSpace;
                    break;
                }
                
                case L: {
                    
                    boolean brokenFL = world.getBlockState(pos
                            .offset(facing.rotateYCCW())).getBlock() != block;
                    boolean brokenM = world.getBlockState(pos
                            .offset(facing.rotateY())).getBlock() != block;
                    boolean hasSpace = block.isSpace(world, pos.down(), null) &&
                            block.isSpace(world, pos.down(2), null);
                    broken = brokenFL || brokenM || !hasSpace;
                    break;
                }
                
                case M: {
                    
                    boolean brokenL = world.getBlockState(pos
                            .offset(facing.rotateYCCW())).getBlock() != block;
                    boolean brokenR = world.getBlockState(pos
                            .offset(facing.rotateY())).getBlock() != block; 
                    boolean hasSpace = block.isSpace(world, pos.down(), null) &&
                            block.isSpace(world, pos.down(2), null);
                    broken = brokenL || brokenR || !hasSpace;
                    break;
                }
                
                case R: {
                    
                    boolean brokenM = world.getBlockState(pos
                            .offset(facing.rotateYCCW())).getBlock() != block;
                    boolean brokenFR = world.getBlockState(pos
                            .offset(facing.rotateY())).getBlock() != block;
                    boolean hasSpace = block.isSpace(world, pos.down(), null) &&
                            block.isSpace(world, pos.down(2), null);
                    broken = brokenM || brokenFR || !hasSpace;
                    break;
                }
                
                case FR: {
                    
                    boolean brokenR = world.getBlockState(pos.offset(facing
                            .rotateYCCW())).getBlock() != block;
                    boolean fsValid = block.isSupport(world,
                            pos.offset(facing.getOpposite()).down(), null);
                    boolean bsValid = block.isSupport(world, pos
                            .offset(facing).down(), null);
                    boolean hasSpace = block.isSpace(world, pos.down(), null) &&
                            block.isSpace(world, pos.down(2), null);
                    
                    broken = brokenR || !fsValid || !bsValid || !hasSpace;
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
                EnumFacing facing, EntityPlayer player) {
            
            if (this == F) {
                
                BlockContainerMulti<EPartSawpit> block =
                        GeoBlocks.CRAFTING_SAWPIT;
                IBlockState state = block.getDefaultState();
                PropertyEnum<EPartSawpit> prop = block.getPartProperty();
                
                // Prepare map of properties
                
                Map<BlockPos, EPartSawpit> map = Maps.newHashMap();
                map.put(pos, F);
                map.put(pos.offset(facing), FL);
                map.put(pos.offset(facing).offset(facing.rotateY()), L);
                map.put(pos.offset(facing).offset(facing.rotateY(), 2), M);
                map.put(pos.offset(facing).offset(facing.rotateY(), 3), R);
                map.put(pos.offset(facing).offset(facing.rotateY(), 4), FR);
                
                // Check validity
                
                for (Entry<BlockPos, EPartSawpit> entry : map.entrySet()) {
                    
                    IBlockState placeState = state
                            .withProperty(prop, entry.getValue());
                    
                    if (!block.isValid(world, entry.getKey(), null,
                            false, placeState, player)) {
                        
                        return false;
                    }
                }

                
                BlockSawpit placeBlock = GeoBlocks.CRAFTING_SAWPIT;
                IBlockState placeState = placeBlock.getDefaultState();
                
                BlockPos posF = pos;
                BlockPos posFL = pos.offset(facing);
                BlockPos posL = pos.offset(facing).offset(facing.rotateY());
                BlockPos posM = pos.offset(facing).offset(facing.rotateY(), 2);
                BlockPos posR = pos.offset(facing).offset(facing.rotateY(), 3);
                BlockPos posFR = pos.offset(facing).offset(facing.rotateY(), 4);
                
                BlockPos supportFL = posFL.offset(facing.getOpposite());
                BlockPos supportBL = posFL.offset(facing);
                BlockPos supportFR = posFR.offset(facing.getOpposite());
                BlockPos supportBR = posFR.offset(facing);
                BlockPos[] allSupports = {supportFL.down(), supportBL.down(),
                        supportFR.down(), supportBR.down()};
                
                BlockPos[] allAir = {posFL.down(), posFL.down(2), posL.down(),
                        posL.down(2), posM.down(), posM.down(2), posR.down(),
                        posR.down(2), posFR.down(), posFR.down(2)};
                
                // Check supports
                
                for (BlockPos support : allSupports) {
                    
                    if (!placeBlock.isSupport(world, support, player)) {

                        return false;
                    }
                }
                
                // Check air
                
                for (BlockPos air : allAir) {
                    
                    if (!placeBlock.isSpace(world, air, player)) {
                        
                        return false;
                    }
                }

                // Place all
                
                map.keySet().forEach((p) -> world.setBlockState(p, state));
                
                // Set up tileentities
                
                map.entrySet().forEach((e) ->
                        ((TECraftingSawpit) world.getTileEntity(e.getKey()))
                        .setState(facing, e.getValue()));
           
                return true;
            }
            
            return false;
        }
    }
}
