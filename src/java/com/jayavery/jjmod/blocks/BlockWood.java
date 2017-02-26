package com.jayavery.jjmod.blocks;

import com.jayavery.jjmod.utilities.ToolType;
import net.minecraft.block.BlockLog;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;

/** Custom log block. */
public class BlockWood extends BlockLog {
    
    public BlockWood(String name, float hardness) {
        
        BlockNew.setupBlock(this, name, CreativeTabs.BUILDING_BLOCKS,
                hardness, ToolType.AXE);
        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(LOG_AXIS, BlockLog.EnumAxis.Y));
    }
    
    @Override
    protected BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this, new IProperty[] {LOG_AXIS});        
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta) {
        
        IBlockState state = this.getDefaultState();
        
        switch (meta & 12) {
            
            case 0 : {
                
                state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.Y);
                break;
            }
            
            case 4 : {
                
                state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.X);
                break;
            }
            
            case 8 : {
                
                state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.Z);
                break;
            }
            
            default : {
                
                state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE);
                break;
            }
        }
        
        return state;        
    }
    
    @Override
    public int getMetaFromState(IBlockState state) {
        
        int meta = 0;
        
        switch (state.getValue(LOG_AXIS)) {
            
            case X: 
                meta |= 4;
                break;
                
            case Z:
                meta |= 8;
                break;
            
            case Y:
            case NONE:
                meta |= 12;
                break;
        }
        
        return meta;
    }
}
