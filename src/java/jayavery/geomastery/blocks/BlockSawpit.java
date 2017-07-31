/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.blocks;

import java.util.List;
import jayavery.geomastery.main.GeoConfig;
import jayavery.geomastery.main.GuiHandler.GuiList;
import jayavery.geomastery.tileentities.TECraftingSawpit;
import jayavery.geomastery.tileentities.TECraftingSawpit.EPartSawpit;
import jayavery.geomastery.utilities.BlockMaterial;
import jayavery.geomastery.utilities.EBlockWeight;
import jayavery.geomastery.utilities.Lang;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Sawpit multi crafting block. */
public class BlockSawpit extends BlockContainerMulti<EPartSawpit> {

    public static final PropertyEnum<EPartSawpit> PART = PropertyEnum.create("part", EPartSawpit.class);
    
    public BlockSawpit() {
        
        super("crafting_sawpit", BlockMaterial.WOOD_FURNITURE, 5F,
                TECraftingSawpit::new, GuiList.SAWPIT.ordinal(), EPartSawpit.F);
    }
    
    @Override
    public PropertyEnum<EPartSawpit> getPartProperty() {
        
        return PART;
    }
    
    @SideOnly(Side.CLIENT) @Override
    public void addInformation(ItemStack stack, World world,
            List<String> tooltip, ITooltipFlag advanced) {
        
        if (GeoConfig.textVisual.buildTooltips) {
        
            tooltip.add(I18n.format(Lang.BUILDTIP_MULTIPART));
            tooltip.add(I18n.format(Lang.BUILDTIP_SAWPIT));
            tooltip.add(I18n.format(EBlockWeight.NONE.supports()));
        }
    }
    
    /** Checks whether the position can support this, sending error message. */
    public boolean isSupport(World world, BlockPos pos, EntityPlayer player) {
        
        if (!EBlockWeight.getWeight(world.getBlockState(pos))
                .canSupport(this.getWeight(this.getDefaultState()))) {
            
            message(player, Lang.BUILDFAIL_SUPPORT);
            return false;
        }
        
        return true;
    }
    
    /** Checks whether the position has a space, sending error message. */
    public boolean isSpace(World world, BlockPos pos, EntityPlayer player) {
        
        if (!world.getBlockState(pos).getBlock().isReplaceable(world, pos)) {
            
            message(player, Lang.BUILDFAIL_OBSTACLE);
            return false;
        }
        
        return true;
    }
}
