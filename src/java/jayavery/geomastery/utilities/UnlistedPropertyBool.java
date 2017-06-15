/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.utilities;

import net.minecraftforge.common.property.IUnlistedProperty;

public class UnlistedPropertyBool implements IUnlistedProperty<Boolean> {

    private final String name;
    
    public UnlistedPropertyBool(String name) {
        
        this.name = name;
    }
    
    @Override
    public String getName() {

        return this.name;
    }

    @Override
    public Class<Boolean> getType() {

        return Boolean.class;
    }

    @Override
    public boolean isValid(Boolean value) {

        return value != null;
    }

    @Override
    public String valueToString(Boolean value) {

        return value.toString();
    }

}
