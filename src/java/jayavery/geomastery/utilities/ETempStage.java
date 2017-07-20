/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.utilities;

import jayavery.geomastery.main.Geomastery;
import net.minecraft.util.ResourceLocation;

/** Enum defining stages of temperature and associated icons. */
public enum ETempStage {
    
    COLD("cold"), COOL("cool"), OK("ok"), WARM("warm"), HOT("hot");
    
    /** Texture of this icon. */
    private final ResourceLocation res;
    
    private ETempStage(String name) {
        
        this.res = new ResourceLocation(Geomastery.MODID,
                "textures/gui/temp_" + name + ".png");
    } 
    
    /** @return The ResourceLocation of the icon for this Stage. */
    public ResourceLocation toResourceLocation() {
        
        return this.res;
    }
    
    /** @return The stage the given temperature belongs to. */
    public static ETempStage fromTemp(float temp) {
        
        return temp <= 0 ? COLD : temp <= 1.5 ? COOL : temp <= 3.5 ?
                OK : temp <=5 ? WARM : HOT;
    }
}
