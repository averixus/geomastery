/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.blocks;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import com.google.common.collect.Lists;
import jayavery.geomastery.main.GeoConfig;
import jayavery.geomastery.utilities.EBlockWeight;
import jayavery.geomastery.utilities.EToolType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Simple solid block for ores etc. */
public class BlockSolid extends BlockNew {
    
    /** This block's dropped item. */
    private final Supplier<Item> item;
    /** Maximum number of dropped items for randomising. */
    private final int maxDropped;

    public BlockSolid(Material material, String name, float hardness,
            Supplier<Item> item, int maxDropped, EToolType tool) {

        super(material, name, CreativeTabs.BUILDING_BLOCKS, hardness, tool);
        this.item = item;
        this.maxDropped = maxDropped;
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos,
            IBlockState state, int fortune) {
        
        Random rand = world instanceof World ?
                ((World) world).rand : new Random();
        return Lists.newArrayList(new ItemStack(this.item.get(),
                rand.nextInt(this.maxDropped) + 1));
    }
}
