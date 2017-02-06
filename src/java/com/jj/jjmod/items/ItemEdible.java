package com.jj.jjmod.items;

import com.jj.jjmod.capabilities.CapFoodstats;
import com.jj.jjmod.capabilities.ICapFoodstats;
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
    public ActionResult<ItemStack> onItemRightClick (World world,
            EntityPlayer player, EnumHand hand) {
        
        ItemStack stack = player.getHeldItem(hand);
        ICapFoodstats food = player
                .getCapability(CapFoodstats.CAP_FOODSTATS, null);
        
        if (food.canEat(this.type)) {
            
            super.onItemRightClick(world, player, hand);
        }
        
        return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
    }
    
    public FoodType getType() {
        
        return this.type;
    }
    
    public enum FoodType {
        
        CARBS, PROTEIN, FRUITVEG;
    }
}
