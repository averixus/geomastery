package com.jj.jjmod.items;

import java.util.Set;
import javax.annotation.Nullable;
import com.google.common.collect.Sets;
import com.jj.jjmod.container.ContainerInventory;
import net.minecraft.block.Block;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ItemSpearAbstract extends ItemTool {

    public static final Set<Block> EFFECTIVE_ON =
            Sets.newHashSet(new Block[] {});

    public ItemSpearAbstract(String name, ToolMaterial material) {

        super(3F, -3.1F, material, EFFECTIVE_ON);
        ItemNew.setupItem(this, name, 1, CreativeTabs.COMBAT);
        
        // Check whether spear is being charged for the model
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

            if (velocity >= 0.1) {

                if (!world.isRemote) {

                    throwSpear(world, player, velocity * 1.8F, stack.getItemDamage() + 1);
                    
                    if (!player.capabilities.isCreativeMode) {
                        
                        stack.shrink(1);
    
                        if (stack.getCount() == 0) {
    
                            stack = null;
                        }
    
                        ((ContainerInventory) player.inventoryContainer)
                                .sendUpdateHighlight();
                        ((ContainerInventory) player.inventoryContainer)
                                .sendUpdateOffhand();
                    }
                }

                world.playSound(null, player.posX, player.posY, player.posZ,
                        SoundEvents.ENTITY_ARROW_SHOOT,
                        SoundCategory.NEUTRAL, 1.0F, 1.0F /
                        (itemRand.nextFloat() * 0.4F + 1.2F) + velocity * 0.5F);
            }
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {

        ItemStack stack = player.getHeldItem(hand);
        player.setActiveHand(hand);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
    }

    public static float getVelocity(int charge) {

        float velocity = charge / 20F;
        velocity = ((velocity * velocity) + (velocity * 2F)) / 3F;

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

    public abstract void throwSpear(World world, EntityPlayer player,
            float velocity, int damage);

}
