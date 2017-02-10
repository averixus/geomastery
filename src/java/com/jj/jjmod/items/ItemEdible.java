package com.jj.jjmod.items;

import com.jj.jjmod.capabilities.ICapPlayer;
import com.jj.jjmod.init.ModCapabilities;
import com.jj.jjmod.utilities.FoodType;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemEdible extends ItemFood {
    
    private final FoodType type;

    public ItemEdible(String name, int hunger, float saturation,
            int stackSize, FoodType type) {
        
        super(hunger, saturation, false);
        this.type = type;
        ItemNew.setupItem(this, name, stackSize, CreativeTabs.FOOD);
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world,
            EntityPlayer player, EnumHand hand) {
        
        ItemStack stack = player.getHeldItem(hand);
        ICapPlayer capability = player
                .getCapability(ModCapabilities.CAP_PLAYER, null);
        System.out.println("on item right click " + this);
        if (capability.canEat(this.type)) {
            System.out.println("can eat, eating");
            player.setActiveHand(hand);
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
        }
        
        return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
    }
    
    public FoodType getType() {
        
        return this.type;
    }
}
