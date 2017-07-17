/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.utilities;

import java.util.List;
import net.minecraft.item.ItemStack;

/** Interface for TEs which need to drop items when block broken. */
public interface IItemStorage {

    /** Drops any items stored in this TileEntity. */
    public abstract List<ItemStack> getDrops();
}
