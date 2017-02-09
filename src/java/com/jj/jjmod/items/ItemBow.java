 package com.jj.jjmod.items;

import javax.annotation.Nullable;
import com.jj.jjmod.container.ContainerInventory;
import com.jj.jjmod.entities.projectile.EntityProjectile;
import com.jj.jjmod.init.ModItems;
import com.jj.jjmod.utilities.InvLocation;
import com.jj.jjmod.utilities.InvLocation.InvType;
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

public class ItemBow extends ItemNew {
    
    public static final Class[] PRIORITY = {ItemArrowSteel.class, ItemArrowBronze.class, ItemArrowCopper.class, ItemArrowFlint.class, ItemArrowWood.class};
    
    private float powerModifier;
    
    public ItemBow(String name, int durability, float powerModifier) {

        super(name, 1, CreativeTabs.COMBAT);
        this.setMaxDamage(durability);

        // Check how far the bow is pulled for the model
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

        // Check whether the bow is being pulled for the model
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

    private InvLocation findAmmoSlot(EntityPlayer player) {

        InvType invType = InvType.MAIN;
        int index = -1;
        
        if (player.getHeldItem(EnumHand.OFF_HAND).getItem() instanceof ItemArrow) {

            index = 0;
            invType = InvType.OFFHAND;

        } else if (player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemArrow) {

            index = player.inventory.currentItem;

        } else {

            for (Class<?> type : PRIORITY) {
                
                for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
    
                    ItemStack stack = player.inventory.getStackInSlot(i);
    
                    if (type.isAssignableFrom(stack.getItem().getClass())) {
    
                        index = i;
                    }
                }
            }
        }
        
        return new InvLocation(player, invType, index);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world,
            EntityLivingBase entity, int timeLeft) {

        if (!(entity instanceof EntityPlayer)) {

            return;
        }

        EntityPlayer player = (EntityPlayer) entity;
        boolean creative = player.capabilities.isCreativeMode;
        InvLocation ammoSlot = this.findAmmoSlot(player);
        ItemStack ammo = ammoSlot.getStack();
        int chargeTime = this.getMaxItemUseDuration(stack) - timeLeft;

        if (ammo.isEmpty()) {

            if (creative) {

                ammo = new ItemStack(ModItems.arrowSteel);

            } else {

                return;
            }
        }

        float velocity = getArrowVelocity(chargeTime);

        if (velocity < 0.1 || world.isRemote) {

            return;
        }

        // Create entity arrow
        Item arrow = ammo.getItem();
        EntityProjectile entityArrow;

        if (arrow == ModItems.arrowWood) {

            ItemArrowWood arrowType = (ItemArrowWood) ammo.getItem();
            entityArrow =
                    arrowType.createArrow(world, ammo, player);


        } else if (arrow == ModItems.arrowFlint) {

            ItemArrowFlint arrowType = (ItemArrowFlint) ammo.getItem();
            entityArrow =
                    arrowType.createArrow(world, ammo, player);

        } else if (arrow == ModItems.arrowCopper) {

            ItemArrowCopper arrowType = (ItemArrowCopper) ammo.getItem();
            entityArrow =
                    arrowType.createArrow(world, ammo, player);

        } else if (arrow == ModItems.arrowBronze) {

            ItemArrowBronze arrowType = (ItemArrowBronze) ammo.getItem();
            entityArrow =
                    arrowType.createArrow(world, ammo, player);

        } else {

            ItemArrowSteel arrowType = (ItemArrowSteel) ammo.getItem();
            entityArrow =
                    arrowType.createArrow(world, ammo, player);

        }
        
        entityArrow.setAim(player, player.rotationPitch,
                player.rotationYaw, 0F, velocity * this.powerModifier, 1F);
        world.spawnEntity(entityArrow);
        world.playSound(null, player.posX, player.posY, player.posZ,
                SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1.0F,
                1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + velocity * 0.5F);

        stack.damageItem(1, player);

        if (!creative) {

            ammo.shrink(1);
            
            if (ammoSlot.getType() == InvType.OFFHAND) {
            
                ((ContainerInventory) player.inventoryContainer).sendUpdateOffhand();
                
            } else {
            
                ((ContainerInventory) player.inventoryContainer).sendUpdateInventory(ammoSlot.getIndex(), ammo);
            }
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {

        boolean hasAmmo = this.findAmmoSlot(player).isValid();
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
