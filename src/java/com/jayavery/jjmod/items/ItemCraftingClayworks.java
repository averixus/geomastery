package com.jayavery.jjmod.items;

import com.jayavery.jjmod.blocks.BlockCraftingClayworks;
import com.jayavery.jjmod.blocks.BlockCraftingClayworks.EnumPartClayworks;
import com.jayavery.jjmod.container.ContainerInventory;
import com.jayavery.jjmod.init.ModBlocks;
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

public class ItemCraftingClayworks extends ItemJj {

    public ItemCraftingClayworks() {

        super("crafting_clayworks", 1, CreativeTabs.DECORATIONS);
    }

    /** Attempts to build clayworks crafting device structure. */
    @Override
    public EnumActionResult onItemUse(EntityPlayer player,
            World world, BlockPos pos, EnumHand hand, EnumFacing side,
            float x, float y, float z) {
        
        ItemStack stack = player.getHeldItem(hand);

        if (world.isRemote) {

            return EnumActionResult.SUCCESS;
        }

        // Calculate positions
        BlockPos posFR = pos.offset(side);
        int intFacing = MathHelper.floor(player.rotationYaw * 4.0F /
                360.0F + 0.5D) & 3;
        EnumFacing enumFacing = EnumFacing.getHorizontal(intFacing);
        BlockPos posFL = posFR.offset(enumFacing.rotateY().getOpposite());
        BlockPos posBL = posFL.offset(enumFacing);
        BlockPos posBR = posFR.offset(enumFacing);

        // Check replaceable
        IBlockState stateFR = world.getBlockState(posFR);
        Block blockFR = stateFR.getBlock();
        boolean replaceableFR = blockFR.isReplaceable(world, posFR);

        IBlockState stateFL = world.getBlockState(posFL);
        Block blockFL = stateFL.getBlock();
        boolean replaceableFL = blockFL.isReplaceable(world, posFL);

        IBlockState stateBL = world.getBlockState(posBL);
        Block blockBL = stateBL.getBlock();
        boolean replaceableBL = blockBL.isReplaceable(world, posBL);

        IBlockState stateBR = world.getBlockState(posBR);
        Block blockBR = stateBR.getBlock();
        boolean replaceableBR = blockBR.isReplaceable(world, posBR);

        if (!replaceableFR || !replaceableFL ||
                !replaceableBL || !replaceableBR) {

            return EnumActionResult.FAIL;
        }

        // Place all
        IBlockState stateDef = ModBlocks.craftingClayworks.getDefaultState();
        stateDef = stateDef.withProperty(BlockCraftingClayworks.FACING,
                enumFacing);

        stateFR = stateDef.withProperty(BlockCraftingClayworks.PART,
                EnumPartClayworks.FR);
        world.setBlockState(posFR, stateFR);

        stateFL = stateDef.withProperty(BlockCraftingClayworks.PART,
                EnumPartClayworks.FL);
        world.setBlockState(posFL, stateFL);

        stateBL = stateDef.withProperty(BlockCraftingClayworks.PART,
                EnumPartClayworks.BL);
        world.setBlockState(posBL, stateBL);

        stateBR = stateDef.withProperty(BlockCraftingClayworks.PART,
                EnumPartClayworks.BR);
        world.setBlockState(posBR, stateBR);

        // Use item
        world.playSound(null, posFR, SoundType.STONE.getPlaceSound(),
                SoundCategory.BLOCKS, SoundType.STONE.getVolume() +
                1.0F / 2.0F, SoundType.STONE.getPitch() * 0.8F);
        
        if (!player.capabilities.isCreativeMode) {
            
            stack.shrink(1);
            ContainerInventory.updateHand(player, hand);
        }
        
        return EnumActionResult.SUCCESS;
    }
}