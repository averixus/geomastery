package com.jayavery.jjmod.blocks;

import java.util.List;
import com.jayavery.jjmod.utilities.BlockMaterial;
import com.jayavery.jjmod.utilities.IBuildingBlock;
import com.jayavery.jjmod.utilities.ToolType;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Vault block. */
public class BlockVault extends BlockNew implements IBuildingBlock {
    
    public static final PropertyEnum<EnumShape> SHAPE =
            PropertyEnum.<EnumShape>create("shape", EnumShape.class);
    public static final PropertyDirection FACING =
            PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    
    public BlockVault(String name) {
        
        super(BlockMaterial.STONE_FURNITURE, name,
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
    
    @Override
    public boolean supportsBeam() {
        
        return true;
    }
    
    @Override
    public boolean isShelter() {
        
        return true;
    }
    
    /** Checks whether there is a valid support at the
     * given direction from this position.
     * @return Whether there is a valid support in the given direction. */
    private boolean hasValidSide(IBlockAccess world,
            BlockPos pos, EnumFacing direction) {
        
        Block block = world.getBlockState(pos.offset(direction)).getBlock();
        
        if (block instanceof IBuildingBlock && block != this) {
            
            IBuildingBlock builtBlock = (IBuildingBlock) block;
            
             if (builtBlock.isDouble()) {
                 
                 return true;
             }
        }
        
        return false;
    }
    
    /** @return Whether the vault at the given pos is an external corner. */
    private boolean isExternalCorner(IBlockAccess world,
            BlockPos pos, EnumFacing direction) {
        
        Block block = world.getBlockState(pos.offset(direction)).getBlock();
        Block cornerA = world.getBlockState(pos.offset(direction.rotateY()))
                .getBlock();
        Block cornerB = world.getBlockState(pos.offset(direction.rotateYCCW()))
                .getBlock();
        
        if ((block == this) && ((cornerA == this) || (cornerB == this))) {
            
            return true;
        }
        
        return false;
    }
    
    /** Checks whether the vault at the given pos is an internal corner,
     * clockwise from the given direction.
     * @return Whether this is an internal corner. */
    private boolean isInternalCorner(IBlockAccess world,
            BlockPos pos, EnumFacing direction) {
        
        return this.hasValidSide(world, pos, direction) &&
                this.hasValidSide(world, pos, direction.rotateY());
    }
    
    /** Checks whether the vault at the given pos is the centre
     * corner from three sides, centred on the given direction.
     * @return Whether this is a centre corner. */
    private boolean isCentreCorner(IBlockAccess world,
            BlockPos pos, EnumFacing direction) {
        
        return this.hasValidSide(world, pos, direction.rotateY()) &&
                this.hasValidSide(world, pos, direction) &&
                this.hasValidSide(world, pos, direction.rotateYCCW());
    }
    
    /** Checks whether the vault at the given pos is a lintel
     * in the axis of the given direction.
     * @return Whether this is a lintel. */
    private boolean hasLintel(IBlockAccess world,
            BlockPos pos, EnumFacing direction) {
        
        return this.hasValidSide(world, pos, direction) &&
                this.hasValidSide(world, pos, direction.getOpposite()) &&
                !this.hasValidSide(world, pos, direction.rotateY()) &&
                !this.hasValidSide(world, pos, direction.rotateYCCW());        
    }
    
    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        
        boolean result = false;
        
        for (EnumFacing direction : EnumFacing.HORIZONTALS) {
            
            result = result ? result :
                    (this.hasValidSide(world, pos, direction) ||
                    this.isExternalCorner(world, pos, direction));
        }
        
        return result;
    }
    
    /** Checks position and breaks if invalid. */
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
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        state = this.getActualState(state, world, pos);
        return state.getValue(SHAPE) == EnumShape.LINTEL ?
                FULL_BLOCK_AABB : TOP_HALF;
    }
    
    @Override
    public void addCollisionBoxToList(IBlockState state, World world,
            BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> list,
            @Nullable Entity entity, boolean unused) {
        
        state = this.getActualState(state, world, pos);
        int facing = state.getValue(FACING).getHorizontalIndex();
        EnumShape shape = state.getValue(SHAPE);
        
        AxisAlignedBB[] boxes;
        
        switch (shape) {
            
            case SINGLE:
                boxes = VAULT_STRAIGHT[facing];
                break;
                
            case INTERNAL:
                boxes = VAULT_INTERNAL[(facing + 3) % 4];
                break;
                
            case EXTERNAL:
                boxes = VAULT_EXTERNAL[facing];
                break;
                
            case LINTEL:
            default:
                return;
        }
        
        for (AxisAlignedBB box : boxes) {
            
            addCollisionBoxToList(pos, entityBox, list, box);
        }
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
    
    /** Enum defining possible shapes for the vault. */
    public enum EnumShape implements IStringSerializable {
        
        SINGLE("single"), INTERNAL("internal"),
        EXTERNAL("external"), LINTEL("lintel");
        
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
