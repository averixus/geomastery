/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.main;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;

@Config(modid = Geomastery.MODID)
public class GeoConfig {

    @Comment("Show the food type in the item tooltip")
    @LangKey("geomastery:config.foodTooltips")
    public static boolean foodTooltips = true;

    @Comment("Show the item's decay level with the filled proportion of durability bar as well as colour")
    @LangKey("geomastery:config.foodDurability")
    public static boolean foodDurability = false;

    @Comment("Show the biomes where a crop can grow in the item tooltip")
    @LangKey("geomastery:config.cropTooltips")
    public static boolean cropTooltips = true;

    @Comment("Hide unusued and inaccessible vanilla items from Just Enough Items")
    @LangKey("geomastery:config.hideVanilla")
    public static boolean hideVanilla = true;
    
    @Comment("Show building block requirements in the item tooltip")
    @LangKey("geomastery:config.buildTooltips")
    public static boolean buildTooltips = true;
}
