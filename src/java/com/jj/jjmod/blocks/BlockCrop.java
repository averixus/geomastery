package com.jj.jjmod.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import com.jj.jjmod.utilities.IBiomeCheck;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
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

public abstract class BlockCrop extends BlockNew implements IPlantable, IBiomeCheck {
    
    public static final PropertyInteger AGE =
            PropertyInteger.create("age", 0, 7);
    
    protected static final AxisAlignedBB[] CROP_BOUNDS = new AxisAlignedBB[]
            {new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), 
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)};
    
    protected Supplier<Item> cropRef;
    protected Supplier<Item> seedRef;
    protected int yield;
    protected float growthChance;

    public BlockCrop(String name, Supplier<Item> cropRef, int yield,
            float hardness, ToolType tool) {

        this(name, cropRef, cropRef, yield, hardness, tool);
    }

    public BlockCrop(String name, Supplier<Item> cropRef,
            Supplier<Item> seedRef, int yield, float hardness, ToolType tool) {

        super(Material.PLANTS, name, null, hardness, tool);
        this.cropRef = cropRef;
        this.seedRef = seedRef;
        this.yield = yield;
        this.setTickRandomly(true);
        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(AGE, 0));
    }
    
    @Override
    public void updateTick(World world, BlockPos pos,
            IBlockState state, Random rand) {
                
        if (!this.checkStay(world, pos, state)) {
            
            return;
        }
        
        if (!this.canGrow(world, pos, state) && rand.nextFloat() <= 0.5) {
            
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
    
    protected void grow(World world, BlockPos pos,
            IBlockState state, Random rand) {
        
        int oldAge = state.getValue(AGE);
        int newAge = (oldAge + 1) > 7 ? 7 : (oldAge + 1);
        IBlockState newState = state.withProperty(AGE, newAge);
        world.setBlockState(pos, newState, 2);
    }
    
    protected float getGrowthChance() {
        
        return this.growthChance;
    }
    
    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        
        return this.canPlant(world, pos) && super.canPlaceBlockAt(world, pos);
    }
    
    @Override
    public void neighborChanged(IBlockState state, World world,
            BlockPos pos, Block block, BlockPos unused) {
        
        this.checkStay(world, pos, state);
    }
    
    @Override
    protected BlockStateContainer createBlockState() {
        
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
    public BlockRenderLayer getBlockLayer() {
        
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
    
    public IBlockState getFullgrown() {
        
        return this.getDefaultState().withProperty(AGE, 7);
    }
    
    protected boolean checkStay(World world, BlockPos pos, IBlockState state) {
        
        if (!this.canStay(world, pos, state)) {
            
            world.setBlockToAir(pos);
            this.dropBlockAsItem(world, pos, state, 0);
            return false;
        }
        
        return true;
    }
    
    protected boolean canStay(World world, BlockPos pos, IBlockState state) {
        
        BlockPos downPos = pos.down();
        Block downBlock = world.getBlockState(downPos).getBlock();
        return (downBlock == Blocks.DIRT ||
                downBlock == Blocks.GRASS || downBlock == Blocks.FARMLAND);
    }
    
    protected boolean canPlant(World world, BlockPos pos) {
        
        BlockPos downPos = pos.down();
        Block downBlock = world.getBlockState(downPos).getBlock();
        return downBlock == Blocks.FARMLAND;
    }
    
    protected boolean canGrow(World world, BlockPos pos, IBlockState state) {
        
        return this.canStay(world, pos, state) && world.canSeeSky(pos) &&
                this.isPermitted(world.getBiome(pos));
    }
    
    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos,
            IBlockState state, int fortune) {
        
        List<ItemStack> items = new ArrayList<ItemStack>();
        
        if (state.getValue(AGE) == 7) {
            
            items.add(new ItemStack(this.seedRef.get()));
            items.add(new ItemStack(this.cropRef.get(), this.yield));
        }
        
        return items;
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
}