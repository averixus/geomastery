package com.jj.jjmod.blocks;

import java.util.Random;
import com.jj.jjmod.init.ModItems;
import com.jj.jjmod.main.GuiHandler.GuiList;
import com.jj.jjmod.utilities.BlockMaterial;
import com.jj.jjmod.utilities.ToolType;
import com.jj.jjmod.main.Main;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCraftingClayworks extends BlockComplexAbstract {

    public static final PropertyEnum<EnumPartClayworks> PART = PropertyEnum
            .<EnumPartClayworks>create("part", EnumPartClayworks.class);
    public static final PropertyDirection FACING =
            PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public BlockCraftingClayworks() {

        super("crafting_clayworks", BlockMaterial.WOOD_FURNITURE, 5F, ToolType.AXE);
    }

    @Override
    public void neighborChanged(IBlockState state, World world,
            BlockPos pos, Block block, BlockPos unused) {

        EnumFacing facing = state.getValue(FACING);
        EnumPartClayworks part = state.getValue(PART);

        switch (part) {

            case FR: {

                boolean brokenFL = world.getBlockState(pos
                        .offset(facing.rotateY().getOpposite()))
                        .getBlock() != this;

                if (brokenFL) {

                    world.setBlockToAir(pos);
                }

                break;
            }

            case FL: {

                boolean brokenBL = world.getBlockState(pos.offset(facing))
                        .getBlock() != this;

                if (brokenBL) {

                    world.setBlockToAir(pos);
                }

                break;
            }

            case BL: {

                boolean brokenBR = world.getBlockState(pos
                        .offset(facing.rotateY())).getBlock() != this;

                if (brokenBR) {

                    world.setBlockToAir(pos);
                }

                break;
            }

            case BR: {

                boolean brokenFR = world.getBlockState(pos
                        .offset(facing.getOpposite())).getBlock() != this;

                if (brokenFR) {

                    world.setBlockToAir(pos);
                }

                break;
            }
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {

        return state.getValue(PART).IS_FLAT ? FLAT_BOUNDS : FULL_BLOCK_AABB;
    }

    @Override
    public BlockStateContainer createBlockState() {

        return new BlockStateContainer(this, new IProperty[] {PART, FACING});
    }

    @Override
    public IBlockState getActualState(IBlockState state,
            IBlockAccess world, BlockPos pos) {

        return state;
    }

    @Override
    public int getMetaFromState(IBlockState state) {

        int i = 0;

        i = i | state.getValue(FACING).getHorizontalIndex();
        i = i | (4 * state.getValue(PART).ordinal());

        return i;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {

        EnumFacing facing = EnumFacing.getHorizontal(meta);
        IBlockState state = this.getDefaultState();
        int partOrdinal = meta / 4;

        state = state.withProperty(FACING, facing);
        state = state.withProperty(PART,
                EnumPartClayworks.values()[partOrdinal]);

        return state;
    }

    @Override
    public void activate(EntityPlayer player, World world,
            int x, int y, int z) {

        player.openGui(Main.instance, GuiList.CLAYWORKS.ordinal(),
                world, x, y, z);
    }

    public static enum EnumPartClayworks implements IStringSerializable {

        FR("fr", true),
        FL("fl", false),
        BL("bl", false),
        BR("br", false);

        private final String NAME;
        private final boolean IS_FLAT;

        private EnumPartClayworks(String name, boolean isFlat) {

            this.NAME = name;
            this.IS_FLAT = isFlat;
        }

        public String toString() {

            return this.NAME;
        }

        public String getName() {

            return this.NAME;
        }
    }
}
