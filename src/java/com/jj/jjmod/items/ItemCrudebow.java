 package com.jj.jjmod.items;

import javax.annotation.Nullable;
import com.jj.jjmod.container.ContainerInventory;
import com.jj.jjmod.container.ContainerInventory.InvType;
import com.jj.jjmod.entities.projectile.EntityArrowBronze;
import com.jj.jjmod.entities.projectile.EntityArrowCopper;
import com.jj.jjmod.entities.projectile.EntityArrowFlint;
import com.jj.jjmod.entities.projectile.EntityArrowSteel;
import com.jj.jjmod.entities.projectile.EntityArrowWood;
import com.jj.jjmod.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCrudebow extends ItemNew {

    public ItemCrudebow() {

        super("crudebow", 1, CreativeTabs.COMBAT);
        this.setMaxDamage(200);

        // Check how far the bow is pulled for the texture
        this.addPropertyOverride(new ResourceLocation("pull"),
                new IItemPropertyGetter() {

            @Override
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World world,
                    @Nullable EntityLivingBase entity) {

                ItemStack activeStack = entity.getActiveItemStack();

                if (activeStack == null ||
                        activeStack.getItem() != ModItems.bowCrude) {

                    return 0F;

                } else {

                    return (stack.getMaxItemUseDuration() -
                            entity.getItemInUseCount()) / 20F;
                }
            }
        });

        // Check whether the bow is being pulled for the texture
        this.addPropertyOverride(new ResourceLocation("pulling"),
                new IItemPropertyGetter() {

            @Override
            @SideOnly(Side.CLIENT)
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

    private int findAmmoSlot(EntityPlayer player) {

        if (this.isArrow(player.getHeldItem(EnumHand.OFF_HAND))) {

            return 0;

        } else if (this.isArrow(player.getHeldItem(EnumHand.MAIN_HAND))) {

            return player.inventory.currentItem;

        } else {

            for (int i = 0; i < player.inventory.getSizeInventory(); i++) {

                ItemStack stack = player.inventory.getStackInSlot(i);

                if (this.isArrow(stack)) {

                    return i;
                }
            }

            return -1;
        }
    }
    
    private ItemStack getAmmoStack(EntityPlayer player, int slot) {
        
        ItemStack check = player.inventory.mainInventory.get(slot);
        
        if (this.isArrow(check)) {
            
            return check;
        }
        
        check = player.inventory.offHandInventory.get(slot);
        
        if (this.isArrow(check)) {
            
            return check;
        }
        
        return ItemStack.field_190927_a;
    }

    // Check whether itemstack is an arrow
    protected boolean isArrow(@Nullable ItemStack stack) {

        return stack != null && stack.getItem() instanceof ItemArrow;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world,
            EntityLivingBase entity, int timeLeft) {

        if (!(entity instanceof EntityPlayer)) {

            return;
        }

        EntityPlayer player = (EntityPlayer) entity;
        boolean creative = player.capabilities.isCreativeMode;
        int ammoSlot = this.findAmmoSlot(player);
        ItemStack ammo = this.getAmmoStack(player, ammoSlot);
        
        int timeSpent = this.getMaxItemUseDuration(stack) - timeLeft;

        if ((net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack,
                world, player, timeSpent,
                stack != null || creative)) < 0) {

            return;
        }

        if (ammo == ItemStack.field_190927_a) {

            if (creative) {

                ammo = new ItemStack(ModItems.arrowSteel);

            } else {

                return;
            }
        }

        float velocity = getArrowVelocity(timeSpent);

        if (velocity < 0.1 || world.isRemote) {

            return;
        }

        // Create entity arrow
        Item arrow = ammo.getItem();

        if (arrow == ModItems.arrowWood) {

            ItemArrowWood arrowType = (ItemArrowWood) ammo.getItem();
            EntityArrowWood entityArrow =
                    arrowType.createArrow(world, ammo, player);
            entityArrow.setAim(player, player.rotationPitch,
                    player.rotationYaw, 0F, velocity * 2F, 1F);
            world.spawnEntityInWorld(entityArrow);

        } else if (arrow == ModItems.arrowFlint) {

            ItemArrowFlint arrowType = (ItemArrowFlint) ammo.getItem();
            EntityArrowFlint entityArrow =
                    arrowType.createArrow(world, ammo, player);
            entityArrow.setAim(player, player.rotationPitch,
                    player.rotationYaw, 0F, velocity * 2F, 1F);
            world.spawnEntityInWorld(entityArrow);

        } else if (arrow == ModItems.arrowCopper) {

            ItemArrowCopper arrowType = (ItemArrowCopper) ammo.getItem();
            EntityArrowCopper entityArrow =
                    arrowType.createArrow(world, ammo, player);
            entityArrow.setAim(player, player.rotationPitch,
                    player.rotationYaw, 0F, velocity * 2F, 1F);
            world.spawnEntityInWorld(entityArrow);

        } else if (arrow == ModItems.arrowBronze) {

            ItemArrowBronze arrowType = (ItemArrowBronze) ammo.getItem();
            EntityArrowBronze entityArrow =
                    arrowType.createArrow(world, ammo, player);
            entityArrow.setAim(player, player.rotationPitch,
                    player.rotationYaw, 0F, velocity * 2F, 1F);
            world.spawnEntityInWorld(entityArrow);

        } else {

            ItemArrowSteel arrowType = (ItemArrowSteel) ammo.getItem();
            EntityArrowSteel entityArrow =
                    arrowType.createArrow(world, ammo, player);
            entityArrow.setAim(player, player.rotationPitch,
                    player.rotationYaw, 0F, velocity * 2.55F, 1F);
            world.spawnEntityInWorld(entityArrow);

        }

        world.playSound(null, player.posX, player.posY, player.posZ,
                SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1.0F,
                1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + velocity * 0.5F);

        stack.damageItem(1, player);

        if (!creative) {

            ammo.func_190918_g(1);

            if (ammo.func_190916_E() == 0) {

                player.inventory.deleteStack(ammo);
            }
            
            ((ContainerInventory) player.inventoryContainer).sendUpdateOffhand();
            ((ContainerInventory) player.inventoryContainer).sendUpdateInventory(InvType.INVENTORY, ammoSlot, ammo);
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {

        boolean hasAmmo = this.findAmmoSlot(player) != -1;
        ItemStack stack = player.getHeldItem(hand);

        ActionResult<ItemStack> action = net.minecraftforge.event
                .ForgeEventFactory.onArrowNock(stack, world,
                player, hand, hasAmmo);

        if (action != null) {

            return action;
        }

        if (!player.capabilities.isCreativeMode && !hasAmmo) {

            return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);

        } else {

            player.setActiveHand(hand);
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
        }
    }

    @Override
    public int getItemEnchantability() {

        return 1;
    }

    public static float getArrowVelocity(int charge) {

        float velocity = charge / 20F;
        velocity = ((velocity * velocity) + (2F * velocity)) / 3F;

        if (velocity > 1F) {

            velocity = 1F;
        }

        return velocity;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {

        return 72000;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {

        return EnumAction.BOW;
    }
}
