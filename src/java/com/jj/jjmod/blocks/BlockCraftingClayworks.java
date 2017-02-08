package com.jj.jjmod.blocks;

import javax.annotation.Nullable;
import com.jj.jjmod.init.ModItems;
import com.jj.jjmod.main.GuiHandler.GuiList;
import com.jj.jjmod.main.Main;
import com.jj.jjmod.utilities.BlockMaterial;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
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

        super("crafting_clayworks", BlockMaterial.WOOD_HANDHARVESTABLE, 5F, ToolType.NONE);
    }
    
    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        
        player.addExhaustion(0.005F);

        if (state.getValue(PART) == EnumPartClayworks.FR) {

            spawnItem(world, pos, ModItems.craftingClayworks);
        }
    }
    
  /*  @Override
    public Item getItemDropped(IBlockState state, Random rand,
            int fortune) {
        
        return state.getValue(PART) == EnumPartClayworks.FR ? ModItems.craftingClayworks : Items.AIR;
    }*/
    
    @Override
    public void dropBlockAsItemWithChance(World world, BlockPos pos, IBlockState state, float chance, int fortune) {
        
        if (this.getActualState(state, world, pos).getValue(PART) == EnumPartClayworks.FR) {
        
            spawnItem(world, pos, ModItems.craftingClayworks);
        }
    }
    
    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        
        return null;
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
                    spawnItem(world, pos, ModItems.craftingClayworks);
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

        return state.getValue(PART).isFlat() ? FLAT_BOUNDS : FULL_BLOCK_AABB;
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
        private final boolean isFlat;

        private EnumPartClayworks(String name, boolean isFlat) {

            this.NAME = name;
            this.isFlat = isFlat;
        }

        @Override
        public String toString() {

            return this.NAME;
        }

        @Override
        public String getName() {

            return this.NAME;
        }
        
        public boolean isFlat() {
            
            return this.isFlat;
        }
    }
}
