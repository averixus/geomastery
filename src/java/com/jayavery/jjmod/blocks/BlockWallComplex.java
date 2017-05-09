package com.jayavery.jjmod.blocks;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jayavery.jjmod.utilities.BlockWeight;
import com.jayavery.jjmod.utilities.IDelayedMultipart;
import com.jayavery.jjmod.utilities.IDoublingBlock;
import com.jayavery.jjmod.utilities.ToolType;
import com.jayavery.jjmod.utilities.UnlistedPropertyEnum;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

/** Adaptive wall building block. */
public class BlockWallComplex extends BlockBuilding 
        implements IDoublingBlock, IDelayedMultipart {
        
    public static final UnlistedPropertyEnum<EnumConnection> NORTH =
            new UnlistedPropertyEnum<EnumConnection>("north", EnumConnection.class);
    public static final UnlistedPropertyEnum<EnumConnection> EAST =
            new UnlistedPropertyEnum<EnumConnection>("east", EnumConnection.class);
    public static final UnlistedPropertyEnum<EnumConnection> SOUTH =
            new UnlistedPropertyEnum<EnumConnection>("south", EnumConnection.class);
    public static final UnlistedPropertyEnum<EnumConnection> WEST =
            new UnlistedPropertyEnum<EnumConnection>("west", EnumConnection.class);
    public static final UnlistedPropertyEnum<EnumPosition> POSITION =
            new UnlistedPropertyEnum<EnumPosition>("position", EnumPosition.class);
    
    /** Convenience map of EnumFacing to connection properties. */
    public static final Map<EnumFacing, UnlistedPropertyEnum<EnumConnection>>
            directionProperties = Maps.newHashMap();
    static {
        directionProperties.put(EnumFacing.NORTH, NORTH);
        directionProperties.put(EnumFacing.EAST, EAST);
        directionProperties.put(EnumFacing.SOUTH, SOUTH);
        directionProperties.put(EnumFacing.WEST, WEST);
    }
    
    /** The item this block drops. */
    protected final Supplier<Item> item;
    /** Whether this block is double. */
    protected final boolean isDouble;
    /** Delayed multipart loader for this block. */
    protected final Supplier<ICustomModelLoader> loader;

    public BlockWallComplex(Material material, String name, float hardness,
            ToolType harvestTool, boolean isDouble, Supplier<Item> item,
            Supplier<ICustomModelLoader> loader) {
        
        super(material, name, CreativeTabs.BUILDING_BLOCKS,
                hardness, harvestTool);
        this.item = item;
        this.isDouble = isDouble;
        this.loader = loader;
    }
    
    @Override
    public ICustomModelLoader getLoader() {
        
        return this.loader.get();
    }
    
    @Override
    public BlockWeight getWeight() {

        return BlockWeight.HEAVY;
    }
    
    @Override
    public boolean shouldDouble(IBlockState state, EnumFacing side) {
        
        return side != EnumFacing.UP;
    }
    
    @Override
    public boolean isDouble() {
        
        return this.isDouble;
    }
    
    /** @return Whether this wall should connect to the given direction. */
    protected boolean hasConnection(IBlockAccess world,
            BlockPos pos, EnumFacing direction) {
        
        IBlockState state = world.getBlockState(pos.offset(direction));
        Block block = state.getBlock();
        
        if (!(block instanceof BlockBuilding)) {
        
            return BlockWeight.getWeight(block) != BlockWeight.NONE;
        }
        
        BlockBuilding building = (BlockBuilding) block;
        return building.shouldConnect(world, state, pos,
                direction.getOpposite());
    }
    
    /** @return How this wall should connect to the given direction. */
    protected EnumConnection connectionType(IBlockAccess world,
            BlockPos pos, EnumFacing direction) {
        
        if (!this.hasConnection(world, pos, direction)) {
            
            return EnumConnection.NONE;
        }
                
        BlockPos posAbove = pos.up();
        IBlockState stateAbove = world.getBlockState(posAbove);
        Block blockAbove = stateAbove.getBlock();
        
        boolean hasAbove = false;

        if (blockAbove instanceof BlockWallComplex) {
            
            hasAbove = this.hasConnection(world, posAbove, direction);
        }
        
        BlockPos posBelow = pos.down();
        IBlockState stateBelow = world.getBlockState(posBelow);
        Block blockBelow = stateBelow.getBlock();
        
        boolean hasBelow = false;
        
        if (blockBelow instanceof BlockWallComplex) {
            
            hasBelow = this.hasConnection(world, posBelow, direction);
        }
        
        BlockPos posSide = pos.offset(direction);
        IBlockState stateSide = world.getBlockState(posSide);
        Block blockSide = stateSide.getBlock();
        
        boolean sideDouble = this.isDouble();
        
        if (this.isDouble() && blockSide instanceof IDoublingBlock && 
                !((IDoublingBlock) blockSide).isDouble()) {
            
            sideDouble = false;
        }
        
        if (hasBelow) {
            
            if (sideDouble) {
            
                if (hasAbove) {
                    
                    return EnumConnection.MIDDLE_DOUBLE;
                    
                } else {
                    
                    return EnumConnection.TOP_DOUBLE;
                }
                
            } else {
                
                if (hasAbove) {
                    
                    return EnumConnection.MIDDLE_SINGLE;
                    
                } else {
                    
                    return EnumConnection.TOP_SINGLE;
                }
            }
            
        } else {
            
            if (sideDouble) {
        
                if (hasAbove) {
                    
                    return EnumConnection.BOTTOM_DOUBLE;
                    
                } else {
                    
                    return EnumConnection.LONE_DOUBLE;
                }
                
            } else {
                
                if (hasAbove) {
                    
                    return EnumConnection.BOTTOM_SINGLE;
                    
                } else {
                    
                    return EnumConnection.LONE_SINGLE;
                }
            }
        }
    }
    
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        
        return state;
    }
    
    @Override
    public IBlockState getExtendedState(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        if (!(state instanceof IExtendedBlockState)) {
            
            return state;
        }
        
        IExtendedBlockState extState = (IExtendedBlockState) state;
        
        for (EnumFacing direction : directionProperties.keySet()) {
            
            extState = extState.withProperty(directionProperties.get(direction),
                    this.connectionType(world, pos, direction));
        }
        
        boolean isBottom = !(this == world
                .getBlockState(pos.down()).getBlock());
        boolean isTop = world.getBlockState(pos.up()).getBlock() != this;
        extState = extState.withProperty(POSITION,
                EnumPosition.get(isBottom, isTop));

        return extState;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        if (this.isDouble()) {
            
            return FULL_BLOCK_AABB;
            
        } else {
            
            return CENTRE_POST;
        }
    }
    
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn,
            BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> list,
            @Nullable Entity entity, boolean unused) {
        
        state = this.getExtendedState(state, worldIn, pos);
        
        if (!(state instanceof IExtendedBlockState)) {
            
            return;
        }
        
        IExtendedBlockState extState = (IExtendedBlockState) state;
        
        if (this.isDouble()) {
            
            addCollisionBoxToList(pos, entityBox, list, FULL_BLOCK_AABB);
            return;
        }
        
        if (extState.getValue(POSITION) == EnumPosition.TOP ||
                extState.getValue(POSITION) == EnumPosition.LONE) {
        
            addCollisionBoxToList(pos, entityBox, list, CENTRE_POST_LOW);
            
            if (extState.getValue(NORTH) != EnumConnection.NONE) {
                
                addCollisionBoxToList(pos, entityBox, list, BRANCH_NORTH_LOW);
            }
            
            if (extState.getValue(EAST) != EnumConnection.NONE) {
                
                addCollisionBoxToList(pos, entityBox, list, BRANCH_EAST_LOW);
            }
            
            if (extState.getValue(SOUTH) != EnumConnection.NONE) {
                
                addCollisionBoxToList(pos, entityBox, list, BRANCH_SOUTH_LOW);
            }
            
            if (extState.getValue(WEST) != EnumConnection.NONE) {
                
                addCollisionBoxToList(pos, entityBox, list, BRANCH_WEST_LOW);
            }
            
        } else {
            
            addCollisionBoxToList(pos, entityBox, list, CENTRE_POST);
            
            if (extState.getValue(NORTH) != EnumConnection.NONE) {
                
                addCollisionBoxToList(pos, entityBox, list, BRANCH_NORTH);
            }
            
            if (extState.getValue(EAST) != EnumConnection.NONE) {
                
                addCollisionBoxToList(pos, entityBox, list, BRANCH_EAST);
            }
            
            if (extState.getValue(SOUTH) != EnumConnection.NONE) {
                
                addCollisionBoxToList(pos, entityBox, list, BRANCH_SOUTH);
            }
            
            if (extState.getValue(WEST) != EnumConnection.NONE) {
                
                addCollisionBoxToList(pos, entityBox, list, BRANCH_WEST);
            }
        }
    }
    
    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos,
            IBlockState state, int fortune) {
        
        return Lists.newArrayList(new ItemStack(this.item.get(),
                this.isDouble() ? 2 : 1));
    }
    
    @Override
    public BlockStateContainer createBlockState() {
        
        return new ExtendedBlockState(this, new IProperty[0],
                new IUnlistedProperty[] {NORTH, EAST, SOUTH, WEST, POSITION});
    }
    
    @Override
    public int getMetaFromState(IBlockState state) {

        return 0;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {

        return this.getDefaultState();
    }
    
    /** Enum defining possible states of side connections. */
    public enum EnumConnection implements IStringSerializable {
        
        NONE("none"), LONE_SINGLE("lonesingle"), BOTTOM_SINGLE("bottomsingle"),
        TOP_SINGLE("topsingle"), MIDDLE_SINGLE("middlesingle"),
        LONE_DOUBLE("lonedouble"), BOTTOM_DOUBLE("bottomdouble"),
        TOP_DOUBLE("topdouble"), MIDDLE_DOUBLE("middledouble");
        
        private String name;
        
        private EnumConnection(String name) {
            
            this.name = name;
        }

        @Override
        public String getName() {

            return this.name;
        }
    }
    
    /** Enum defining possible positions for wall blocks. */
    public enum EnumPosition implements IStringSerializable {
        
        LONE("lone"), BOTTOM("bottom"), TOP("top"), MIDDLE("middle");
        
        private String name;
        
        private EnumPosition(String name) {
            
            this.name = name;
        }
        
        @Override
        public String getName() {
            
            return this.name;
        }

        /** @return The EnumPosition according to the given properties. */
        public static EnumPosition get(boolean isBottom, boolean isTop) {

            if (isBottom) {
                
                if (isTop) {
                    
                    return LONE;
                    
                } else {
                    
                    return BOTTOM;
                }
                
            } else {
                
                if (isTop) {
                    
                    return TOP;
                    
                } else {
                    
                    return MIDDLE;
                }
            }
        }
    }
}
