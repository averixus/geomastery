package com.jj.jjmod.blocks;

import java.util.Random;
import javax.annotation.Nullable;
import com.jj.jjmod.init.ModItems;
import com.jj.jjmod.main.GuiHandler.GuiList;
import com.jj.jjmod.main.Main;
import com.jj.jjmod.tileentities.TECraftingForge;
import com.jj.jjmod.tileentities.TECraftingWoodworking;
import com.jj.jjmod.tileentities.TECraftingArmourer.EnumPartArmourer;
import com.jj.jjmod.tileentities.TECraftingForge.EnumPartForge;
import com.jj.jjmod.tileentities.TECraftingWoodworking.EnumPartWoodworking;
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
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCraftingWoodworking extends BlockComplexAbstract
        implements ITileEntityProvider {

    public static final PropertyEnum<EnumPartWoodworking> PART = PropertyEnum
            .<EnumPartWoodworking>create("part", EnumPartWoodworking.class);
    public static final PropertyDirection FACING =
            PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public BlockCraftingWoodworking() {

        super("crafting_woodworking", BlockMaterial.WOOD_HANDHARVESTABLE, 5F, null);
    }
    
    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        
        player.addExhaustion(0.005F);

        if (((TECraftingWoodworking) te).getPart() == EnumPartWoodworking.FM) {

            spawnItem(world, pos, ModItems.craftingWoodworking);
        }
    }
    
    @Override
    public void dropBlockAsItemWithChance(World world, BlockPos pos, IBlockState state, float chance, int fortune) {
        
        if (this.getActualState(state, world, pos).getValue(PART) == EnumPartWoodworking.FM) {
        
            spawnItem(world, pos, ModItems.craftingWoodworking);
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {

        return new TECraftingWoodworking();
    }

    @Override
    public void neighborChanged(IBlockState state, World world,
            BlockPos pos, Block block, BlockPos unused) {

        TileEntity tileEntity = world.getTileEntity(pos);

        if (!(tileEntity instanceof TECraftingWoodworking)) {

            return;
        }

        TECraftingWoodworking tileWoodworking =
                (TECraftingWoodworking) tileEntity;
        EnumPartWoodworking part = tileWoodworking.getPart();
        EnumFacing facing = tileWoodworking.getFacing();

        switch (part) {

            case FM: {

                boolean brokenFL = world.getBlockState(pos
                        .offset(facing.rotateY().getOpposite()))
                        .getBlock() != this;

                if (brokenFL) {

                    world.setBlockToAir(pos);
                    spawnItem(world, pos, ModItems.craftingWoodworking);
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

                boolean brokenBM = world
                        .getBlockState(pos.offset(facing.rotateY()))
                        .getBlock() != this;

                if (brokenBM) {

                    world.setBlockToAir(pos);
                }

                break;
            }

            case BM: {

                boolean brokenBR = world
                        .getBlockState(pos.offset(facing.rotateY()))
                        .getBlock() != this;

                if (brokenBR) {

                    world.setBlockToAir(pos);
                }

                break;
            }

            case BR: {

                boolean brokenFR = world
                        .getBlockState(pos.offset(facing.getOpposite()))
                        .getBlock() != this;

                if (brokenFR) {

                    world.setBlockToAir(pos);
                }

                break;
            }

            case FR: {

                boolean brokenFM = world.getBlockState(pos
                        .offset(facing.rotateY().getOpposite()))
                        .getBlock() != this;

                if (brokenFM) {

                    world.setBlockToAir(pos);
                }

                break;
            }
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {

        TileEntity tileEntity = world.getTileEntity(pos);

        if (!(tileEntity instanceof TECraftingWoodworking)) {

            return FULL_BLOCK_AABB;
        }

        TECraftingWoodworking tileWoodworking =
                (TECraftingWoodworking) tileEntity;
        return tileWoodworking.getPart().isFlat() ? FLAT_BOUNDS
                : FULL_BLOCK_AABB;
    }

    @Override
    public BlockStateContainer createBlockState() {

        return new BlockStateContainer(this, new IProperty[] {PART, FACING});
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world,
            BlockPos pos) {

        TileEntity tileEntity = world.getTileEntity(pos);

        if (tileEntity instanceof TECraftingWoodworking) {

            TECraftingWoodworking tileWoodworking =
                    (TECraftingWoodworking) tileEntity;

            state = state.withProperty(PART, tileWoodworking.getPart());
            state = state.withProperty(FACING, tileWoodworking.getFacing());
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

        player.openGui(Main.instance, GuiList.WOODWORKING.ordinal(),
                world, x, y, z);
    }
}
