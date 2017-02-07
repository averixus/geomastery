package com.jj.jjmod.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockCropBlockfruit extends BlockCrop {

    public static final PropertyDirection FACING = BlockTorch.FACING;

    protected Supplier<BlockFruit> fruit;

    protected static final AxisAlignedBB[] STEM_BOUNDS =
            new AxisAlignedBB[] {new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D,
                    0.125D, 0.625D), new AxisAlignedBB(0.375D, 0.0D, 0.375D,
                            0.625D, 0.25D, 0.625D), new AxisAlignedBB(0.375D,
                                    0.0D, 0.375D, 0.625D, 0.375D,
                                    0.625D), new AxisAlignedBB(0.375D, 0.0D,
                                            0.375D, 0.625D, 0.5D,
                                            0.625D), new AxisAlignedBB(0.375D,
                                                    0.0D, 0.375D, 0.625D,
                                                    0.625D,
                                                    0.625D), new AxisAlignedBB(
                                                            0.375D, 0.0D,
                                                            0.375D, 0.625D,
                                                            0.75D,
                                                            0.625D), new AxisAlignedBB(
                                                                    0.375D,
                                                                    0.0D,
                                                                    0.375D,
                                                                    0.625D,
                                                                    0.875D,
                                                                    0.625D), new AxisAlignedBB(
                                                                            0.375D,
                                                                            0.0D,
                                                                            0.375D,
                                                                            0.625D,
                                                                            1.0D,
                                                                            0.625D)};

    public BlockCropBlockfruit(String name, float growthChance,
            float hardness, ToolType tool, Supplier<BlockFruit> fruit) {

        super(name, () -> Items.AIR, () -> new Integer(0),
                growthChance, hardness, tool);
        this.fruit = fruit;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {

        return STEM_BOUNDS[state.getValue(AGE)];
    }
    
    @Override
    public BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this, new IProperty[] {AGE, FACING});
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
        System.out.println("actual state is " + state);
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
                world, pos.down(), EnumFacing.UP,
                this) || soilBlock == Blocks.DIRT || soilBlock == Blocks.GRASS)) {

            world.setBlockState(fruitPos, this.fruit.get().getDefaultState()
                    .withProperty(BlockFruit.STEM, side.getOpposite()));
        }
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos,
            IBlockState state, int fortune) {

        return new ArrayList<ItemStack>();
    }
}
