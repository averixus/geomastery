package com.jayavery.jjmod.items;

import com.jayavery.jjmod.container.ContainerInventory;
import com.jayavery.jjmod.init.ModBlocks;
import com.jayavery.jjmod.tileentities.TEFurnaceStone;
import com.jayavery.jjmod.tileentities.TEFurnaceStone.EnumPartStone;
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

/** Stone furnace item. */
public class ItemFurnaceStone extends ItemJj {

    public ItemFurnaceStone() {

        super("furnace_stone", 1, CreativeTabs.DECORATIONS);
    }

    /** Attempts to build stone furnace structure. */
    @Override
    public EnumActionResult onItemUse(EntityPlayer player,
            World world, BlockPos pos, EnumHand hand,
            EnumFacing side, float x, float y, float z) {
        
        if (world.isRemote) {

            return EnumActionResult.SUCCESS;
        }
        
        ItemStack stack = player.getHeldItem(hand);

        // Calculate positions
        BlockPos posBM = pos.offset(side);
        int intFacing = MathHelper.floor((player.rotationYaw * 4.0F / 
                360.0F) + 0.5D) & 3;
        EnumFacing enumFacing = EnumFacing.getHorizontal(intFacing);
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
        ((TEFurnaceStone) world.getTileEntity(posBL)).setState(enumFacing,
                EnumPartStone.BL);
        ((TEFurnaceStone) world.getTileEntity(posBM)).setState(enumFacing,
                EnumPartStone.BM);
        ((TEFurnaceStone) world.getTileEntity(posBR)).setState(enumFacing,
                EnumPartStone.BR);
        ((TEFurnaceStone) world.getTileEntity(posTR)).setState(enumFacing,
                EnumPartStone.TR);
        ((TEFurnaceStone) world.getTileEntity(posTM)).setState(enumFacing,
                EnumPartStone.TM);
        ((TEFurnaceStone) world.getTileEntity(posTL)).setState(enumFacing,
                EnumPartStone.TL);

        // Use item
        world.playSound(null, posBL, SoundType.STONE.getPlaceSound(),
                SoundCategory.BLOCKS,
                SoundType.STONE.getVolume() + 1.0F / 2.0F,
                SoundType.STONE.getPitch() * 0.8F);

        if (!player.capabilities.isCreativeMode) {
            
            stack.shrink(1);
            ContainerInventory.updateHand(player, hand);
        }
        
        return EnumActionResult.SUCCESS;
    }
}