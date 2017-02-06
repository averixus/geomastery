package com.jj.jjmod.items;

import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.container.ContainerInventory;
import com.jj.jjmod.tileentities.TEFurnaceClay;
import com.jj.jjmod.tileentities.TEFurnaceClay.EnumPartClay;
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

public class ItemFurnaceClay extends ItemNew {

    public ItemFurnaceClay() {

        super("furnace_clay", 1, CreativeTabs.DECORATIONS);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player,
            World world, BlockPos pos, EnumHand hand,
            EnumFacing side, float x, float y, float z) {

        ItemStack stack = player.getActiveItemStack();
        
        if (world.isRemote) {

            return EnumActionResult.SUCCESS;
        }

        // Calculate positions
        BlockPos posBL = pos.up();
        int facing = MathHelper.floor((player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        EnumFacing enumFacing = EnumFacing.getHorizontal(facing);
        BlockPos posBR = posBL.offset(enumFacing.rotateY());
        BlockPos posTL = posBL.up();
        BlockPos posTR = posBR.up();

        // Check replaceable
        IBlockState stateBL = world.getBlockState(posBL);
        Block blockBL = stateBL.getBlock();
        boolean replaceableBL = blockBL.isReplaceable(world, posBL);

        IBlockState stateBR = world.getBlockState(posBR);
        Block blockBR = stateBR.getBlock();
        boolean replaceableBR = blockBR.isReplaceable(world, posBR);

        IBlockState stateTL = world.getBlockState(posTL);
        Block blockTL = stateTL.getBlock();
        boolean replaceableTL = blockTL.isReplaceable(world, posTL);

        IBlockState stateTR = world.getBlockState(posTR);
        Block blockTR = stateTR.getBlock();
        boolean replaceableTR = blockTR.isReplaceable(world, posTR);

        if (!replaceableBL || !replaceableBR ||
                !replaceableTL || !replaceableTR) {

            return EnumActionResult.FAIL;
        }

        // Place all
        IBlockState placeState = ModBlocks.furnaceClay.getDefaultState();

        world.setBlockState(posBL, placeState);
        world.setBlockState(posBR, placeState);
        world.setBlockState(posTR, placeState);
        world.setBlockState(posTL, placeState);

        // Set up tileentities
        ((TEFurnaceClay) world.getTileEntity(posBL)).setState(facing,
                EnumPartClay.BL.ordinal());
        ((TEFurnaceClay) world.getTileEntity(posBR)).setState(facing,
                EnumPartClay.BR.ordinal());
        ((TEFurnaceClay) world.getTileEntity(posTL)).setState(facing,
                EnumPartClay.TL.ordinal());
        ((TEFurnaceClay) world.getTileEntity(posTR)).setState(facing,
                EnumPartClay.TR.ordinal());

        // Use item
        world.playSound(null, posBL, SoundType.STONE.getPlaceSound(),
                SoundCategory.BLOCKS,
                SoundType.STONE.getVolume() + 1.0F / 2.0F,
                SoundType.STONE.getPitch() * 0.8F);
        
        stack.shrink(1);

        if (stack.getCount() == 0) {

            stack = null;
        }

        ((ContainerInventory) player.inventoryContainer).sendUpdateHighlight();
        return EnumActionResult.SUCCESS;
    }
}