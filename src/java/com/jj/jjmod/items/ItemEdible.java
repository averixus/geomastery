package com.jj.jjmod.items;

import com.jj.jjmod.capabilities.ICapPlayer;
import com.jj.jjmod.container.ContainerInventory;
import com.jj.jjmod.init.ModCaps;
import com.jj.jjmod.utilities.FoodType;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

/** Food items with food type. */
public class ItemEdible extends ItemFood {
    
    /** This item's food type. */
    protected final FoodType type;

    public ItemEdible(String name, int hunger, float saturation,
            int stackSize, FoodType type) {
        
        super(hunger, saturation, false);
        this.type = type;
        ItemJj.setupItem(this, name, stackSize, CreativeTabs.FOOD);
    }
    
    /** @return This item's food type. */
    public FoodType getType() {

        return this.type;
    }
    
    /** Starts eating this item if its food type is not full. */
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world,
            EntityPlayer player, EnumHand hand) {
        
        ItemStack stack = player.getHeldItem(hand);
        ICapPlayer playerCap = player
                .getCapability(ModCaps.CAP_PLAYER, null);

        if (playerCap.canEat(this.type)) {

            player.setActiveHand(hand);
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
        }
        
        return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
    }
    
    /** Eats this item. */
    @Override
    public ItemStack onItemUseFinish(ItemStack stack,
            World world, EntityLivingBase entity) {
        
        if (entity instanceof EntityPlayer) {
        
            EntityPlayer player = (EntityPlayer) entity;
            
            player.getCapability(ModCaps.CAP_PLAYER, null)
                .addStats(this, stack);
            
            world.playSound(player, player.posX, player.posY, player.posZ,
                    SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F,
                    world.rand.nextFloat() * 0.1F + 0.9F);
            
            stack.shrink(1);
            ContainerInventory.updateHand(player, player.getActiveHand());
        }
        
        return stack;

    }
}
