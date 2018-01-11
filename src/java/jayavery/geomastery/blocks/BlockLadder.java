/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.blocks;

import jayavery.geomastery.items.ItemPlacing;
import jayavery.geomastery.utilities.BlockMaterial;
import jayavery.geomastery.utilities.EBlockWeight;
import jayavery.geomastery.utilities.Lang;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLadder extends BlockFacing {

    private static final AxisAlignedBB LADDER_WEST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.1875D, 1.0D, 1.0D);
    private static final AxisAlignedBB LADDER_EAST_AABB = new AxisAlignedBB(0.8125D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    private static final AxisAlignedBB LADDER_NORTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.1875D);
    private static final AxisAlignedBB LADDER_SOUTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.8125D, 1.0D, 1.0D, 1.0D);

    public BlockLadder() {
        
        super("ladder", BlockMaterial.WOOD_FURNITURE,
                0.4F, 10, EBlockWeight.NONE);
    }
    
    @Override
    public ItemPlacing.Building createItem(int stackSize) {
        
        return new ItemPlacing.Building(this, stackSize);
    }
    
    @Override
    public boolean isValid(World world, BlockPos pos, ItemStack stack,
            boolean alreadyPresent, IBlockState setState, EntityPlayer player) {
        
        if (!alreadyPresent && !world.getBlockState(pos).getBlock()
                .isReplaceable(world, pos)) {
            
            message(player, Lang.BUILDFAIL_OBSTACLE);
            return false;
        }
        
        IBlockState stateBelow = world.getBlockState(pos.down());
        EBlockWeight weightBelow = EBlockWeight.getWeight(stateBelow);
        
        if (!weightBelow.canSupport(this.getWeight(stateBelow)) &&
                !(stateBelow.getBlock() instanceof BlockLadder)) {
            
            message(player, Lang.BUILDFAIL_SUPPORT);
            return false;
        }
        
        IBlockState stateSupport = world.getBlockState(pos
                .offset(setState.getValue(FACING)));
        EBlockWeight weightSupport = EBlockWeight.getWeight(stateSupport);
        
        if (!weightSupport.canSupport(this.getWeight(stateSupport))) {
            
            message(player, Lang.BUILDFAIL_LADDER);
            return false;
        }
        
        return true;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        switch (state.getValue(FACING)) {
            
            case NORTH:
                return LADDER_NORTH_AABB;
            case SOUTH:
                return LADDER_SOUTH_AABB;
            case WEST:
                return LADDER_WEST_AABB;
            case EAST: default:
                return LADDER_EAST_AABB;
        }
    }
    
    @Override
    public boolean isLadder(IBlockState state, IBlockAccess world,
            BlockPos pos, EntityLivingBase entity) {
        
        return true;
    }
}
