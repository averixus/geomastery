/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.blocks;

import java.util.List;
import javax.annotation.Nullable;
import jayavery.geomastery.utilities.BlockMaterial;
import jayavery.geomastery.utilities.BlockWeight;
import jayavery.geomastery.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IExtendedBlockState;

public class BlockWallFence extends BlockWallThin {

    public BlockWallFence(BlockMaterial material, String name, float hardness,
            ToolType toolType, int sideAngle) {
        
        super(material, name, hardness, toolType, sideAngle);
    }

    @Override
    public BlockWeight getWeight() {
        
        return BlockWeight.NONE;
    }
    
    @Override
    public boolean isValid(World world, BlockPos pos) {
        
        Block blockBelow = world.getBlockState(pos.down()).getBlock();
        return super.isValid(world, pos) ||
                blockBelow instanceof BlockWallFence;
    }
    
    @Override
    public void addCollisionBoxToList(IBlockState state, World world,
            BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> list,
            @Nullable Entity entity, boolean unused) {
        
        state = this.getExtendedState(state, world, pos);
        
        if (!(state instanceof IExtendedBlockState)) {
            
            return;
        }
        
        IExtendedBlockState extState = (IExtendedBlockState) state; 
        
        addCollisionBoxToList(pos, entityBox, list, CENTRE_POST_THIN_TALL);
        
        if (extState.getValue(NORTH) != null) {
            
            addCollisionBoxToList(pos, entityBox, list, BRANCH_NORTH_THIN_TALL);
        }
        
        if (extState.getValue(EAST) != null) {
            
            addCollisionBoxToList(pos, entityBox, list, BRANCH_EAST_THIN_TALL);
        }
        
        if (extState.getValue(SOUTH) != null) {
            
            addCollisionBoxToList(pos, entityBox, list, BRANCH_SOUTH_THIN_TALL);
        }
        
        if (extState.getValue(WEST) != null) {
            
            addCollisionBoxToList(pos, entityBox, list, BRANCH_WEST_THIN_TALL);
        }
    }
}
