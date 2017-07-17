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
import jayavery.geomastery.items.ItemPlacing;
import jayavery.geomastery.utilities.EBlockWeight;
import jayavery.geomastery.utilities.Lang;
import net.minecraft.block.Block;
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
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Torch and candle blocks. */
public abstract class BlockLight
        extends BlockBuildingAbstract<ItemPlacing.Building> {

    protected static final AxisAlignedBB FLAT_BOUNDS =
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.18D, 1.0D);
    public static final PropertyDirection FACING = PropertyDirection
            .create("facing", (f) -> f != EnumFacing.UP);
    
    /** Chance of extinguishing per update tick. */
    private final float extinguishChance;

    public BlockLight(String name, int light, float extinguish, int stackSize) {

        super(Material.CIRCUITS, name, CreativeTabs.DECORATIONS, 0F, stackSize);
        this.lightValue = light;
        this.extinguishChance = extinguish;
    }
    
    @Override
    protected ItemPlacing.Building createItem(int stackSize) {
        
        return new ItemPlacing.Building(this, stackSize);
    }
    
    @Override
    public EBlockWeight getWeight(IBlockState state) {
        
        return EBlockWeight.NONE;
    }
    
    @Override
    public boolean shouldConnect(IBlockAccess world, IBlockState state,
            BlockPos pos, EnumFacing direction) {
        
        return false;
    }
    
    @Override
    public boolean place(World world, BlockPos targetPos, EnumFacing targetSide,
            EnumFacing placeFacing, ItemStack stack, EntityPlayer player) {
        
        BlockPos placePos = targetPos.offset(targetSide);
        
        if (!this.isValid(world, placePos, stack, false, this.getDefaultState(), player)) {
            
            return false;
        }
        
        if ((EBlockWeight.getWeight(world.getBlockState(targetPos))
                .canSupport(EBlockWeight.NONE))) {
            
            world.setBlockState(placePos, this.getDefaultState()
                    .withProperty(FACING, targetSide.getOpposite()));
            
        } else {
            
            for (EnumFacing facing : EnumFacing.Plane.HORIZONTAL) {
                
                if (EBlockWeight.getWeight(world.getBlockState(placePos
                        .offset(facing))).canSupport(EBlockWeight.NONE)) {
                    
                    world.setBlockState(placePos,this.getDefaultState()
                            .withProperty(FACING, facing));
                }
            }

            world.setBlockState(placePos, this.getDefaultState());
        }
        
        return true;
    }
    
    @Override
    public boolean isValid(World world, BlockPos pos, ItemStack stack,
            boolean alreadyPresent, IBlockState setState, EntityPlayer player) {
        
        if (alreadyPresent) {
            
            if ((EBlockWeight.getWeight(world.getBlockState(pos
                    .offset(world.getBlockState(pos).getValue(FACING))))
                    .canSupport(EBlockWeight.NONE))) {
                
                return true;
            }
            
            return false;
            
        } else {
            
            if (!world.getBlockState(pos).getBlock()
                    .isReplaceable(world, pos)) {
                
                message(player, Lang.BUILDFAIL_OBSTACLE);
                return false;
            }
            
            for (EnumFacing facing : FACING.getAllowedValues()) {
                
                if (EBlockWeight.getWeight(world.getBlockState(pos
                        .offset(facing))).canSupport(EBlockWeight.NONE)) {
                    
                    return true;
                }
            }
            
            message(player, Lang.BUILDFAIL_SUPPORT);
            return false;
        }
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
    public BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this, FACING);
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
    
    /** Candle block. */
    public static class Candle extends BlockLight {

        public Candle(String name, float extinguish, int stackSize) {
            
            super(name, 10, extinguish, stackSize);
        }
        
        @Override
        public AxisAlignedBB getBoundingBox(IBlockState state,
                IBlockAccess world, BlockPos pos) {
            
            EnumFacing facing = state.getValue(FACING);
            return facing == EnumFacing.DOWN ? BlockNew.CENTRE_FOUR :
                BlockNew.BLIP[(facing.getHorizontalIndex()) % 4];
        }
    }
    
    /** Torch block. */
    public static class Torch extends BlockLight {

        public Torch(String name, float extinguish, int stackSize) {
            
            super(name, 13, extinguish, stackSize);
        }
        
        @Override
        public AxisAlignedBB getBoundingBox(IBlockState state,
                IBlockAccess world, BlockPos pos) {
            
            EnumFacing facing = state.getValue(FACING);
            return facing == EnumFacing.DOWN ? BlockNew.CENTRE_TEN :
                BlockNew.BLIP[(facing.getHorizontalIndex()) % 4];
        }
    }
    
    /** Lamp block. */
    public static class Lamp extends BlockLight {
        
        public Lamp(String name, int stackSize) {
            
            super(name, 14, -1, stackSize);
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
