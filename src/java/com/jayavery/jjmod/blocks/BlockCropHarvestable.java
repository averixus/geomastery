package com.jayavery.jjmod.blocks;

import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import com.jayavery.jjmod.init.ModItems;
import com.jayavery.jjmod.items.ItemJj;
import net.minecraft.block.Block ;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeForest;
import net.minecraft.world.biome.BiomeJungle;
import net.minecraft.world.biome.BiomeOcean;
import net.minecraft.world.biome.BiomePlains;
import net.minecraft.world.biome.BiomeSavanna;

/** Harvestable crop blocks. */
public abstract class BlockCropHarvestable extends BlockCrop {
    
    /** Maximum permitted height for this crop. */
    protected int maxHeight;

    public BlockCropHarvestable(String name, int maxHeight,
            Supplier<Item> cropRef, Function<Random, Integer> yieldRef,
            float growthRate, float hardness) {
        
        super(name, cropRef, yieldRef, growthRate, hardness, null);
        this.maxHeight = maxHeight;
    }
    
    @Override
    protected void grow(World world, BlockPos pos,
            IBlockState state, Random rand) {
        
        if (state.getValue(AGE) == 7) {
            
            this.growUp(world, pos);
        }
        
        super.grow(world, pos, state, rand);
    }
    
    /** Harvests the crop's items if full grown. */
    @Override
    public boolean onBlockActivated(World world, BlockPos pos,
            IBlockState state, EntityPlayer player, EnumHand hand,
            EnumFacing side, float x, float y, float z) {
        
        int age = state.getValue(AGE);
        
        if (age != 7) {
            
            return false;
        }
        
        if (!this.canStay(world, pos)) {
            
            world.setBlockToAir(pos);
            return false;
        }
            
        IBlockState newState = state.withProperty(AGE, 0);
        world.setBlockState(pos, newState);
        
        if (!world.isRemote) {
            
            spawnAsEntity(world, pos, ItemJj.newStack(this.cropRef.get(),
                    this.yieldRef.apply(world.rand), world));
        }
        
        return true;
    }
    
    @Override
    public boolean canStay(World world, BlockPos pos) {
        
        BlockPos downPos = pos.down();
        Block downBlock = world.getBlockState(downPos).getBlock();
        return super.canStay(world, pos) || downBlock == this;
    }
    
    /** Attempts to grow another block of this Crop above. */
    protected void growUp(World world, BlockPos pos) {
        
        if (!world.isAirBlock(pos.up())) {
            
            return;
        }
        
        int height;
        
        for (height = 1; world.getBlockState(pos.down(height))
                .getBlock() == this; height++) {}
        
        if (height < this.maxHeight) {
            
            world.setBlockState(pos.up(), this.getDefaultState());
        }
    }
    
    public static class Bean extends BlockCropHarvestable {
        
        public Bean() {
            
            super("bean", 3, () -> ModItems.bean, (rand) -> 3, 0.4F, 0.2F);
        }

        @Override
        public boolean isPermitted(Biome biome) {

            return biome instanceof BiomeForest || biome == Biomes.RIVER ||
                    biome instanceof BiomePlains || biome == Biomes.BEACH ||
                    biome instanceof BiomeJungle;
        }
    }
    
    public static class Tomato extends BlockCropHarvestable {
        
        public Tomato() {
            
            super("tomato", 2, () -> ModItems.tomato, (rand) -> 1, 0.4F, 0.2F);
        }

        @Override
        public boolean isPermitted(Biome biome) {

            return biome instanceof BiomeForest || biome == Biomes.RIVER ||
                    biome instanceof BiomePlains || biome == Biomes.BEACH ||
                    biome instanceof BiomeJungle;
        }
    }
    
    public static class Berry extends BlockCropHarvestable {
        
        public Berry() {
            
            super("berry", 2, () -> ModItems.berry, (rand) -> 8, 0.4F, 0.2F);
        }

        @Override
        public boolean isPermitted(Biome biome) {

            return biome instanceof BiomeForest || biome == Biomes.RIVER ||
                    biome instanceof BiomeOcean ||
                    biome instanceof BiomePlains ||
                    biome instanceof BiomeJungle ||
                    biome instanceof BiomeSavanna;
        }
    }
}
