package com.jayavery.jjmod.items;

import java.util.Collections;
import java.util.Random;
import java.util.function.Function;
import com.jayavery.jjmod.container.ContainerInventory;
import com.jayavery.jjmod.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.IShearable;

/** Shears tool item. */
public class ItemShears extends ItemToolAbstract {
    
    /** Random function for wool yield using these shears. */
    private Function<Random, Integer> yield;

    public ItemShears(String name, ToolMaterial material,
            Function<Random, Integer> yield) {

        super(1F, -3.1F, material, Collections.emptySet());
        ItemJj.setupItem(this, name, 1, CreativeTabs.TOOLS);
        this.yield = yield;
    }

    /** Shears sheep. */
    @Override
    public boolean itemInteractionForEntity(ItemStack stack,
            EntityPlayer player, EntityLivingBase entity, EnumHand hand) {

        if (entity.world.isRemote) {
            
            return false;
        }
        
        if (entity instanceof IShearable) {
            
            IShearable target = (IShearable) entity;
            
            BlockPos pos = new BlockPos(entity.posX, entity.posY, entity.posZ);
            
            if (target.isShearable(stack, entity.world, pos)) {
                
                target.onSheared(stack, entity.world, pos, 0);
                entity.dropItem(ModItems.wool,
                        this.yield.apply(entity.world.rand));
                
                stack.damageItem(1, entity);
                ContainerInventory.updateHand(player, hand);
            }
            
            return true;
        }
        
        return false;
    }
}
