/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.tileentities;

import jayavery.geomastery.main.GeoRecipes;

/** TileEntity for pot fire furnace. */
public class TEFurnacePotfire extends TEFurnaceSingleAbstract {

    public TEFurnacePotfire() {

        super(GeoRecipes.COOKFIRE);
    }
}
