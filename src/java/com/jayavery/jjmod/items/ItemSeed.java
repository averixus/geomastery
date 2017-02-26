package com.jayavery.jjmod.items;

import java.util.Set;
import com.google.common.collect.Sets;
import com.jayavery.jjmod.container.ContainerInventory;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

/** Seed item. */
public class ItemSeed extends ItemSeeds {
    
    /** Set of animals which can be bred with this item. */
    private final Set<Class<? extends EntityAnimal>> animalEaters;

    @SafeVarargs
    public ItemSeed(String name, int stackSize, Block crop,
            Class<? extends EntityAnimal>... animalEaters) {

        super(crop, Blocks.FARMLAND);
        this.animalEaters = Sets.newHashSet(animalEaters);
        ItemJj.setupItem(this, name, stackSize, CreativeTabs.MATERIALS);
    }
    
    /** Breeds or grows the right-clicked animal if applicable. */
    @Override
    public boolean itemInteractionForEntity(ItemStack stack,
            EntityPlayer player, EntityLivingBase entity, EnumHand hand) {
        
        if (entity.world.isRemote) {
            
            return true;
        }
        
        if (this.animalEaters.contains(entity.getClass())) {
            
            EntityAnimal animal = (EntityAnimal) entity;
            
            if (animal.getGrowingAge() == 0 && !animal.isInLove()) {
                
                if (!player.capabilities.isCreativeMode) {
                    
                    stack.shrink(1);
                    ContainerInventory.updateHand(player, hand);
                }
                
                animal.setInLove(player);
                return true;
            }
            
            if (animal.isChild()) {
                
                if (!player.capabilities.isCreativeMode) {
                    
                    stack.shrink(1);
                    ContainerInventory.updateHand(player, hand);
                }
                
                animal.ageUp((int)(((float)-animal.getGrowingAge() / 20) *
                        0.1F), true);
                return true;
            }
        }
        
        return false;
    }
}
