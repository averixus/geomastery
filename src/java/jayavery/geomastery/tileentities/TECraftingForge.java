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
import jayavery.geomastery.tileentities.TECraftingForge.EPartForge;
import jayavery.geomastery.utilities.IMultipart;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** TileEntity for forge crafting block. */
public class TECraftingForge extends TECraftingAbstract<EPartForge> {

    @Override
    protected EPartForge partByOrdinal(int ordinal) {
        
        return EPartForge.values()[ordinal];
    }

    /** Enum defining parts of the whole forge structure. */
    public enum EPartForge implements IMultipart {

        FM("fm"), FL("fl"), BL("bl"), BM("bm"), BR("br"), FR("fr");

        private String name;

        private EPartForge(String name) {

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
            
            BlockBuildingAbstract<?> block = GeoBlocks.CRAFTING_FORGE;
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
                EnumFacing facing, EntityPlayer player) {
            
            if (this == FM) {
                
                BlockContainerMulti<EPartForge> block =
                        GeoBlocks.CRAFTING_FORGE;
                IBlockState state = block.getDefaultState();
                PropertyEnum<EPartForge> prop = block.getPartProperty();
                
                // Prepare map of properties
                
                Map<BlockPos, EPartForge> map = Maps.newHashMap();
                map.put(pos, FM);
                map.put(pos.offset(facing.rotateY().getOpposite()), FL);
                map.put(pos.offset(facing.rotateY().getOpposite())
                        .offset(facing), BL);
                map.put(pos.offset(facing), BM);
                map.put(pos.offset(facing).offset(facing.rotateY()), BR);
                map.put(pos.offset(facing.rotateY()), FR);
                
                // Check validity
                
                for (Entry<BlockPos, EPartForge> entry : map.entrySet()) {
                    
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
                        ((TECraftingForge) world.getTileEntity(e.getKey()))
                        .setState(facing, e.getValue()));
                
                return true;
            }
            
            return false;
        }
    }
}
