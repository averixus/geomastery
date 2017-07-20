/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.blocks;

import jayavery.geomastery.items.ItemPlacing;
import jayavery.geomastery.utilities.EBlockWeight;
import jayavery.geomastery.utilities.EToolType;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Building block with horizontal facing. */
public abstract class BlockFacing extends BlockBuildingAbstract<ItemPlacing.Building> {

    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    /** This block's weight. */
    protected final EBlockWeight weight;
    
    public BlockFacing(String name, Material material, float hardness,
            int stackSize, EBlockWeight weight) {
        
        super(material, name, CreativeTabs.BUILDING_BLOCKS,
                hardness, stackSize);
        this.weight = weight;
    }
    
    @Override
    protected ItemPlacing.Building createItem(int stackSize) {
        
        return new ItemPlacing.Building(this, stackSize);
    }
    
    @Override
    public EBlockWeight getWeight(IBlockState state) {
        
        return this.weight;
    }
    
    @Override
    public boolean place(World world, BlockPos targetPos, EnumFacing targetSide,
            EnumFacing placeFacing, ItemStack stack, EntityPlayer player) {
        
        BlockPos placePos = targetPos.offset(targetSide);
        placeFacing = targetSide.getAxis().isHorizontal() ?
                targetSide.getOpposite() : placeFacing;
        IBlockState setState = this.getDefaultState()
                .withProperty(FACING, placeFacing);
        
        if (this.isValid(world, placePos, stack, false, setState, player)) {
            
            world.setBlockState(placePos, setState);
            return true;
        }
        
        return false;
    }
    
    @Override
    public BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this, FACING);
    }
    
    @Override
    public int getMetaFromState(IBlockState state) {
        
        return state.getValue(FACING).getHorizontalIndex();
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta) {
        
        return this.getDefaultState().withProperty(FACING,
                EnumFacing.getHorizontal(meta));
    }
}
