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

/** Decayable food items. */
public class ItemEdibleDecayable extends ItemEdible {
    
    /** This item's shelf life in days. */
    private int shelfLife;

    public ItemEdibleDecayable(String name, int hunger, float saturation,
            int stackSize, FoodType foodType, int shelfLife) {
        
        super(name, hunger, saturation, stackSize, foodType);
        this.shelfLife = shelfLife;
    }
    
    /** Gives this item an ICapDecay with its shelf life. */
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack,
            NBTTagCompound nbt) {
        
        return new ProviderCapDecay(new DefaultCapDecay(this.shelfLife));
    }
    
    /** Ticks the ICapDecay while this item is an entity. */
    @Override
    public boolean onEntityItemUpdate(EntityItem entity) {
        
        if (!entity.world.isRemote && entity.getEntityItem()
                .getCapability(ModCapabilities.CAP_DECAY, null)
                .updateAndRot()) {
            
            entity.setEntityItemStack(new ItemStack(ModItems.rot));
        }
        
        return false;
    }
    
    /** Makes this item always show a durability bar. */
    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        
        return true;
    }
    
    /** Makes this item always show a full durability bar. */
    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        
        return 0;
    }
    
    /** Makes this item's durability bar colour represent its decay. */
    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        
        float fraction = stack.getCapability(ModCapabilities.CAP_DECAY, null)
                .getRenderFraction();
        return MathHelper.hsvToRGB(fraction / 3.0F, 1.0F, 1.0F);
    }
}

