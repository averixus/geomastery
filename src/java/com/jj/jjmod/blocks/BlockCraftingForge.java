package com.jj.jjmod.blocks;

import java.util.Random;
import com.jj.jjmod.init.ModItems;
import com.jj.jjmod.main.GuiHandler.GuiList;
import com.jj.jjmod.main.Main;
import com.jj.jjmod.tileentities.TECraftingForge;
import com.jj.jjmod.tileentities.TECraftingForge.EnumPartForge;
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
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCraftingForge extends BlockComplexAbstract
        implements ITileEntityProvider {

    public static final PropertyEnum<EnumPartForge> PART =
            PropertyEnum.<EnumPartForge>create("part", EnumPartForge.class);
    public static final PropertyDirection FACING =
            PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public BlockCraftingForge() {

        super("crafting_forge", BlockMaterial.STONE_FURNITURE, 5F, ToolType.NONE);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {

        return new TECraftingForge();
    }
    
    @Override
    public boolean hasTileEntity() {
        
        return true;
    }

    @Override
    public void neighborChanged(IBlockState state, World world,
            BlockPos pos, Block block, BlockPos unused) {

        TileEntity tileEntity = world.getTileEntity(pos);

        if (!(tileEntity instanceof TECraftingForge)) {

            return;
        }

        TECraftingForge tileForge = (TECraftingForge) tileEntity;
        EnumPartForge part = tileForge.getPart();
        EnumFacing facing = tileForge.getFacing();

        switch (part) {

            case FM: {

                boolean brokenFL = world
                        .getBlockState(pos.offset(facing.rotateY()
                        .getOpposite())).getBlock() != this;

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

                boolean brokenBM = world.getBlockState(pos
                        .offset(facing.rotateY())).getBlock() != this;

                if (brokenBM) {

                    world.setBlockToAir(pos);
                }

                break;
            }

            case BM: {

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

        if (!(tileEntity instanceof TECraftingForge)) {

            return FULL_BLOCK_AABB;
        }

        TECraftingForge tileForge = (TECraftingForge) tileEntity;
        return tileForge.getPart().isFlat() ? FLAT_BOUNDS : FULL_BLOCK_AABB;
    }

    @Override
    public BlockStateContainer createBlockState() {

        return new BlockStateContainer(this, new IProperty[] {PART, FACING});
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world,
            BlockPos pos) {
        
        TileEntity tileEntity = world.getTileEntity(pos);

        if (tileEntity instanceof TECraftingForge) {

            TECraftingForge tileForge = (TECraftingForge) tileEntity;

            state = state.withProperty(PART, tileForge.getPart());
            state = state.withProperty(FACING, tileForge.getFacing());
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

        player.openGui(Main.instance, GuiList.FORGE.ordinal(), world, x, y, z);
    }
}
