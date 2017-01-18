package com.jj.jjmod.blocks;

import java.util.Random;
import java.util.function.Supplier;
import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.utilities.IBuildingBlock;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSlabSingle extends BlockNew {
    
    public Supplier<Item> item;
    protected static final AxisAlignedBB BOUNDS = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);

    public BlockSlabSingle(Material material, String name,
            float hardness, ToolType harvestTool, Supplier<Item> item) {
        
        super(material, name, null, hardness, harvestTool);
        this.item = item;
    }
    
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        
        return this.item.get();
    }
    
    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        
        Block block = world.getBlockState(pos.down()).getBlock();
        
        boolean natural = ModBlocks.HEAVY.contains(block) || ModBlocks.LIGHT.contains(block);
        boolean built = false;
        
        if (block instanceof IBuildingBlock) {
            
            IBuildingBlock builtBlock = (IBuildingBlock) block;
            built = builtBlock.isDouble();
        }
        
        return natural || built;
    }
    
    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos unused) {
        
        if (!this.canPlaceBlockAt(world, pos)) {
            
            world.destroyBlock(pos, true);
        }
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        
        return BOUNDS;
    }
}
