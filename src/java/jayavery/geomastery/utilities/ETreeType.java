/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.utilities;

import net.minecraft.util.IStringSerializable;

// TEST
public enum ETreeType implements IStringSerializable {
    
    OAK("oak"), BIRCH("birch"), LARCH("larch"), SPRUCE("spruce"), PINE("pine"),
    WILLOW("willow"), BEECH("beech"), EBONY("ebony"), BRAZIL("brazil"), MAHOGANY("mahogany"), ACACIA("acacia");
    
    private final String name;

    private ETreeType(String name) {
        
        this.name = name;
    }

    @Override
    public String getName() {

        return this.name;
    }
}
