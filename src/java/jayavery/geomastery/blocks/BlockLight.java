/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.blocks;

import java.util.List;
import java.util.Random;
import com.google.common.collect.Lists;
import jayavery.geomastery.utilities.BlockWeight;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Torch and candle blocks. */
public abstract class BlockLight extends BlockBuilding {

    protected static final AxisAlignedBB FLAT_BOUNDS =
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.18D, 1.0D);
    public static final PropertyDirection FACING = PropertyDirection
            .create("facing", (f) -> f != EnumFacing.UP);
    
    /** Chance of extinguishing per update tick. */
    private final float extinguishChance;

    public BlockLight(String name, int light, float extinguish) {

        super(Material.CIRCUITS, name, CreativeTabs.DECORATIONS, 0, null);
        this.lightValue = light;
        this.extinguishChance = extinguish;
    }
    
    @Override
    public BlockWeight getWeight() {
        
        return BlockWeight.NONE;
    }
    
    @Override
    public boolean shouldConnect(IBlockAccess world, IBlockState state,
            BlockPos pos, EnumFacing direction) {
        
        return false;
    }
    
    @Override
    public void neighborChanged(IBlockState state, World world,
            BlockPos pos, Block block, BlockPos unused) {
        
        if (!BlockWeight.getWeight(world.getBlockState(pos
                .offset(state.getValue(FACING)))
                .getBlock()).canSupport(BlockWeight.NONE)) {

            world.destroyBlock(pos, true);
        }
    }
    
    @Override
    public boolean isValid(World world, BlockPos pos) {
        
        for (EnumFacing facing : FACING.getAllowedValues()) {
            
            if (BlockWeight.getWeight(world.getBlockState(pos
                    .offset(facing)).getBlock())
                    .canSupport(BlockWeight.NONE)) {
                
                return true;
            }
        }
        
        return false;
    }
    
    public int getLightLevel() {
        
        return this.lightValue;
    }
    
    /** Extinguishes according to chance. */
    @Override
    public void updateTick(World world, BlockPos pos,
            IBlockState state, Random rand) {
        
        if (rand.nextFloat() <= this.extinguishChance) {
            
            world.playSound(pos.getX(), pos.getY(), pos.getZ(),
                    SoundEvents.BLOCK_FIRE_EXTINGUISH,
                    SoundCategory.BLOCKS, 1, 1, false);
            world.setBlockToAir(pos);
        }
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos,
            EnumFacing side, float x, float y, float z,
            int meta, EntityLivingBase placer) {
        
        if (BlockWeight.getWeight(world.getBlockState(pos.offset(side
                .getOpposite())).getBlock()).canSupport(BlockWeight.NONE)) {
            
            return this.getDefaultState().withProperty(FACING,
                    side.getOpposite());
            
        } else {
            
            for (EnumFacing facing : EnumFacing.Plane.HORIZONTAL) {
                
                if (BlockWeight.getWeight(world.getBlockState(pos
                        .offset(facing)).getBlock())
                        .canSupport(BlockWeight.NONE)) {
                    
                    return this.getDefaultState().withProperty(FACING, facing);
                }
            }

            return this.getDefaultState();
        }
    }
    
    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos,
            IBlockState state, int fortune) {
        
        return Lists.newArrayList(new ItemStack(Item.getItemFromBlock(this)));
    }
    
    @Override
    public BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this, FACING);
    }
    
    @Override
    public IBlockState getActualState(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        return state;
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta) {
        
        return this.getDefaultState().withProperty(FACING,
                EnumFacing.VALUES[meta]);
    }
    
    @Override
    public int getMetaFromState(IBlockState state) {
        
        return state.getValue(FACING).getIndex();
    }
    
    public static class Candle extends BlockLight {

        public Candle(String name, float extinguish) {
            
            super(name, 10, extinguish);
        }
        
        @Override
        public AxisAlignedBB getBoundingBox(IBlockState state,
                IBlockAccess world, BlockPos pos) {
            
            EnumFacing facing = state.getValue(FACING);
            return facing == EnumFacing.DOWN ? BlockNew.CENTRE_FOUR :
                BlockNew.BLIP[(facing.getHorizontalIndex()) % 4];
        }
    }
    
    public static class Torch extends BlockLight {

        public Torch(String name, float extinguish) {
            
            super(name, 13, extinguish);
        }
        
        @Override
        public AxisAlignedBB getBoundingBox(IBlockState state,
                IBlockAccess world, BlockPos pos) {
            
            EnumFacing facing = state.getValue(FACING);
            return facing == EnumFacing.DOWN ? BlockNew.CENTRE_TEN :
                BlockNew.BLIP[(facing.getHorizontalIndex()) % 4];
        }
    }
    
    public static class Lamp extends BlockLight {
        
        public Lamp(String name) {
            
            super(name, 14, -1);
        }
        
        @Override
        public AxisAlignedBB getBoundingBox(IBlockState state,
                IBlockAccess world, BlockPos pos) {
            
            EnumFacing facing = state.getValue(FACING);
            return facing == EnumFacing.DOWN ? BlockNew.CENTRE_FOUR :
                BlockNew.BLIP[(facing.getHorizontalIndex()) % 4];
        }
    }
}
