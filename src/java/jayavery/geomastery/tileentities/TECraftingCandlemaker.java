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
import jayavery.geomastery.tileentities.TECraftingCandlemaker.EPartCandlemaker;
import jayavery.geomastery.utilities.IMultipart;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** TileEntity for candlemaker crafting block. */
public class TECraftingCandlemaker extends
        TECraftingAbstract<EPartCandlemaker> {
    
    @Override
    protected EPartCandlemaker partByOrdinal(int ordinal) {
        
        return EPartCandlemaker.values()[ordinal];
    }
    
    /** Enum defining parts of the whole Candlemaker structure. */
    public enum EPartCandlemaker implements IMultipart {

        FRONT("front"), BACK("back");

        private final String name;

        private EPartCandlemaker(String name) {

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
            
            BlockBuildingAbstract<?> block = GeoBlocks.CRAFTING_CANDLEMAKER;
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
            
            return this == BACK ? BlockNew.TWELVE : BlockNew.CENTRE_FOUR;
        }
        
        @Override
        public AxisAlignedBB getCollisionBox(EnumFacing facing) {
            
            return this == BACK ? BlockNew.TWELVE : Block.NULL_AABB;
        }
        
        @Override
        public boolean buildStructure(World world, BlockPos pos,
                EnumFacing facing, EntityPlayer player) {

            if (this == FRONT) {
                
                BlockContainerMulti<EPartCandlemaker> block =
                        GeoBlocks.CRAFTING_CANDLEMAKER;
                IBlockState state = block.getDefaultState();
                PropertyEnum<EPartCandlemaker> prop = block.getPartProperty();
                
                // Prepare map of properties
                
                Map<BlockPos, EPartCandlemaker> map = Maps.newHashMap();
                map.put(pos, FRONT);
                map.put(pos.offset(facing), BACK);
                
                // Check validity
                
                for (Entry<BlockPos, EPartCandlemaker> entry : map.entrySet()) {
                    
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
                        ((TECraftingCandlemaker) world.getTileEntity(e
                        .getKey())).setState(facing, e.getValue()));
                
                return true;
            }
            
            return false;
        }
    }
}
