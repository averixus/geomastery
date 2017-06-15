/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.blocks;

import java.util.Random;
import jayavery.geomastery.main.GeoItems;
import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

/** Antler block. */
public class BlockAntler extends BlockBush {

    public BlockAntler() {

        BlockNew.setupBlock(this, "antler", null, 0, null);
    }
    
    @Override
    public Item getItemDropped(IBlockState state,
            Random rand, int fortune) {

        return GeoItems.SHOVEL_ANTLER;
    }
}
