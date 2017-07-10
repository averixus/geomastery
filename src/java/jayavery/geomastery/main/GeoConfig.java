/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.main;

import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class GeoConfig {
    
    public static final String CATEGORY = "General";
    public static final String CONFIG = "geomastery.config.";
    public static final String FOOD_TOOLTIPS = "foodTooltips";
    public static final String FOOD_DURABILITY = "foodDurability";
    public static final String CROP_BIOMES = "cropBiomes";

    public static boolean foodTooltips;
    public static boolean foodDurability;
    public static boolean cropBiomes;

    public static void syncConfig(Configuration config) {
        
        Geomastery.LOG.info("Reading config settings");
        
        foodTooltips = config.get(CATEGORY, FOOD_TOOLTIPS, true,
                I18n.translateToLocal(CONFIG + FOOD_TOOLTIPS)).getBoolean();
        foodDurability = config.get(CATEGORY, FOOD_DURABILITY, false,
                I18n.translateToLocal(CONFIG + FOOD_DURABILITY)).getBoolean();
        cropBiomes = config.get(CATEGORY, CROP_BIOMES, true,
                I18n.translateToLocal(CONFIG + CROP_BIOMES)).getBoolean();
        
        if (config.hasChanged()) {

            config.save();
        }
    }
}
