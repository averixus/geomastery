package com.jj.jjmod.items;

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

public class ItemWall extends ItemNew {
    
    public Block singleWall;
    public Block doubleWall;

    public ItemWall(String name, int stackSize, Block single, Block duble) {
        
        super(name, stackSize, CreativeTabs.BUILDING_BLOCKS);
        this.singleWall = single;
        this.doubleWall = duble;
    }
    
    @Override
    public EnumActionResult onItemUse(EntityPlayer player,
            World world, BlockPos pos, EnumHand hand, EnumFacing side,
            float x, float y, float z) {
                
        if (world.isRemote) {
            
            return EnumActionResult.SUCCESS;
        }
        
        ItemStack stack = player.getHeldItem(hand);
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        
        if (block == this.singleWall && side != EnumFacing.UP && this.doubleWall.canPlaceBlockAt(world, pos)) {
            
            world.setBlockState(pos, this.doubleWall.getDefaultState());
            
        } else if (block.isReplaceable(world, pos) && this.singleWall.canPlaceBlockAt(world, pos)) {
            
            world.setBlockState(pos, this.singleWall.getDefaultState());
            
        } else {
            
            pos = pos.offset(side);
            
            if (!block.isReplaceable(world, pos) || !this.singleWall.canPlaceBlockAt(world, pos)) {
                
                return EnumActionResult.FAIL;
            }
            
            world.setBlockState(pos, this.singleWall.getDefaultState());
        }
        
        SoundType sound = this.singleWall.getSoundType();
        world.playSound(player, pos, sound.getPlaceSound(),
                SoundCategory.BLOCKS, (sound.getVolume() + 1.0F) / 2.0F,
                sound.getPitch() * 0.8F);
        
        if (!player.capabilities.isCreativeMode) {
            
            stack.func_190918_g(1);
            
            if (stack.func_190916_E() == 0) {
                
                stack = ItemStack.field_190927_a;
            }
            
            ((ContainerInventory) player.inventoryContainer).sendUpdateHighlight();
        }
        
        return EnumActionResult.SUCCESS;
    }
}
