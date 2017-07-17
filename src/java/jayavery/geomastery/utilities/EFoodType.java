/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.utilities;

/** Enum defining types of food. */
public enum EFoodType {
    
    CARBS(Lang.FOODTIP_CARBS),
    PROTEIN(Lang.FOODTIP_PROTEIN),
    FRUITVEG(Lang.FOODTIP_FRUITVEG);
    
    private final String tip;
    
    private EFoodType(String tip) {
        
        this.tip = tip;
    }
    
    public String tip() {
        
        return this.tip;
    }
}
