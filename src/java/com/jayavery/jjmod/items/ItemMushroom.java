package com.jayavery.jjmod.items;

import com.jayavery.jjmod.container.ContainerInventory;
import com.jayavery.jjmod.utilities.FoodType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

public class ItemMushroom extends ItemEdibleDecayable implements IPlantable {

    public ItemMushroom() {
        
        super("mushroom", 2, 2, 10, FoodType.FRUITVEG, 2);
    }
    
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world,
            BlockPos pos, EnumHand hand, EnumFacing side,
            float x, float y, float z) {
        
        ItemStack stack = player.getHeldItem(hand);
        IBlockState state = world.getBlockState(pos);
        
        if (side == EnumFacing.UP &&
                state.getBlock().canSustainPlant(state, world,
                pos, EnumFacing.UP, this) && world.isAirBlock(pos.up())) {
            
            world.setBlockState(pos.up(),
                    Blocks.BROWN_MUSHROOM.getDefaultState());
            
            if (!player.capabilities.isCreativeMode) {
            
                stack.shrink(1);
                ContainerInventory.updateHand(player, hand);
            }
            
            return EnumActionResult.SUCCESS;
            
        } else {
            
            return EnumActionResult.FAIL;
        }
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {

        return EnumPlantType.Cave;
    }

    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos) {

        return Blocks.BROWN_MUSHROOM.getDefaultState();
    }

}
