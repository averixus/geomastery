package com.jj.jjmod.items;

import com.jj.jjmod.blocks.BlockSlabDouble;
import com.jj.jjmod.blocks.BlockSlabSingle;
import com.jj.jjmod.container.ContainerInventory;
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
import net.minecraft.world.World;

public class ItemSlab extends ItemNew {

    public Block single;
    public Block doubble;
    
    public ItemSlab(String name, int stackSize, BlockSlabSingle single, BlockSlabDouble doubble) {
        
        super(name, stackSize, CreativeTabs.BUILDING_BLOCKS);
        this.single = single;
        this.doubble = doubble;
    }
    
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float x, float y, float z) {
        
        if (world.isRemote) {
            
            return EnumActionResult.SUCCESS;
        }
        
        ItemStack stack = player.getHeldItem(hand);
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        
        if (block == this.single) {
            
            world.setBlockState(pos, this.doubble.getDefaultState());
            
        } else if (block.isReplaceable(world, pos) && this.single.canPlaceBlockAt(world, pos)) {
            
            world.setBlockState(pos, this.single.getDefaultState());
            
        } else {
            
            pos = pos.offset(side);
            
            if (!block.isReplaceable(world, pos) || !this.single.canPlaceBlockAt(world, pos)) {
                
                return EnumActionResult.FAIL;
            }
            
            world.setBlockState(pos, this.single.getDefaultState());
        }
        
        SoundType sound = this.single.getSoundType();
        world.playSound(player, pos, sound.getPlaceSound(), SoundCategory.BLOCKS, (sound.getVolume() + 1.0F) / 2.0F, sound.getPitch() * 0.8F);
        
        if (!player.capabilities.isCreativeMode) {
            
            stack.func_190918_g(1);
            ((ContainerInventory) player.inventoryContainer).sendUpdateHighlight();
        }
        
        return EnumActionResult.SUCCESS;
    }

}
