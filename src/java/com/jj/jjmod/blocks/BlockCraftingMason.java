package com.jj.jjmod.blocks;

import com.jj.jjmod.init.ModItems;
import com.jj.jjmod.main.GuiHandler.GuiList;
import com.jj.jjmod.main.Main;
import com.jj.jjmod.tileentities.TECraftingMason;
import com.jj.jjmod.tileentities.TECraftingMason.EnumPartMason;
import com.jj.jjmod.utilities.BlockMaterial;
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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Mason crafting block. */
public class BlockCraftingMason extends BlockComplexAbstract {

    public static final PropertyEnum<EnumPartMason> PART =
            PropertyEnum.<EnumPartMason>create("part", EnumPartMason.class);
    public static final PropertyDirection FACING =
            PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public BlockCraftingMason() {

        super("crafting_mason", BlockMaterial.STONE_HANDHARVESTABLE, 5F, null);
    }
    
    /** Breaks this block and drops item if applicable. */
    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos,
            IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        
        player.addExhaustion(0.005F);

        if (((TECraftingMason) te).getPart().shouldDrop()) {

            spawnAsEntity(world, pos, new ItemStack(ModItems.craftingMason));
        }
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {

        return new TECraftingMason();
    }

    @Override
    public void neighborChanged(IBlockState state, World world,
            BlockPos pos, Block block, BlockPos unused) {

        state = this.getActualState(state, world, pos);
        EnumPartMason part = state.getValue(PART);
        EnumFacing facing = state.getValue(FACING);
        boolean broken = false;

        switch (part) {

            case FM: {

                boolean brokenFL = world.getBlockState(pos.offset(facing.rotateY()
                        .getOpposite())).getBlock() != this;
                boolean brokenBM = world.getBlockState(pos.offset(facing))
                        .getBlock() != this;
                broken = brokenFL || brokenBM;
                break;
            }

            case FL: {

                broken = world.getBlockState(pos.offset(facing.rotateY()))
                        .getBlock() != this;
                break;
            }

            case BM: {

                broken = world.getBlockState(pos.offset(facing.rotateY()))
                        .getBlock() != this;
                break;
            }

            case BR: {

                broken = world.getBlockState(pos.offset(facing.getOpposite()))
                        .getBlock() != this;
                break;
            }

            case FR: {

                broken = world.getBlockState(pos.offset(facing.rotateY()
                        .getOpposite())).getBlock() != this;
                break;
            }
        }
        
        if (broken) {
            
            world.setBlockToAir(pos);
            
            if (part.shouldDrop()) {
                
                spawnAsEntity(world, pos,
                        new ItemStack(ModItems.craftingMason));
            }
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {

        state = this.getActualState(state, world, pos);
        EnumPartMason part = state.getValue(PART);
        int facing = state.getValue(FACING).getHorizontalIndex();
        
        switch (part) {
            
            case BR:
                return CORNER[(facing + 1) % 4];
            
            case BM:
                return CORNER[facing % 4];
            
            case FR: 
                return CORNER[(facing + 2) % 4];
            
            case FM: 
                return CORNER[(facing + 3) % 4];
            
            case FL: 
                return HALF[(facing + 3) % 4];
            
            default:
                return FULL_BLOCK_AABB;
        }
    }

    @Override
    public BlockStateContainer createBlockState() {

        return new BlockStateContainer(this, new IProperty[] {PART, FACING});
    }

    @Override
    public IBlockState getActualState(IBlockState state,
            IBlockAccess world, BlockPos pos) {

        TileEntity tileEntity = world.getTileEntity(pos);

        if (tileEntity instanceof TECraftingMason) {

            TECraftingMason tileMason = (TECraftingMason) tileEntity;

            state = state.withProperty(PART, tileMason.getPart() == null ?
                    state.getValue(PART) : tileMason.getPart());
            state = state.withProperty(FACING, tileMason.getFacing() == null ?
                    state.getValue(FACING) : tileMason.getFacing());
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
    public boolean activate(EntityPlayer player, World world,
            int x, int y, int z) {

        if (!world.isRemote) {
            
            player.openGui(Main.instance, GuiList.MASON.ordinal(),
                    world, x, y, z);
        }
        
        return true;
    }
}
