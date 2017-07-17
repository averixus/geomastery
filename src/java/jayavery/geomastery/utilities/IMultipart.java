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
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Implemented for enums which define multipart structures. */
public interface IMultipart extends IStringSerializable {
    
    /** @return Whether this is still part of a valid multipart structure. */
    public boolean shouldBreak(World world, BlockPos pos, EnumFacing facing);
    /** @return Whether this part needs support other than its own blocks. */
    public boolean needsSupport();
    /** @return The position of the master for this structure. */
    public BlockPos getMaster(BlockPos pos, EnumFacing facing);
    /** @return The selection bounding box of this part. */
    public AxisAlignedBB getBoundingBox(EnumFacing facing);
    /** @return The collision bounding box of this part. */
    public AxisAlignedBB getCollisionBox(EnumFacing facing);
    /** Attempts to build the structure starting from this part.
     * @return Whether the structure was successfully built. */
    public boolean buildStructure(World world, BlockPos pos,
            EnumFacing facing, EntityPlayer player);
}
