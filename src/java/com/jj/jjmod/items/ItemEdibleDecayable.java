package com.jj.jjmod.items;

import java.util.Arrays;
import java.util.List;
import com.jj.jjmod.capabilities.CapDecay;
import com.jj.jjmod.capabilities.DefaultCapDecay;
import com.jj.jjmod.capabilities.ProviderCapDecay;
import com.jj.jjmod.init.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class ItemEdibleDecayable extends ItemEdible {

    public ItemEdibleDecayable(String name, int hunger, float saturation,
            int stackSize, FoodType foodType) {
        
        super(name, hunger, saturation, stackSize, foodType);
    }
    
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack,
            NBTTagCompound nbt) {
        
        return new ProviderCapDecay(new DefaultCapDecay(1000));
    }
    
    @Override
    public boolean onEntityItemUpdate(EntityItem entity) {
        
        if (!entity.world.isRemote && entity.getEntityItem()
                .getCapability(CapDecay.CAP_DECAY, null).updateAndRot()) {
            
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
        
        return stack.getCapability(CapDecay.CAP_DECAY, null)
                .getRenderFraction();
    }
}

