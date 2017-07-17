/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.blocks;

import java.util.List;
import com.google.common.collect.Lists;
import jayavery.geomastery.items.ItemPlacing;
import jayavery.geomastery.main.Geomastery;
import jayavery.geomastery.utilities.EBlockWeight;
import jayavery.geomastery.utilities.EToolType;
import jayavery.geomastery.utilities.IItemStorage;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Abstract superclass for complex blocks with TileEntities,
 * GUIs, Containers, complex drops. */
public abstract class BlockContainerAbstract<I extends ItemPlacing.Building>
        extends BlockBuildingAbstract<I> {
    
    /** This container's gui ordinal. */
    private final int ordinal;

    public BlockContainerAbstract(String name, Material material,
            float hardness, int ordinal) {

        super(material, name, CreativeTabs.DECORATIONS, hardness, 1);
        this.ordinal = ordinal;
    }
    
    @Override
    public EBlockWeight getWeight(IBlockState state) {
        
        return EBlockWeight.NONE;
    }
    
    @Override
    public boolean shouldConnect(IBlockAccess world, IBlockState state,
            BlockPos pos, EnumFacing direction) {
        
        return false;
    }
    
    @Override
    public boolean onBlockActivated(World world, BlockPos pos,
            IBlockState state, EntityPlayer player, EnumHand hand,
            EnumFacing side, float x, float y, float z) {

        if (!world.isRemote) {
            
            player.openGui(Geomastery.instance, this.ordinal, world,
                    pos.getX(), pos.getY(), pos.getZ());
        }
        
        return true;
    }
    
    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos,
            IBlockState state, int fortune, TileEntity te,
            ItemStack tool, EntityPlayer player) {
        
        List<ItemStack> result = Lists.newArrayList(new ItemStack(this.item));

        if (te instanceof IItemStorage) {

            result.addAll(((IItemStorage) te).getDrops());
        }
        
        return result;
    }
    
    @Override
    public boolean hasTileEntity(IBlockState state) {
        
        return true;
    }
}
