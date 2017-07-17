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
import java.util.function.Function;
import java.util.function.Supplier;
import jayavery.geomastery.items.ItemSimple;
import jayavery.geomastery.main.GeoItems;
import jayavery.geomastery.tileentities.TECrop;
import jayavery.geomastery.utilities.IBiomeCheck;
import jayavery.geomastery.utilities.EToolType;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeBeach;
import net.minecraft.world.biome.BiomeForest;
import net.minecraft.world.biome.BiomeHills;
import net.minecraft.world.biome.BiomeJungle;
import net.minecraft.world.biome.BiomeMushroomIsland;
import net.minecraft.world.biome.BiomeOcean;
import net.minecraft.world.biome.BiomePlains;
import net.minecraft.world.biome.BiomeSavanna;
import net.minecraft.world.biome.BiomeSwamp;
import net.minecraft.world.biome.BiomeTaiga;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

/** Crop blocks. */
public abstract class BlockCrop extends BlockNew
        implements IPlantable, IBiomeCheck {
    
    public static final PropertyInteger AGE =
            PropertyInteger.create("age", 0, 7);
    
    /** Chance of death per update tick when in wrong conditions. */
    protected static final float DEATH_CHANCE = 0.5F;
    /** Growth and harvest multiplier for crops on wet farmland. */
    protected static final float WET_MULTIPLIER = 1.5F;

    /** Supplier for the harvested crop Item. */
    protected final Supplier<Item> cropRef;
    /** Supplier for the planted seed Item. */
    protected final Supplier<Item> seedRef;
    /** Random function for the crop yield. */
    protected final Function<Random, Integer> yieldRef;
    /** Chance of growth per update tick. */
    protected final float growthChance;

    public BlockCrop(String name, Supplier<Item> cropRef,
            Supplier<Item> seedRef, Function<Random, Integer> yieldRef,
            float growthChance, float hardness, EToolType tool) {

        super(Material.PLANTS, name, null, hardness, tool);
        this.cropRef = cropRef;
        this.seedRef = seedRef;
        this.yieldRef = yieldRef;
        this.growthChance = growthChance;
        this.setTickRandomly(true);
        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(AGE, 0));
    }
    
    /** Convenience constructor for crops with seed == crop. */
    public BlockCrop(String name, Supplier<Item> cropRef,
            Function<Random, Integer> function, float growthChance,
            float hardness, EToolType tool) {

        this(name, cropRef, cropRef, function, growthChance, hardness, tool);
    }
    
    @Override
    public boolean hasTileEntity(IBlockState state) {
        
        return true;
    }
    
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        
        return new TECrop();
    }
    
    /** Checks validity of position (breaks if invalid),
     * grows or dies according to chance. */
    @Override
    public void updateTick(World world, BlockPos pos,
            IBlockState state, Random rand) {

        if (!this.canStay(world, pos)) {

            world.setBlockToAir(pos);
        }
        
        Block below = world.getBlockState(pos.down()).getBlock();
        
        if (!this.canGrow(world, pos, state) &&
                rand.nextFloat() <= DEATH_CHANCE) {

            
            if (below == Blocks.FARMLAND || below == Blocks.GRASS ||
                    below == Blocks.DIRT) {

                world.setBlockState(pos, Blocks.DEADBUSH.getDefaultState());
            
            } else {
                
                world.setBlockToAir(pos);
            }
            
        }
        
        float growthChance = below.isFertile(world, pos.down()) ? 
                this.growthChance * WET_MULTIPLIER : this.growthChance;
        
        TileEntity tileEntity = world.getTileEntity(pos);
        
        if (tileEntity instanceof TECrop) {
            
            growthChance *= ((TECrop) tileEntity).getMultiplier();
        }

        if (rand.nextFloat() <= growthChance) {

            this.grow(world, pos, state, rand);
        }
    }
    
    /** Ages up this crop if it is less than full grown. */
    protected void grow(World world, BlockPos pos,
            IBlockState state, Random rand) {
        
        int oldAge = state.getValue(AGE);
        IBlockState newState = state.withProperty(AGE, Math.min(oldAge + 1, 7));
        world.setBlockState(pos, newState, 2);
    }
    
    /** @return The IBlockState for this crop when full grown. */
    public IBlockState getFullgrown() {
        
        return this.getDefaultState().withProperty(AGE, 7);
    }
    
    /** @return Whether this crop can stay at its position. */
    public boolean canStay(World world, BlockPos pos) {
        
        Block downBlock = world.getBlockState(pos.down()).getBlock();
        return (downBlock == Blocks.DIRT || downBlock == Blocks.GRASS ||
                downBlock == Blocks.FARMLAND);
    }
    
    /** @return Whether this crop can be planted at this position. */
    protected boolean canPlant(World world, BlockPos pos) {
        
        return world.getBlockState(pos.down()).getBlock() == Blocks.FARMLAND;
    }
    
    /** @return Whether this crop can grow at this posiiton. */
    protected boolean canGrow(World world, BlockPos pos, IBlockState state) {
        
        return this.canStay(world, pos) && world.canSeeSky(pos) &&
                this.isPermitted(world.getBiome(pos));
    }
    
    /** Check validity of position and break if invalid. */
    @Override
    public void neighborChanged(IBlockState state, World world,
            BlockPos pos, Block block, BlockPos unused) {
        
        if (!this.canStay(world, pos)) {
            
            world.destroyBlock(pos, true);
        }
    }
    
    /** @return Crop and seed items to harvest. */
    @Override
    public List<ItemStack> getDrops(IBlockAccess blockAccess, BlockPos pos,
            IBlockState state, int fortune) {
        
        List<ItemStack> items = new ArrayList<ItemStack>();
        
        if (!(blockAccess instanceof World)) {
            
            return items;
        }
        
        World world = (World) blockAccess;
        
        float yieldMultiplier = world.getBlockState(pos.down()).getBlock()
                .isFertile(world, pos.down()) ? WET_MULTIPLIER : 1;
                
        TileEntity tileEntity = world.getTileEntity(pos);
        
        if (tileEntity instanceof TECrop) {
            
            yieldMultiplier *= ((TECrop) tileEntity).getMultiplier();
        }
        
        if (state.getValue(AGE) == 7) {
            
            items.add(ItemSimple.newStack(this.cropRef.get(),
                    (int) (this.yieldRef.apply(world.rand) * yieldMultiplier),
                    world));
            items.add(ItemSimple.newStack(this.seedRef.get(), 2, world));
        }
        
        return items;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        return CENTRE_SIXTEEN;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        return NULL_AABB;
    }
    
    /** @return Plant blockstate for IPlantable. */
    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
        
        IBlockState state = world.getBlockState(pos);
        
        if (state.getBlock() != this) {
            
            return this.getDefaultState();
        }
        
        return state;
    }
    
    /** @return Plan type for IPlantable. */
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
        
        return new BlockStateContainer(this, AGE);
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
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        
        return this.canPlant(world, pos) && super.canPlaceBlockAt(world, pos);
    }
    
    /** Wheat crop block. */
    public static class Wheat extends BlockCrop {
        
        public Wheat() {
            
            super("wheat", () -> GeoItems.WHEAT, (rand) -> 0,
                    0.2F, 0.2F, EToolType.SICKLE);
        }

        @Override
        public boolean isPermitted(Biome biome) {

            return (biome instanceof BiomeTaiga && biome != Biomes.COLD_TAIGA &&
                    biome != Biomes.COLD_TAIGA_HILLS &&
                    biome != Biomes.MUTATED_TAIGA_COLD) ||
                    biome instanceof BiomeForest ||
                    biome instanceof BiomePlains ||
                    biome == Biomes.BEACH;
        }
    }
    
    /** Potato crop block. */
    public static class Potato extends BlockCrop {
        
        public Potato() {
            
            super("potato", () -> GeoItems.POTATO, (rand) -> rand.nextInt(3),
                    0.15F, 0.2F, EToolType.SICKLE);
        }
        
        @Override
        public boolean isPermitted(Biome biome) {

            return biome instanceof BiomeTaiga || biome instanceof BiomeHills ||
                    biome == Biomes.STONE_BEACH ||
                    biome instanceof BiomeForest ||
                    biome == Biomes.RIVER || biome instanceof BiomePlains;
        }
    }
    
    /** Pepper crop block. */
    public static class Pepper extends BlockCrop {
        
        public Pepper() {
            
            super("pepper", () -> GeoItems.PEPPER, (rand) -> 2,
                    0.15F, 0.2F, EToolType.SICKLE);
        }

        @Override
        public boolean isPermitted(Biome biome) {

            return biome instanceof BiomeOcean || biome == Biomes.RIVER ||
                    biome instanceof BiomeBeach ||
                    biome instanceof BiomeForest ||
                    biome instanceof BiomePlains ||
                    biome instanceof BiomeJungle;
        }
    }
    
    /** Hemp crop block. */
    public static class Hemp extends BlockCrop {
        
        public Hemp() {
            
            super("hemp", () -> GeoItems.TWINE_HEMP,
                    () -> GeoItems.CUTTING_HEMP,
                    (rand) -> 1, 0.2F, 0.2F, EToolType.SICKLE);
        }

        @Override
        public boolean isPermitted(Biome biome) {

            return biome == Biomes.RIVER || biome instanceof BiomeOcean ||
                    biome instanceof BiomeForest ||
                    biome instanceof BiomePlains ||
                    biome instanceof BiomeJungle ||
                    biome instanceof BiomeSavanna;
        }
    }
    
    /** Beetroot crop block. */
    public static class Beetroot extends BlockCrop {
        
        public Beetroot() {
            
            super("beetroot", () -> GeoItems.BEETROOT,
                    (rand) -> rand.nextInt(3), 0.15F, 0.2F, EToolType.SICKLE);
        }

        @Override
        public boolean isPermitted(Biome biome) {

            return biome instanceof BiomeTaiga || biome instanceof BiomeHills ||
                    biome == Biomes.STONE_BEACH ||
                    biome instanceof BiomeForest ||
                    biome == Biomes.RIVER || biome instanceof BiomePlains;
        }
    }
    
    /** Carrot crop block. */
    public static class Carrot extends BlockCrop {
        
        public Carrot() {
            
            super("carrot", () -> GeoItems.CARROT, (rand) -> rand.nextInt(3),
                    0.15F, 0.2F, EToolType.SICKLE);
        }

        @Override
        public boolean isPermitted(Biome biome) {

            return biome instanceof BiomeTaiga || biome instanceof BiomeHills ||
                    biome == Biomes.STONE_BEACH ||
                    biome instanceof BiomeForest ||
                    biome == Biomes.RIVER || biome instanceof BiomePlains;
        }
    }
    
    /** Chickpea crop block. */
    public static class Chickpea extends BlockCrop {
        
        public Chickpea() {
            
            super("chickpea", () -> GeoItems.CHICKPEAS, (rand) -> 1,
                    0.15F, 0.2F, EToolType.SICKLE);
        }

        @Override
        public boolean isPermitted(Biome biome) {

            return biome instanceof BiomeBeach ||
                    biome instanceof BiomePlains ||
                    biome instanceof BiomeSwamp ||
                    biome instanceof BiomeMushroomIsland ||
                    biome instanceof BiomeJungle ||
                    biome instanceof BiomeSavanna;
        }
    }
    
    /** Cotton crop block. */
    public static class Cotton extends BlockCrop {
        
        public Cotton() {
            
            super("cotton", () -> GeoItems.COTTON, () -> GeoItems.CUTTING_COTTON,
                    (rand) -> 1, 0.15F, 0.2F, EToolType.SICKLE);
        }

        @Override
        public boolean isPermitted(Biome biome) {

            return biome instanceof BiomePlains || biome == Biomes.BEACH ||
                    biome instanceof BiomeJungle;
        }
    }
}
