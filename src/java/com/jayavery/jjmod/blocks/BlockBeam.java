package com.jayavery.jjmod.blocks;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import com.jayavery.jjmod.tileentities.TEBeam;
import com.jayavery.jjmod.tileentities.TEBeam.EnumFloor;
import com.jayavery.jjmod.tileentities.TEBeam.EnumPartBeam;
import com.jayavery.jjmod.utilities.BlockMaterial;
import com.jayavery.jjmod.utilities.BlockWeight;
import com.jayavery.jjmod.utilities.IDelayedMultipart;
import com.jayavery.jjmod.utilities.IDoublingBlock;
import com.jayavery.jjmod.utilities.ToolType;
import com.jayavery.jjmod.utilities.UnlistedPropertyBool;
import com.jayavery.jjmod.utilities.UnlistedPropertyEnum;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.properties.IProperty;
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
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

/** Beam block. */
public class BlockBeam extends BlockBuilding implements IDelayedMultipart {

    public static final UnlistedPropertyEnum<EnumAxis> AXIS =
            new UnlistedPropertyEnum<EnumAxis>("axis", EnumAxis.class);
    public static final UnlistedPropertyBool FRONTBEAM =
            new UnlistedPropertyBool("frontbeam");
    public static final UnlistedPropertyBool BACKBEAM =
            new UnlistedPropertyBool("backbeam");
    public static final UnlistedPropertyEnum<EnumFloor> FLOOR =
            new UnlistedPropertyEnum<EnumFloor>
    ("floor", TEBeam.EnumFloor.class);
    public static final UnlistedPropertyBool LEFT =
            new UnlistedPropertyBool("left");
    public static final UnlistedPropertyBool RIGHT =
            new UnlistedPropertyBool("right");
    public static final UnlistedPropertyBool FRONT =
            new UnlistedPropertyBool("front");
    public static final UnlistedPropertyBool BACK =
            new UnlistedPropertyBool("back");
    public static final UnlistedPropertyBool FL =
            new UnlistedPropertyBool("fl");
    public static final UnlistedPropertyBool FR = 
            new UnlistedPropertyBool("fr");
    public static final UnlistedPropertyBool BL =
            new UnlistedPropertyBool("bl");
    public static final UnlistedPropertyBool BR =
            new UnlistedPropertyBool("br");
    
    private final Supplier<ICustomModelLoader> loader;
        
    public BlockBeam(String name, Supplier<ICustomModelLoader> loader) {
        
        super(BlockMaterial.WOOD_FURNITURE, name, null, 2F, ToolType.AXE);
        this.loader = loader;
    }
    
    @Override
    public ICustomModelLoader getLoader() {
        
        return this.loader.get();
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

        state = this.getExtendedState(state, world, pos);
        
        if (!(state instanceof IExtendedBlockState)) {
            
            return FULL_BLOCK_AABB;
        }
        
        IExtendedBlockState extState = (IExtendedBlockState) state;
        
        EnumFloor floor = extState.getValue(FLOOR);
        
        if (floor != EnumFloor.NONE) {
            
            return TOP_HALF;
        }
        
        EnumAxis axis = extState.getValue(AXIS);
        int ordinal = axis == null ? 0 : axis.ordinal();
        
        return BEAM[ordinal];
    }
    
    @Override
    public void addCollisionBoxToList(IBlockState state, World world,
            BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> list,
            @Nullable Entity entity, boolean unused) {
        
        state = this.getExtendedState(state, world, pos);
        
        if (!(state instanceof IExtendedBlockState)) {
            
            return;
        }
        
        IExtendedBlockState extState = (IExtendedBlockState) state;
        
        EnumAxis axis = extState.getValue(AXIS);
        int ordinal = axis == null ? 0 : axis.ordinal();
        
        addCollisionBoxToList(pos, entityBox, list, BEAM[ordinal]);
        
        if (extState.getValue(FLOOR) != EnumFloor.NONE) {
            
            addCollisionBoxToList(pos, entityBox, list, BEAM_FLOOR);
        }
    }

    @Override
    public BlockStateContainer createBlockState() {

        return new ExtendedBlockState(this, new IProperty[0],
                new IUnlistedProperty[] {AXIS, FRONTBEAM, BACKBEAM,
                FLOOR, FRONT, RIGHT, BACK, LEFT, FL, FR, BL, BR});
    }

    @Override
    public IBlockState getActualState(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        return state;
    }
    
    @Override
    public IBlockState getExtendedState(IBlockState state, IBlockAccess world,
            BlockPos pos) {

        TileEntity tileEntity = world.getTileEntity(pos);
        
        if (!(tileEntity instanceof TEBeam) ||
                !(state instanceof IExtendedBlockState)) {
            
            return state;
        }
        
        IExtendedBlockState extState = (IExtendedBlockState) state;
        TEBeam tileBeam = (TEBeam) tileEntity;
        EnumFacing facing = tileBeam.getFacing();
        EnumPartBeam part = tileBeam.getPart();
        EnumFloor floor = tileBeam.getFloor();
        
        extState = floor == null ? extState :
                extState.withProperty(FLOOR, floor);
        extState = facing == null ? extState :
                extState.withProperty(AXIS, EnumAxis.get(facing));
        
        EnumFacing frontFacing = extState.getValue(AXIS) == EnumAxis.NS ?
                EnumFacing.NORTH : EnumFacing.EAST;

        Block blockFront = world.getBlockState(pos.offset(frontFacing))
                .getBlock();
        boolean front = blockFront instanceof IDoublingBlock;
        Block blockBack = world.getBlockState(pos.offset(frontFacing
                .getOpposite())).getBlock();
        boolean back = blockBack instanceof IDoublingBlock;
        
        extState = extState.withProperty(FRONT, front);
        extState = extState.withProperty(BACK, back);
        
        boolean frontBeam = (part == EnumPartBeam.FRONT &&
                facing == frontFacing) || (part == EnumPartBeam.BACK &&
                facing == frontFacing.getOpposite());
        boolean backBeam = (part == EnumPartBeam.FRONT &&
                facing == frontFacing.getOpposite()) ||
                (part == EnumPartBeam.BACK && facing == frontFacing);
        
        extState = extState.withProperty(FRONTBEAM, frontBeam);
        extState = extState.withProperty(BACKBEAM, backBeam);
        
        if (extState.getValue(FLOOR) != EnumFloor.NONE) {
            
            Block blockRight = world.getBlockState(pos.offset(frontFacing
                    .rotateY())).getBlock();
            boolean right = blockRight instanceof IDoublingBlock;
            Block blockLeft = world.getBlockState(pos.offset(frontFacing
                    .rotateYCCW())).getBlock();
            boolean left = blockLeft instanceof IDoublingBlock;
            
            extState = extState.withProperty(RIGHT, right);
            extState = extState.withProperty(LEFT, left);
            extState = extState.withProperty(FL, front && left);
            extState = extState.withProperty(FR, front && right);
            extState = extState.withProperty(BL, back && left);
            extState = extState.withProperty(BR, back && right);
        }
        
        return extState;
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
