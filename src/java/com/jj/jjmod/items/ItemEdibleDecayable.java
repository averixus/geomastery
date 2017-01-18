package com.jj.jjmod.items;

import com.jj.jjmod.capabilities.CapDecay;
import com.jj.jjmod.capabilities.DefaultCapDecay;
import com.jj.jjmod.capabilities.ProviderCapDecay;
import com.jj.jjmod.init.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class ItemEdibleDecayable extends ItemEdible {

    public ItemEdibleDecayable(String name, int hunger, float saturation,
            int stackSize) {
        
        super(name, hunger, saturation, stackSize);
    }
    
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack,
            NBTTagCompound nbt) {
        
        return new ProviderCapDecay(new DefaultCapDecay(1000));
    }
    
    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity,
            int slot, boolean isSelected) {
        
        if (stack.getCapability(CapDecay.CAP_DECAY, null).updateAndRot() &&
                entity instanceof EntityPlayer) {
        
            EntityPlayer player = (EntityPlayer) entity;
            int size = stack.func_190916_E();  
            ItemStack rot = new ItemStack(ModItems.rot, size);
            
            ItemStack compare = player.inventory.mainInventory.get(slot);
            
            if (ItemStack.areItemStacksEqual(stack, compare)) {
                
                player.inventory.mainInventory.set(slot, rot);
            }
            
            compare = player.inventory.offHandInventory.get(slot);
            
            if (ItemStack.areItemStacksEqual(stack, compare)) {
                
                player.inventory.offHandInventory.set(slot, rot);
            }
        }
    }
    
    @Override
    public boolean onEntityItemUpdate(EntityItem entity) {
        
        if (!entity.worldObj.isRemote && entity.getEntityItem()
                .getCapability(CapDecay.CAP_DECAY, null).updateAndRot()) {
            
            entity.setEntityItemStack(new ItemStack(ModItems.rot,
                    entity.getEntityItem().func_190916_E()));
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

