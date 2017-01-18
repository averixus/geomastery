package com.jj.jjmod.blocks;

import java.util.Random;
import java.util.function.Supplier;
import com.jj.jjmod.utilities.BlockMaterial;
import com.jj.jjmod.utilities.IBuildingBlock;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDoor extends net.minecraft.block.BlockDoor implements IBuildingBlock {
    
    protected Supplier<Item> item;

    public BlockDoor(String name, Supplier<Item> item) {
        
        super(BlockMaterial.WOOD_FURNITURE);
        BlockNew.setupBlock(this, name, null, 2F, ToolType.AXE);
        this.item = item;
    }
    
    @Override
    public boolean isLight() {
        
        return true;
    }
    
    @Override
    public boolean isHeavy() {
        
        return false;
    }
    
    @Override
    public boolean isDouble() {
        
        return true;
    }
    
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        
        if (state.getValue(HALF) != EnumDoorHalf.UPPER) {
            
            return this.item.get();
            
        } else {
            
            return Items.field_190931_a;
        }
    }
    
    public boolean isValidPosition(World world, BlockPos pos) {
        
        return world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world,
            BlockPos pos) {

        // TODO 
        return FULL_BLOCK_AABB;
    }

    @Override
    public BlockStateContainer createBlockState() {
        //TODO  
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world,
            BlockPos pos) {
        //TODO
        return state;
    }
}
