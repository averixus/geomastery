/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.blocks;

import jayavery.geomastery.tileentities.TEContainerAbstract;
import jayavery.geomastery.tileentities.TEStorage;
import jayavery.geomastery.utilities.BlockWeight;
import jayavery.geomastery.utilities.ToolType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Abstract superclass for complex blocks with TileEntities,
 * GUIs, Containers, complex drops. */
public abstract class BlockComplexAbstract extends BlockBuilding {

    public BlockComplexAbstract(String name, Material material,
            float hardness, ToolType harvestTool) {

        super(material, name, null, hardness, harvestTool);
    }
    
    @Override
    public BlockWeight getWeight() {
        
        return BlockWeight.NONE;
    }
    
    @Override
    public boolean shouldConnect(IBlockAccess world, IBlockState state,
            BlockPos pos, EnumFacing direction) {
        
        return false;
    }

    /** All implementations have to override this. Activates TileEntitiy/GUI. */
    public abstract boolean activate(EntityPlayer player, World world, int x,
            int y, int z);
    
    /** Convenience to activate TileEntity/GUI. */
    @Override
    public boolean onBlockActivated(World world, BlockPos pos,
            IBlockState state, EntityPlayer player, EnumHand hand,
            EnumFacing side, float x, float y, float z) {

        return this.activate(player, world, pos.getX(), pos.getY(), pos.getZ());
    }

    /** Always remove any TileEntity and drop contents. */
    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {

        TileEntity tileEntity = world.getTileEntity(pos);

        if (tileEntity instanceof TEContainerAbstract) {

            ((TEContainerAbstract) tileEntity).dropItems();
            
        }
        
        if (tileEntity instanceof TEStorage) {
            
            ((TEStorage) tileEntity).dropItems();
        }

        super.breakBlock(world, pos, state);
        world.removeTileEntity(pos);
    }
    
    @Override
    public boolean hasTileEntity(IBlockState state) {
        
        return true;
    }
}
