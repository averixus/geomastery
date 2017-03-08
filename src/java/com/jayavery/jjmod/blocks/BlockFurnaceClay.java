package com.jayavery.jjmod.blocks;

import com.jayavery.jjmod.init.ModItems;
import com.jayavery.jjmod.main.Main;
import com.jayavery.jjmod.main.GuiHandler.GuiList;
import com.jayavery.jjmod.tileentities.TEFurnaceClay;
import com.jayavery.jjmod.tileentities.TEFurnaceClay.EnumPartClay;
import com.jayavery.jjmod.utilities.BlockMaterial;
import javax.annotation.Nullable;
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

/** Clay furnace block. */
public class BlockFurnaceClay extends BlockComplexAbstract {

    public static final PropertyEnum<EnumPartClay> PART = PropertyEnum
            .<EnumPartClay>create("part", EnumPartClay.class);
    public static final PropertyDirection FACING =
            PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public BlockFurnaceClay() {

        super("furnace_clay", BlockMaterial.STONE_HANDHARVESTABLE, 5F, null);
        this.lightValue = 12;
    }
    
    /** Breaks this block and drops item if applicable. */
    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos,
            IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        
        player.addExhaustion(0.005F);

        if (((TEFurnaceClay) te).getPart().shouldDrop()) {

            spawnAsEntity(world, pos, new ItemStack(ModItems.furnaceClay));
        }
    }
    
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {

        return new TEFurnaceClay();
    }

    @Override
    public void neighborChanged(IBlockState state, World world,
            BlockPos pos, Block block, BlockPos unused) {

        state = this.getActualState(state, world, pos);
        EnumPartClay part = state.getValue(PART);
        EnumFacing facing = state.getValue(FACING);
        boolean broken = false;

        switch (part) {

            case BL: {

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

            case TL: {

                broken = world.getBlockState(pos.down()).getBlock() != this;
                break;
            }
        }
        
        if (broken) {
            
            world.setBlockToAir(pos);
            
            if (part.shouldDrop()) {
                
                spawnAsEntity(world, pos, new ItemStack(ModItems.furnaceClay));
            }
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {

        state = this.getActualState(state, world, pos);
        EnumPartClay part = state.getValue(PART);
        
        switch (part) {

            case TR: 
            case TL: 
                return EIGHT;
                
            case BR: 
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
    public IBlockState getActualState(IBlockState state,
            IBlockAccess world, BlockPos pos) {

        TileEntity tileEntity = world.getTileEntity(pos);

        if (tileEntity instanceof TEFurnaceClay) {

            TEFurnaceClay tileClay = (TEFurnaceClay) tileEntity;

            state = tileClay.getPart() == null ? state :
                state.withProperty(PART, tileClay.getPart());
            state = tileClay.getFacing() == null ? state :
                state.withProperty(FACING, tileClay.getFacing());
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
        
        if (tileEntity instanceof TEFurnaceClay) {
            
            BlockPos master = ((TEFurnaceClay) tileEntity).getMaster();
            x = master.getX();
            y = master.getY();
            z = master.getZ();
        }

        if (!world.isRemote) {
            
            player.openGui(Main.instance, GuiList.CLAY.ordinal(),
                    world, x, y, z);
        }
        
        return true;
    }
}