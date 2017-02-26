package com.jayavery.jjmod.items;

import com.jayavery.jjmod.container.ContainerInventory;
import com.jayavery.jjmod.init.ModBlocks;
import com.jayavery.jjmod.tileentities.TECraftingMason;
import com.jayavery.jjmod.tileentities.TECraftingMason.EnumPartMason;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

/** Mason crafting device item. */
public class ItemCraftingMason extends ItemJj {

    public ItemCraftingMason() {

        super("crafting_mason", 1, CreativeTabs.DECORATIONS);
    }

    /** Attempts to build mason crafting device structure. */
    @Override
    public EnumActionResult onItemUse(EntityPlayer player,
            World world, BlockPos pos, EnumHand hand, EnumFacing side,
            float x, float y, float z) {
        
        if (world.isRemote) {

            return EnumActionResult.SUCCESS;
        }
        
        ItemStack stack = player.getHeldItem(hand);

        // Calculate positions
        BlockPos posFM = pos.offset(side);
        int intFacing = MathHelper.floor((player.rotationYaw * 4.0F /
                360.0F) + 0.5D) & 3;
        EnumFacing enumFacing = EnumFacing.getHorizontal(intFacing);
        BlockPos posFL = posFM.offset(enumFacing.rotateY().getOpposite());
        BlockPos posBM = posFM.offset(enumFacing);
        BlockPos posFR = posFM.offset(enumFacing.rotateY());
        BlockPos posBR = posFR.offset(enumFacing);

        // Check replaceable
        IBlockState stateFM = world.getBlockState(posFM);
        Block blockFM = stateFM.getBlock();
        boolean replaceableFM = blockFM.isReplaceable(world, posFM);

        IBlockState stateFL = world.getBlockState(posFL);
        Block blockFL = stateFL.getBlock();
        boolean replaceableFL = blockFL.isReplaceable(world, posFL);

        IBlockState stateBM = world.getBlockState(posBM);
        Block blockBM = stateBM.getBlock();
        boolean replaceableBM = blockBM.isReplaceable(world, posBM);

        IBlockState stateBR = world.getBlockState(posBR);
        Block blockBR = stateBR.getBlock();
        boolean replaceableBR = blockBR.isReplaceable(world, posBR);

        IBlockState stateFR = world.getBlockState(posFR);
        Block blockFR = stateFR.getBlock();
        boolean replaceableFR = blockFR.isReplaceable(world, posFR);

        if (!replaceableFM || !replaceableFL || !replaceableBM ||
                !replaceableBR || !replaceableFR) {

            return EnumActionResult.FAIL;
        }

        // Place all
        IBlockState placeState = ModBlocks.craftingMason.getDefaultState();

        world.setBlockState(posFM, placeState);
        world.setBlockState(posFL, placeState);
        world.setBlockState(posBM, placeState);
        world.setBlockState(posBR, placeState);
        world.setBlockState(posFR, placeState);

        // Set up tileentities
        ((TECraftingMason) world.getTileEntity(posFM)).setState(enumFacing,
                EnumPartMason.FM);
        ((TECraftingMason) world.getTileEntity(posFL)).setState(enumFacing,
                EnumPartMason.FL);
        ((TECraftingMason) world.getTileEntity(posBM)).setState(enumFacing,
                EnumPartMason.BM);
        ((TECraftingMason) world.getTileEntity(posBR)).setState(enumFacing,
                EnumPartMason.BR);
        ((TECraftingMason) world.getTileEntity(posFR)).setState(enumFacing,
                EnumPartMason.FR);

        // Use item
        world.playSound(null, posFM, SoundType.STONE.getPlaceSound(),
                SoundCategory.BLOCKS,
                SoundType.METAL.getVolume() + 1.0F / 2.0F,
                SoundType.METAL.getPitch() * 0.8F);
        
        if (!player.capabilities.isCreativeMode) {
            
            stack.shrink(1);
            ContainerInventory.updateHand(player, hand);
        }
        
        return EnumActionResult.SUCCESS;
    }
}
