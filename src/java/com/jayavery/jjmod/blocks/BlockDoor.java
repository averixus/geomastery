package com.jayavery.jjmod.blocks;

import java.util.Random;
import java.util.function.Supplier;
import com.jayavery.jjmod.utilities.BlockMaterial;
import com.jayavery.jjmod.utilities.IBuildingBlock;
import com.jayavery.jjmod.utilities.IMultipart;
import com.jayavery.jjmod.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
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
public class BlockDoor extends BlockNew implements IBuildingBlock {
    
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyBool OPEN = PropertyBool.create("open");
    public static final PropertyEnum<EnumPartDoor> PART =
            PropertyEnum.<EnumPartDoor>create("part", EnumPartDoor.class);
    
    /** Supplier for the door item. */
    private Supplier<Item> item;

    public BlockDoor(String name, Supplier<Item> item) {
        
        super(BlockMaterial.WOOD_FURNITURE, name, null, 2F, ToolType.AXE);
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
    public boolean supportsBeam() {
        
        return false;
    }
    
    @Override
    public boolean isShelter() {
        
        return false;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
       // return DOOR_CLOSED[state.getValue(FACING).getHorizontalIndex()];
        state = this.getActualState(state, world, pos);
        int facing = state.getValue(FACING).getHorizontalIndex();
        EnumPartDoor part = state.getValue(PART);
        boolean open = state.getValue(OPEN);
        
        if (!open) {

            return DOOR_CLOSED[facing];
        }
        
        if (part == EnumPartDoor.LT || part == EnumPartDoor.LB) {

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
        
        if (part == EnumPartDoor.LT || part == EnumPartDoor.LB) {

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
        
        BlockPos otherPos = thisState.getValue(PART).isTop() ?
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
    
    /** Checks position and breaks if invalid. */
    @Override
    public void neighborChanged(IBlockState thisState, World world,
            BlockPos thisPos, Block block, BlockPos unused) {
        
        BlockPos otherPos = thisState.getValue(PART).isTop() ?
                thisPos.down() : thisPos.up();
        IBlockState otherState = world.getBlockState(otherPos);
        
        if (otherState.getBlock() != this ||
                !this.canPlaceBlockAt(world, thisPos)) {
            
            world.setBlockToAir(thisPos);
            
            if (thisState.getValue(PART).isTop()) {
                
                spawnAsEntity(world, thisPos, new ItemStack(this.item.get()));
            }
            
        } else if (otherState.getValue(OPEN) != thisState.getValue(OPEN)) {
            
            thisState = thisState.withProperty(OPEN, otherState.getValue(OPEN));
            world.setBlockState(thisPos, thisState);
        }
    }
    
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        
        if (state.getValue(PART).isTop()) {
       
            return this.item.get();
            
        } else {
            
            return Items.AIR;
        }
    }
    
    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
                
        return world.getBlockState(pos.down())
                .isSideSolid(world, pos.down(), EnumFacing.UP) ||
                world.getBlockState(pos.down()).getBlock() == this;
    }
    
    @Override
    public IBlockState getActualState(IBlockState state,
            IBlockAccess world, BlockPos pos) {
                
        EnumFacing facing = state.getValue(FACING);
        boolean isTop = state.getValue(PART).isTop();
        
        IBlockState leftState = world.getBlockState(pos
                .offset(facing.rotateYCCW()));
        
        if (leftState.getBlock() == this &&
                leftState.getValue(FACING) == facing) {
            
            state = state.withProperty(PART, isTop ? EnumPartDoor.RT : EnumPartDoor.RB);
      
        }
        
        IBlockState rightState = world.getBlockState(pos
                .offset(facing.rotateY()));
        
        if (rightState.getBlock() == this &&
                rightState.getValue(FACING) == facing) {
            
            state = state.withProperty(PART, isTop ? EnumPartDoor.LT : EnumPartDoor.LB);
        }
        
        return state;
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta) {
        
        IBlockState state = this.getDefaultState();
        
        if ((meta & 8) > 0) {
            
            state = state.withProperty(OPEN, true);
        }
        
        if ((meta & 4) > 0) {
            
            state = state.withProperty(PART, EnumPartDoor.ST);
            
        } else {
            
            state = state.withProperty(PART, EnumPartDoor.SB);
        }
        
        state = state.withProperty(FACING, EnumFacing.getHorizontal(meta));
        return state;
    }
    
    @Override
    public int getMetaFromState(IBlockState state) {
        
        int meta = state.getValue(FACING).getHorizontalIndex();
        
        if (state.getValue(PART).isTop()) {
            
            meta |= 4;
        }
        
        if (state.getValue(OPEN)) {
            
            meta |= 8;
        }
        
        return meta;
    }
    
    @Override
    public BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this,
                new IProperty[]{FACING, OPEN, PART});
    }
    
    /** Enum defining part and position of door blocks. */
    public enum EnumPartDoor implements IStringSerializable {
        
        SB("sb", false), ST("st", true), RB("rb", false),
        RT("rt", true), LB("lb", false), LT("lt", true);
        
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
        
        /** @return Whether or not this part is the top of the door. */
        public boolean isTop() {
            
            return this.isTop;
        }
    }
}
