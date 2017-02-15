package com.jj.jjmod.items;

import com.jj.jjmod.container.ContainerInventory;
import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.tileentities.TEBeam;
import com.jj.jjmod.tileentities.TEBeam.EnumFloor;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Floor item. */
public class ItemFloor extends ItemNew {
    
    /** This item's floor type. */
    private EnumFloor floor;

    public ItemFloor(int stackSize, EnumFloor floor) {
        
        super("floor_" + floor.getName(), stackSize, CreativeTabs.DECORATIONS);
        this.floor = floor;
    }
    
    /** Attempts to apply this floor to the targested block. */
    @Override
    public EnumActionResult onItemUse(EntityPlayer player,
            World world, BlockPos pos, EnumHand hand,
            EnumFacing side, float x, float y, float z) {
        
        if (world.isRemote) {

            return EnumActionResult.SUCCESS;
        }
        
        ItemStack stack = player.getHeldItem(hand);
        
        Block block = world.getBlockState(pos).getBlock();
        TileEntity tileEntity = world.getTileEntity(pos);
        
        if (block != ModBlocks.beam || !(tileEntity instanceof TEBeam)) {

            return EnumActionResult.FAIL;
        }
        
        TEBeam tileBeam = (TEBeam) tileEntity;
        
        if (tileBeam.getFloor() != EnumFloor.NONE) {

            return EnumActionResult.FAIL;
        }
        
        // Use item
        if (tileBeam.applyFloor(this.floor)) {
            
            world.playSound(null, pos, SoundType.WOOD.getPlaceSound(),
                    SoundCategory.BLOCKS,
                    SoundType.WOOD.getVolume() + 1.0F / 2.0F,
                    SoundType.WOOD.getPitch() * 0.8F);
            
            if (!player.capabilities.isCreativeMode) {

                stack.shrink(1);
                ContainerInventory.updateHand(player, hand);
            }
            
            return EnumActionResult.SUCCESS;
        }
        
        return EnumActionResult.FAIL;
    }
}
