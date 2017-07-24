/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.items;

import java.util.List;
import jayavery.geomastery.blocks.BlockBeam;
import jayavery.geomastery.main.GeoConfig;
import jayavery.geomastery.tileentities.TEBeam;
import jayavery.geomastery.tileentities.TEBeam.ETypeFloor;
import jayavery.geomastery.utilities.Lang;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Floor placing item. */
public class ItemFloor extends ItemPlacing {

    /** This item's floor type. */
    private ETypeFloor floor;

    public ItemFloor(int stackSize, ETypeFloor floor) {
        
        super("floor_" + floor.getName(), stackSize,
                CreativeTabs.DECORATIONS);
        this.floor = floor;
    }
    
    @Override
    protected boolean place(World world, BlockPos targetPos,
            EnumFacing targetSide, EnumFacing placeFacing,
            ItemStack stack, EntityPlayer player) {
        
        Block block = world.getBlockState(targetPos).getBlock();
        TileEntity tileEntity = world.getTileEntity(targetPos);
        
        if (!(block instanceof BlockBeam) ||
                !(tileEntity instanceof TEBeam)) {

            return false;
        }
        
        TEBeam tileBeam = (TEBeam) tileEntity;
        
        if (tileBeam.getFloor() != ETypeFloor.NONE) {

            return false;
        }
        
        return tileBeam.applyFloor(this.floor);
    }

    @Override
    protected SoundType getSoundType() {

        return SoundType.WOOD;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player,
            List<String> tooltip, boolean advanced) {

        if (GeoConfig.textVisual.buildTooltips) {
            
            tooltip.add(I18n.format(Lang.BUILDTIP_FLOOR));
        }
    }
}
