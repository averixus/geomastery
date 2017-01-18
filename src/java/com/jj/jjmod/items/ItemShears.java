package com.jj.jjmod.items;

import java.util.ArrayList;
import java.util.Set;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.math.BlockPos;

public class ItemShears extends ItemTool {

    public static final Set<Block> EFFECTIVE_ON =
            Sets.newHashSet(new Block[] {});

    public ItemShears(String name, ToolMaterial material) {

        super(1F, -3.1F, material, EFFECTIVE_ON);
        ItemNew.setupItem(this, name, 1, CreativeTabs.TOOLS);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack itemstack,
            net.minecraft.entity.player.EntityPlayer player,
            EntityLivingBase entity, net.minecraft.util.EnumHand hand) {

        if (entity.worldObj.isRemote) {
            
            return false;
        }
        
        if (entity instanceof net.minecraftforge.common.IShearable) {
            
            net.minecraftforge.common.IShearable target =
                    (net.minecraftforge.common.IShearable) entity;
            
            BlockPos pos = new BlockPos(entity.posX, entity.posY, entity.posZ);
            
            if (target.isShearable(itemstack, entity.worldObj, pos)) {
                
                java.util.List<ItemStack> drops = target
                        .onSheared(itemstack, entity.worldObj, pos,
                        net.minecraft.enchantment.EnchantmentHelper
                        .getEnchantmentLevel(net.minecraft.init
                        .Enchantments.FORTUNE, itemstack));

                java.util.Random rand = new java.util.Random();
                
                for (ItemStack stack: drops) {
                    
                    net.minecraft.entity.item.EntityItem ent =
                            entity.entityDropItem(stack, 1.0F);
                    ent.motionY += rand.nextFloat() * 0.05F;
                    ent.motionX += (rand.nextFloat() -
                            rand.nextFloat()) * 0.1F;
                    ent.motionZ += (rand.nextFloat() -
                            rand.nextFloat()) * 0.1F;
                }
                
                itemstack.damageItem(1, entity);
            }
            
            return true;
        }
        
        return false;
    }
}
