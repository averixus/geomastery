/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.tileentities;

import jayavery.geomastery.utilities.EPartSingle;

/** Tile entity for knapping crafting block. */
public class TECraftingKnapping extends TECraftingAbstract<EPartSingle> {

    @Override
    public void update() {}
    
    @Override
    public boolean hasWeathering() {
        
        return false;
    }

    @Override
    protected EPartSingle partByOrdinal(int ordinal) {

        return EPartSingle.SINGLE;
    }
}
