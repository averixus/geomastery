package com.jj.jjmod.items;

import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.container.ContainerInventory;
import com.jj.jjmod.tileentities.TEFurnaceStone;
import com.jj.jjmod.tileentities.TEFurnaceStone.EnumPartStone;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemFurnaceStone extends ItemNew {

    public ItemFurnaceStone() {

        super("furnace_stone", 1, CreativeTabs.DECORATIONS);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player,
            World world, BlockPos pos, EnumHand hand,
            EnumFacing playerFacing, float x, float y, float z) {

        ItemStack stack = player.getHeldItem(hand);
        
        if (world.isRemote) {

            return EnumActionResult.SUCCESS;
        }

        // Calculate positions
        BlockPos posBM = pos.up();
        int facing = MathHelper.floor((player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        EnumFacing enumFacing = EnumFacing.getHorizontal(facing);
        BlockPos posBL = posBM.offset(enumFacing.rotateY().getOpposite());
        BlockPos posBR = posBM.offset(enumFacing.rotateY());
        BlockPos posTL = posBL.up();
        BlockPos posTM = posBM.up();
        BlockPos posTR = posBR.up();

        // Check replaceable
        IBlockState stateBL = world.getBlockState(posBL);
        Block blockBL = stateBL.getBlock();
        boolean replaceableBL = blockBL.isReplaceable(world, posBL);

        IBlockState stateBM = world.getBlockState(posBM);
        Block blockBM = stateBM.getBlock();
        boolean replaceableBM = blockBM.isReplaceable(world, posBM);

        IBlockState stateBR = world.getBlockState(posBR);
        Block blockBR = stateBR.getBlock();
        boolean replaceableBR = blockBR.isReplaceable(world, posBR);

        IBlockState stateTL = world.getBlockState(posTL);
        Block blockTL = stateTL.getBlock();
        boolean replaceableTL = blockTL.isReplaceable(world, posTL);

        IBlockState stateTM = world.getBlockState(posTM);
        Block blockTM = stateTM.getBlock();
        boolean replaceableTM = blockTM.isReplaceable(world, posTM);

        IBlockState stateTR = world.getBlockState(posTR);
        Block blockTR = stateTR.getBlock();
        boolean replaceableTR = blockTR.isReplaceable(world, posTR);

        if (!replaceableBL || !replaceableBM || !replaceableBR ||
                !replaceableTL || !replaceableTM || !replaceableTR) {

            return EnumActionResult.FAIL;
        }

        // Place all
        IBlockState placeState = ModBlocks.furnaceStone.getDefaultState();

        world.setBlockState(posBM, placeState);
        world.setBlockState(posBR, placeState);
        world.setBlockState(posTR, placeState);
        world.setBlockState(posTM, placeState);
        world.setBlockState(posTL, placeState);
        world.setBlockState(posBL, placeState);

        // Set up tileentities with data
        ((TEFurnaceStone) world.getTileEntity(posBL)).setState(facing,
                EnumPartStone.BL.ordinal());
        ((TEFurnaceStone) world.getTileEntity(posBM)).setState(facing,
                EnumPartStone.BM.ordinal());
        ((TEFurnaceStone) world.getTileEntity(posBR)).setState(facing,
                EnumPartStone.BR.ordinal());
        ((TEFurnaceStone) world.getTileEntity(posTR)).setState(facing,
                EnumPartStone.TR.ordinal());
        ((TEFurnaceStone) world.getTileEntity(posTM)).setState(facing,
                EnumPartStone.TM.ordinal());
        ((TEFurnaceStone) world.getTileEntity(posTL)).setState(facing,
                EnumPartStone.TL.ordinal());

        // Use item
        world.playSound(null, posBL, SoundType.STONE.getPlaceSound(),
                SoundCategory.BLOCKS,
                SoundType.STONE.getVolume() + 1.0F / 2.0F,
                SoundType.STONE.getPitch() * 0.8F);

        if (!player.capabilities.isCreativeMode) {
            
            stack.shrink(1);
            ((ContainerInventory) player.inventoryContainer).sendUpdateOffhand();
        }
        
        return EnumActionResult.SUCCESS;
    }
}