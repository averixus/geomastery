package com.jayavery.jjmod.blocks;

import com.jayavery.jjmod.init.ModItems;
import com.jayavery.jjmod.main.Main;
import com.jayavery.jjmod.main.GuiHandler.GuiList;
import com.jayavery.jjmod.utilities.BlockMaterial;
import com.jayavery.jjmod.utilities.IMultipart;
import com.sun.istack.internal.Nullable;
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
    
    /** Breaks this block and drops item if applicable. */
    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos,
            IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        
        player.addExhaustion(0.005F);

        if (state.getValue(PART).shouldDrop()) {

            spawnAsEntity(world, pos,
                    new ItemStack(ModItems.craftingCandlemaker));
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
        boolean broken = false;

        if (part == EnumPartCandlemaker.FRONT) {

            broken = world.getBlockState(pos.offset(facing)).getBlock() != this;

        } else {

            broken = world.getBlockState(pos.offset(facing.getOpposite()))
                    .getBlock() != this;
        }
        
        if (broken) {
            
            world.setBlockToAir(pos);
            
            if (part.shouldDrop()) {
                
                spawnAsEntity(world, pos,
                        new ItemStack(ModItems.craftingCandlemaker));
            }
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {

        return state.getValue(PART) == EnumPartCandlemaker.BACK ?
                TWELVE : CENTRE_FOUR;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        return state.getValue(PART) == EnumPartCandlemaker.BACK ?
                TWELVE : NULL_AABB;
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
    public boolean activate(EntityPlayer player, World world,
            int x, int y, int z) {

        if (!world.isRemote) {
            
            player.openGui(Main.instance, GuiList.CANDLEMAKER.ordinal(),
                    world, x, y, z);
        }
        
        return true;
    }

    /** Enum defining parts of the whole Candlemaker structure. */
    public enum EnumPartCandlemaker implements IStringSerializable, IMultipart {

        FRONT("front"),
        BACK("back");

        private final String name;

        private EnumPartCandlemaker(String name) {

            this.name = name;
        }
        
        @Override
        public boolean shouldDrop() {
            
            return this == FRONT;
        }

        @Override
        public String getName() {

            return this.name;
        }
    }
}
