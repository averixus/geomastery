/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.utilities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** 'null' implementation of IMultipart for single blocks. */
public enum EPartSingle implements IMultipart {
    
    SINGLE;

    @Override
    public String getName() {

        return "single";
    }
    
    @Override
    public boolean needsSupport() {
        
        return true;
    }

    @Override
    public boolean shouldBreak(World world, BlockPos pos, EnumFacing facing) {
        
        return false;
    }

    @Override
    public BlockPos getMaster(BlockPos pos, EnumFacing facing) {

        return pos;
    }

    @Override
    public AxisAlignedBB getBoundingBox(EnumFacing facing) {
        // never called
        return null;
    }

    @Override
    public AxisAlignedBB getCollisionBox(EnumFacing facing) {
        // never called
        return null;
    }

    @Override
    public boolean buildStructure(World world, BlockPos pos,
            EnumFacing facing, EntityPlayer player) {
        // never called
        return false;
    }
}
