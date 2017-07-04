/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.items;

import java.util.Set;
import com.google.common.collect.Sets;
import jayavery.geomastery.capabilities.ICapPlayer;
import jayavery.geomastery.container.ContainerInventory;
import jayavery.geomastery.main.GeoCaps;
import jayavery.geomastery.utilities.FoodType;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
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
    /** Set of animals which can be bred with this item. */
    protected final Set<Class<? extends EntityAnimal>> animalEaters;

    @SafeVarargs
    public ItemEdible(String name, int hunger, float saturation, int stackSize,
            FoodType type, Class<? extends EntityAnimal>... animalEaters) {
        
        super(hunger, saturation, false);
        this.type = type;
        this.animalEaters = Sets.newHashSet(animalEaters);
        ItemSimple.setupItem(this, name, stackSize, CreativeTabs.FOOD);
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
                .getCapability(GeoCaps.CAP_PLAYER, null);

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
            
            player.getCapability(GeoCaps.CAP_PLAYER, null)
                .addStats(this, stack);
            
            world.playSound(player, player.posX, player.posY, player.posZ,
                    SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F,
                    world.rand.nextFloat() * 0.1F + 0.9F);
            
            if (!player.capabilities.isCreativeMode) {
                
                stack.shrink(1);
                ContainerInventory.updateHand(player, player.getActiveHand());
            }
        }
        
        return stack;

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
