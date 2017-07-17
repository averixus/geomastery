/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.blocks;

import java.util.List;
import com.google.common.collect.Lists;
import jayavery.geomastery.blocks.BlockDoor.EVaultAbove;
import jayavery.geomastery.utilities.BlockMaterial;
import jayavery.geomastery.utilities.EBlockWeight;
import jayavery.geomastery.utilities.EToolType;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Window block. */
public class BlockWindow extends BlockFacing {

    public static final PropertyEnum<EVaultAbove> VAULT =
            PropertyEnum.create("vault", EVaultAbove.class);
    
    public BlockWindow() {
        
        super("window", BlockMaterial.WOOD_FURNITURE,
                2F, 4, EBlockWeight.LIGHT);
    }
    
    @Override
    public boolean shouldConnect(IBlockAccess world, IBlockState state,
            BlockPos pos, EnumFacing direction) {
        
        EnumFacing facing = state.getValue(FACING);
        return facing != direction && facing != direction.getOpposite();
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world,
            BlockPos pos) {
        
        int facing = state.getValue(FACING).getHorizontalIndex();
        return DOOR_CLOSED[(facing + 2) % 4];
    }
    
    @Override
    public IBlockState getActualState(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        EnumFacing facing = state.getValue(FACING);
        BlockPos posUp = pos.up();
        IBlockState upState = world.getBlockState(posUp);
        EVaultAbove vault = EVaultAbove.NONE;
        
        if (upState.getBlock() instanceof BlockVault) {
            
            EnumFacing upFacing = upState.getActualState(world, posUp)
                    .getValue(BlockVault.FACING);
            
            if (upFacing == facing.rotateY()) {
                
                vault = EVaultAbove.LEFT;
                
            } else if (upFacing == facing.rotateYCCW()) {
                
                vault = EVaultAbove.RIGHT;
            }
        }
        
        return state.withProperty(VAULT, vault);
    }
    
    @Override
    public BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this, FACING, VAULT);
    }
}
