/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.main;

import jayavery.geomastery.utilities.Lang;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.RequiresMcRestart;

/** Geomastery configuration. */
@Config(modid = Geomastery.MODID)
public class GeoConfig {
    
    @Comment("Show the food type in the item tooltip")
    @LangKey(Lang.CONFIG + "foodTooltips")
    public static boolean foodTooltips = true;

    @Comment("Show the item's decay level with the filled proportion of weathering bar as well as colour")
    @LangKey(Lang.CONFIG + "foodDurability")
    public static boolean foodDurability = false;

    @Comment("Show the biomes where a crop can grow in the item tooltip")
    @LangKey(Lang.CONFIG + "cropTooltips")
    public static boolean cropTooltips = true;

    @Comment("Show building block requirements in the item tooltip")
    @LangKey(Lang.CONFIG + "buildTooltips")
    public static boolean buildTooltips = true;
    
    @Comment("Send messages about invalid building block placement")
    @LangKey(Lang.CONFIG + "buildMessages")
    public static boolean buildMessages = true;
    
    @Comment("Hide unusued and inaccessible vanilla items from Just Enough Items")
    @LangKey(Lang.CONFIG + "hideVanilla")
    @RequiresMcRestart
    public static boolean hideVanilla = true;
    
    @Comment("Add a recipe for vanilla crafting table")
    @LangKey(Lang.CONFIG + "addCrafting")
    @RequiresMcRestart
    public static boolean addCrafting = false;
    
    @Comment("Enable damage from extreme temperatures")
    @LangKey(Lang.CONFIG + ":temperature")
    public static boolean temperature = true;
    
    @Comment("Enable speed and sprint restrictions from apparel and carrying items")
    @LangKey(Lang.CONFIG + ":speed")
    public static boolean speed = true;
    
    @Comment("Enable three separate food types")
    @LangKey(Lang.CONFIG + ":food")
    public static boolean food = true;
}
