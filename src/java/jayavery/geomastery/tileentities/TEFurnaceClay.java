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
import jayavery.geomastery.tileentities.TECraftingArmourer.EPartArmourer;
import jayavery.geomastery.tileentities.TEFurnaceClay.EPartClay;
import jayavery.geomastery.utilities.IMultipart;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** TileEntity for clay furnace. */
public class TEFurnaceClay extends TEFurnaceAbstract<EPartClay> {

    public TEFurnaceClay() {

        super(GeoRecipes.CLAY_ALL, 4);
    }
    
    @Override
    protected EPartClay partByOrdinal(int ordinal) {

        return EPartClay.values()[ordinal];
    }

    /** Enum defining parts of the clay furnace structure. */
    public enum EPartClay implements IMultipart {

        BL("bl"), BR("br"), TL("tl"), TR("tr");

        private final String name;

        private EPartClay(String name) {

            this.name = name;
        }

        @Override
        public String getName() {

            return this.name;
        }
        
        @Override
        public boolean needsSupport() {
            
            return this == BL || this == BR;
        }
        
        @Override
        public BlockPos getMaster(BlockPos pos, EnumFacing facing) {
            
            switch (this) {

                case BR: 
                    return pos.offset(facing.rotateY().getOpposite());
                case TL: 
                    return pos.down();
                case TR: 
                    return pos.offset(facing.rotateY().getOpposite()).down();
                case BL: default: 
                    return pos;
            }
        }
        
        @Override
        public boolean shouldBreak(World world, BlockPos pos,
                EnumFacing facing) {
            
            BlockBuildingAbstract<?> block = GeoBlocks.FURNACE_CLAY;
            
            switch (this) {
                
                case BL: 
                    return world.getBlockState(pos.offset(facing.rotateY()))
                            .getBlock() != block;
                case BR: 
                    return world.getBlockState(pos.up()).getBlock() != block;
                case TR:
                    return world.getBlockState(pos.offset(facing.rotateY()
                            .getOpposite())).getBlock() != block;
                case TL:
                    return world.getBlockState(pos.down()).getBlock()
                            != block;
                default:
                    return false;
            }
        }
        
        @Override
        public AxisAlignedBB getBoundingBox(EnumFacing facing) {
            
            switch (this) {

                case TR: case TL: 
                    return BlockNew.EIGHT;
                case BR: case BL: default: 
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
            
            if (this != BL) {
                
                return false;
            }
                
            BlockContainerMulti<EPartClay> block = GeoBlocks.FURNACE_CLAY;
            IBlockState state = block.getDefaultState();
            PropertyEnum<EPartClay> prop = block.getPartProperty();
            
            // Prepare map of properties
            
            Map<BlockPos, EPartClay> map = Maps.newHashMap();
            map.put(pos, BL);
            map.put(pos.offset(facing.rotateY()), BR);
            map.put(pos.up(), TL);
            map.put(pos.offset(facing.rotateY()).up(), TR);
            
            // Check validity
            
            for (Entry<BlockPos, EPartClay> entry : map.entrySet()) {
                
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
            
            map.entrySet().forEach((e) -> ((TEFurnaceClay) world
                    .getTileEntity(e.getKey())).setState(facing, e.getValue()));
            
            return true;
        }
    }
}
