package com.jj.jjmod.items;

import com.jj.jjmod.utilities.FoodType;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

public class ItemEdibleSeed extends ItemEdible implements IPlantable {

    private final Block crop;

    public ItemEdibleSeed(String name, int hunger,
            float saturation, int stackSize, Block crop, FoodType foodType) {

        super(name, hunger, saturation, stackSize, foodType);
        this.crop = crop;
    }
    
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world,
            BlockPos pos, EnumHand hand, EnumFacing side,
            float x, float y, float z) {
        
        ItemStack itemstack = player.getHeldItem(hand);
        IBlockState state = world.getBlockState(pos);
        
        if (side == EnumFacing.UP &&
                player.canPlayerEdit(pos.offset(side), side, itemstack) &&
                state.getBlock().canSustainPlant(state, world,
                pos, EnumFacing.UP, this) && world.isAirBlock(pos.up())) {
            
            world.setBlockState(pos.up(), this.crop.getDefaultState());
            itemstack.shrink(1);
            return EnumActionResult.SUCCESS;
            
        } else {
            
            return EnumActionResult.FAIL;
        }
    }
    
    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        
        return EnumPlantType.Crop;
    }
    
    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
        
        return this.crop.getDefaultState();
    }
}
