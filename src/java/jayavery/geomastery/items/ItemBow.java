/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
 package jayavery.geomastery.items;

import javax.annotation.Nullable;
import jayavery.geomastery.entities.projectile.EntityProjectile;
import jayavery.geomastery.main.GeoItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

/** Bow item with variable power and funcitonality to choose best arrows. */
public class ItemBow extends ItemSimple {
    
    /** Arrow classes in order of priority. */
    private static final Class<?>[] PRIORITY = {ItemArrow.Steel.class,
            ItemArrow.Bronze.class, ItemArrow.Copper.class,
            ItemArrow.Flint.class, ItemArrow.Wood.class};
    
    /** Modifier for firing velocity. */
    private float powerModifier;
    
    public ItemBow(String name, int durability, float powerModifier) {

        super(name, 1, CreativeTabs.COMBAT);
        this.setMaxDamage(durability);
        this.powerModifier = powerModifier;

        // Check how far the bow is pulled for the model
        this.addPropertyOverride(new ResourceLocation("pull"),
                new IItemPropertyGetter() {

            @Override
            public float apply(ItemStack stack, @Nullable World world,
                    @Nullable EntityLivingBase entity) {

                ItemStack activeStack = entity.getActiveItemStack();

                if (!(activeStack.getItem() instanceof ItemBow)) {

                    return 0F;

                } else {

                    return (stack.getMaxItemUseDuration() -
                            entity.getItemInUseCount()) / 20F;
                }
            }
        });

        // Check whether the bow is being pulled for the model
        this.addPropertyOverride(new ResourceLocation("pulling"),
                new IItemPropertyGetter() {

            @Override
            public float apply(ItemStack stack, @Nullable World world,
                    @Nullable EntityLivingBase entity) {

                if (entity == null || !entity.isHandActive() ||
                        entity.getActiveItemStack() != stack) {

                    return 0F;

                } else {

                    return 1F;
                }
            }
        });
    }
    
    @Override
    public boolean isEnchantable(ItemStack stack) {
        
        return false;
    }
    
    /** Fires an arrow. */
    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world,
            EntityLivingBase entity, int timeLeft) {

        if (!(entity instanceof EntityPlayer) || world.isRemote) {

            return;
        }

        EntityPlayer player = (EntityPlayer) entity;

        // Find and prepare arrow item
        ItemStack ammo = this.getAmmoStack(player);
        int chargeTime = this.getMaxItemUseDuration(stack) - timeLeft;

        if (ammo.isEmpty()) {

            if (player.capabilities.isCreativeMode) {

                ammo = new ItemStack(GeoItems.ARROW_STEEL);

            } else {

                return;
            }
        }

        float velocity = getArrowVelocity(chargeTime);

        if (velocity < 0.2) {

            return;
        }

        // Create arrow entity
        ItemArrow arrow = (ItemArrow) ammo.getItem();
        EntityProjectile entityArrow = arrow.createArrow(world, stack, player);

        entityArrow.setAim(player, player.rotationPitch,
                player.rotationYaw, 0F, velocity, 1F);
        world.spawnEntity(entityArrow);
        world.playSound(null, player.posX, player.posY, player.posZ,
                SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1.0F,
                1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + velocity * 0.5F);

        // Use and damage items
        if (!player.capabilities.isCreativeMode) {

            ammo.shrink(1);
            stack.damageItem(1, player);
        }
    }

    /** Charges bow if there is ammo. */
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world,
            EntityPlayer player, EnumHand hand) {

        boolean hasAmmo = !this.getAmmoStack(player).isEmpty();
        ItemStack stack = player.getHeldItem(hand);

        if (!player.capabilities.isCreativeMode && !hasAmmo) {

            return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);

        } else {

            player.setActiveHand(hand);
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
        }
    }

    /** @return The velocity of an arrow fired from this bow. */
    private float getArrowVelocity(int charge) {

        float velocity = charge / 20F;
        velocity = ((velocity * velocity) + (2F * velocity)) / 3F;

        if (velocity > 1F) {

            velocity = 1F;
        }

        return velocity * this.powerModifier;
    }
    
    /** @return The primary or prioritised arrow stack. */
    private ItemStack getAmmoStack(EntityPlayer player) {
        
        if (player.getHeldItem(EnumHand.OFF_HAND).getItem()
                instanceof ItemArrow) {

            return player.getHeldItem(EnumHand.OFF_HAND);

        } else if (player.getHeldItem(EnumHand.MAIN_HAND).getItem()
                instanceof ItemArrow) {

            return player.getHeldItem(EnumHand.MAIN_HAND);

        } else {

            for (Class<?> type : PRIORITY) {
                
                for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
    
                    ItemStack stack = player.inventory.getStackInSlot(i);
    
                    if (type.isAssignableFrom(stack.getItem().getClass())) {
    
                        return stack;
                    }
                }
            }
        }
        
        return ItemStack.EMPTY;
    }

    /** @return The time taken for the bow to charge. */
    @Override
    public int getMaxItemUseDuration(ItemStack stack) {

        return 72000;
    }
    
    /** Makes the player move like pulling a bow. */
    @Override
    public EnumAction getItemUseAction(ItemStack stack) {

        return EnumAction.BOW;
    }
}
