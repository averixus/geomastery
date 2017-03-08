package com.jayavery.jjmod.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

/** Invisible light-producing block for moving light sources. */
public class BlockInvisibleLight extends BlockNew {
    
    public static final PropertyInteger LIGHT =
            PropertyInteger.create("light", 0, 15);
    
    public BlockInvisibleLight() {
        
        super(Material.AIR, "invisiblelight", null, -1, null);
    }
    
    @Override
    public int getLightValue(IBlockState state) {
        
        return state.getValue(LIGHT);
    }
    
    @Override
    public BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this, new IProperty[] {LIGHT});
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta) {
        
        return this.getDefaultState().withProperty(LIGHT, meta);
    }
    
    @Override
    public int getMetaFromState(IBlockState state) {
        
        return state.getValue(LIGHT);
    }

    @Override
    protected RayTraceResult rayTrace(BlockPos pos, Vec3d start,
            Vec3d end, AxisAlignedBB boundingBox) {
        
        return null;
    }
    
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        
        return EnumBlockRenderType.INVISIBLE;
    }
}
