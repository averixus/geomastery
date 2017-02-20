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

/** Candlemaker crafting block. */
public class BlockCraftingCandlemaker extends BlockComplexAbstract {

    public static final PropertyEnum<EnumPartCandlemaker> PART = PropertyEnum
            .<EnumPartCandlemaker>create("part", EnumPartCandlemaker.class);
    public static final PropertyDirection FACING =
            PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public BlockCraftingCandlemaker() {

        super("crafting_candlemaker", BlockMaterial.WOOD_HANDHARVESTABLE,
                5F, null);
    }
    
    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos,
            IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        
        player.addExhaustion(0.005F);

        if (state.getValue(PART) == EnumPartCandlemaker.FRONT) {

            spawnAsEntity(world, pos, new ItemStack(ModItems.craftingCandlemaker));
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
        EnumPartCandlemaker part = state.getValue(PART);

        if (part == EnumPartCandlemaker.FRONT) {

            boolean brokenBack = world.getBlockState(pos.offset(facing))
                    .getBlock() != this;

            if (brokenBack) {

                world.setBlockToAir(pos);
                spawnAsEntity(world, pos, new ItemStack(ModItems.craftingCandlemaker));
            }

        } else {

            boolean brokenFront = world.getBlockState(pos
                    .offset(facing.getOpposite())).getBlock() != this;

            if (brokenFront) {

                world.setBlockToAir(pos);
            }
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {

        return state.getValue(PART) == EnumPartCandlemaker.BACK ? TWELVE : CENTRE_FOUR;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        
        return state.getValue(PART) == EnumPartCandlemaker.BACK ? TWELVE : NULL_AABB;
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

        if (state.getValue(PART) == EnumPartCandlemaker.BACK) {

            i |= 8;
        }

        return i;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {

        EnumFacing facing = EnumFacing.getHorizontal(meta);
        IBlockState state = this.getDefaultState();

        if ((meta & 8) > 0) {

            state = state.withProperty(PART, EnumPartCandlemaker.FRONT);
            state = state.withProperty(FACING, facing);

        } else {

            state = state.withProperty(PART, EnumPartCandlemaker.BACK);
            state = state.withProperty(FACING, facing);
        }

        return state;
    }

    @Override
    public void activate(EntityPlayer player, World world,
            int x, int y, int z) {

        player.openGui(Main.instance, GuiList.CANDLEMAKER.ordinal(),
                world, x, y, z);
    }

    /** Enum defining parts of the whole Candlemaker structure. */
    public enum EnumPartCandlemaker implements IStringSerializable {

        FRONT("front", true),
        BACK("back", false);

        private final String name;
        private final boolean isFlat;

        private EnumPartCandlemaker(String name, boolean isFlat) {

            this.name = name;
            this.isFlat = isFlat;
        }

        @Override
        public String toString() {

            return this.name;
        }

        @Override
        public String getName() {

            return this.name;
        }
        
        /** @return Whether this Part has the flat bounding box. */
        public boolean isFlat() {
            
            return this.isFlat;
        }
    }
}
