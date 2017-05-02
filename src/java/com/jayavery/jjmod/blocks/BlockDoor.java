package com.jayavery.jjmod.blocks;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import com.google.common.collect.Lists;
import com.jayavery.jjmod.utilities.BlockMaterial;
import com.jayavery.jjmod.utilities.BlockWeight;
import com.jayavery.jjmod.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Door block. */
public class BlockDoor extends BlockBuilding {
    
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyBool OPEN = PropertyBool.create("open");
    public static final PropertyBool TOP = PropertyBool.create("top");
    public static final PropertyEnum<EnumPartDoor> PART =
            PropertyEnum.<EnumPartDoor>create("part", EnumPartDoor.class);
    public static final PropertyEnum<VaultAbove> VAULT =
            PropertyEnum.create("vault", VaultAbove.class);
    
    /** Supplier for the door item. */
    protected Supplier<Item> item;

    public BlockDoor(String name, Supplier<Item> item) {
        
        super(BlockMaterial.WOOD_FURNITURE, name, CreativeTabs.DECORATIONS,
                2F, ToolType.AXE);
        this.item = item;
    }
    
    @Override
    public BlockWeight getWeight() {
        
        return BlockWeight.LIGHT;
    }
    
    @Override
    public boolean shouldConnect(IBlockAccess world, IBlockState state,
            BlockPos pos, EnumFacing direction) {
        
        EnumFacing facing = state.getValue(FACING);
        return facing != direction && facing != direction.getOpposite();
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        state = this.getActualState(state, world, pos);
        int facing = state.getValue(FACING).getHorizontalIndex();
        EnumPartDoor part = state.getValue(PART);
        boolean open = state.getValue(OPEN);
        
        if (!open) {

            return DOOR_CLOSED[facing];
        }
        
        if (part.isLeft()) {

            return DOOR_OPEN_LEFT[facing];
            
        } else {

            return DOOR_OPEN_RIGHT[facing];
        }
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        state = this.getActualState(state, world, pos);
        int facing = state.getValue(FACING).getHorizontalIndex();
        EnumPartDoor part = state.getValue(PART);
        boolean open = state.getValue(OPEN);
        
        if (!open) {

            return DOOR_CLOSED[facing];
        }
        
        if (part.isLeft()) {

            return DOOR_OPEN_LEFT[facing];
            
        } else {

            return DOOR_OPEN_RIGHT[facing];
        }
    }
    
    /** Checks position (breaks if invalid) and toggles door open. */
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
    
    /** Checks position and open state, changes or breaks if invalid. */
    @Override
    public void neighborChanged(IBlockState thisState, World world,
            BlockPos thisPos, Block block, BlockPos unused) {
        
        BlockPos otherPos = thisState.getValue(TOP) ?
                thisPos.down() : thisPos.up();
        IBlockState otherState = world.getBlockState(otherPos);
        
        if (otherState.getBlock() != this ||
                !this.isValid(world, thisPos)) {
            
            world.setBlockToAir(thisPos);
            
            if (thisState.getValue(TOP)) {
                
                spawnAsEntity(world, thisPos, new ItemStack(this.item.get()));
            }
            
        } else if (otherState.getValue(OPEN) != thisState.getValue(OPEN)) {
            
            thisState = thisState.withProperty(OPEN, otherState.getValue(OPEN));
            world.setBlockState(thisPos, thisState);
        }
    }
    
    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos,
            IBlockState state, int fortune) {
        
        state = this.getActualState(state, world, pos);
        
        if (state.getValue(PART).isTop()) {
       
            return Lists.newArrayList(new ItemStack(this.item.get()));
            
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
        
        state = state.withProperty(PART, EnumPartDoor
                .get(isTop, isLeft, isRight, state.getValue(OPEN)));
        
        VaultAbove vault = VaultAbove.NONE;
        BlockPos posUp = pos.up();
        IBlockState upState = world.getBlockState(posUp);
        
        if (isTop && upState.getBlock() instanceof BlockVault) {

            EnumFacing upFacing = upState.getActualState(world, posUp)
                    .getValue(BlockVault.FACING);

            if (upFacing == facing.rotateY()) {
                
                vault = VaultAbove.LEFT;
                
            } else if (upFacing == facing.rotateYCCW()) {
                
                vault = VaultAbove.RIGHT;
            }
        }

        return state.withProperty(VAULT, vault);
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta) {
        
        IBlockState state = this.getDefaultState();
        
        if ((meta & 8) > 0) {
            
            state = state.withProperty(OPEN, true);
        } 
            
        state = state.withProperty(TOP, ((meta & 4) > 0));
        state = state.withProperty(FACING, EnumFacing.getHorizontal(meta));
        return state;
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
    public BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this, FACING, OPEN, TOP, PART, VAULT);
    }
    
    /** Enum defining vault extensions. */
    public enum VaultAbove implements IStringSerializable {
        
        LEFT("left"), RIGHT("right"), NONE("none");
        
        private final String name;
        
        private VaultAbove(String name) {
            
            this.name = name;
        }
        
        @Override
        public String getName() {
            
            return this.name;
        }
    }
    
    /** Enum defining part and position of door blocks. */
    public enum EnumPartDoor implements IStringSerializable {
        
        SBC("sbc", false), STC("stc", true), RBC("rbc", false),
        RTC("rtc", true), LBC("lbc", false), LTC("ltc", true),
        SBO("sbo", false), STO("sto", true), RBO("rbo", false),
        RTO("rto", true), LBO("lbo", false), LTO("lto", true);
        
        private final String name;
        private final boolean isTop;
        
        private EnumPartDoor(String name, boolean isTop) {
            
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
                
                case LBC:
                case LTC:
                case LBO:
                case LTO:
                    return true;
                default:
                    return false;
            }
        }
        
        public static EnumPartDoor get(boolean isTop, boolean isLeft,
                boolean isRight, boolean isOpen) {
            
            if (isTop) {
                
                if (isLeft) {
                    
                    if (isOpen) {
                        
                        return LTO;
                        
                    } else {
                        
                        return LTC;
                    }
                    
                } else if (isRight) {
                    
                   if (isOpen) {
                       
                       return RTO;
                       
                   } else {
                       
                       return RTC;
                   }
                    
                } else {
                    
                    if (isOpen) {
                        
                        return STO;
                        
                    } else {
                        
                        return STC;
                    }
                }
                
            } else {
                
                if (isLeft) {
                    
                    if (isOpen) {
                        
                        return LBO;
                        
                    } else {
                        
                        return LBC;
                    }
                    
                } else if (isRight) {
                    
                   if (isOpen) {
                       
                       return RBO;
                       
                   } else {
                       
                       return RBC;
                   }
                    
                } else {
                    
                    if (isOpen) {
                        
                        return SBO;
                        
                    } else {
                        
                        return SBC;
                    }
                }
            }
        }
    }
}
