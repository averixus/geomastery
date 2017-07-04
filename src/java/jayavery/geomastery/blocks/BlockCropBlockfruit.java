/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import com.google.common.collect.Lists;
import jayavery.geomastery.main.GeoBlocks;
import jayavery.geomastery.main.GeoItems;
import jayavery.geomastery.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeBeach;
import net.minecraft.world.biome.BiomeForest;
import net.minecraft.world.biome.BiomeHills;
import net.minecraft.world.biome.BiomeJungle;
import net.minecraft.world.biome.BiomePlains;
import net.minecraft.world.biome.BiomeSavanna;
import net.minecraft.world.biome.BiomeTaiga;

/** Block-fruiting crop blocks. */
public abstract class BlockCropBlockfruit extends BlockCrop {

    public static final PropertyDirection FACING = BlockTorch.FACING;
    
    /** Supplier for fruit block. */
    protected final Supplier<BlockFruit> fruit;
    /** Supplier for the seed item. */
    protected final Supplier<Item> seed;

    public BlockCropBlockfruit(String name, float growthChance, float hardness,
            Supplier<BlockFruit> fruit, Supplier<Item> seed) {

        super(name, () -> Items.AIR, (rand) -> 0,
                growthChance, hardness, ToolType.SICKLE);
        this.fruit = fruit;
        this.seed = seed;
    }
    
    @Override
    public BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this, AGE, FACING);
    }

    @Override
    public IBlockState getActualState(IBlockState state,
            IBlockAccess world, BlockPos pos) {

        int age = state.getValue(AGE);
        state = state.withProperty(FACING, EnumFacing.UP);

        for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
            
            if (world.getBlockState(pos.offset(enumfacing))
                    .getBlock() == this.fruit.get() && age == 7) {
                
                state = state.withProperty(FACING, enumfacing);
                break;
            }
        }

        return state;
    }

    @Override
    protected void grow(World world, BlockPos pos,
            IBlockState state, Random rand) {

        if (state.getValue(AGE) == 7) {

            this.growFruit(world, pos, rand);
        }

        super.grow(world, pos, state, rand);
    }

    /** Attempts to grow a fruit in an adjacent block. */
    protected void growFruit(World world, BlockPos pos, Random rand) {

        for (EnumFacing facing : EnumFacing.Plane.HORIZONTAL) {

            if (world.getBlockState(pos.offset(facing))
                    .getBlock() == this.fruit) {

                return;
            }
        }

        EnumFacing side = EnumFacing.Plane.HORIZONTAL.random(rand);
        BlockPos fruitPos = pos.offset(side);
        IBlockState soilState = world.getBlockState(fruitPos.down());
        Block soilBlock = soilState.getBlock();

        if (world.isAirBlock(fruitPos) && (soilBlock.canSustainPlant(soilState,
                world, pos.down(), EnumFacing.UP, this) ||
                soilBlock == Blocks.DIRT || soilBlock == Blocks.GRASS)) {

            world.setBlockState(fruitPos, this.fruit.get().getDefaultState()
                    .withProperty(BlockFruit.FACING, side.getOpposite()));
        }
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos,
            IBlockState state, int fortune) {
        
        return Lists.newArrayList(new ItemStack(this.seed.get()));
    }
    
    public static class Pumpkin extends BlockCropBlockfruit {
        
        public Pumpkin() {
            
            super("pumpkin_crop", 0.15F, 0.2F, () -> GeoBlocks.PUMPKIN_FRUIT,
                    () -> GeoItems.SEED_PUMPKIN);
        }

        @Override
        public boolean isPermitted(Biome biome) {

            return biome instanceof BiomeTaiga || biome instanceof BiomeHills ||
                    biome instanceof BiomeBeach ||
                    biome instanceof BiomeForest ||
                    biome instanceof BiomePlains || biome == Biomes.RIVER ||
                    biome instanceof BiomeJungle;
        }
    }
    
    public static class Melon extends BlockCropBlockfruit {
        
        public Melon() {
            
            super("melon_crop", 0.2F, 0.2F, () -> GeoBlocks.MELON_FRUIT,
                    () -> GeoItems.SEED_MELON);
        }

        @Override
        public boolean isPermitted(Biome biome) {

            return biome instanceof BiomePlains || biome == Biomes.BEACH ||
                    biome instanceof BiomeJungle ||
                    biome instanceof BiomeSavanna;
        }
    }
}
