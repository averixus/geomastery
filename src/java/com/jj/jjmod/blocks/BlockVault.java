package com.jj.jjmod.blocks;

import com.jj.jjmod.utilities.BlockMaterial;
import com.jj.jjmod.utilities.IBuildingBlock;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs.EnumShape;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockVault extends BlockNew implements IBuildingBlock {
    
    public static final PropertyEnum SHAPE = PropertyEnum.<EnumShape>create("shape", EnumShape.class);
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    
    public BlockVault() {
        
        super(BlockMaterial.STONE_FURNITURE, "vault",
                CreativeTabs.BUILDING_BLOCKS, 2, ToolType.PICKAXE);
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
    
    protected boolean hasValidSide(IBlockAccess world, BlockPos pos, EnumFacing direction) {
        
        Block block = world.getBlockState(pos.offset(direction)).getBlock();
        
        if (block instanceof IBuildingBlock) {
            
            IBuildingBlock builtBlock = (IBuildingBlock) block;
            
             if (builtBlock.isDouble()) {
                 
                 return true;
             }
        }
        
        return false;
    }
    
    protected boolean isExternalCorner(IBlockAccess world, BlockPos pos, EnumFacing direction) {
        
        Block block = world.getBlockState(pos.offset(direction)).getBlock();
        Block cornerA = world.getBlockState(pos.offset(direction.rotateY())).getBlock();
        Block cornerB = world.getBlockState(pos.offset(direction.rotateYCCW())).getBlock();
        
        if ((block == this) && ((cornerA == this) || (cornerB == this))) {
            
            return true;
        }
        
        return false;
    }
    
    protected boolean isInternalCorner(IBlockAccess world, BlockPos pos, EnumFacing direction) {
        
        return this.hasValidSide(world, pos, direction) && this.hasValidSide(world, pos, direction.rotateY());
    }
    
    protected boolean isCentreCorner(IBlockAccess world, BlockPos pos, EnumFacing direction) {
        
        return this.hasValidSide(world, pos, direction.rotateY()) &&
                this.hasValidSide(world, pos, direction) &&
                this.hasValidSide(world, pos, direction.rotateYCCW());
    }
    
    protected boolean hasLintel(IBlockAccess world, BlockPos pos, EnumFacing direction) {
        
        return this.hasValidSide(world, pos, direction) &&
                this.hasValidSide(world, pos, direction.getOpposite()) &&
                !this.hasValidSide(world, pos, direction.rotateY()) &&
                !this.hasValidSide(world, pos, direction.rotateYCCW());        
    }
    
    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        
        boolean result = false;
        
        for (EnumFacing direction : EnumFacing.HORIZONTALS) {
            
            result = result ? result : (this.hasValidSide(world, pos, direction) ||
                    this.isExternalCorner(world, pos, direction));
        }
        
        return result;
    }
    
    @Override
    public void neighborChanged(IBlockState state, World world,
            BlockPos pos, Block blockIn, BlockPos unused) {
        
        if (!this.canPlaceBlockAt(world, pos)) {
            
            world.destroyBlock(pos, true);
        }
    }
    
    @Override
    public BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this, new IProperty[] {SHAPE, FACING});
    }
    
    @Override
    public int getMetaFromState(IBlockState state) {
        
        return 0;
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta) {
        
        return this.getDefaultState();
    }
    
    @Override
    public IBlockState getActualState(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        for (EnumFacing facing : EnumFacing.HORIZONTALS) {
            
            if (this.hasLintel(world, pos, facing)) {
                
                state = state.withProperty(SHAPE, EnumShape.LINTEL);
                state = state.withProperty(FACING, facing);
                return state;
            }
        }
        
        for (EnumFacing facing : EnumFacing.HORIZONTALS) {
            
            if (this.isCentreCorner(world, pos, facing)) {
                
                state = state.withProperty(SHAPE, EnumShape.SINGLE);
                state = state.withProperty(FACING, facing);
                return state;
            }
        }
        
        for (EnumFacing facing : EnumFacing.HORIZONTALS) {
                
            if (this.isInternalCorner(world, pos, facing)) {
                
                state = state.withProperty(SHAPE, EnumShape.INTERNAL);
                state = state.withProperty(FACING, facing);
                return state;
            }
        }
         
        for (EnumFacing facing : EnumFacing.HORIZONTALS) {

            if (this.hasValidSide(world, pos, facing)) {
                
                state = state.withProperty(SHAPE, EnumShape.SINGLE);
                state = state.withProperty(FACING, facing);
                return state;
            }
        }
        
        for (EnumFacing facing : EnumFacing.HORIZONTALS) {

            if (this.isExternalCorner(world, pos, facing)) {
                
                state = state.withProperty(SHAPE, EnumShape.EXTERNAL);
                state = state.withProperty(FACING, facing);
                return state;
            }
        }
        
        return state;
    }
    
    @Override
    public BlockRenderLayer getBlockLayer() {

        return BlockRenderLayer.CUTOUT_MIPPED;
    }
    
    public enum EnumShape implements IStringSerializable {
        
        SINGLE("single"), INTERNAL("internal"), EXTERNAL("external"), LINTEL("lintel");
        
        private String name;
        
        private EnumShape(String name) {
            
            this.name = name;
        }
        
        @Override
        public String getName() {
            
            return this.name;
        }
    }
}
