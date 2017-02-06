package com.jj.jjmod.items;

import com.jj.jjmod.blocks.BlockDoor;
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

public class ItemDoor extends ItemNew {
    
    public BlockDoor block;
    
    public ItemDoor(BlockDoor block, String name) {
        
        super(name, 1, CreativeTabs.DECORATIONS);
        this.block = block;
    }
    
    @Override
    public EnumActionResult onItemUse(EntityPlayer player,
            World world, BlockPos pos, EnumHand hand,
            EnumFacing side, float x, float y, float z) {
        
        System.out.println("on item use");
        
        if (world.isRemote) {
            
            return EnumActionResult.SUCCESS;
        }
        
        ItemStack stack = player.getHeldItem(hand);
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        
        if (!block.isReplaceable(world, pos) || !this.block.canPlaceBlockAt(world, pos)) {
            
            pos = pos.offset(side);
        }
        
        BlockPos top = pos.up();
        IBlockState stateTop = world.getBlockState(top);
        Block blockTop = stateTop.getBlock();
        
        if (!block.isReplaceable(world, pos) || !blockTop.isReplaceable(world, top) || !this.block.canPlaceBlockAt(world, pos)) {
            System.out.println("this block " + block + " not replaceable OR");
            System.out.println("upper block " + blockTop + " not replaceable OR");
            System.out.println("can't place block at " + pos);
            return EnumActionResult.FAIL;
        }
        
        int facing = MathHelper.floor(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        EnumFacing playerFacing = EnumFacing.getHorizontal(facing);
        
        IBlockState placeState = this.block.getDefaultState().withProperty(BlockDoor.FACING, playerFacing).withProperty(BlockDoor.OPEN, false);
        IBlockState bottomState = placeState.withProperty(BlockDoor.PART, BlockDoor.EnumPart.SB);
        IBlockState topState = placeState.withProperty(BlockDoor.PART, BlockDoor.EnumPart.ST);

        world.setBlockState(pos, bottomState);
        world.setBlockState(top, topState);
        
        SoundType soundtype = world.getBlockState(pos).getBlock().getSoundType(world.getBlockState(pos), world, pos, player);
        world.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
        stack.shrink(1);
        System.out.println("success place");
        return EnumActionResult.SUCCESS;
    }
}
