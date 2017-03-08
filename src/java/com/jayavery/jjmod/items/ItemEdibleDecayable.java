package com.jayavery.jjmod.items;

import com.jayavery.jjmod.capabilities.DefaultCapDecay;
import com.jayavery.jjmod.capabilities.ICapDecay;
import com.jayavery.jjmod.capabilities.ICapPlayer;
import com.jayavery.jjmod.capabilities.ProviderCapDecay;
import com.jayavery.jjmod.init.ModCaps;
import com.jayavery.jjmod.main.Main;
import com.jayavery.jjmod.utilities.FoodType;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Decayable food items. */
public class ItemEdibleDecayable extends ItemEdible {
    
    /** This item's shelf life in days. */
    private int shelfLife;

    @SafeVarargs
    public ItemEdibleDecayable(String name, int hunger, float saturation,
            int stackSize, FoodType foodType, int shelfLife,
            Class<? extends EntityAnimal>... animalEaters) {
        
        super(name, hunger, saturation, stackSize, foodType, animalEaters);
        this.shelfLife = shelfLife;
        
        // Check whether the item is rotten for model
        this.addPropertyOverride(new ResourceLocation("rot"),
                new IItemPropertyGetter() {
            
            @Override
            public float apply(ItemStack stack, @Nullable World world,
                    @Nullable EntityLivingBase entity) {
                
                if (world == null && entity != null) {
                    
                    world = entity.world;
                }
                
                if (stack.hasCapability(ModCaps.CAP_DECAY, null)) {
                    
                    ICapDecay decayCap = stack
                            .getCapability(ModCaps.CAP_DECAY, null);
                    decayCap.updateFromNBT(stack.getTagCompound());

                    if (decayCap.isRot(world)) {

                        return 1;
                    }
                }

                return 0;
            }
        });
    }

    @Override
    public NBTTagCompound getNBTShareTag(ItemStack stack) {

        NBTTagCompound tag = stack.getTagCompound() == null ?
                new NBTTagCompound() : stack.getTagCompound();
        
        if (stack.hasCapability(ModCaps.CAP_DECAY, null)) {

            tag.setLong("birthTime", stack.getCapability(ModCaps.CAP_DECAY,
                    null).getBirthTime());
            tag.setInteger("stageSize", stack.getCapability(ModCaps.CAP_DECAY,
                    null).getStageSize());
        }
        
        return tag;
    }
    
    /** Gives this item an ICapDecay with its shelf life. */
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack,
            NBTTagCompound nbt) {

        return new ProviderCapDecay(new DefaultCapDecay(this.shelfLife));
    }
    
    /** Starts eating if not rotten. */
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world,
            EntityPlayer player, EnumHand hand) {
        
        ItemStack stack = player.getHeldItem(hand);
        ICapDecay decayCap = stack
                .getCapability(ModCaps.CAP_DECAY, null);
        decayCap.updateFromNBT(stack.getTagCompound());
        ICapPlayer playerCap = player
                .getCapability(ModCaps.CAP_PLAYER, null);

        if (playerCap.canEat(this.type) && !decayCap.isRot(world)) {

            player.setActiveHand(hand);
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
        }
        
        return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
    }
    
    /** Makes this item named rotten according to capability. */
    @SideOnly(Side.CLIENT)
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        
        if (stack.hasCapability(ModCaps.CAP_DECAY, null)) {
                
            ICapDecay decayCap = stack.getCapability(ModCaps.CAP_DECAY, null);
            decayCap.updateFromNBT(stack.getTagCompound());
            
            if (decayCap.isRot(Main.proxy.getClientWorld())) {
            
                return "Rotten " + super.getItemStackDisplayName(stack);
            }
        }
        
        return super.getItemStackDisplayName(stack);
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
        
        if (stack.hasCapability(ModCaps.CAP_DECAY, null)) {
            
            ICapDecay decayCap = stack.getCapability(ModCaps.CAP_DECAY, null);
            decayCap.updateFromNBT(stack.getTagCompound());
            float fraction = decayCap.getRenderFraction();
            return MathHelper.hsvToRGB(fraction / 3.0F, 1.0F, 1.0F);
        }
        
        return 0;
    }
    
    /** Feeds to animals if not rotten. */
    @Override
    public boolean itemInteractionForEntity(ItemStack stack,
            EntityPlayer player, EntityLivingBase entity, EnumHand hand) {
        
        if (stack.hasCapability(ModCaps.CAP_DECAY, null)) {
            
            ICapDecay decayCap = stack.getCapability(ModCaps.CAP_DECAY, null);
            decayCap.updateFromNBT(stack.getTagCompound());

            if (!stack.getCapability(ModCaps.CAP_DECAY, null)
                .isRot(player.world)) {
            
                return super.itemInteractionForEntity(stack,
                        player, entity, hand);
            }
        }
        
        return false;
    }
}
