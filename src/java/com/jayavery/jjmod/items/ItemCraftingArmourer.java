package com.jayavery.jjmod.items;

import com.jayavery.jjmod.container.ContainerInventory;
import com.jayavery.jjmod.init.ModBlocks;
import com.jayavery.jjmod.tileentities.TECraftingArmourer;
import com.jayavery.jjmod.tileentities.TECraftingArmourer.EnumPartArmourer;
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

/** Armourer crafting device item. */
public class ItemCraftingArmourer extends ItemJj {

    public ItemCraftingArmourer() {
        
        super("crafting_armourer", 1, CreativeTabs.DECORATIONS);
    }
    
    /** Attempts to build armourer crafting device structure. */
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world,
            BlockPos pos, EnumHand hand, EnumFacing side,
            float x, float y, float z) {
         
        if (world.isRemote) {
            
            return EnumActionResult.SUCCESS;
        }
        
        ItemStack stack = player.getHeldItem(hand);

        // Calculate Positions
        BlockPos posM = pos.offset(side);
        int intFacing = MathHelper.floor(player.rotationYaw* 4.0F /
                360.0F + 0.5D) & 3;
        EnumFacing enumFacing = EnumFacing.getHorizontal(intFacing);
        BlockPos posL = posM.offset(enumFacing.rotateYCCW());
        BlockPos posT = posL.up();
        BlockPos posR = posM.offset(enumFacing.rotateY());
        
        // Check replaceable
        IBlockState stateT = world.getBlockState(posT);
        Block blockT = stateT.getBlock();
        boolean replaceableT = blockT.isReplaceable(world, posT);
        
        IBlockState stateL = world.getBlockState(posL);
        Block blockL = stateL.getBlock();
        boolean replaceableL = blockL.isReplaceable(world, posL);
        
        IBlockState stateM = world.getBlockState(posM);
        Block blockM = stateM.getBlock();
        boolean replaceableM = blockM.isReplaceable(world, posM);
        
        IBlockState stateR = world.getBlockState(posR);
        Block blockR = stateR.getBlock();
        boolean replaceableR = blockR.isReplaceable(world, posR);
        
        if (!replaceableT || !replaceableL || !replaceableM || !replaceableR) {
            
            return EnumActionResult.FAIL;
        }
        
        // Place all
        IBlockState placeState = ModBlocks.craftingArmourer.getDefaultState();
        
        world.setBlockState(posT, placeState);
        world.setBlockState(posL, placeState);
        world.setBlockState(posM, placeState);
        world.setBlockState(posR, placeState);
        
        // Set up tileentities
        ((TECraftingArmourer) world.getTileEntity(posT))
                .setState(enumFacing, EnumPartArmourer.T);
        ((TECraftingArmourer) world.getTileEntity(posL))
                .setState(enumFacing, EnumPartArmourer.L);
        ((TECraftingArmourer) world.getTileEntity(posM))
                .setState(enumFacing, EnumPartArmourer.M);
        ((TECraftingArmourer) world.getTileEntity(posR))
                .setState(enumFacing, EnumPartArmourer.R);
        
        // Use item
        world.playSound(null, posM, SoundType.METAL.getPlaceSound(),
                SoundCategory.BLOCKS, SoundType.METAL.getVolume() + 1.0F
                / 2.0F, SoundType.METAL.getPitch());
        
        if (!player.capabilities.isCreativeMode) {
            
            stack.shrink(1);
            ContainerInventory.updateHand(player, hand);
        }
        
        return EnumActionResult.SUCCESS;
    }
}
