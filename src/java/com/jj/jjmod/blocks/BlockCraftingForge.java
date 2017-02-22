package com.jj.jjmod.blocks;

import java.util.Random;
import javax.annotation.Nullable;
import com.jj.jjmod.init.ModItems;
import com.jj.jjmod.main.GuiHandler.GuiList;
import com.jj.jjmod.main.Main;
import com.jj.jjmod.tileentities.TECraftingForge;
import com.jj.jjmod.tileentities.TECraftingArmourer.EnumPartArmourer;
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
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Forge crafting block. */
public class BlockCraftingForge extends BlockComplexAbstract {

    public static final PropertyEnum<EnumPartForge> PART =
            PropertyEnum.<EnumPartForge>create("part", EnumPartForge.class);
    public static final PropertyDirection FACING =
            PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public BlockCraftingForge() {

        super("crafting_forge", BlockMaterial.STONE_HANDHARVESTABLE, 5F, null);
    }
    
    /** Breaks this block and drops item if applicable. */
    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos,
            IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        
        player.addExhaustion(0.005F);

        if (((TECraftingForge) te).getPart().shouldDrop()) {

            spawnAsEntity(world, pos, new ItemStack(ModItems.craftingForge));
        }
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

        state = this.getActualState(state, world, pos);
        EnumPartForge part = state.getValue(PART);
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
                        new ItemStack(ModItems.craftingForge));
            }
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {

        state = this.getActualState(state, world, pos);
        EnumPartForge part = state.getValue(PART);
        int facing = state.getValue(FACING).getHorizontalIndex();
        
        switch (part) {
            
            case BR: 
            case BM:
                return HALF[facing % 4];
            
            case FR: 
                return CORNER[(facing + 2) % 4];
            
            case FM: 
                return CORNER[(facing + 3) % 4];
                
            case BL: 
            case FL: 
                return TEN;
            
            default:
                return FULL_BLOCK_AABB;
        }
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        state = this.getActualState(state, world, pos);

        EnumPartForge part = state.getValue(PART);
        
        return part == EnumPartForge.FR ? CENTRE_TWO :
                part == EnumPartForge.FL ? NULL_AABB :
                this.getBoundingBox(state, world, pos);
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

            state = tileForge.getPart() == null ? state :
                    state.withProperty(PART, tileForge.getPart());
            state = tileForge.getFacing() == null ? state :
                    state.withProperty(FACING, tileForge.getFacing());
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
            
            player.openGui(Main.instance, GuiList.FORGE.ordinal(),
                    world, x, y, z);
        }
        
        return true;
    }
}
