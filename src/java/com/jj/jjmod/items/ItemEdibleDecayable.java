package com.jj.jjmod.items;

import javax.annotation.Nullable;
import com.jj.jjmod.capabilities.DefaultCapDecay;
import com.jj.jjmod.capabilities.ICapDecay;
import com.jj.jjmod.capabilities.ICapPlayer;
import com.jj.jjmod.capabilities.ProviderCapDecay;
import com.jj.jjmod.init.ModCapabilities;
import com.jj.jjmod.utilities.FoodType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

/** Decayable food items. */
public class ItemEdibleDecayable extends ItemEdible {
    
    /** This item's shelf life in days. */
    private int shelfLife;

    public ItemEdibleDecayable(String name, int hunger, float saturation,
            int stackSize, FoodType foodType, int shelfLife) {
        
        super(name, hunger, saturation, stackSize, foodType);
        this.shelfLife = shelfLife;
        
        // Check whether the item is rotten for model
        this.addPropertyOverride(new ResourceLocation("rot"),
                new IItemPropertyGetter() {
            
            @Override
            public float apply(ItemStack stack, @Nullable World world,
                    @Nullable EntityLivingBase entity) {
                
                if (stack.hasCapability(ModCapabilities.CAP_DECAY, null)) {
                    
                    if (stack.getCapability(ModCapabilities.CAP_DECAY, null)
                            .isRot()) {
                        
                        return 1;
                    }
                }
                
                return 0;
            }
        });
    }
    
    /** Gives this item an ICapDecay with its shelf life. */
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack,
            NBTTagCompound nbt) {

        return new ProviderCapDecay(new DefaultCapDecay(this.shelfLife));
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world,
            EntityPlayer player, EnumHand hand) {
        
        ItemStack stack = player.getHeldItem(hand);
        ICapDecay decayCap = stack
                .getCapability(ModCapabilities.CAP_DECAY, null);
        ICapPlayer playerCap = player
                .getCapability(ModCapabilities.CAP_PLAYER, null);

        if (playerCap.canEat(this.type) && !decayCap.isRot()) {

            player.setActiveHand(hand);
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
        }
        
        return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
    }
    
    /** Makes this item always show a durability bar. */
    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        
        return true;
    }
    
    /** Makes this item always show a full durability bar. */
    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        
        return 0;
    }
    
    /** Makes this item's durability bar colour represent its decay. */
    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        
        if (stack.hasCapability(ModCapabilities.CAP_DECAY, null)) {
            
            float fraction = stack.getCapability(ModCapabilities.CAP_DECAY,
                    null).getRenderFraction();
            return MathHelper.hsvToRGB(fraction / 3.0F, 1.0F, 1.0F);
        }
        
        return 0;
    }
}

