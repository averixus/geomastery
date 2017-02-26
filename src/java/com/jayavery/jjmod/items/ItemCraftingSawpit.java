package com.jayavery.jjmod.items;

import com.jayavery.jjmod.container.ContainerInventory;
import com.jayavery.jjmod.init.ModBlocks;
import com.jayavery.jjmod.tileentities.TECraftingSawpit;
import com.jayavery.jjmod.tileentities.TECraftingSawpit.EnumPartSawpit;
import com.jayavery.jjmod.utilities.IBuildingBlock;
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

/** Sawpit crafting device item. */
public class ItemCraftingSawpit extends ItemJj {
    
    public ItemCraftingSawpit() {
        
        super("crafting_sawpit", 1, CreativeTabs.DECORATIONS);
    }
    
    /** Attempts to build sawpit crafting device structure. */
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world,
            BlockPos pos, EnumHand hand, EnumFacing side,
            float x, float y, float z) {
                        
        if (world.isRemote) {
            
            return EnumActionResult.SUCCESS;
        }
        
        ItemStack stack = player.getHeldItem(hand);
        
        // Calculate positions
        BlockPos posB3 = pos.offset(side);
        int intFacing = MathHelper.floor(player.rotationYaw * 4F /
                360F + 0.5D) & 3;
        EnumFacing enumFacing = EnumFacing.getHorizontal(intFacing);
                
        BlockPos posB1 = posB3.offset(enumFacing.rotateYCCW(), 2);
        BlockPos posB2 = posB1.offset(enumFacing.rotateY());
        BlockPos posB4 = posB3.offset(enumFacing.rotateY());
        BlockPos posB5 = posB4.offset(enumFacing.rotateY());
        BlockPos posM1 = posB1.up();
        BlockPos posM2 = posB2.up();
        BlockPos posM3 = posB3.up();
        BlockPos posM4 = posB4.up();
        BlockPos posM5 = posB5.up();
        BlockPos posT1 = posM1.up();
        BlockPos posT2 = posM2.up();
        BlockPos posT3 = posM3.up();
        BlockPos posT4 = posM4.up();
        BlockPos posT5 = posM5.up();
        
        BlockPos[] allPositions = {posB1, posB2, posB3, posB4, posB5, posM1,
                posM2, posM3, posM4, posM5, posT1, posT2, posT3, posT4, posT5};
        
        BlockPos supportFL = posT1.offset(enumFacing.getOpposite());
        BlockPos supportBL = posT1.offset(enumFacing);
        BlockPos supportFR = posT5.offset(enumFacing.getOpposite());
        BlockPos supportBR = posT5.offset(enumFacing);
        BlockPos[] allSupports = {supportFL, supportBL, supportFR, supportBR};
        
        // Check supports
        for (BlockPos support : allSupports) {
            
            IBlockState state = world.getBlockState(support);
            Block block = state.getBlock();
            
            if (block instanceof IBuildingBlock) {
                
                IBuildingBlock building = (IBuildingBlock) block;
                
                if (!building.isDouble() || !building.isHeavy()) {
                    
                    return EnumActionResult.FAIL;
                }
                
            } else if (!ModBlocks.HEAVY.contains(block)) {
                
                return EnumActionResult.FAIL;
            }
        }
        
        // Check replaceable
        for (BlockPos aPos : allPositions) {
            
            IBlockState state = world.getBlockState(aPos);
            Block block = state.getBlock();
            
            if (!block.isReplaceable(world, aPos)) {
                
                return EnumActionResult.FAIL;
            }
        }
        
        // Place all
        IBlockState placeState = ModBlocks.craftingSawpit.getDefaultState();

        world.setBlockState(posB1, placeState);
        world.setBlockState(posB2, placeState);
        world.setBlockState(posB3, placeState);
        world.setBlockState(posB4, placeState);
        world.setBlockState(posB5, placeState);
        world.setBlockState(posM5, placeState);
        world.setBlockState(posM4, placeState);
        world.setBlockState(posM3, placeState);
        world.setBlockState(posM2, placeState);
        world.setBlockState(posM1, placeState);
        world.setBlockState(posT1, placeState);
        world.setBlockState(posT2, placeState);
        world.setBlockState(posT3, placeState);
        world.setBlockState(posT4, placeState);
        world.setBlockState(posT5, placeState);
        
        // Set up tileentities
        ((TECraftingSawpit) world.getTileEntity(posB1))
                .setState(enumFacing, EnumPartSawpit.B1);
        ((TECraftingSawpit) world.getTileEntity(posB2))
                .setState(enumFacing, EnumPartSawpit.B2);
        ((TECraftingSawpit) world.getTileEntity(posB3))
                .setState(enumFacing, EnumPartSawpit.B3);
        ((TECraftingSawpit) world.getTileEntity(posB4))
                .setState(enumFacing, EnumPartSawpit.B4);
        ((TECraftingSawpit) world.getTileEntity(posB5))
                .setState(enumFacing, EnumPartSawpit.B5);
        ((TECraftingSawpit) world.getTileEntity(posM5))
                .setState(enumFacing, EnumPartSawpit.M5);
        ((TECraftingSawpit) world.getTileEntity(posM4))
                .setState(enumFacing, EnumPartSawpit.M4);
        ((TECraftingSawpit) world.getTileEntity(posM3))
                .setState(enumFacing, EnumPartSawpit.M3);
        ((TECraftingSawpit) world.getTileEntity(posM2))
                .setState(enumFacing, EnumPartSawpit.M2);
        ((TECraftingSawpit) world.getTileEntity(posM1))
                .setState(enumFacing, EnumPartSawpit.M1);
        ((TECraftingSawpit) world.getTileEntity(posT1))
                .setState(enumFacing, EnumPartSawpit.T1);
        ((TECraftingSawpit) world.getTileEntity(posT2))
                .setState(enumFacing, EnumPartSawpit.T2);
        ((TECraftingSawpit) world.getTileEntity(posT3))
                .setState(enumFacing, EnumPartSawpit.T3);
        ((TECraftingSawpit) world.getTileEntity(posT4))
                .setState(enumFacing, EnumPartSawpit.T4);
        ((TECraftingSawpit) world.getTileEntity(posT5))
                .setState(enumFacing, EnumPartSawpit.T5);
   
        // Use item
        world.playSound(null, posB3, SoundType.WOOD.getPlaceSound(),
                SoundCategory.BLOCKS, SoundType.WOOD.getVolume() + 1F / 2F,
                SoundType.WOOD.getPitch() * 0.8F);
        
        if (!player.capabilities.isCreativeMode) {
            
            stack.shrink(1);
            ContainerInventory.updateHand(player, hand);
        }
        
        return EnumActionResult.SUCCESS;
    }
}
