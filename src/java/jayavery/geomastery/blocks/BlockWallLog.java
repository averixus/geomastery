/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.blocks;

import java.util.List;
import javax.annotation.Nullable;
import jayavery.geomastery.items.ItemPlacing;
import jayavery.geomastery.utilities.BlockMaterial;
import jayavery.geomastery.utilities.EBlockWeight;
import jayavery.geomastery.utilities.EToolType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IExtendedBlockState;

/** Crossing log wall block. */
public class BlockWallLog extends BlockWall {
        
    public BlockWallLog() {
        
        super(BlockMaterial.WOOD_FURNITURE, "wall_log", 1F, 0, 4);
    }
    
    @Override
    public ItemPlacing.Building createItem(int stackSize) {
        
        return new ItemPlacing.Building(this, stackSize);
    }
    
    @Override
    public EBlockWeight getWeight(IBlockState state) {
        
        return EBlockWeight.MEDIUM;
    }

    @Override
    public boolean shouldDouble(IBlockState state, EnumFacing facing) {
        
        return false;
    }

    @Override
    public boolean isDouble(IBlockState state) {
        
        return false;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world,
            BlockPos pos) {
    
        return CENTRE_POST;
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
        addCollisionBoxToList(pos, entityBox, list, CENTRE_POST_THIN);
        
        if (extState.getValue(NORTH) != null ||
                extState.getValue(SOUTH) != null) {
            
            addCollisionBoxToList(pos, entityBox, list, BRANCH_NORTH_THIN);
            addCollisionBoxToList(pos, entityBox, list, BRANCH_SOUTH_THIN);
        }
        
        if (extState.getValue(EAST) != null ||
                extState.getValue(WEST) != null) {
            
            addCollisionBoxToList(pos, entityBox, list, BRANCH_EAST_THIN);
            addCollisionBoxToList(pos, entityBox, list, BRANCH_WEST_THIN);
        }
    }
}
