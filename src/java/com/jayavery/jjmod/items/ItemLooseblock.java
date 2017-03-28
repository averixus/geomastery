package com.jayavery.jjmod.items;

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

/** Loose block item. */
public class ItemLooseblock extends ItemJj {
    
    /** Normal version of the block. */
    private final Block block;
    
    public ItemLooseblock(String name, Block block) {
        
        super(name, 1, CreativeTabs.DECORATIONS);
        this.block = block;
    }
    
    /** Attempts to place this block. */
    @Override
    public EnumActionResult onItemUse(EntityPlayer player,
            World world, BlockPos pos, EnumHand hand, EnumFacing side,
            float x, float y, float z) {
        
        if (world.isRemote) {
            
            return EnumActionResult.SUCCESS;
        }
        
        ItemStack stack = player.getHeldItem(hand);
        BlockPos posTarget = pos.offset(side);
        IBlockState stateTarget = world.getBlockState(posTarget);
        Block blockTarget = stateTarget.getBlock();
        
        if (!blockTarget.isReplaceable(world, pos)) {
            
            return EnumActionResult.FAIL;
        }
        
        BlockPos posBelow = pos;
        IBlockState stateBelow = world.getBlockState(posBelow);
        
        if (side != EnumFacing.DOWN) {
            
            posBelow = pos.offset(side).down();
        }
        
        if (!stateBelow.getBlock().isBlockSolid(world,
                posBelow, EnumFacing.UP)) {
            
            return EnumActionResult.FAIL;
        }
        
        for (EnumFacing facing : EnumFacing.HORIZONTALS) {
            
            BlockPos offset = posBelow.offset(facing);
            IBlockState state = world.getBlockState(offset);
            
            if (!state.getBlock().isBlockSolid(world,
                    offset, facing.getOpposite())) {
                
                return EnumActionResult.FAIL;
            }
        }
                
        IBlockState place = this.block.getDefaultState();
        world.setBlockState(posTarget, place);
        
        SoundType sound = this.block.getSoundType(place, world,
                posTarget, player);
        world.playSound(null, posTarget, sound.getPlaceSound(),
                SoundCategory.BLOCKS, sound.getVolume() + 1.0F / 2.0F,
                sound.getPitch());
        
        if (!player.capabilities.isCreativeMode) {
            
            stack.shrink(1);
            ContainerInventory.updateHand(player, hand);
        }
        
        return EnumActionResult.SUCCESS;
    }
}
