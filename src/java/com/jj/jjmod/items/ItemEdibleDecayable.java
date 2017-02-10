package com.jj.jjmod.items;

import com.jj.jjmod.capabilities.DefaultCapDecay;
import com.jj.jjmod.capabilities.ProviderCapDecay;
import com.jj.jjmod.init.ModCapabilities;
import com.jj.jjmod.init.ModItems;
import com.jj.jjmod.utilities.FoodType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class ItemEdibleDecayable extends ItemEdible {
    
    private int shelfLife;

    public ItemEdibleDecayable(String name, int hunger, float saturation,
            int stackSize, FoodType foodType, int shelfLife) {
        
        super(name, hunger, saturation, stackSize, foodType);
        this.shelfLife = shelfLife;
    }
    
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack,
            NBTTagCompound nbt) {
        
        return new ProviderCapDecay(new DefaultCapDecay(this.shelfLife));
    }
    
    @Override
    public boolean onEntityItemUpdate(EntityItem entity) {
        
        if (!entity.world.isRemote && entity.getEntityItem()
                .getCapability(ModCapabilities.CAP_DECAY, null).updateAndRot()) {
            
            entity.setEntityItemStack(new ItemStack(ModItems.rot));
        }
        
        return false;
    }
    
    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        
        return true;
    }
    
    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        
        return 0;
    }
    
    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        
        float fraction = stack.getCapability(ModCapabilities.CAP_DECAY, null)
                .getRenderFraction();
        return MathHelper.hsvToRGB(fraction / 3.0F, 1.0F, 1.0F);
    }
}

