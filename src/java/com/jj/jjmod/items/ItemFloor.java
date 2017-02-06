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

public class ItemFloor extends ItemNew {
    
    protected EnumFloor floor;

    public ItemFloor(int stackSize, EnumFloor floor) {
        
        super("floor_" + floor.getName(), stackSize, CreativeTabs.DECORATIONS);
        this.floor = floor;
    }
    
    @Override
    public EnumActionResult onItemUse(EntityPlayer player,
            World world, BlockPos pos, EnumHand hand,
            EnumFacing side,
            float x, float y, float z) {
        
        ItemStack stack = player.getHeldItem(hand);
        System.out.println("on item use");
        if (world.isRemote) {

            return EnumActionResult.SUCCESS;
        }
        
        Block block = world.getBlockState(pos).getBlock();
        TileEntity tileEntity = world.getTileEntity(pos);
        
        if (block != ModBlocks.beam || !(tileEntity instanceof TEBeam)) {
            System.out.println("not beam or TEBeam");
            return EnumActionResult.FAIL;
        }
        
        TEBeam tileBeam = (TEBeam) tileEntity;
        
        if (tileBeam.getFloor() != EnumFloor.NONE) {
            System.out.println("floor not none");
            return EnumActionResult.FAIL;
        }
        
        // Use item
        System.out.println("about to apply floor to " + tileBeam.getFloor());
        if (tileBeam.applyFloor(this.floor)) {
            System.out.println("just applied floor to " + tileBeam.getFloor());
            
            world.playSound(null, pos, SoundType.WOOD.getPlaceSound(),
                    SoundCategory.BLOCKS,
                    SoundType.WOOD.getVolume() + 1.0F / 2.0F,
                    SoundType.WOOD.getPitch() * 0.8F);
            
            if (!player.capabilities.isCreativeMode) {
                System.out.println("using item");
                stack.shrink(1);
                ((ContainerInventory) player.inventoryContainer).sendUpdateHighlight();
            }
            
            return EnumActionResult.SUCCESS;
        }
        
        return EnumActionResult.FAIL;
    }

}
