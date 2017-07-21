/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.blocks;

import jayavery.geomastery.tileentities.TETrunk;
import jayavery.geomastery.utilities.EBlockWeight;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

// TEST
public class BlockTrunk extends BlockFacing {

    public BlockTrunk() {
        
        super("trunk", Material.WOOD, 5F, 1, EBlockWeight.NONE);
    }
    
    @Override
    public boolean hasTileEntity(IBlockState state) {
        
        return true;
    }
    
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        
        return new TETrunk();
    }
    
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }
    
    @Override
    public boolean isFullCube(IBlockState state) {
        
        return false;
    }
    
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        
        return false;
    }
}
