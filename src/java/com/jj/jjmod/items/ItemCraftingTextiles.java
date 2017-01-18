package com.jj.jjmod.items;

import com.jj.jjmod.blocks.BlockCraftingTextiles;
import com.jj.jjmod.blocks.BlockCraftingTextiles.EnumPartTextiles;
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

public class ItemCraftingTextiles extends ItemNew {

    public ItemCraftingTextiles() {

        super("crafting_textiles", 1, CreativeTabs.DECORATIONS);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player,
            World world, BlockPos pos, EnumHand hand, EnumFacing enumFacing,
            float x, float y, float z) {
        
        ItemStack stack = player.getActiveItemStack();

        if (world.isRemote) {

            return EnumActionResult.SUCCESS;
        }

        // Calculate positions
        BlockPos frontPos = pos.up();
        int i = MathHelper.floor_double(
                (double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        EnumFacing facing = EnumFacing.getHorizontal(i);
        BlockPos backPos = frontPos.offset(facing);

        // Check replaceable
        IBlockState frontState = world.getBlockState(frontPos);
        Block frontBlock = frontState.getBlock();
        boolean frontReplaceable = frontBlock.isReplaceable(world, frontPos);

        IBlockState backState = world.getBlockState(backPos);
        Block backBlock = backState.getBlock();
        boolean backReplaceable = backBlock.isReplaceable(world, backPos);

        if (!frontReplaceable || !backReplaceable) {

            return EnumActionResult.FAIL;
        }

        // Place all blocks
        IBlockState placeState = ModBlocks.craftingTextiles.getDefaultState()
                .withProperty(BlockCraftingTextiles.FACING, facing);

        backState = placeState.withProperty(BlockCraftingTextiles.PART,
                EnumPartTextiles.BACK);
        world.setBlockState(backPos, backState);

        frontState = placeState.withProperty(BlockCraftingTextiles.PART,
                EnumPartTextiles.FRONT);
        world.setBlockState(frontPos, frontState);

        // Use item
        world.playSound((EntityPlayer) null, frontPos,
                SoundType.WOOD.getPlaceSound(), SoundCategory.BLOCKS,
                (SoundType.WOOD.getVolume() + 1.0F) / 2.0F,
                SoundType.WOOD.getPitch() * 0.8F);
        
        stack.func_190918_g(1);

        if (stack.func_190916_E() == 0) {

            stack = null;
        }

        ((ContainerInventory) player.inventoryContainer).sendUpdateHighlight();
        return EnumActionResult.SUCCESS;
    }
}