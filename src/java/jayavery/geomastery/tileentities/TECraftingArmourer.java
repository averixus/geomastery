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
import jayavery.geomastery.blocks.BlockBuildingAbstract;
import jayavery.geomastery.blocks.BlockContainerMulti;
import jayavery.geomastery.blocks.BlockNew;
import jayavery.geomastery.main.GeoBlocks;
import jayavery.geomastery.tileentities.TECraftingArmourer.EPartArmourer;
import jayavery.geomastery.utilities.IMultipart;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** TileEntity for armourer crafting block. */
public class TECraftingArmourer extends TECraftingAbstract<EPartArmourer> {
    
    @Override
    protected EPartArmourer partByOrdinal(int ordinal) {

        return EPartArmourer.values()[ordinal];
    }
    
    /** Enum defining parts of the whole Armourer structure. */
    public enum EPartArmourer implements IMultipart {
        
        T("t"), L("l"), M("m"), R("r");
        
        private final String name;
        
        private EPartArmourer(String name) {
            
            this.name = name;
        }
        
        @Override
        public String getName() {
            
            return this.name;
        }
        
        @Override
        public boolean needsSupport() {
            
            return this != T;
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
                case T: default:
                    return pos;
            }
        }
        
        @Override
        public boolean shouldBreak(World world, BlockPos pos,
                EnumFacing facing) {
            
            BlockBuildingAbstract<?> block = GeoBlocks.CRAFTING_ARMOURER;
            boolean broken = false;
            
            switch (this) {
                
                case T: {
                    
                    broken |= world.getBlockState(pos.down())
                            .getBlock() != block;
                    break;
                }
                
                case L: {
                    
                    boolean brokenT = world.getBlockState(pos.up())
                            .getBlock() != block;
                    boolean brokenM = world.getBlockState(pos
                            .offset(facing.rotateY())).getBlock() != block;
                    
                    broken |= brokenM || brokenT;
                    break;
                }
                
                case M: {
                    
                    boolean brokenL = world.getBlockState(pos
                            .offset(facing.rotateYCCW())).getBlock() != block;
                    boolean brokenR = world.getBlockState(pos
                            .offset(facing.rotateY())).getBlock() != block;
                    
                    broken |= brokenL || brokenR;
                    break;
                }
                
                case R: {
                    
                    broken |= world.getBlockState(pos
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
                case M: default: 
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
                case L: case T: 
                    return Block.NULL_AABB;
                default: 
                    return Block.FULL_BLOCK_AABB;
            }
        }
        
        @Override
        public boolean buildStructure(World world, BlockPos pos,
                EnumFacing facing, EntityPlayer player) {
            
            if (this != M) {
                
                return false;
            }
                
            BlockContainerMulti<EPartArmourer> block =
                    GeoBlocks.CRAFTING_ARMOURER;
            IBlockState state = block.getDefaultState();
            PropertyEnum<EPartArmourer> prop = block.getPartProperty();
            
            // Prepare map of properties
            
            Map<BlockPos, EPartArmourer> map = Maps.newHashMap();
            map.put(pos, M);
            map.put(pos.offset(facing.rotateYCCW()), L);
            map.put(pos.offset(facing.rotateYCCW()).up(), T);
            map.put(pos.offset(facing.rotateY()), R);
            
            // Check validity
            
            for (Entry<BlockPos, EPartArmourer> entry : map.entrySet()) {
                
                IBlockState placeState = state
                        .withProperty(prop, entry.getValue());
                
                if (!block.isValid(world, entry.getKey(), null,
                        false, placeState, player)) {
                    
                    return false;
                }
            }

            // Place all
            
            map.keySet().forEach((p) -> world.setBlockState(p, state));
            
            // Set up tileentities
            
            map.entrySet().forEach((e) -> ((TECraftingArmourer) world
                    .getTileEntity(e.getKey())).setState(facing, e.getValue()));

            
            return true;
        }
    }
}
