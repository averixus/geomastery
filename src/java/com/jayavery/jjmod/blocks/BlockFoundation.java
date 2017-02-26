package com.jayavery.jjmod.blocks;

import com.jayavery.jjmod.init.ModBlocks;
import com.jayavery.jjmod.utilities.BlockMaterial;
import com.jayavery.jjmod.utilities.IBuildingBlock;
import com.jayavery.jjmod.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Foundation block. */
public class BlockFoundation extends BlockNew implements IBuildingBlock {

    public BlockFoundation() {
        
        super(BlockMaterial.SOIL, "foundation", CreativeTabs.BUILDING_BLOCKS,
                1.5F, ToolType.PICKAXE);
    }
    
    @Override
    public boolean isLight() {
        
        return true;
    }
    
    @Override
    public boolean isHeavy() {
        
        return true;
    }
    
    @Override
    public boolean isDouble() {
        
        return true;
    }
    
    @Override
    public boolean supportsBeam() {
        
        return true;
    }
    
    /** Checks conditions required to place block. */
    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        
        int count = 0;
        
        for (EnumFacing facing : EnumFacing.HORIZONTALS) {
            
            Block block = world.getBlockState(pos.offset(facing)).getBlock();
            
            if (ModBlocks.LIGHT.contains(block) ||
                    ModBlocks.HEAVY.contains(block)) {
                
                count++;
            }
        }
        
        return count >= 2;
    }
    
    /** Checks position and breaks if invalid. */
    @Override
    public void neighborChanged(IBlockState state, World world,
            BlockPos pos, Block blockIn, BlockPos unused) {
        
        if (!this.canPlaceBlockAt(world, pos)) {
            
            world.destroyBlock(pos, true);
        }
    }
}
