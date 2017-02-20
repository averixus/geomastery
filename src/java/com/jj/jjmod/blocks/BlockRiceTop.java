package com.jj.jjmod.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.init.ModItems;
import com.jj.jjmod.items.ItemJj;
import com.jj.jjmod.utilities.IBiomeCheck;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeJungle;
import net.minecraft.world.biome.BiomeMushroomIsland;
import net.minecraft.world.biome.BiomePlains;
import net.minecraft.world.biome.BiomeSwamp;

/** Rice crop top block. */
public class BlockRiceTop extends BlockNew implements IBiomeCheck {
    
    public static final PropertyInteger AGE =
            PropertyInteger.create("age", 0, 7);

    public BlockRiceTop() {
        
        super(Material.PLANTS, "rice_top", null, 0.2F, ToolType.SICKLE);
        this.setTickRandomly(true);
        this.setDefaultState(this.blockState
                .getBaseState().withProperty(AGE, 0));
    }
    
    @Override
    public BlockRenderLayer getBlockLayer() {
        
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
    
    @Override
    public void neighborChanged(IBlockState state, World world,
            BlockPos pos, Block block, BlockPos unused) {
        
        this.checkStay(world, state, pos);
    }
    
    /** Checks whether this block can stay in the
     * given position, drops it if not.
     * @return Whether this block stayed. */
    protected boolean checkStay(World world, IBlockState state, BlockPos pos) {
        
        if (world.getBlockState(pos.down()).getBlock() != ModBlocks.riceBase) {
            
            world.setBlockToAir(pos); 
            
            if (!world.isRemote) {

                this.dropBlockAsItem(world, pos, state, 0);
            }
            
            return false;
            
        } else {
        
            return true;
        }
    }
    
    @Override
    public List<ItemStack> getDrops(IBlockAccess blockAccess, BlockPos pos,
            IBlockState state, int fortune) {
        
        List<ItemStack> items = new ArrayList<ItemStack>();
        
        if (!(blockAccess instanceof World)) {
            
            return items;
        }
        
        if (state.getValue(AGE) == 7) {

            World world = (World) blockAccess;
            items.add(ItemJj.newStack(ModItems.rice, 1, world));
        }
        
        return items;
    }
    
    @Override
    public void updateTick(World world, BlockPos pos,
            IBlockState state, Random rand) {
        
        if (!this.checkStay(world, state, pos)) {
            
            return;
        }
        
        if (!this.canGrow(world, pos) && rand.nextFloat() <= 0.5) {
            
            world.setBlockToAir(pos);
            return;
        }
        

        int oldAge = state.getValue(AGE);
        int newAge = Math.min(oldAge + 1, 7);
        
        if (rand.nextFloat() <= 0.3) {
        
            IBlockState newState = state.withProperty(AGE, newAge);
            world.setBlockState(pos, newState);
        }
    }
    
    /** @return Whether this crop can grow at the given position. */
    protected boolean canGrow(World world, BlockPos pos) {
        
        return world.getLightFor(EnumSkyBlock.SKY, pos) >= 8 &&
                this.isPermitted(world.getBiome(pos));
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
            IBlockAccess source, BlockPos pos) {
        
        return CENTRE_SIXTEEN;
    }
    
    @Override
    public boolean isPermitted(Biome biome) {

        return biome instanceof BiomePlains || biome == Biomes.BEACH ||
                biome instanceof BiomeSwamp ||
                biome instanceof BiomeMushroomIsland ||
                biome instanceof BiomeJungle;
    }
}
