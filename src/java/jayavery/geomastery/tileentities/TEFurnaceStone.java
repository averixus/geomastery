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
import jayavery.geomastery.main.GeoRecipes;
import jayavery.geomastery.tileentities.TEFurnaceStone.EPartStone;
import jayavery.geomastery.utilities.IMultipart;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** TileEntity for stone furnace blocks. */
public class TEFurnaceStone extends TEFurnaceAbstract<EPartStone> {

    public TEFurnaceStone() {

        super(GeoRecipes.STONE_ALL, 6);
    }
    
    @Override
    public EPartStone partByOrdinal(int ordinal) {
        
        return EPartStone.values()[ordinal];
    }

    /** Enum defining parts of the whole stone furnace structure. */
    public enum EPartStone implements IMultipart {

        BL("bl"), BM("bm"), BR("br"), TL("tl"), TM("tm"), TR("tr");

        private final String name;

        private EPartStone(String name) {

            this.name = name;
        }

        @Override
        public String getName() {

            return this.name;
        }
        
        @Override
        public boolean needsSupport() {
            
            switch (this) {
                
                case BL:
                case BM:
                case BR:
                    return true;
                case TL:
                case TM:
                case TR:
                default:
                    return false;
            }
        }
        
        @Override
        public BlockPos getMaster(BlockPos pos, EnumFacing facing) {
            
            switch (this) {

                case BM:
                    return pos.offset(facing.rotateY().getOpposite());
                case BR:
                    return pos.offset(facing.rotateY().getOpposite(), 2);
                case TL:
                    return pos.down();
                case TM: 
                    return pos.offset(facing.rotateY().getOpposite()).down();
                case TR: 
                    return pos.offset(facing.rotateY().getOpposite(), 2).down();
                case BL:
                default: 
                    return pos;
            }
        }
        
        @Override
        public boolean shouldBreak(World world, BlockPos pos,
                EnumFacing facing) {
            
            BlockBuildingAbstract<?> block = GeoBlocks.FURNACE_STONE;
            boolean broken = false;
            
            switch (this) {
                
                case BM: {

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

                case TM: {

                    broken |= world.getBlockState(pos.offset(facing.rotateY()
                            .getOpposite())).getBlock() != block;
                    break;
                }

                case TL: {

                    broken |= world.getBlockState(pos.down()).getBlock()
                            != block;
                    break;
                }

                case BL: {

                    broken |= world.getBlockState(pos.offset(facing.rotateY()))
                            .getBlock() != block;
                    break;
                }
            }
            
            return broken;
        }
        
        @Override
        public AxisAlignedBB getBoundingBox(EnumFacing facing) {
            
            switch (this) {

                case TR: 
                case TM: 
                case TL: 
                    return BlockNew.TWELVE;
                    
                case BR: 
                case BM:             
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
                EnumFacing facing, EntityPlayer player) {
            
            if (this == BM) {
                
                BlockContainerMulti<EPartStone> block = GeoBlocks.FURNACE_STONE;
                IBlockState state = block.getDefaultState();
                PropertyEnum<EPartStone> prop = block.getPartProperty();
                
                // Prepare map of properties
                
                Map<BlockPos, EPartStone> map = Maps.newHashMap();
                map.put(pos, BM);
                map.put(pos.offset(facing.rotateY().getOpposite()), BL);
                map.put(pos.offset(facing.rotateY()), BR);
                map.put(pos.offset(facing.rotateY().getOpposite()).up(), TL);
                map.put(pos.up(), TM);
                map.put(pos.offset(facing.rotateY()).up(), TR);
                
                // Check validity
                
                for (Entry<BlockPos, EPartStone> entry : map.entrySet()) {
                    
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
                        ((TEFurnaceStone) world.getTileEntity(e.getKey()))
                        .setState(facing, e.getValue()));
                
                return true;
            }
            
            return false;
        }
    }
}
