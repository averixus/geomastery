/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.blocks;

import java.util.List;
import javax.annotation.Nullable;
import com.google.common.collect.Lists;
import jayavery.geomastery.utilities.EBlockWeight;
import jayavery.geomastery.utilities.EToolType;
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

/** Pitched roof block. */
public class BlockPitchroof extends BlockFacing {

    public static final PropertyEnum<ERoofShape> SHAPE = PropertyEnum.create("shape", ERoofShape.class);

    public BlockPitchroof(Material material, String name,
            float hardness, int stackSize) {
        
        super(name, material, hardness, stackSize, EBlockWeight.NONE);
    }
    
    @Override
    public boolean shouldConnect(IBlockAccess world, IBlockState state,
            BlockPos pos, EnumFacing direction) {
        
        return false;
    }
    
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world,
            BlockPos pos) {
        
        EnumFacing facing = state.getValue(FACING);
        ERoofShape shape = ERoofShape.MC;
        
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
            
            shape = ERoofShape.getExternal(isTop, isBottom);
            
        } else if (infront.getBlock() instanceof BlockPitchroof &&
                infront.getValue(FACING).getAxis() != facing.getAxis()) {
                
            if (infront.getValue(FACING) == facing.rotateY()) {
                
                state = state.withProperty(FACING, facing.rotateY());
            }
            
            shape = ERoofShape.getInternal(isTop, isBottom);
            
        } else {
            
            shape = ERoofShape.getStraight(isTop, isBottom, isLeft, isRight);
        }
        
        return state.withProperty(SHAPE, shape);
    }
    
    @Override
    public BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this, FACING, SHAPE);
    }
    @Override
    public void addCollisionBoxToList(IBlockState state, World world,
            BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> list,
            @Nullable Entity entity, boolean unused) {
        
        state = this.getActualState(state, world, pos);
        ERoofShape shape = state.getValue(SHAPE);
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
            
            for (AxisAlignedBB box : STAIRS_STRAIGHT[(facing + 1) % 4]) {
                
                addCollisionBoxToList(pos, entityBox, list, box);
            }
        }
    }
    
    /** All possible shapes of roof blocks. */
    public enum ERoofShape implements IStringSerializable {
        
        TL("tl"), TC("tc"), TR("tr"), ML("ml"), MC("mc"), MR("mr"),
        BL("bl"), BC("bc"), BR("br"), ST("st"), SM("sm"), SB("sb"),
        SL("sl"), SC("sc"), SR("sr"), SS("ss"), IT("it"), IM("im"),
        IB("ib"), IS("is"), ET("et"), EM("em"), EB("eb"), ES("es");
        
        private final String name;
        
        private ERoofShape(String name) {
            
            this.name = name;
        }
        
        @Override
        public String getName() {
            
            return this.name;
        }
        
        /** @return Whether this is an internal corner. */
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
        
        /** @return Whether this is an external corner. */
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
        
        /** @return The external corner shape from the given information. */
        public static ERoofShape getExternal(boolean isTop, boolean isBottom) {
            
            return isTop ? isBottom ? ES : ET : isBottom ? EB : EM;
        }
        
        /** @return The internal corner shape from given information. */
        public static ERoofShape getInternal(boolean isTop, boolean isBottom) {
            
            return isTop ? isBottom ? IS : IT : isBottom ? IB : IM;
        }
        
        /** @return The straight shape from the given information. */
        public static ERoofShape getStraight(boolean isTop, boolean isBottom,
                boolean isLeft, boolean isRight) {
            
            return isTop ? isBottom ? isLeft ? isRight ? SS : SL : isRight ?
                    SR : SC : isLeft ? isRight ? ST : TL : isRight ?
                    TR : TC : isBottom ? isLeft ? isRight ? SB : BL : isRight ?
                    BR : BC : isLeft ? isRight ? SM : ML : isRight ? MR : MC;
        }
    }
}
