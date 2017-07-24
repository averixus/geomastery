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
import net.minecraftforge.common.config.Config.RequiresWorldRestart;

/** Geomastery configuration. */
@Config(modid = Geomastery.MODID)
public class GeoConfig {
    
    @Comment("Changes to text and visuals for textVisual")
    @LangKey(Lang.CONFIG_CAT + "textvisual")
    public static TextVisual textVisual = new TextVisual();
    
    @Comment("Compatibility with other mods")
    @LangKey(Lang.CONFIG_CAT + "compatibility")
    public static Compatibility compatibility = new Compatibility();
    
    @Comment("Major gameplay options")
    @LangKey(Lang.CONFIG_CAT + "gameplay")
    public static Gameplay gameplay = new Gameplay();
    
    public static class TextVisual {

        @Comment("Show the food type in the item tooltip")
        @LangKey(Lang.CONFIG_PROP + "foodTooltips")
        public boolean foodTooltips = true;
    
        @Comment("Show the item's decay level with the filled proportion of weathering bar as well as colour")
        @LangKey(Lang.CONFIG_PROP + "foodDurability")
        public boolean foodDurability = false;
    
        @Comment("Show the biomes where a crop can grow in the item tooltip")
        @LangKey(Lang.CONFIG_PROP + "cropTooltips")
        public boolean cropTooltips = true;
    
        @Comment("Show building block requirements in the item tooltip")
        @LangKey(Lang.CONFIG_PROP + "buildTooltips")
        public boolean buildTooltips = true;
        
        @Comment("Send messages about invalid building block placement")
        @LangKey(Lang.CONFIG_PROP + "buildMessages")
        public boolean buildMessages = true;
    }
    
    public static class Compatibility {
        
        @Comment("Hide unusued and inaccessible vanilla items from Just Enough Items")
        @LangKey(Lang.CONFIG_PROP + "hideVanilla")
        @RequiresMcRestart
        public boolean hideVanilla = true;
        
        @Comment("Add a recipe for vanilla crafting table")
        @LangKey(Lang.CONFIG_PROP + "addCrafting")
        @RequiresMcRestart
        public boolean addCrafting = false;
    }
    
    public static class Gameplay {
    
        @Comment("Enable damage from extreme temperatures")
        @LangKey(Lang.CONFIG_PROP + "temperature")
        public boolean temperature = true;
        
        @Comment("Enable speed and sprint restrictions from apparel and carried items")
        @LangKey(Lang.CONFIG_PROP + "speed")
        public boolean speed = true;
        
        @Comment("Enable three separate food types and associated health effects")
        @LangKey(Lang.CONFIG_PROP + "food")
        @RequiresWorldRestart
        public boolean food = true;
        
        @Comment("Enable restricted and variable inventory size")
        @LangKey(Lang.CONFIG_PROP + "inventory")
        @RequiresWorldRestart
        public boolean inventory = true;
    }
}
