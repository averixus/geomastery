package com.jj.jjmod.blocks;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.utilities.BlockMaterial;
import com.jj.jjmod.utilities.IBuildingBlock;
import com.jj.jjmod.utilities.ToolType;
import com.sun.istack.internal.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Wall block (implementations: stone, brick). */
public class BlockWall extends BlockNew implements IBuildingBlock {
    
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");
    public static final PropertyEnum<EnumPosition> POSITION =
            PropertyEnum.create("position", EnumPosition.class);
    public static final PropertyEnum<EnumStraight> STRAIGHT =
            PropertyEnum.create("straight", EnumStraight.class);
    
    protected static final int maxTypeHeight = 6;
    
    /** Whether this wall is the double form. */
    protected final boolean isDouble;
    /** Whether this wall is classed as heavy. */
    protected final boolean isHeavy;
    /** The maximum height of this wall. */
    protected final int selfHeight;
    /** Whether this wall supports beams. */
    protected final boolean supportsBeam;
    
    /** Supplier for the wall item. */
    protected Supplier<Item> item;

    public BlockWall(BlockMaterial material, String name, float hardness,
            ToolType toolType, boolean isDouble, Supplier<Item> item,
            boolean isHeavy, int selfHeight, boolean supportsBeam) {
        
        super(material, name, null, hardness, toolType);
        this.isDouble = isDouble;
        this.isHeavy = isHeavy;
        this.selfHeight = selfHeight;
        this.supportsBeam = supportsBeam;
        this.item = item != null ? item : () -> Item.getItemFromBlock(this);
    }
    

    @Override
    public boolean isLight() {

        return true;
    }

    @Override
    public boolean isHeavy() {

        return this.isHeavy;
    }
    
    @Override
    public boolean isDouble() {
        
        return this.isDouble;
    }
    
    @Override
    public boolean supportsBeam() {
        
        return this.supportsBeam;
    }
    
    @Override
    public int quantityDropped(Random rand) {
        
        return this.isDouble ? 2 : 1;
    }
    
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        
        return this.item.get();
    }
    
    /** @return Whether this wall should connect to the given direction. */
    protected boolean isValidHorizontal(IBlockAccess world,
            BlockPos pos, EnumFacing direction) {
        
        IBlockState state = world.getBlockState(pos.offset(direction));
        Block block = state.getBlock();
        
        boolean sameType = false;
        
        if (this.isHeavy) {
            
            sameType = ModBlocks.HEAVY.contains(block);
            
        } else {
            
            sameType = ModBlocks.HEAVY.contains(block) ||
                    ModBlocks.LIGHT.contains(block);
        }
        
        boolean sameWall = false;
        
        if (block instanceof BlockWall) {
            
            BlockWall wallBlock = (BlockWall) block;
            sameWall = wallBlock.item.get() == this.item.get();
        }
        
        boolean door = false;
        
        if (block instanceof BlockDoor) {
            
            EnumFacing facing = state.getValue(BlockDoor.FACING);
            
            if (facing != direction && facing != direction.getOpposite()) {
                
                door = true;
            }
        }
        
        return sameType || sameWall || door;
    }

    /** @return Whether this has a valid foundation at the given pos. */
    protected boolean hasValidFoundation(IBlockAccess world, BlockPos pos) {

        Block block = world.getBlockState(pos.down()).getBlock();
                
        if (this.isHeavy) {
            
            boolean natural = ModBlocks.HEAVY.contains(block);

            if (this.isDouble || this.selfHeight == 1) {
                
                boolean built = false;
                
                if (block instanceof IBuildingBlock) {
                    
                    IBuildingBlock builtBlock =
                            (IBuildingBlock) block;
                    built = builtBlock.isDouble() && builtBlock.isHeavy();
                }

                return natural || built;
                
            } else {
                
                boolean built = false;
                
                if (block instanceof IBuildingBlock) {
                    
                    IBuildingBlock builtBlock =
                            (IBuildingBlock) block;
                    built = builtBlock.isHeavy();
                }
                
                return natural || built;
            }
            
        } else {
            
            boolean natural = ModBlocks.HEAVY.contains(block) ||
                    ModBlocks.LIGHT.contains(block);

            if (this.isDouble || this.selfHeight == 1) {
                
                boolean built = false;
                
                if (block instanceof IBuildingBlock) {
                    
                    IBuildingBlock builtBlock =
                            (IBuildingBlock) block;
                    built = builtBlock.isDouble();
                }
                
                return natural || built;
                
            } else {
                
                boolean built = block instanceof IBuildingBlock;
                        
                return natural || built;
            }
        }
    }
    
    /** @return Whether this is within the allowed height for its type. */
    protected boolean isBelowTypeHeight(World world, BlockPos pos) {

        int height = 0;
        Block block = world.getBlockState(pos.down()).getBlock();
        boolean sameType = block instanceof IBuildingBlock &&
                ((IBuildingBlock) block).isDouble() == this.isDouble;
        
        while (sameType && height <= maxTypeHeight + 1) {
            
            height++;
            pos = pos.down();
            block = world.getBlockState(pos).getBlock();
            sameType = block instanceof IBuildingBlock &&
                    ((IBuildingBlock) block).isDouble() == this.isDouble;
        }
        
        return height <= maxTypeHeight;
    }
    
    /** @return Whether this is within its own allowed height. */
    protected boolean isBelowSelfHeight(World world, BlockPos pos) {
                
        int height = 0;
        Block block = world.getBlockState(pos.down()).getBlock();
        boolean same = block == this;
        
        while (same && height <= this.selfHeight + 1) {
            
            height++;
            pos = pos.down();
            block = world.getBlockState(pos).getBlock();
            same = block == this;
        }
        
        return height <= this.selfHeight;
    }
    
    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        
        boolean belowTypeHeight = this.isBelowTypeHeight(world, pos);
        boolean belowSelfHeight = this.isBelowSelfHeight(world, pos);
        boolean foundation = this.hasValidFoundation(world, pos);

        return belowTypeHeight && belowSelfHeight && foundation;
    }
    
    /** Check position and break if invalid. */
    @Override
    public void neighborChanged(IBlockState state, World world,
            BlockPos pos, Block block, BlockPos unused) {
        
        if (!this.canPlaceBlockAt(world, pos)) {
            
            world.destroyBlock(pos, true);
        }
    }
    
    @Override
    public BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this, new IProperty[]
                {NORTH, EAST, SOUTH, WEST, POSITION, STRAIGHT});
    }
    
    @Override
    public int getMetaFromState(IBlockState state) {
        
        return 0;
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta) {
        
        return this.getDefaultState();
    }
    
    @Override
    public IBlockState getActualState(IBlockState state,
            IBlockAccess world, BlockPos pos) {
                
        boolean north = this.isValidHorizontal(world, pos, EnumFacing.NORTH);
        boolean east = this.isValidHorizontal(world, pos, EnumFacing.EAST);
        boolean south = this.isValidHorizontal(world, pos, EnumFacing.SOUTH);
        boolean west = this.isValidHorizontal(world, pos, EnumFacing.WEST);
        
        boolean isBottom = !(this == world
                .getBlockState(pos.down()).getBlock());
        boolean isTop = world.getBlockState(pos.up()).getBlock() != this;

        state = state.withProperty(NORTH, north);
        state = state.withProperty(EAST, east);
        state = state.withProperty(SOUTH, south);
        state = state.withProperty(WEST, west);
        state = state.withProperty(POSITION,
                EnumPosition.get(isBottom, isTop));
        state = state.withProperty(STRAIGHT,
                EnumStraight.get(north, east, south, west));
        
        return state;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
                
        if (this.isDouble) {
            
            return FULL_BLOCK_AABB;

        } else {
            
            switch (this.getActualState(state, world, pos).getValue(STRAIGHT)) {
                
                case NS:
                    return CENTRE_HALF_LOW[0];
                    
                case EW:
                    return CENTRE_HALF_LOW[1];
                    
                default:
                    return CENTRE_POST;
            }           
        }
    }
    
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn,
            BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> list,
            @Nullable Entity entity, boolean unused) {
        
        state = this.getActualState(state, worldIn, pos);
        
        if (this.isDouble) {
            
            addCollisionBoxToList(pos, entityBox, list, FULL_BLOCK_AABB);
            return;
        }
        
        if (state.getValue(POSITION) == EnumPosition.TOP ||
                state.getValue(POSITION) == EnumPosition.LONE) {
        
            addCollisionBoxToList(pos, entityBox, list, CENTRE_POST_LOW);
            
            if (state.getValue(NORTH)) {
                
                addCollisionBoxToList(pos, entityBox, list, BRANCH_NORTH_LOW);
            }
            
            if (state.getValue(EAST)) {
                
                addCollisionBoxToList(pos, entityBox, list, BRANCH_EAST_LOW);
            }
            
            if (state.getValue(SOUTH)) {
                
                addCollisionBoxToList(pos, entityBox, list, BRANCH_SOUTH_LOW);
            }
            
            if (state.getValue(WEST)) {
                
                addCollisionBoxToList(pos, entityBox, list, BRANCH_WEST_LOW);
            }
            
        } else {
            
            addCollisionBoxToList(pos, entityBox, list, CENTRE_POST);
            
            if (state.getValue(NORTH)) {
                
                addCollisionBoxToList(pos, entityBox, list, BRANCH_NORTH);
            }
            
            if (state.getValue(EAST)) {
                
                addCollisionBoxToList(pos, entityBox, list, BRANCH_EAST);
            }
            
            if (state.getValue(SOUTH)) {
                
                addCollisionBoxToList(pos, entityBox, list, BRANCH_SOUTH);
            }
            
            if (state.getValue(WEST)) {
                
                addCollisionBoxToList(pos, entityBox, list, BRANCH_WEST);
            }
        }
    }
        
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {

        return EnumBlockRenderType.MODEL;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {

        return BlockRenderLayer.CUTOUT_MIPPED;
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
    
    /** Enum defining possible straight directions of walls. */
    public enum EnumStraight implements IStringSerializable {
        
        NO("no"), NS("ns"), EW("ew");
        
        private String name;
        
        private EnumStraight(String name) {
            
            this.name = name;
        }

        @Override
        public String getName() {

            return this.name;
        }

        /** @return The EnumStraight according to the given properties. */
        public static EnumStraight get(boolean north, boolean east,
                boolean south, boolean west) {

            if (north && south && !east && !west) {
                
                return NS;
                
            } else if (!north && !south && east && west) {
                
                return EW;
 
            } else {
                
                return NO;
            }
        }
        
        /** @return The EnumStraight for walls which are always
         * straight or crossed, according to the given properties. */
        public static EnumStraight getStraight(boolean north, boolean east,
                boolean south, boolean west) {
            
            if ((north || south) && !east && !west) {
                
                return NS;
                
            } else if ((east || west) && !north && !south) {
                
                return EW;
                
            } else {
                
                return NO;                
            }
        }
    }
}
