package com.jayavery.jjmod.blocks;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import com.jayavery.jjmod.tileentities.TEBeam;
import com.jayavery.jjmod.tileentities.TEBeam.EnumFloor;
import com.jayavery.jjmod.tileentities.TEBeam.EnumPartBeam;
import com.jayavery.jjmod.utilities.BlockMaterial;
import com.jayavery.jjmod.utilities.BlockWeight;
import com.jayavery.jjmod.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Beam block. */
public class BlockBeam extends BlockBuilding {

    public static final PropertyEnum<EnumAxis> AXIS =
            PropertyEnum.<EnumAxis>create("axis", EnumAxis.class);
    public static final PropertyEnum<EnumFloor> FLOOR =
            PropertyEnum.<TEBeam.EnumFloor>
            create("floor", TEBeam.EnumFloor.class);
    public static final PropertyBool LEFT = PropertyBool.create("left");
    public static final PropertyBool RIGHT = PropertyBool.create("right");
    public static final PropertyBool FRONT = PropertyBool.create("front");
    public static final PropertyBool BACK = PropertyBool.create("back");
    public static final PropertyBool FL = PropertyBool.create("fl");
    public static final PropertyBool FR = PropertyBool.create("fr");
    public static final PropertyBool BL = PropertyBool.create("bl");
    public static final PropertyBool BR = PropertyBool.create("br");
        
    public BlockBeam() {
        
        super(BlockMaterial.WOOD_FURNITURE, "beam", null, 2F, ToolType.AXE);
    }
    
    @Override
    public BlockWeight getWeight() {
        
        return BlockWeight.LIGHT;
    }
    
    @Override
    public boolean shouldConnect(IBlockAccess world, IBlockState state,
            BlockPos pos, EnumFacing direction) {
        
        return false;
    }
    
    @Override
    public boolean hasTileEntity(IBlockState state) {
        
        return true;
    }
    
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        
        return new TEBeam();
    }
    
    /** Drops are handled by TE. */
    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos,
            IBlockState state, int fortune) {
        
        return Collections.emptyList();
    }

    /** Checks if this block is still a valid part of a beam structure,
     * removes it if not. Override default BlockBuilding behaviour. */
    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos,
            Block block, BlockPos other) {
    
        TileEntity tileEntity = world.getTileEntity(pos);
        
        if (!(tileEntity instanceof TEBeam)) {
            
            return;
        }
        
        TEBeam tileBeam = (TEBeam) tileEntity;
        EnumPartBeam part = tileBeam.getPart();
        EnumFacing facing = tileBeam.getFacing();
        
        boolean destroy = false;

        switch (part) { 
            
            case FRONT: {
                
                BlockPos posFront = pos.offset(facing);
                IBlockState stateFront = world.getBlockState(posFront);
                Block blockFront = stateFront.getBlock();
                boolean validFront = BlockWeight.getWeight(blockFront)
                        .canSupport(this.getWeight());
                
                BlockPos posBack = pos.offset(facing.getOpposite());
                TileEntity tileBack = world.getTileEntity(posBack);
                boolean validBack = (tileBack instanceof TEBeam) &&
                        (((TEBeam) tileBack).getFacing() == facing);  
                
                if (!validBack || !validFront) {
                    
                    destroy = true;
                }
                
                break;
            }
            
            case MIDDLE: {
                
                BlockPos posFront = pos.offset(facing);
                TileEntity tileFront = world.getTileEntity(posFront);
                boolean validFront = (tileFront instanceof TEBeam) &&
                        (((TEBeam) tileFront).getFacing() == facing);   
                
                BlockPos posBack = pos.offset(facing.getOpposite());
                TileEntity tileBack = world.getTileEntity(posBack);
                boolean validBack = (tileBack instanceof TEBeam) &&
                        (((TEBeam) tileBack).getFacing() == facing);   
                
                if (!validBack || !validFront) {
                    
                    destroy = true;
                }
                
                break;
            }
            
            case BACK: {
                
                BlockPos posFront = pos.offset(facing);
                TileEntity tileFront = world.getTileEntity(posFront);
                boolean validFront = (tileFront instanceof TEBeam) &&
                        (((TEBeam) tileFront).getFacing() == facing);  
                
                BlockPos posBack = pos.offset(facing.getOpposite());
                IBlockState stateBack = world.getBlockState(posBack);
                Block blockBack = stateBack.getBlock();
                boolean validBack = BlockWeight.getWeight(blockBack)
                        .canSupport(this.getWeight());
                
                if (!validBack || !validFront) {
                    
                    destroy = true;
                }
                
                break;
            }
        }
        
        if (destroy) {
            
            world.setBlockToAir(pos);
            
            if (part.shouldDrop()) {

                spawnAsEntity(world, pos, new ItemStack(tileBeam.getItem()));
            }
        }
    }
    
    /** If this beam has a floor, removes the floor but leaves the block. */
    @Override
    public boolean removedByPlayer(IBlockState state, World world,
            BlockPos pos, EntityPlayer player, boolean willHarvest) {
        
        TileEntity tileEntity = world.getTileEntity(pos);
        
        if (!(tileEntity instanceof TEBeam)) {
            
            world.setBlockToAir(pos);
            return true;
        }
        
        TEBeam tileBeam = (TEBeam) tileEntity;
        EnumFloor floor = tileBeam.getFloor();
        
        if (floor == EnumFloor.NONE) {
            
            world.setBlockToAir(pos);
            
            if (tileBeam.getPart().shouldDrop()) {
                
                spawnAsEntity(world, pos, new ItemStack(tileBeam.getItem()));
            }
            
            return true;
          
        } else {
            
            spawnAsEntity(world, pos, new ItemStack(floor.getItem()));
            tileBeam.removeFloor();
            return false;
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world,
            BlockPos pos) {

        state = this.getActualState(state, world, pos);
        EnumFloor floor = state.getValue(FLOOR);
        
        if (floor != EnumFloor.NONE) {
            
            return TOP_HALF;
        }
        
        return BEAM[state.getValue(AXIS).ordinal()];
    }
    
    @Override
    public void addCollisionBoxToList(IBlockState state, World world,
            BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> list,
            @Nullable Entity entity, boolean unused) {
        
        state = this.getActualState(state, world, pos);
        
        addCollisionBoxToList(pos, entityBox, list,
                BEAM[state.getValue(AXIS).ordinal()]);
        
        if (state.getValue(FLOOR) != EnumFloor.NONE) {
            
            addCollisionBoxToList(pos, entityBox, list, BEAM_FLOOR);
        }
    }

    @Override
    public BlockStateContainer createBlockState() {

        return new BlockStateContainer(this, AXIS, FLOOR, FRONT, RIGHT,
                BACK, LEFT, FL, FR, BL, BR);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world,
            BlockPos pos) {

        TileEntity tileEntity = world.getTileEntity(pos);
        
        if (!(tileEntity instanceof TEBeam)) {
            
            return state;
        }
            
        TEBeam tileBeam = (TEBeam) tileEntity;
        
        state = tileBeam.getFloor() == null ?
                state : state.withProperty(FLOOR, tileBeam.getFloor());
        state = tileBeam.getFacing() == null ? state : state
                .withProperty(AXIS, EnumAxis.get(tileBeam.getFacing()));
        
        EnumFacing frontFacing = state.getValue(AXIS) == EnumAxis.NS ?
                EnumFacing.NORTH : EnumFacing.EAST;

        Block blockFront = world.getBlockState(pos.offset(frontFacing))
                .getBlock();
        boolean front = blockFront != this;
        Block blockBack = world.getBlockState(pos.offset(frontFacing
                .getOpposite())).getBlock();
        boolean back = blockBack != this;
        
        state = state.withProperty(FRONT, front);
        state = state.withProperty(BACK, back);
        
        if (state.getValue(FLOOR) != EnumFloor.NONE) {
            
            Block blockRight = world.getBlockState(pos.offset(frontFacing
                    .rotateY())).getBlock();
            boolean right = blockRight != this;
            Block blockLeft = world.getBlockState(pos.offset(frontFacing
                    .rotateYCCW())).getBlock();
            boolean left = blockLeft != this;
            
            state = state.withProperty(RIGHT, right);
            state = state.withProperty(LEFT, left);
            state = state.withProperty(FL, front && left);
            state = state.withProperty(FR, front && right);
            state = state.withProperty(BL, back && left);
            state = state.withProperty(BR, back && right);
        }
        
        return state;
    }
    
    /** Enum defining which axis the Beam structure is aligned on. */
    public enum EnumAxis implements IStringSerializable {
        
        NS("ns"), EW("ew");
        
        private String name;
        
        private EnumAxis(String name) {
            
            this.name = name;
        }
        
        @Override
        public String getName() {
            
            return this.name;
        }
        
        /** @return The EnumAxis associated with the given direction. */
        public static EnumAxis get(EnumFacing facing) {
            
            if (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH) {
                
                return NS;
                
            } else {
                
                return EW;
            }
        }
    }
}
