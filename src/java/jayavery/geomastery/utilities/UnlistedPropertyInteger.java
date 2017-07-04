/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.utilities;

import net.minecraftforge.common.property.IUnlistedProperty;

/** Unlisted property for integer. */
public class UnlistedPropertyInteger implements IUnlistedProperty<Integer> {

    private final String name;
    
    public UnlistedPropertyInteger(String name) {
        
        this.name = name;
    }
    
    @Override
    public String getName() {

        return this.name;
    }

    @Override
    public boolean isValid(Integer value) {

        return value < 8;
    }

    @Override
    public Class<Integer> getType() {

        return Integer.class;
    }

    @Override
    public String valueToString(Integer value) {

        return value.toString();
    }

}
