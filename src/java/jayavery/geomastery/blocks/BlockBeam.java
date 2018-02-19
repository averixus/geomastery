/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.blocks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import com.google.common.collect.Lists;
import jayavery.geomastery.items.ItemPlacing;
import jayavery.geomastery.tileentities.TEBeam;
import jayavery.geomastery.tileentities.TEBeam.ETypeFloor;
import jayavery.geomastery.tileentities.TEBeam.EPartBeam;
import jayavery.geomastery.utilities.BlockMaterial;
import jayavery.geomastery.utilities.EBlockWeight;
import jayavery.geomastery.utilities.IDoublingBlock;
import jayavery.geomastery.utilities.Lang;
import jayavery.geomastery.utilities.EToolType;
import jayavery.geomastery.utilities.UnlistedPropertyBool;
import jayavery.geomastery.utilities.UnlistedPropertyEnum;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
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
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

/** Beam block. */
public class BlockBeam extends BlockBuildingAbstract<ItemPlacing.Building> {

    public static final UnlistedPropertyEnum<EBeamAxis> AXIS = new UnlistedPropertyEnum<EBeamAxis>("axis", EBeamAxis.class);
    public static final UnlistedPropertyBool FRONTBEAM = new UnlistedPropertyBool("frontbeam");
    public static final UnlistedPropertyBool BACKBEAM = new UnlistedPropertyBool("backbeam");
    public static final UnlistedPropertyEnum<ETypeFloor> FLOOR = new UnlistedPropertyEnum<ETypeFloor>("floor", TEBeam.ETypeFloor.class);
    public static final UnlistedPropertyBool LEFT = new UnlistedPropertyBool("left");
    public static final UnlistedPropertyBool RIGHT = new UnlistedPropertyBool("right");
    public static final UnlistedPropertyBool FRONT = new UnlistedPropertyBool("front");
    public static final UnlistedPropertyBool BACK = new UnlistedPropertyBool("back");
    public static final UnlistedPropertyBool FL = new UnlistedPropertyBool("fl");
    public static final UnlistedPropertyBool FR = new UnlistedPropertyBool("fr");
    public static final UnlistedPropertyBool BL = new UnlistedPropertyBool("bl");
    public static final UnlistedPropertyBool BR = new UnlistedPropertyBool("br");
    
    /** Minimum length of this beam structure. */
    private final int minLength;
    /** Maximum length of this beam structure. */
    private final int maxLength;
            
    public BlockBeam(String name, int minLength, int maxLength, int stackSize) {
        
        super(BlockMaterial.WOOD_FURNITURE, name,
                CreativeTabs.BUILDING_BLOCKS, 2F, stackSize);
        this.minLength = minLength;
        this.maxLength = maxLength;
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
        
        return false;
    }
    
    @Override
    public boolean place(World world, BlockPos targetPos,
            EnumFacing targetSide, EnumFacing placeFacing,
            ItemStack stack, EntityPlayer player) {
                  
        // Get positions
        
        BlockPos posBack = targetPos.offset(targetSide);
        BlockPos posMiddle = posBack.offset(targetSide);
        ArrayList<BlockPos> middles = new ArrayList<BlockPos>();
        int length = 2;
        
        while (length <= this.maxLength &&
                world.getBlockState(posMiddle).getBlock()
                .isReplaceable(world, posMiddle) &&
                world.getBlockState(posMiddle.offset(targetSide)).getBlock()
                .isReplaceable(world, posMiddle.offset(targetSide))) {
    
            middles.add(posMiddle);
            posMiddle = posMiddle.offset(targetSide);
            length++;
        }
        
        BlockPos posFront = posMiddle;
        
        IBlockState frontEnd = world.getBlockState(posFront.offset(targetSide));
        IBlockState backEnd = world.getBlockState(targetPos);
        
        // Check validity of supports and length
                
        boolean frontValid = EBlockWeight.getWeight(frontEnd)
                .canSupport(this.getWeight(this.getDefaultState()));
        boolean backValid = EBlockWeight.getWeight(backEnd)
                .canSupport(this.getWeight(this.getDefaultState()));
        
        if (length < this.minLength || length > this.maxLength) {
            
            message(player, Lang.BUILDFAIL_BEAM);
            return false;
        }
        
        if (!frontValid || !backValid) {
    
            message(player, Lang.BUILDFAIL_SUPPORT);
            return false;
        }   
        
        // Check ends replaceable
        IBlockState stateBack = world.getBlockState(posBack);
        Block blockBack = stateBack.getBlock();
        boolean replaceableBack = blockBack.isReplaceable(world, posBack);
        
        IBlockState stateFront = world.getBlockState(posFront);
        Block blockFront = stateFront.getBlock();
        boolean replaceableFront = blockFront.isReplaceable(world, posFront);
        
        if (!replaceableBack || !replaceableFront) {
    
            message(player, Lang.BUILDFAIL_OBSTACLE);
            return false;
        }
        
        // Place blocks
        IBlockState state = this.getDefaultState();
        
        world.setBlockState(posBack, state);
        world.setBlockState(posFront, state);
        
        for (BlockPos mid : middles) {
            
            world.setBlockState(mid, state);
        }
        
        // Apply TE states
        ((TEBeam) world.getTileEntity(posBack))
                .setState(targetSide, EPartBeam.BACK);
        ((TEBeam) world.getTileEntity(posFront))
                .setState(targetSide, EPartBeam.FRONT);
        
        for (BlockPos mid : middles) {
            
            ((TEBeam) world.getTileEntity(mid))
                    .setState(targetSide, EPartBeam.MIDDLE);
        }
        
        return true;
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos,
            Block block, BlockPos other) {
    
        TileEntity tileEntity = world.getTileEntity(pos);
        
        if (!(tileEntity instanceof TEBeam)) {
            
            return;
        }
        
        TEBeam tileBeam = (TEBeam) tileEntity;
        EPartBeam part = tileBeam.getPart();
        EnumFacing facing = tileBeam.getFacing();
        
        boolean destroy = false;
    
        switch (part) { 
            
            case FRONT: {
                
                BlockPos posFront = pos.offset(facing);
                IBlockState stateFront = world.getBlockState(posFront);
                boolean validFront = EBlockWeight.getWeight(stateFront)
                        .canSupport(this.getWeight(state));
                
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
                boolean validBack = EBlockWeight.getWeight(stateBack)
                        .canSupport(this.getWeight(state));
                
                if (!validBack || !validFront) {
                    
                    destroy = true;
                }
                
                break;
            }
        }
        
        if (destroy) {
            
            world.setBlockToAir(pos);
            
            if (part.shouldDrop()) {
    
                spawnAsEntity(world, pos, new ItemStack(this.item));
            }
        }
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos,
            IBlockState state, @Nullable TileEntity te, ItemStack tool) {
        
        if (te instanceof TEBeam) {
        
            TEBeam tileBeam = (TEBeam) te;
            ETypeFloor floor = tileBeam.getFloor();
            
            if (floor == ETypeFloor.NONE) {
                
                world.setBlockToAir(pos);
              
            } else {
                
                tileBeam.removeFloor();
            }
        }
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos,
            IBlockState state, int fortune, TileEntity te,
            ItemStack stack, EntityPlayer player) {
        
        List<ItemStack> result = Lists.newArrayList();
        
        if (te instanceof TEBeam) {
            
            TEBeam tileBeam = (TEBeam) te;
            ETypeFloor floor = tileBeam.getFloor();
            
            if (floor == ETypeFloor.NONE) {
                
                if (tileBeam.getPart().shouldDrop()) {
                    
                    result.add(new ItemStack(this.item));
                }
              
            } else {
                
                result.add(new ItemStack(floor.getItem()));
            }
        }
        
        return result;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        
        return true;
    }
    
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        
        return new TEBeam();
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
        EPartBeam part = tileBeam.getPart();
        ETypeFloor floor = tileBeam.getFloor();
        
        extState = floor == null ? extState :
                extState.withProperty(FLOOR, floor);
        extState = facing == null ? extState :
                extState.withProperty(AXIS, EBeamAxis.get(facing));
        
        EnumFacing frontFacing = extState.getValue(AXIS) == EBeamAxis.NS ?
                EnumFacing.NORTH : EnumFacing.EAST;
    
        Block blockFront = world.getBlockState(pos.offset(frontFacing))
                .getBlock();
        boolean front = blockFront instanceof IDoublingBlock;
        Block blockBack = world.getBlockState(pos.offset(frontFacing
                .getOpposite())).getBlock();
        boolean back = blockBack instanceof IDoublingBlock;
        
        extState = extState.withProperty(FRONT, front);
        extState = extState.withProperty(BACK, back);
        
        boolean frontBeam = (part == EPartBeam.FRONT &&
                facing == frontFacing) || (part == EPartBeam.BACK &&
                facing == frontFacing.getOpposite());
        boolean backBeam = (part == EPartBeam.FRONT &&
                facing == frontFacing.getOpposite()) ||
                (part == EPartBeam.BACK && facing == frontFacing);
        
        extState = extState.withProperty(FRONTBEAM, frontBeam);
        extState = extState.withProperty(BACKBEAM, backBeam);
        
        if (extState.getValue(FLOOR) != ETypeFloor.NONE) {
            
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

    @Override
    public BlockStateContainer createBlockState() {
    
        return new ExtendedBlockState(this, new IProperty[0],
                new IUnlistedProperty[] {AXIS, FRONTBEAM, BACKBEAM,
                FLOOR, FRONT, RIGHT, BACK, LEFT, FL, FR, BL, BR});
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world,
            BlockPos pos) {

        state = this.getExtendedState(state, world, pos);
        
        if (!(state instanceof IExtendedBlockState)) {
            
            return FULL_BLOCK_AABB;
        }
        
        IExtendedBlockState extState = (IExtendedBlockState) state;
        
        ETypeFloor floor = extState.getValue(FLOOR);
        
        if (floor != ETypeFloor.NONE) {
            
            return TOP_HALF;
        }
        
        EBeamAxis axis = extState.getValue(AXIS);
        int ordinal = axis == null ? 0 : axis.ordinal();
        
        TileEntity tileEntity = world.getTileEntity(pos);
        
        if (tileEntity instanceof TEBeam && this.maxLength > 5) {
        
            return BEAM_THICK[ordinal];
            
        } else {
            
            return BEAM_THIN[ordinal];
        }
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
        
        EBeamAxis axis = extState.getValue(AXIS);
        int ordinal = axis == null ? 0 : axis.ordinal();
        
        TileEntity tileEntity = world.getTileEntity(pos);
        
        if (tileEntity instanceof TEBeam && this.maxLength > 5) {
        
            addCollisionBoxToList(pos, entityBox, list, BEAM_THICK[ordinal]);
            
        } else {
            
            addCollisionBoxToList(pos, entityBox, list, BEAM_THIN[ordinal]);
        }
        
        if (extState.getValue(FLOOR) != ETypeFloor.NONE) {
            
            addCollisionBoxToList(pos, entityBox, list, BEAM_FLOOR);
        }
    }

    /** Enum defining which axis the Beam structure is aligned on. */
    public enum EBeamAxis implements IStringSerializable {
        
        NS("ns"), EW("ew");
        
        private String name;
        
        private EBeamAxis(String name) {
            
            this.name = name;
        }
        
        @Override
        public String getName() {
            
            return this.name;
        }
        
        /** @return The EBeamAxis associated with the given direction. */
        public static EBeamAxis get(EnumFacing facing) {
            
            return (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH) ?
                    NS : EW;
        }
    }
}
