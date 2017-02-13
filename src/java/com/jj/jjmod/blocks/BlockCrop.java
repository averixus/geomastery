package com.jj.jjmod.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import com.jj.jjmod.utilities.IBiomeCheck;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

/** Abstract superclass for Crop blocks. */
public abstract class BlockCrop extends BlockNew
        implements IPlantable, IBiomeCheck {
    
    public static final PropertyInteger AGE =
            PropertyInteger.create("age", 0, 7);
    
    /** Bounding boxes indexed by crop age. */
    protected static final AxisAlignedBB[] CROP_BOUNDS = new AxisAlignedBB[]
            {new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), 
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)};
    
    /** Supplier for the harvested crop Item. */
    protected Supplier<Item> cropRef;
    /** Supplier for the planted seed Item. */
    protected Supplier<Item> seedRef;
    /** Random function for the crop yield. */
    protected Function<Random, Integer> yieldRef;
    /** Chance of growth per update tick. */
    protected float growthChance;
    /** Chance of death per update tick when in the wrong conditions. */
    protected float deathChance = 0.5F;

    public BlockCrop(String name, Supplier<Item> cropRef,
            Function<Random, Integer> function, float growthChance,
            float hardness, ToolType tool) {

        this(name, cropRef, cropRef, function, growthChance, hardness, tool);
    }

    public BlockCrop(String name, Supplier<Item> cropRef,
            Supplier<Item> seedRef, Function<Random, Integer> yieldRef,
            float growthChance, float hardness, ToolType tool) {

        super(Material.PLANTS, name, null, hardness, tool);
        this.cropRef = cropRef;
        this.seedRef = seedRef;
        this.yieldRef = yieldRef;
        this.growthChance = growthChance;
        this.setTickRandomly(true);
        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(AGE, 0));
    }
    
    @Override
    public void updateTick(World world, BlockPos pos,
            IBlockState state, Random rand) {
                
        if (!this.canStay(world, pos, state)) {
            
            world.setBlockToAir(pos);
        }
        
        if (!this.canGrow(world, pos, state) &&
                rand.nextFloat() <= this.deathChance) {
            
            Block below = world.getBlockState(pos.down()).getBlock();
            
            if (below == Blocks.FARMLAND ||
                    below == Blocks.GRASS || below == Blocks.DIRT) {
                
                world.setBlockState(pos, Blocks.DEADBUSH.getDefaultState());
            
            } else {
                
                world.setBlockToAir(pos);
            }
            
            return;
        }
       
        if (rand.nextFloat() <= this.getGrowthChance()) {
            
            this.grow(world, pos, state, rand);
        }
    }
    
    /** Ages up this Crop if it is less than full grown. */
    protected void grow(World world, BlockPos pos,
            IBlockState state, Random rand) {
        
        int oldAge = state.getValue(AGE);
        int newAge = Math.min(oldAge + 1, 7);
        IBlockState newState = state.withProperty(AGE, newAge);
        world.setBlockState(pos, newState, 2);
    }
    
    /** @return The chance of growth per update tick. */
    protected float getGrowthChance() {
        
        return this.growthChance;
    }
    
    @Override
    public void neighborChanged(IBlockState state, World world,
            BlockPos pos, Block block, BlockPos unused) {
        
        if (!this.canStay(world, pos, state)) {
            
            world.setBlockToAir(pos);
        }
    }
    
    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos,
            IBlockState state, int fortune) {
        
        List<ItemStack> items = new ArrayList<ItemStack>();
        items.add(new ItemStack(this.seedRef.get()));
        
        if (state.getValue(AGE) == 7) {
            
            items.add(new ItemStack(this.cropRef.get(),
                    this.yieldRef.apply(((World) world).rand)));
        }
        
        return items;
    }
    
    /** @return The IBlockState for this Crop when full grown. */
    public IBlockState getFullgrown() {
        
        return this.getDefaultState().withProperty(AGE, 7);
    }
    
    /** @return Whether this Crop can stay at this position. */
    protected boolean canStay(World world, BlockPos pos, IBlockState state) {
        
        BlockPos downPos = pos.down();
        Block downBlock = world.getBlockState(downPos).getBlock();
        return (downBlock == Blocks.DIRT ||
                downBlock == Blocks.GRASS || downBlock == Blocks.FARMLAND);
    }
    
    /** @return Whether this Crop can be planted at this position. */
    protected boolean canPlant(World world, BlockPos pos) {
        
        BlockPos downPos = pos.down();
        Block downBlock = world.getBlockState(downPos).getBlock();
        return downBlock == Blocks.FARMLAND;
    }
    
    /** @return Whether this Crop can grow at this posiiton. */
    protected boolean canGrow(World world, BlockPos pos, IBlockState state) {
        
        return this.canStay(world, pos, state) && world.canSeeSky(pos) &&
                this.isPermitted(world.getBiome(pos));
    }
    
    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
        
        IBlockState state = world.getBlockState(pos);
        
        if (state.getBlock() != this) {
            
            return getDefaultState();
        }
        
        return state;
    }
    
    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        
        return EnumPlantType.Crop;
    }
    
    @Override
    public BlockRenderLayer getBlockLayer() {
        
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
    
    @Override
    public BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this, new IProperty[] {AGE});
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta) {
        
        return this.getDefaultState().withProperty(AGE, meta);
    }
    
    @Override
    public int getMetaFromState(IBlockState state) {
        
        return state.getValue(AGE);
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        return CROP_BOUNDS[state.getValue(AGE)];
    }
    
    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        
        return this.canPlant(world, pos) && super.canPlaceBlockAt(world, pos);
    }
    
}