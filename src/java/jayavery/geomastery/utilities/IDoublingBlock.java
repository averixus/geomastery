/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.utilities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;

/** Building blocks with two possible thicknesses. */
public interface IDoublingBlock {

    /** @return Whether this block is double. */
    public boolean isDouble();
    /** @return Whether this block should be doubled in the given state
     * when clicked with its item on the given side. */
    public boolean shouldDouble(IBlockState state, EnumFacing side);
}
