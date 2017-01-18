package com.jj.jjmod.blocks;

import java.util.Random;
import java.util.function.Supplier;
import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.utilities.BlockMaterial;
import com.jj.jjmod.utilities.IBuildingBlock;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockWall extends BlockNew implements IBuildingBlock {
    
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");
    public static final PropertyEnum POSITION =
            PropertyEnum.create("position", EnumPosition.class);
    public static final PropertyEnum STRAIGHT =
            PropertyEnum.create("straight", EnumStraight.class);
    
    public final boolean isDouble;
    public final boolean isHeavy;
    public final int selfHeight;
    
    protected Supplier<Item> item;

    public BlockWall(BlockMaterial material, String name, float hardness,
            ToolType toolType, boolean isDouble, Supplier<Item> item,
            boolean isHeavy, int selfHeight) {
        
        super(material, name, null, hardness, toolType);
        this.isDouble = isDouble;
        this.isHeavy = isHeavy;
        this.selfHeight = selfHeight;
        
        if (this.item != null) {
        
            this.item = item;
            
        } else {
            
            this.item = () -> Item.getItemFromBlock(this);
        }
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
    public int quantityDropped(Random rand) {
        
        return this.isDouble ? 2 : 1;
    }
    
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        
        return this.item.get();
    }
    
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

    protected boolean hasValidFoundation(IBlockAccess world, BlockPos pos) {

        System.out.println("checking for foundation");
        Block block = world.getBlockState(pos.down()).getBlock();
        System.out.println("checking block" + block);
                
        if (this.isHeavy) {
            
            boolean natural = ModBlocks.HEAVY.contains(block);
            System.out.println("natural heavy? " + natural);
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
    
    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        
        boolean belowTypeHeight = this.isBelowTypeHeight(world, pos);
        boolean belowSelfHeight = this.isBelowSelfHeight(world, pos);
        boolean foundation = this.hasValidFoundation(world, pos);

        return belowTypeHeight && belowSelfHeight && foundation;
    }
    
    @Override
    public void neighborChanged(IBlockState state, World world,
            BlockPos pos, Block blockIn, BlockPos unused) {
        
        if (!this.canPlaceBlockAt(world, pos)) {
            
            world.destroyBlock(pos, true);
        }
    }
    
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
    
    protected boolean isBelowTypeHeight(World world, BlockPos pos) {

        int height = 0;
        Block block = world.getBlockState(pos.down()).getBlock();
        boolean sameType = block instanceof IBuildingBlock &&
                ((IBuildingBlock) block).isDouble() == this.isDouble;
        
        while (sameType && height <= 6 + 1) {
            
            height++;
            pos = pos.down();
            block = world.getBlockState(pos).getBlock();
            sameType = block instanceof IBuildingBlock &&
                    ((IBuildingBlock) block).isDouble() == this.isDouble;
        }
        
        return height <= 6;
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
    public EnumBlockRenderType getRenderType(IBlockState state) {

        return EnumBlockRenderType.MODEL;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {

        return BlockRenderLayer.CUTOUT_MIPPED;
    }
    
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
