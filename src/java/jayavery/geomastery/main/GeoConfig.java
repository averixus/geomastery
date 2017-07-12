/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.main;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

@Config(modid = Geomastery.MODID)
public class GeoConfig {

    @Comment("Show the food type in the item tooltip")
    public static boolean foodTooltips = true;

    @Comment("Show the item's decay level with the filled proportion of durability bar as well as colour")
    public static boolean foodDurability = false;

    @Comment("Show the biomes where a crop can grow in the item tooltip")
    public static boolean cropTooltips = true;

    @Comment("Hide unusued and inaccessible vanilla items from Just Enough Items")
    public static boolean hideVanilla = true;
    
    @Comment("Show simple building block requirements in the item tooltip")
    public static boolean buildTooltips = true;
}
