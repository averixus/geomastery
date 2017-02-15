package com.jj.jjmod.items;

import com.jj.jjmod.utilities.FoodType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

/** Decayable and poisonous food items. */
public class ItemEdibleDecayablePoison extends ItemEdibleDecayable {

    public ItemEdibleDecayablePoison(String name, int hunger, float saturation,
            int stackSize, FoodType foodType, int shelfLife) {
        
        super(name, hunger, saturation, stackSize, foodType, shelfLife);
    }
    
    /** Eats this item and randomly applies poison. */
    @Override
    public ItemStack onItemUseFinish(ItemStack stack,
            World world, EntityLivingBase entity) {
        
        stack = super.onItemUseFinish(stack, world, entity);
        
        if (!world.isRemote && world.rand.nextFloat() < 0.5) {
            
            entity.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 600, 0));
        }
        
        return stack;
    }
}
