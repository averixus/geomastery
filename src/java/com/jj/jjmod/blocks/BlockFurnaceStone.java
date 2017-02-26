package com.jj.jjmod.blocks;

import com.jj.jjmod.init.ModItems;
import com.jj.jjmod.main.GuiHandler.GuiList;
import com.jj.jjmod.main.Main;
import com.jj.jjmod.tileentities.TEFurnaceStone;
import com.jj.jjmod.tileentities.TEFurnaceStone.EnumPartStone;
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

/** Stone furnace block. */
public class BlockFurnaceStone extends BlockComplexAbstract {

    public static final PropertyEnum<EnumPartStone> PART = PropertyEnum
            .<EnumPartStone>create("part", EnumPartStone.class);
    public static final PropertyDirection FACING =
            PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public BlockFurnaceStone() {

        super("furnace_stone", BlockMaterial.STONE_HANDHARVESTABLE, 5F, null);
    }
    
    /** Breaks this block and drops item if applicable. */
    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos,
            IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        
        player.addExhaustion(0.005F);

        if (((TEFurnaceStone) te).getPart().shouldDrop()) {

            spawnAsEntity(world, pos, new ItemStack(ModItems.furnaceStone));
        }
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {

        return new TEFurnaceStone();
    }

    @Override
    public void neighborChanged(IBlockState state, World world,
            BlockPos pos, Block block, BlockPos unused) {

        state = this.getActualState(state, world, pos);
        EnumPartStone part = state.getValue(PART);
        EnumFacing facing = state.getValue(FACING);
        boolean broken = false;

        switch (part) {

            case BM: {

                broken = world.getBlockState(pos.offset(facing.rotateY()))
                        .getBlock() != this;
                break;
            }

            case BR: {

                broken = world.getBlockState(pos.up()).getBlock() != this;
                break;
            }

            case TR: {

                broken = world.getBlockState(pos.offset(facing.rotateY()
                        .getOpposite())).getBlock() != this;
                break;
            }

            case TM: {

                broken = world.getBlockState(pos.offset(facing.rotateY()
                        .getOpposite())).getBlock() != this;
                break;
            }

            case TL: {

                broken = world.getBlockState(pos.down()).getBlock() != this;
                break;
            }

            case BL: {

                broken = world.getBlockState(pos.offset(facing.rotateY()))
                        .getBlock() != this;
                break;
            }
        }
        
        if (broken) {
            
            world.setBlockToAir(pos);
            
            if (part.shouldDrop()) {
                
                spawnAsEntity(world, pos, new ItemStack(ModItems.furnaceStone));
            }
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {

        state = this.getActualState(state, world, pos);
        EnumPartStone part = state.getValue(PART);
        
        switch (part) {

            case TR: 
            case TM: 
            case TL: 
                return TWELVE;
                
            case BR: 
            case BM:             
            case BL:
            default: 
                return FULL_BLOCK_AABB;
        }
    }

    @Override
    public BlockStateContainer createBlockState() {

        return new BlockStateContainer(this, new IProperty[] {PART, FACING});
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world,
            BlockPos pos) {

        TileEntity tileEntity = world.getTileEntity(pos);

        if (tileEntity instanceof TEFurnaceStone) {

            TEFurnaceStone tileStone = (TEFurnaceStone) tileEntity;

            state = tileStone.getPart() == null ? state :
                state.withProperty(PART, tileStone.getPart());
            state = tileStone.getFacing() == null ? state :
                state.withProperty(FACING, tileStone.getFacing());
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

        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
        
        if (tileEntity instanceof TEFurnaceStone) {
            
            BlockPos master = ((TEFurnaceStone) tileEntity).getMaster();
            x = master.getX();
            y = master.getY();
            z = master.getZ();
        }

        if (!world.isRemote) {
            
            player.openGui(Main.instance, GuiList.STONE.ordinal(),
                    world, x, y, z);
        }
        
        return true;
    }
}