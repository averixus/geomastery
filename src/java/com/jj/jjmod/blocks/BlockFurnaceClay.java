package com.jj.jjmod.blocks;

import java.util.Random;
import com.jj.jjmod.main.GuiHandler.GuiList;
import com.jj.jjmod.main.Main;
import com.jj.jjmod.tileentities.TEFurnaceClay;
import com.jj.jjmod.tileentities.TEFurnaceClay.EnumPartClay;
import com.jj.jjmod.utilities.BlockMaterial;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFurnaceClay extends BlockComplexAbstract
        implements ITileEntityProvider {

    public static final PropertyEnum<EnumPartClay> PART = PropertyEnum
            .<EnumPartClay>create("part", EnumPartClay.class);
    public static final PropertyDirection FACING =
            PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public BlockFurnaceClay() {

        super("furnace_clay", BlockMaterial.STONE_FURNITURE, 5F, ToolType.NONE);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {

        return new TEFurnaceClay();
    }

    @Override
    public void neighborChanged(IBlockState state, World world,
            BlockPos pos, Block block, BlockPos unused) {

        TileEntity tileEntity = world.getTileEntity(pos);

        if (!(tileEntity instanceof TEFurnaceClay)) {

            return;
        }

        TEFurnaceClay tileClay = (TEFurnaceClay) tileEntity;
        EnumPartClay part = tileClay.getPart();
        EnumFacing facing = tileClay.getFacing();

        switch (part) {

            case BL: {

                boolean brokenBR = world
                        .getBlockState(pos.offset(facing.rotateY()))
                        .getBlock() != this;

                if (brokenBR) {

                    world.setBlockToAir(pos);
                }

                break;
            }

            case BR: {

                boolean brokenTR = world
                        .getBlockState(pos.up()).getBlock() != this;

                if (brokenTR) {

                    world.setBlockToAir(pos);
                }

                break;
            }

            case TR: {

                boolean brokenTL = world
                        .getBlockState(pos
                        .offset(facing.rotateY().getOpposite()))
                        .getBlock() != this;

                if (brokenTL) {

                    world.setBlockToAir(pos);
                }

                break;
            }

            case TL: {

                boolean brokenBL = world
                        .getBlockState(pos.down()).getBlock() != this;

                if (brokenBL) {

                    world.setBlockToAir(pos);
                }

                break;
            }
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {

        return FULL_BLOCK_AABB;
    }

    @Override
    public BlockStateContainer createBlockState() {

        return new BlockStateContainer(this, new IProperty[] {PART, FACING});
    }

    @Override
    public IBlockState getActualState(IBlockState state,
            IBlockAccess world, BlockPos pos) {

        TileEntity tileEntity = world.getTileEntity(pos);

        if (tileEntity instanceof TEFurnaceClay) {

            TEFurnaceClay tileClay = (TEFurnaceClay) tileEntity;

            state = state.withProperty(PART, tileClay.getPart());
            state = state.withProperty(FACING, tileClay.getFacing());
        }

        return state;
    }

    @Override
    public int getMetaFromState(IBlockState state) {

        return 0;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {

        return this.getDefaultState();
    }

    @Override
    public void activate(EntityPlayer player, World world,
            int x, int y, int z) {

        player.openGui(Main.instance, GuiList.CLAY.ordinal(), world, x, y, z);
    }
}