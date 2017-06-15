/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.tileentities;

import jayavery.geomastery.blocks.BlockBuilding;
import jayavery.geomastery.blocks.BlockNew;
import jayavery.geomastery.main.GeoBlocks;
import jayavery.geomastery.main.GeoItems;
import jayavery.geomastery.tileentities.TECraftingTextiles.EnumPartTextiles;
import jayavery.geomastery.utilities.BlockWeight;
import jayavery.geomastery.utilities.IMultipart;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TECraftingTextiles extends TECraftingAbstract<EnumPartTextiles> {

    @Override
    protected EnumPartTextiles partByOrdinal(int ordinal) {
        
        return EnumPartTextiles.values()[ordinal];
    }
    
    /** Enum defining parts of the whole Textiles structure. */
    public enum EnumPartTextiles implements IMultipart {

        FRONT("front"), BACK("back");

        private final String name;

        private EnumPartTextiles(String name) {

            this.name = name;
        }

        @Override
        public String getName() {

            return this.name;
        }
        
        @Override
        public ItemStack getDrop() {
            
            if (this == FRONT) {
                
                return new ItemStack(GeoItems.CRAFTING_TEXTILES);
                
            } else {
                
                return ItemStack.EMPTY;
            }
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
            
            BlockBuilding block = GeoBlocks.CRAFTING_TEXTILES;
            Block below = world.getBlockState(pos.down()).getBlock();
            boolean broken = !BlockWeight.getWeight(below)
                    .canSupport(block.getWeight());
            
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
                EnumFacing facing) {
            
            if (this == FRONT) {
                
                BlockPos frontPos = pos;
                BlockPos backPos = frontPos.offset(facing);
                
                BlockBuilding block = GeoBlocks.CRAFTING_TEXTILES;
                BlockPos[] positions = {frontPos, backPos};
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

                    // Place all blocks
                    IBlockState placeState = block.getDefaultState();
                    
                    world.setBlockState(backPos, placeState);
                    world.setBlockState(frontPos, placeState);
                    
                    ((TECraftingTextiles) world.getTileEntity(backPos))
                            .setState(facing, BACK);
                    ((TECraftingTextiles) world.getTileEntity(frontPos))
                            .setState(facing, FRONT);
                    
                    return true;
                }
            }
            
            return false;
        }
    }
}
