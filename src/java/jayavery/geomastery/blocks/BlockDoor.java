/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.blocks;

import java.util.Collections;
import java.util.List;
import com.google.common.collect.Lists;
import jayavery.geomastery.items.ItemPlacing;
import jayavery.geomastery.utilities.BlockMaterial;
import jayavery.geomastery.utilities.EBlockWeight;
import jayavery.geomastery.utilities.EToolType;
import jayavery.geomastery.utilities.Lang;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Door block. */
public class BlockDoor extends BlockBuildingAbstract<ItemPlacing.Building> {
    
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyBool OPEN = PropertyBool.create("open");
    public static final PropertyBool TOP = PropertyBool.create("top");
    public static final PropertyEnum<EPartDoor> PART = PropertyEnum.<EPartDoor>create("part", EPartDoor.class);
    public static final PropertyEnum<EVaultAbove> VAULT = PropertyEnum.create("vault", EVaultAbove.class);

    public BlockDoor(String name) {
        
        super(BlockMaterial.WOOD_FURNITURE, name,
                CreativeTabs.DECORATIONS, 2F, 1);
    }
    
    @Override
    public ItemPlacing.Building createItem(int stackSize) {
        
        return new ItemPlacing.Building(this, stackSize);
    }
    
    @Override
    public EBlockWeight getWeight(IBlockState state) {
        
        return EBlockWeight.LIGHT;
    }

    @Override
    public boolean shouldConnect(IBlockAccess world, IBlockState state,
            BlockPos pos, EnumFacing direction) {
        
        EnumFacing facing = state.getValue(FACING);
        return facing != direction && facing != direction.getOpposite();
    }

    @Override
    public boolean place(World world, BlockPos targetPos,
            EnumFacing targetSide, EnumFacing placeFacing,
            ItemStack stack, EntityPlayer player) {

        BlockPos bottomPos = targetPos.offset(targetSide);
        BlockPos topPos = bottomPos.up();
        
        IBlockState botState = this.getDefaultState().withProperty(FACING,
                placeFacing).withProperty(OPEN, false).withProperty(TOP, false);
        IBlockState topState = botState.withProperty(TOP, true);
                
        if (!this.isValid(world, bottomPos, stack, false, botState, player) ||
                !this.isValid(world, topPos, stack, false, topState, player)) {
        
            return false;
        }
        

        world.setBlockState(bottomPos, botState);
        world.setBlockState(topPos, topState);
        
        return true;
    }
    
    @Override
    public boolean onBlockActivated(World world, BlockPos thisPos,
            IBlockState thisState, EntityPlayer player, EnumHand hand,
            EnumFacing side, float x, float y, float z) {
        
        BlockPos otherPos = thisState.getValue(TOP) ?
                thisPos.down() : thisPos.up();
        IBlockState otherState = world.getBlockState(otherPos);
        
        if (otherState.getBlock() != this) {
            
            return false;
        }
            
        thisState = thisState.cycleProperty(OPEN);
        world.setBlockState(thisPos, thisState);
        world.setBlockState(otherPos, otherState
                .withProperty(OPEN, thisState.getValue(OPEN)));
        world.playEvent(player, thisState.getValue(OPEN) ?
                1006 : 1012, thisPos, 0);
        
        return true;
    }

    @Override
    public boolean isValid(World world, BlockPos pos, ItemStack stack,
            boolean alreadyPresent, IBlockState setState, EntityPlayer player) {
        
        if (alreadyPresent) {
            
            IBlockState state = world.getBlockState(pos);
            BlockPos otherPos = state.getValue(TOP) ? pos.down() : pos.up();
            IBlockState otherState = world.getBlockState(otherPos);
            
            if (otherState.getBlock() != this) {
                
                return false;
            }
            
        } else {
        
            if (!world.getBlockState(pos).getBlock()
                    .isReplaceable(world, pos)) {
                
                message(player, Lang.BUILDFAIL_OBSTACLE);
                return false;
            }
        }
            
        if (!setState.getValue(TOP)) {
            
            IBlockState stateBelow = world.getBlockState(pos.down());
            EBlockWeight weightBelow = EBlockWeight.getWeight(stateBelow);
            
            if (!weightBelow.canSupport(this.getWeight(stateBelow))) {
                
                message(player, Lang.BUILDFAIL_SUPPORT);
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos,
            IBlockState state, int fortune, TileEntity te,
            ItemStack tool, EntityPlayer player) {
        
        state = this.getActualState(state, world, pos);
        
        if (state.getValue(PART).isTop()) {
    
            return Lists.newArrayList(new ItemStack(this.item));
            
        } else {
            
            return Collections.emptyList();
        }
    }

    @Override
    public IBlockState getActualState(IBlockState state,
            IBlockAccess world, BlockPos pos) {
                
        EnumFacing facing = state.getValue(FACING);
        boolean isTop = state.getValue(TOP);
        
        IBlockState leftState = world.getBlockState(pos
                .offset(facing.rotateYCCW()));
        boolean isRight = leftState.getBlock() == this &&
                leftState.getValue(FACING) == facing;
        
        IBlockState rightState = world.getBlockState(pos
                .offset(facing.rotateY()));
        boolean isLeft = rightState.getBlock() == this &&
                rightState.getValue(FACING) == facing;
        
        state = state.withProperty(PART, EPartDoor
                .get(isTop, isLeft, isRight, state.getValue(OPEN)));
        
        EVaultAbove vault = EVaultAbove.NONE;
        BlockPos posUp = pos.up();
        IBlockState upState = world.getBlockState(posUp);
        
        if (isTop && upState.getBlock() instanceof BlockVault) {
    
            EnumFacing upFacing = upState.getActualState(world, posUp)
                    .getValue(FACING);
    
            if (upFacing == facing.rotateY()) {
                
                vault = EVaultAbove.LEFT;
                
            } else if (upFacing == facing.rotateYCCW()) {
                
                vault = EVaultAbove.RIGHT;
            }
        }
    
        return state.withProperty(VAULT, vault);
    }

    @Override
    public BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this, FACING, OPEN, TOP, PART, VAULT);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        
        int meta = state.getValue(FACING).getHorizontalIndex();
        
        if (state.getValue(TOP)) {
            
            meta |= 4;
        }
        
        if (state.getValue(OPEN)) {
            
            meta |= 8;
        }
        
        return meta;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        
        IBlockState state = this.getDefaultState();
        
        state = state.withProperty(OPEN, ((meta & 8) > 0));
        state = state.withProperty(TOP, ((meta & 4) > 0));
        state = state.withProperty(FACING, EnumFacing.getHorizontal(meta));
        return state;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        state = this.getActualState(state, world, pos);
        int facing = state.getValue(FACING).getHorizontalIndex();
        EPartDoor part = state.getValue(PART);
        boolean open = state.getValue(OPEN);
        
        if (!open) {

            return DOOR_CLOSED[facing];
        }
        
        return part.isLeft() ? DOOR_OPEN_LEFT[facing] : DOOR_OPEN_RIGHT[facing];
    }
    
    /** Enum defining vault extensions. */
    public enum EVaultAbove implements IStringSerializable {
        
        LEFT("left"), RIGHT("right"), NONE("none");
        
        private final String name;
        
        private EVaultAbove(String name) {
            
            this.name = name;
        }
        
        @Override
        public String getName() {
            
            return this.name;
        }
    }
    
    /** Enum defining part and position of door blocks. */
    public enum EPartDoor implements IStringSerializable {
        
        SBC("sbc", false), STC("stc", true), RBC("rbc", false),
        RTC("rtc", true), LBC("lbc", false), LTC("ltc", true),
        SBO("sbo", false), STO("sto", true), RBO("rbo", false),
        RTO("rto", true), LBO("lbo", false), LTO("lto", true);
        
        private final String name;
        /** Whether this part is the top of a door structure. */
        private final boolean isTop;
        
        private EPartDoor(String name, boolean isTop) {
            
            this.isTop = isTop;
            this.name = name;
        }

        @Override
        public String getName() {

            return this.name;
        }
        
        public boolean isTop() {
            
            return this.isTop;
        }
        
        public boolean isLeft() {
            
            switch (this) {
                
                case LBC: case LTC: case LBO: case LTO:
                    return true;
                default:
                    return false;
            }
        }
        
        /** @return The EPartDoor defined by the given information. */
        public static EPartDoor get(boolean isTop, boolean isLeft,
                boolean isRight, boolean isOpen) {
            
            return isTop ? isLeft ? isOpen ? LTO : LTC : isRight ? isOpen ?
                    RTO : RTC : isOpen ? STO : STC : isLeft ? isOpen ? LBO :
                    LBC : isRight ? isOpen ? RBO : RBC : isOpen ? SBO : SBC;
        }
    }
}
