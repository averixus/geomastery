package com.jj.jjmod.items;

import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

public class ItemEdibleDecayablePoison extends ItemEdibleDecayable {

    public ItemEdibleDecayablePoison(String name, int hunger, float saturation,
            int stackSize, FoodType foodType, int shelfLife) {
        
        super(name, hunger, saturation, stackSize, foodType, shelfLife);
        this.setPotionEffect(new PotionEffect(MobEffects.HUNGER, 600, 0), 0.5F);
    }

}
