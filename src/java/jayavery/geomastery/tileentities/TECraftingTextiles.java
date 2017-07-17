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
import jayavery.geomastery.tileentities.TECraftingTextiles.EPartTextiles;
import jayavery.geomastery.utilities.IMultipart;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** TileEntity for textiles crafting block. */
public class TECraftingTextiles extends TECraftingAbstract<EPartTextiles> {

    @Override
    protected EPartTextiles partByOrdinal(int ordinal) {
        
        return EPartTextiles.values()[ordinal];
    }
    
    /** Enum defining parts of the whole Textiles structure. */
    public enum EPartTextiles implements IMultipart {

        FRONT("front"), BACK("back");

        private final String name;

        private EPartTextiles(String name) {

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
            
            if (this == FRONT) {
                
                return pos;
                
            } else {
                
                return pos.offset(facing.getOpposite());
            }
        }
        
        @Override
        public boolean shouldBreak(World world, BlockPos pos,
                EnumFacing facing) {
            
            BlockBuildingAbstract<?> block = GeoBlocks.CRAFTING_TEXTILES;
            boolean broken = false;
            
            if (this == FRONT) {

                broken |= world.getBlockState(pos.offset(facing)).getBlock()
                        != block;
                
            } else {

                broken |= world.getBlockState(pos.offset(facing.getOpposite()))
                        .getBlock() != block;
            }
            
            return broken;
        }
        
        @Override
        public AxisAlignedBB getBoundingBox(EnumFacing facing) {
                        
            return this == BACK ? Block.FULL_BLOCK_AABB :
                BlockNew.HALF[facing.getHorizontalIndex()];
        }
        
        @Override
        public AxisAlignedBB getCollisionBox(EnumFacing facing) {
            
            return this == BACK ? Block.FULL_BLOCK_AABB : Block.NULL_AABB;
        }
        
        @Override
        public boolean buildStructure(World world, BlockPos pos,
                EnumFacing facing, EntityPlayer player) {
            
            if (this == FRONT) {
                
                BlockContainerMulti<EPartTextiles> block = 
                        GeoBlocks.CRAFTING_TEXTILES;
                IBlockState state = block.getDefaultState();
                PropertyEnum<EPartTextiles> prop = block.getPartProperty();
                
                // Prepare map of properties
                
                Map<BlockPos, EPartTextiles> map = Maps.newHashMap();
                map.put(pos, FRONT);
                map.put(pos.offset(facing), BACK);
                
                // Check validity
                
                for (Entry<BlockPos, EPartTextiles> entry : map.entrySet()) {
                    
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
                        ((TECraftingTextiles) world.getTileEntity(e.getKey()))
                        .setState(facing, e.getValue()));
                
                return true;
            }
            
            return false;
        }
    }
}
