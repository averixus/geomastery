/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.items;

import java.util.Collections;
import javax.annotation.Nullable;
import jayavery.geomastery.entities.projectile.EntityProjectile;
import jayavery.geomastery.utilities.IProjectileFactory;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

/** Abstract superclass for spear tool items. */
public class ItemSpear extends ItemTool {
    
    /** Factory for spear projectile entity. */
    private IProjectileFactory factory;

    public ItemSpear(String name, ToolMaterial material,
            IProjectileFactory factory) {

        super(3F, -3.1F, material, Collections.emptySet());
        ItemSimple.setupItem(this, name, 1, CreativeTabs.COMBAT);
        this.factory = factory;
        
        // Check whether spear is being charged for the model
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
    
    // Starts charging throw
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world,
            EntityPlayer player, EnumHand hand) {
    
        ItemStack stack = player.getHeldItem(hand);
        player.setActiveHand(hand);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
    }

    // Throws spear
    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world,
            EntityLivingBase entity, int timeLeft) {

        if (entity instanceof EntityPlayer) {

            EntityPlayer player = (EntityPlayer) entity;
            int i = this.getMaxItemUseDuration(stack) - timeLeft;

            if (i < 0) {

                return;
            }

            float velocity = getVelocity(i);

            if (velocity >= 0.2) {

                if (!world.isRemote) {

                    this.throwSpear(world, player, velocity,
                            stack.getItemDamage() + 1);
                    
                    if (!player.capabilities.isCreativeMode) {
                        
                        stack.shrink(1);
                    }
                }

                world.playSound(null, player.posX, player.posY, player.posZ,
                        SoundEvents.ENTITY_ARROW_SHOOT,
                        SoundCategory.NEUTRAL, 1.0F, 1.0F /
                        (itemRand.nextFloat() * 0.4F + 1.2F) + velocity * 0.5F);
            }
        }
    }

    /** @return The velocity of thrown spear. */
    private static float getVelocity(int charge) {
    
        float velocity = charge / 20F;
        velocity = ((velocity * velocity) + (velocity * 2F)) / 3F;
    
        if (velocity > 1F) {
    
            velocity = 1F;
        }
    
        return velocity * EntityProjectile.SPEAR_MOD;
    }

    /** Creates this spear's thrown entity. */
    protected void throwSpear(World world, EntityPlayer player,
            float velocity, int durability) {
        
        EntityProjectile thrown = this.factory.create(world,
                player, durability);
        thrown.setAim(player, player.rotationPitch, player.rotationYaw,
                0, velocity, 1F);
        world.spawnEntity(thrown);
    }

    // Returns the time taken for the throw to charge
    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
    
        return 72000;
    }

    // Makes the player move like pulling a bow
    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
    
        return EnumAction.BOW;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        
        return false;
    }
}
