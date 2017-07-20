/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.utilities;

import jayavery.geomastery.main.Geomastery;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

/** Custom tool and armour materials. */
public class EquipMaterial {

    /** Flint tools material. */
    public static final ToolMaterial FLINT_TOOL =  EnumHelper.addToolMaterial("flint_tool", 1, 100, 4F, 0.5F, -1);
    /** Copper tools material. */
    public static final ToolMaterial COPPER_TOOL = EnumHelper.addToolMaterial("copper_tool", 1, 150, 6F, 1.5F, -1);
    /** Bronze tools material. */
    public static final ToolMaterial BRONZE_TOOL = EnumHelper.addToolMaterial("bronze_tool", 1, 200, 8F, 2F, -1);
    /** Steel tools material. */
    public static final ToolMaterial STEEL_TOOL =  EnumHelper.addToolMaterial("steel_tool", 1, 300, 10F, 2.5F, -1);
    /** Antler tools material. */
    public static final ToolMaterial ANTLER_TOOL = EnumHelper.addToolMaterial("antler_tool", 1, 100, 4F, 0.0F, -1);
    /** Wood tools material. */
    public static final ToolMaterial WOOD_TOOL =   EnumHelper.addToolMaterial("wood_tool", 1, 50, 3F, 0.0F, -1);
    
    /** Cotton apparel material. */
    public static final ArmorMaterial COTTON_APPAREL =      EnumHelper.addArmorMaterial("cotton_apparel", Geomastery.MODID + ":cotton", 2, new int[] {0, 0, 0, 0}, -1, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);
    /** Wool apparel material. */
    public static final ArmorMaterial WOOL_APPAREL =        EnumHelper.addArmorMaterial("wool_apparel", Geomastery.MODID + ":wool", 3, new int[] {0, 0, 0, 0}, -1, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);
    /** Fur apparel material. */
    public static final ArmorMaterial FUR_APPAREL =         EnumHelper.addArmorMaterial("fur_apparel", Geomastery.MODID + ":fur", 4, new int[] {0, 0, 0, 0}, -1, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);
    /** Leather apparel material. */
    public static final ArmorMaterial LEATHER_APPAREL =     EnumHelper.addArmorMaterial("leather_apparel", Geomastery.MODID + ":leather", 7, new int[] {1, 2, 3, 1}, -1, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0F);
    /** Steel chainmail apparel material. */
    public static final ArmorMaterial STEELMAIL_APPAREL =   EnumHelper.addArmorMaterial("steelmail_apparel", Geomastery.MODID + ":steelmail", 11, new int[] {1, 4, 5, 2}, -1, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 0F);
    /** Steel plate apparel material. */
    public static final ArmorMaterial STEELPLATE_APPAREL =  EnumHelper.addArmorMaterial("steelplate_apparel", Geomastery.MODID + ":steelplate", 15, new int[] {2, 5, 6, 2}, -1, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0);
}
