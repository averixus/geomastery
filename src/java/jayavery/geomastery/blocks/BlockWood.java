/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.blocks;

import jayavery.geomastery.utilities.EToolType;
import net.minecraft.block.BlockLog;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;

/** Custom log block. */
public class BlockWood extends BlockLog {
    
    public BlockWood(String name, float hardness) {
        
        BlockNew.setupBlock(this, name, CreativeTabs.BUILDING_BLOCKS,
                hardness, EToolType.AXE);
        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(LOG_AXIS, BlockLog.EnumAxis.Y));
    }
    
    @Override
    protected BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this, LOG_AXIS);        
    }
    
    @Override
    public int getMetaFromState(IBlockState state) {
        
        int meta = 0;
        
        switch (state.getValue(LOG_AXIS)) {
            
            case X: 
                return meta | 4;
            case Z:
                return meta | 8;
            case Y: case NONE: default:
                return meta | 12;
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        
        IBlockState state = this.getDefaultState();
        
        switch (meta & 12) {
            
            case 0:
                return state.withProperty(LOG_AXIS, BlockLog.EnumAxis.Y);
            case 4:
                return state.withProperty(LOG_AXIS, BlockLog.EnumAxis.X);
            case 8:
                return state.withProperty(LOG_AXIS, BlockLog.EnumAxis.Z);
            default: 
                return state.withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE);  
        }
    }
}
