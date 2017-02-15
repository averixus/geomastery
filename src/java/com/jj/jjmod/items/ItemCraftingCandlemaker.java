package com.jj.jjmod.items;

import com.jj.jjmod.blocks.BlockCraftingCandlemaker;
import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.container.ContainerInventory;
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

public class ItemCraftingCandlemaker extends ItemNew {

    public ItemCraftingCandlemaker() {

        super("crafting_candlemaker", 1, CreativeTabs.DECORATIONS);
    }

    /** Attempts to build candlemaker crafting device structure. */
    @Override
    public EnumActionResult onItemUse(EntityPlayer player,
            World world, BlockPos pos, EnumHand hand, EnumFacing side,
            float x, float y, float z) {
        
        ItemStack stack = player.getHeldItem(hand);

        if (world.isRemote) {

            return EnumActionResult.SUCCESS;
        }

        // Calculate positions
        int intFacing = MathHelper.floor((player.rotationYaw * 4.0F /
                360.0F) + 0.5D) & 3;
        EnumFacing enumFacing = EnumFacing.getHorizontal(intFacing);
        BlockPos posFront = pos.offset(side);
        BlockPos posBack = posFront.offset(enumFacing);

        // Check replaceable
        IBlockState stateFront = world.getBlockState(posFront);
        Block blockFront = stateFront.getBlock();
        boolean replaceableFront = blockFront.isReplaceable(world, posFront);

        IBlockState stateBack = world.getBlockState(posBack);
        Block blockBack = stateBack.getBlock();
        boolean replaceableBack = blockBack.isReplaceable(world, posBack);

        if (!replaceableBack || !replaceableFront) {

            return EnumActionResult.FAIL;
        }

        // Place all
        IBlockState stateDef = ModBlocks.craftingCandlemaker.getDefaultState();
        stateDef = stateDef.withProperty(BlockCraftingCandlemaker.FACING,
                enumFacing);

        stateFront = stateDef.withProperty(BlockCraftingCandlemaker.PART,
                BlockCraftingCandlemaker.EnumPartCandlemaker.FRONT);
        world.setBlockState(posFront, stateFront);

        stateBack = stateDef.withProperty(BlockCraftingCandlemaker.PART,
                BlockCraftingCandlemaker.EnumPartCandlemaker.BACK);
        world.setBlockState(posBack, stateBack);

        // Use item
        world.playSound(null, posFront, SoundType.WOOD.getPlaceSound(),
                SoundCategory.BLOCKS, (SoundType.WOOD.getVolume() + 1) / 2,
                SoundType.WOOD.getPitch() * 0.8F);
        
        if (!player.capabilities.isCreativeMode) {
            
            stack.shrink(1);
            ContainerInventory.updateHand(player, hand);
        }
        
        return EnumActionResult.SUCCESS;
    }
}
