/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.utilities;

import jayavery.geomastery.blocks.BlockWall;
import net.minecraftforge.common.property.IUnlistedProperty;

/** Unlisted property for a BlockWall type. */
public class UnlistedPropertyWall implements IUnlistedProperty<BlockWall> {

    private final String name;
    
    public UnlistedPropertyWall(String name) {
        
        this.name = name;
    }
    @Override
    public String getName() {

        return this.name;
    }

    @Override
    public boolean isValid(BlockWall value) {

        return true;
    }

    @Override
    public Class<BlockWall> getType() {

        return BlockWall.class;
    }

    @Override
    public String valueToString(BlockWall value) {

        return value.getRegistryName().toString();
    } 
}
