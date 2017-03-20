package com.jayavery.jjmod.items;

import com.jayavery.jjmod.blocks.BlockDoor;
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
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

/** Item for custom doors. */
public class ItemDoor extends ItemJj {
    
    /** The door block of this item. */
    private BlockDoor block;
    
    public ItemDoor(BlockDoor block, String name) {
        
        super(name, 1, CreativeTabs.DECORATIONS);
        this.block = block;
    }
    
    /** Attemps to build this item's door structure. */
    @Override
    public EnumActionResult onItemUse(EntityPlayer player,
            World world, BlockPos bottomPos, EnumHand hand,
            EnumFacing side, float x, float y, float z) {
                
        if (world.isRemote) {
            
            return EnumActionResult.SUCCESS;
        }
        
        ItemStack stack = player.getHeldItem(hand);
        
        bottomPos = bottomPos.offset(side);
        IBlockState state = world.getBlockState(bottomPos);
        Block block = state.getBlock();
        
        BlockPos topPos = bottomPos.up();
        IBlockState stateTop = world.getBlockState(topPos);
        Block blockTop = stateTop.getBlock();
        
        if (!block.isReplaceable(world, bottomPos) ||
                !blockTop.isReplaceable(world, topPos) ||
                !this.block.canPlaceBlockAt(world, bottomPos)) {

            return EnumActionResult.FAIL;
        }
        
        int facing = MathHelper.floor(player.rotationYaw * 4.0F /
                360.0F + 0.5D) & 3;
        EnumFacing playerFacing = EnumFacing.getHorizontal(facing);
        
        IBlockState placeState = this.block.getDefaultState()
                .withProperty(BlockDoor.FACING, playerFacing)
                .withProperty(BlockDoor.OPEN, false);
        IBlockState bottomState = placeState
                .withProperty(BlockDoor.PART, BlockDoor.EnumPartDoor.SB);
        IBlockState topState = placeState
                .withProperty(BlockDoor.PART, BlockDoor.EnumPartDoor.ST);

        world.setBlockState(bottomPos, bottomState);
        world.setBlockState(topPos, topState);
        
        world.playSound(player, bottomPos, SoundType.WOOD.getPlaceSound(),
                SoundCategory.BLOCKS, (SoundType.WOOD.getVolume() + 1.0F) /
                2.0F, SoundType.WOOD.getPitch() * 0.8F);
        
        if (player.capabilities.isCreativeMode) {
        
            stack.shrink(1);
            ContainerInventory.updateHand(player, hand);
        }
        
        return EnumActionResult.SUCCESS;
    }
}
