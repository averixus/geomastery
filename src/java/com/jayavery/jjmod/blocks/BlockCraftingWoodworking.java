package com.jayavery.jjmod.blocks;

import com.jayavery.jjmod.init.ModItems;
import com.jayavery.jjmod.main.Jjmod;
import com.jayavery.jjmod.main.GuiHandler.GuiList;
import com.jayavery.jjmod.tileentities.TECraftingWoodworking;
import com.jayavery.jjmod.tileentities.TECraftingWoodworking.EnumPartWoodworking;
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

/** Woodworking crafting block. */
public class BlockCraftingWoodworking extends BlockComplexAbstract {

    public static final PropertyEnum<EnumPartWoodworking> PART = PropertyEnum
            .<EnumPartWoodworking>create("part", EnumPartWoodworking.class);
    public static final PropertyDirection FACING =
            PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public BlockCraftingWoodworking() {

        super("crafting_woodworking", BlockMaterial.WOOD_HANDHARVESTABLE,
                5F, null);
    }
    
    /** Breaks this block and drops item if applicable. */
    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos,
            IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        
        player.addExhaustion(0.005F);

        if (((TECraftingWoodworking) te).getPart().shouldDrop()) {

            spawnAsEntity(world, pos,
                    new ItemStack(ModItems.craftingWoodworking));
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {

        return new TECraftingWoodworking();
    }

    @Override
    public void neighborChanged(IBlockState state, World world,
            BlockPos pos, Block block, BlockPos unused) {

        state = this.getActualState(state, world, pos);
        EnumPartWoodworking part = state.getValue(PART);
        EnumFacing facing = state.getValue(FACING);
        boolean broken = false;

        switch (part) {

            case FM: {

                broken = world.getBlockState(pos.offset(facing.rotateY()
                        .getOpposite())).getBlock() != this;
                break;
            }

            case FL: {

                broken = world.getBlockState(pos.offset(facing))
                        .getBlock() != this;
                break;
            }

            case BL: {

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
                        new ItemStack(ModItems.craftingWoodworking));
            }
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {

        state = this.getActualState(state, world, pos);
        EnumPartWoodworking part = state.getValue(PART);
        int facing = state.getValue(FACING).getHorizontalIndex();
        
        switch (part) {
            
            case BR:
                return HALF[(facing + 3) % 4];
            
            case FR: 
                return CORNER[(facing + 2) % 4];
            
            case FM: 
                return HALF[facing];
            
            case FL: 
                return TWELVE;
                
            case BM: 
            case BL: 
            default:
                return FULL_BLOCK_AABB;
        }
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        state = this.getActualState(state, world, pos);
        EnumPartWoodworking part = state.getValue(PART);
        
        if (part == EnumPartWoodworking.BR ||
                part == EnumPartWoodworking.FR ||
                part == EnumPartWoodworking.FM) {
            
            return NULL_AABB;
        }
        
        return this.getBoundingBox(state, world, pos);
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

            state = tileWoodworking.getPart() == null ? state :
                state.withProperty(PART, tileWoodworking.getPart());
            state = tileWoodworking.getFacing() == null ? state :
                state.withProperty(FACING, tileWoodworking.getFacing());
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
            
            player.openGui(Jjmod.instance, GuiList.WOODWORKING.ordinal(),
                    world, x, y, z);
        }
        
        return true;
    }
}
