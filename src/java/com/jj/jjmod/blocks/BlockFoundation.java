package com.jj.jjmod.blocks;

import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.utilities.BlockMaterial;
import com.jj.jjmod.utilities.IBuildingBlock;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockFoundation extends BlockNew implements IBuildingBlock {

    public BlockFoundation() {
        
        super(BlockMaterial.SOIL, "foundation", CreativeTabs.BUILDING_BLOCKS, 1.5F, ToolType.PICKAXE);
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
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        
        int count = 0;
        
        for (EnumFacing facing : EnumFacing.HORIZONTALS) {
            
            Block block = world.getBlockState(pos.offset(facing)).getBlock();
            
            if (ModBlocks.LIGHT.contains(block) || ModBlocks.HEAVY.contains(block) || ((block instanceof IBuildingBlock) && ((IBuildingBlock) block).isLight())) {
                
                count++;
            }
        }
        
        return count >= 2;
    }
    
    @Override
    public void neighborChanged(IBlockState state, World world,
            BlockPos pos, Block blockIn, BlockPos unused) {
        
        if (!this.canPlaceBlockAt(world, pos)) {
            
            world.destroyBlock(pos, true);
        }
    }

}
