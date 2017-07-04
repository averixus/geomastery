/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.blocks;

import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import jayavery.geomastery.items.ItemSimple;
import jayavery.geomastery.main.GeoItems;
import jayavery.geomastery.tileentities.TECrop;
import net.minecraft.block.Block ;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
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
    protected final int maxHeight;

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
        
        float yieldMultiplier = world.getBlockState(pos.down()).getBlock()
                .isFertile(world, pos.down()) ? WET_MULTIPLIER : 1;
                
        TileEntity tileEntity = world.getTileEntity(pos);
        
        if (tileEntity instanceof TECrop) {
            
            yieldMultiplier *= ((TECrop) tileEntity).getMultiplier();
        }
        
        if (!world.isRemote) {
            
            spawnAsEntity(world, pos, ItemSimple.newStack(this.cropRef.get(),
                    (int) (this.yieldRef.apply(world.rand) * yieldMultiplier),
                    world));
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
            
            super("bean", 3, () -> GeoItems.BEAN, (rand) -> 3, 0.2F, 0.2F);
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
            
            super("tomato", 2, () -> GeoItems.TOMATO, (rand) -> 1, 0.2F, 0.2F);
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
            
            super("berry", 2, () -> GeoItems.BERRY, (rand) -> 8, 0.2F, 0.2F);
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
