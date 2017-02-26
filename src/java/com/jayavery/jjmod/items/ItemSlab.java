package com.jayavery.jjmod.items;

import com.jayavery.jjmod.blocks.BlockSlabDouble;
import com.jayavery.jjmod.blocks.BlockSlabSingle;
import com.jayavery.jjmod.container.ContainerInventory;
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

/** Slab item. */
public class ItemSlab extends ItemJj {

    /** Single slab block. */
    private final Block single;
    /** Double slab block. */
    private final Block doubble;
    
    public ItemSlab(String name, int stackSize, BlockSlabSingle single,
            BlockSlabDouble doubble) {
        
        super(name, stackSize, CreativeTabs.BUILDING_BLOCKS);
        this.single = single;
        this.doubble = doubble;
    }
    
    /** Attempts to place this slab. */
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world,
            BlockPos pos, EnumHand hand, EnumFacing side,
            float x, float y, float z) {
        
        if (world.isRemote) {
            
            return EnumActionResult.SUCCESS;
        }
        
        ItemStack stack = player.getHeldItem(hand);
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        
        if (block == this.single) {
            
            world.setBlockState(pos, this.doubble.getDefaultState());
            
        } else if (block.isReplaceable(world, pos) &&
                this.single.canPlaceBlockAt(world, pos)) {
            
            world.setBlockState(pos, this.single.getDefaultState());
            
        } else {
            
            pos = pos.offset(side);
            
            if (!block.isReplaceable(world, pos) ||
                    !this.single.canPlaceBlockAt(world, pos)) {
                
                return EnumActionResult.FAIL;
            }
            
            world.setBlockState(pos, this.single.getDefaultState());
        }
        
        world.playSound(player, pos, SoundType.STONE.getPlaceSound(),
                SoundCategory.BLOCKS, (SoundType.STONE.getVolume() + 1.0F) /
                2.0F, SoundType.STONE.getPitch() * 0.8F);
        
        if (!player.capabilities.isCreativeMode) {
            
            stack.shrink(1);
            ContainerInventory.updateHand(player, hand);
        }
        
        return EnumActionResult.SUCCESS;
    }
}
