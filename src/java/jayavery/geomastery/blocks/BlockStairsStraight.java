package jayavery.geomastery.blocks;

import java.util.List;
import com.google.common.collect.Lists;
import jayavery.geomastery.utilities.BlockMaterial;
import jayavery.geomastery.utilities.BlockWeight;
import jayavery.geomastery.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Straight stairs block. */
public abstract class BlockStairsStraight extends BlockBuilding {

    public static final PropertyDirection FACING = BlockHorizontal.FACING;


    public BlockStairsStraight(String name, float hardness) {
        
        super(BlockMaterial.WOOD_FURNITURE, name, CreativeTabs.BUILDING_BLOCKS,
                hardness, ToolType.AXE);
    }
    
    @Override
    public boolean isValid(World world, BlockPos pos) {
        
        boolean supported = false;
        
        if (BlockWeight.getWeight(world.getBlockState(pos.down()).getBlock())
                .canSupport(this.getWeight())) {
            
            supported = true;
            
        } else {
            
            for (EnumFacing facing : EnumFacing.HORIZONTALS) {
                
                BlockPos posUp = pos.offset(facing).up();
                Block blockUp = world.getBlockState(posUp).getBlock();
                Block foundationUp = world.getBlockState(posUp.down())
                        .getBlock();
                BlockPos posDown = pos.offset(facing.getOpposite()).down();
                Block blockDown = world.getBlockState(posDown).getBlock();
                Block foundationDown = world.getBlockState(posDown.down())
                        .getBlock();
                
                if ((blockUp instanceof BlockStairsStraight &&
                        BlockWeight.getWeight(foundationUp)
                        .canSupport(this.getWeight())) ||
                        (blockDown instanceof BlockStairsStraight &&
                        BlockWeight.getWeight(foundationDown)
                        .canSupport(this.getWeight()))) {
                    
                    supported = true;
                    break;
                }
            }
        }

        return this.hasValidConnection(world, pos) && supported;
    }
    
    protected abstract boolean hasValidConnection(World world, BlockPos pos);
    
    @Override
    public BlockWeight getWeight() {
        
        return BlockWeight.NONE;
    }
    
    @Override
    public boolean shouldConnect(IBlockAccess world, IBlockState state,
            BlockPos pos, EnumFacing direction) {
        
        return false;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        return FULL_BLOCK_AABB;
    }
    
    @Override
    public void addCollisionBoxToList(IBlockState state, World world,
            BlockPos pos, AxisAlignedBB entityBox,
            List<AxisAlignedBB> list, Entity entity, boolean unused) {
                
        int facing = (state.getValue(FACING).getHorizontalIndex() + 1) % 4;
        
        for (AxisAlignedBB box : STAIRS_STRAIGHT[facing]) {
            
            addCollisionBoxToList(pos, entityBox, list, box);
        }
    }
    
    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos,
            IBlockState state, int fortune) {
        
        return Lists.newArrayList(new ItemStack(Item.getItemFromBlock(this)));
    }
    
    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos,
            EnumFacing side, float x, float y, float z,
            int meta, EntityLivingBase placer) {
        
        return this.getDefaultState().withProperty(FACING,
                placer.getHorizontalFacing());
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta) {
        
        EnumFacing facing = EnumFacing.getHorizontal(meta);
        return this.getDefaultState().withProperty(FACING, facing);
    }
    
    @Override
    public int getMetaFromState(IBlockState state) {
        
        return state.getValue(FACING).getHorizontalIndex();
    }
    
    public static class Single extends BlockStairsStraight {
        
        public Single(String name, float hardness) {
            
            super(name, hardness);
        }

        @Override
        public IBlockState getActualState(IBlockState state, IBlockAccess world,
                BlockPos pos) {

            return state;
        }

        @Override
        public BlockStateContainer createBlockState() {

            return new BlockStateContainer(this, FACING);
        }

        @Override
        protected boolean hasValidConnection(World world, BlockPos pos) {

            for (EnumFacing direction : EnumFacing.HORIZONTALS) {
                
                Block connect = world.getBlockState(pos
                        .offset(direction)).getBlock();
                
                if (connect == this) {
                    
                    return false;
                }
            }
            
            return true;
        }
    }
    
    public static class Joining extends BlockStairsStraight {
        
        public static final PropertyEnum<EnumConnection> CONNECTION =
                PropertyEnum.<EnumConnection>create("connection",
                EnumConnection.class);
        
        public Joining(String name, float hardness) {
            
            super(name, hardness);
        }
        
        @Override
        public IBlockState getActualState(IBlockState state, IBlockAccess world,
                BlockPos pos) {
            
            EnumFacing facing = state.getValue(FACING);
            EnumFacing left = facing.rotateYCCW();
            EnumFacing right = facing.rotateY();
            
            if (world.getBlockState(pos.offset(right)).getBlock() == this) {
                
                state = state.withProperty(CONNECTION, EnumConnection.RIGHT);
                
            } else if (world.getBlockState(pos.offset(left))
                    .getBlock() == this) {
                
                state = state.withProperty(CONNECTION, EnumConnection.LEFT);
                
            } else {
                
                state = state.withProperty(CONNECTION, EnumConnection.NONE);
            }

            return state;
        }
        
        @Override
        public BlockStateContainer createBlockState() {
            
            return new BlockStateContainer(this, FACING, CONNECTION);
        }

        @Override
        protected boolean hasValidConnection(World world, BlockPos pos) {

            int count = 0;

            for (EnumFacing direction : EnumFacing.HORIZONTALS) {
                
                Block block = world.getBlockState(pos.offset(direction))
                        .getBlock();
                
                if (block == this) {
                    
                    count++;
                    
                    Block nextBlock = world.getBlockState(pos
                            .offset(direction, 2)).getBlock();
                    
                    if (nextBlock == this) {
                        
                        return false;
                    }
                }
            }
            
            if (count > 1) {
                
                return false;
            }
            
            return true;
        }
    }
    
    /** Enum defining connection type of this block. */
    public enum EnumConnection implements IStringSerializable {
        
        LEFT("left"), RIGHT("right"), NONE("none");
        
        private String name;
        
        private EnumConnection(String name) {
            
            this.name = name;
        }

        @Override
        public String getName() {

            return this.name;
        }
    }
}
