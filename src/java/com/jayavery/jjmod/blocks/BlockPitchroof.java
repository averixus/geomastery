package com.jayavery.jjmod.blocks;

import java.util.List;
import javax.annotation.Nullable;
import com.google.common.collect.Lists;
import com.jayavery.jjmod.utilities.BlockWeight;
import com.jayavery.jjmod.utilities.IDelayedMultipart;
import com.jayavery.jjmod.utilities.ToolType;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
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

public class BlockPitchroof extends BlockBuilding {

    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyEnum<EnumShape> SHAPE =
            PropertyEnum.create("shape", EnumShape.class);

    public BlockPitchroof(Material material, String name, float hardness) {
        
        super(material, name, CreativeTabs.BUILDING_BLOCKS,
                hardness, ToolType.AXE);
    }
    
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
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos,
            IBlockState state, int fortune) {
        
        return Lists.newArrayList(new ItemStack(Item.getItemFromBlock(this)));
    }
    
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world,
            BlockPos pos) {
        
        EnumFacing facing = state.getValue(FACING);
        EnumShape shape = EnumShape.MC;
        
        boolean isTop = !(world.getBlockState(pos.offset(facing).up())
                .getBlock() instanceof BlockPitchroof);
        
        boolean isBottom = !(world.getBlockState(pos
                .offset(facing.getOpposite()).down()).getBlock()
                instanceof BlockPitchroof);

        boolean isLeft = !(world.getBlockState(pos
                .offset(facing.rotateYCCW())).getBlock()
                instanceof BlockPitchroof);
        
        boolean isRight = !(world.getBlockState(pos
                .offset(facing.rotateY())).getBlock()
                instanceof BlockPitchroof);
        
        IBlockState behind = world.getBlockState(pos.offset(facing));
        IBlockState infront = world.getBlockState(pos
                .offset(facing.getOpposite()));
        
        if (behind.getBlock() instanceof BlockPitchroof &&
                behind.getValue(FACING).getAxis() != facing.getAxis()) {
                
            if (behind.getValue(FACING) == facing.rotateY()) {
                
                state = state.withProperty(FACING, facing.rotateY());
            }
            
            shape = EnumShape.getExternal(isTop, isBottom);
            
        } else if (infront.getBlock() instanceof BlockPitchroof &&
                infront.getValue(FACING).getAxis() != facing.getAxis()) {
                
            if (infront.getValue(FACING) == facing.rotateY()) {
                
                state = state.withProperty(FACING, facing.rotateY());
            }
            
            shape = EnumShape.getInternal(isTop, isBottom);
            
        } else {
            
            shape = EnumShape.getStraight(isTop, isBottom, isLeft, isRight);
        }
        
        return state.withProperty(SHAPE, shape);
    }
    
    @Override
    public BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this, FACING, SHAPE);
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        
        return FULL_BLOCK_AABB;
    }
    
    @Override
    public void addCollisionBoxToList(IBlockState state, World world,
            BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> list,
            @Nullable Entity entity, boolean unused) {
        
        state = this.getActualState(state, world, pos);
        EnumShape shape = state.getValue(SHAPE);
        int facing = state.getValue(FACING).getHorizontalIndex();
        
        if (shape.isInternal()) {
            
            for (AxisAlignedBB box : STAIRS_INTERNAL[facing]) {
                
                addCollisionBoxToList(pos, entityBox, list, box);
            }
            
        } else if (shape.isExternal()) {
            
            for (AxisAlignedBB box : STAIRS_EXTERNAL[facing]) {
                
                addCollisionBoxToList(pos, entityBox, list, box);
            }
            
        } else {
            
            for (AxisAlignedBB box : STAIRS_STRAIGHT[facing]) {
                
                addCollisionBoxToList(pos, entityBox, list, box);
            }
        }
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta) {
        
        return this.getDefaultState().withProperty(FACING,
                EnumFacing.getHorizontal(meta));
    }
    
    @Override
    public int getMetaFromState(IBlockState state) {
        
        return state.getValue(FACING).getHorizontalIndex();
    }
    
    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos,
            EnumFacing side, float x, float y, float z,
            int meta, EntityLivingBase placer) {
        
        return this.getDefaultState().withProperty(FACING,
                placer.getHorizontalFacing());
    }
    
    public enum EnumShape implements IStringSerializable {
        
        TL("tl"), TC("tc"), TR("tr"), ML("ml"), MC("mc"), MR("mr"),
        BL("bl"), BC("bc"), BR("br"), ST("st"), SM("sm"), SB("sb"),
        SL("sl"), SC("sc"), SR("sr"), SS("ss"), IT("it"), IM("im"),
        IB("ib"), IS("is"), ET("et"), EM("em"), EB("eb"), ES("es");
        
        private final String name;
        
        private EnumShape(String name) {
            
            this.name = name;
        }
        
        @Override
        public String getName() {
            
            return this.name;
        }
        
        public boolean isInternal() {
            
            switch (this) {
                
                case IT:
                case IM:
                case IB:
                case IS:
                    return true;
                default:
                    return false;
            }
        }
        
        public boolean isExternal() {
            
            switch (this) {
                
                case ET:
                case EM:
                case EB:
                case ES:
                    return true;
                default:
                    return false;
            }
        }
        
        public static EnumShape getExternal(boolean isTop, boolean isBottom) {
            
            if (isTop) {
                
                if (isBottom) {
                    
                    return ES;
                    
                } else {
                    
                    return ET;
                }
                
            } else {
                
                if (isBottom) {
                    
                    return EB;
                    
                } else {
                    
                    return EM;
                }
            }
        }
        
        public static EnumShape getInternal(boolean isTop, boolean isBottom) {
            
            if (isTop) {
                
                if (isBottom) {
                    
                    return IS;
                    
                } else {
                    
                    return IT;
                }
                
            } else {
                
                if (isBottom) {
                    
                    return IB;
                    
                } else {
                    
                    return IM;
                }
            }
        }
        
        public static EnumShape getStraight(boolean isTop, boolean isBottom,
                boolean isLeft, boolean isRight) {
            
            if (isTop) {
                
                if (isBottom) {
                    
                    if (isLeft) {
                        
                        if (isRight) {
                            
                            return SS;
                            
                        } else {
                            
                            return SL;
                        }
                        
                    } else {
                        
                        if (isRight) {
                            
                            return SR;
                            
                        } else {
                            
                            return SC;
                        }
                    }
                    
                } else {
                    
                    if (isLeft) {
                        
                        if (isRight) {
                            
                            return ST;
                            
                        } else {
                            
                            return TL;
                        }
                        
                    } else {
                        
                        if (isRight) {
                            
                            return TR;
                            
                        } else {
                            
                            return TC;
                        }
                    }
                }
                
            } else {
                
                if (isBottom) {
                    
                    if (isLeft) {
                        
                        if (isRight) {
                            
                            return SB;
                            
                        } else {
                            
                            return BL;
                        }
                        
                    } else {
                        
                        if (isRight) {
                            
                            return BR;
                            
                        } else {
                            
                            return BC;
                        }
                    }
                    
                } else {
                    
                    if (isLeft) {
                        
                        if (isRight) {
                            
                            return SM;
                            
                        } else {
                            
                            return ML;
                        }
                        
                    } else {
                        
                        if (isRight) {
                            
                            return MR;
                            
                        } else {
                            
                            return MC;
                        }
                    }
                }
            }
        }
    }
}
