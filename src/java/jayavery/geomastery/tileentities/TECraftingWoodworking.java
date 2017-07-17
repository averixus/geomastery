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
import jayavery.geomastery.tileentities.TECraftingWoodworking.EPartWoodworking;
import jayavery.geomastery.utilities.IMultipart;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** TileEntity for woodworking crafting block. */
public class TECraftingWoodworking extends
        TECraftingAbstract<EPartWoodworking> {

    @Override
    protected EPartWoodworking partByOrdinal(int ordinal) {
        
        return EPartWoodworking.values()[ordinal];
    }
    
    /** Enum defining parts of the whole woodworking structure. */
    public enum EPartWoodworking implements IMultipart {

        FM("fm"), FL("fl"), BL("bl"), BM("bm"), BR("br"), FR("fr");

        private final String name;

        private EPartWoodworking(String name) {

            this.name = name;
        }

        @Override
        public String getName() {

            return this.name;
        }
        
        @Override
        public boolean needsSupport() {
            
            return true;
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
            
            BlockBuildingAbstract<?> block = GeoBlocks.CRAFTING_WOODWORKING;
            boolean broken = false;
            
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
                EnumFacing facing, EntityPlayer player) {
            
            if (this == FM) {
                
                BlockContainerMulti<EPartWoodworking> block =
                        GeoBlocks.CRAFTING_WOODWORKING;
                IBlockState state = block.getDefaultState();
                PropertyEnum<EPartWoodworking> prop = block.getPartProperty();
                
                // Prepare map of properties
                
                Map<BlockPos, EPartWoodworking> map = Maps.newHashMap();
                map.put(pos, FM);
                map.put(pos.offset(facing.rotateY().getOpposite()), FL);
                map.put(pos.offset(facing.rotateY().getOpposite())
                        .offset(facing), BL);
                map.put(pos.offset(facing), BM);
                map.put(pos.offset(facing).offset(facing.rotateY()), BR);
                map.put(pos.offset(facing.rotateY()), FR);
                
                // Check validity
                
                for (Entry<BlockPos, EPartWoodworking> entry : map.entrySet()) {
                    
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
                
                map.entrySet().forEach((e) ->
                        ((TECraftingWoodworking) world.getTileEntity(e
                        .getKey())).setState(facing, e.getValue()));
                
                return true;
            }
            
            return false;
        }
    }
}
