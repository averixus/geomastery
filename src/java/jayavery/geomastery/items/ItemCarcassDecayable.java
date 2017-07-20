/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.items;

import javax.annotation.Nullable;
import jayavery.geomastery.blocks.BlockCarcass;
import jayavery.geomastery.capabilities.DefaultCapDecay;
import jayavery.geomastery.capabilities.ICapDecay;
import jayavery.geomastery.capabilities.ProviderCapDecay;
import jayavery.geomastery.main.GeoCaps;
import jayavery.geomastery.main.GeoConfig;
import jayavery.geomastery.main.Geomastery;
import jayavery.geomastery.utilities.Lang;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Decayable carcass placing item. */
public class ItemCarcassDecayable extends ItemPlacing.Building {
    
    /** The shelf life of this carcass. */
    private final int shelfLife;
    
    public ItemCarcassDecayable(BlockCarcass block, int stackSize) {
        
        super(block, stackSize);
        this.setCreativeTab(CreativeTabs.FOOD);
        this.shelfLife = block.getShelfLife();
        this.addPropertyOverride(new ResourceLocation("rot"),
                new IItemPropertyGetter() {
            
            @Override
            public float apply(ItemStack stack, @Nullable World world,
                    @Nullable EntityLivingBase entity) {
                
                if (world == null && entity != null) {
                    
                    world = entity.world;
                }
                
                if (stack.hasCapability(GeoCaps.CAP_DECAY, null)) {
                    
                    ICapDecay decayCap = stack
                            .getCapability(GeoCaps.CAP_DECAY, null);
                    decayCap.updateFromNBT(stack.getTagCompound());

                    if (decayCap.isRot(world)) {

                        return 1;
                    }
                }

                return 0;
            }
        });
    }
    
    // Adds rotten to name if applicable
    @SideOnly(Side.CLIENT) @Override
    public String getItemStackDisplayName(ItemStack stack) {
        
        if (stack.hasCapability(GeoCaps.CAP_DECAY, null)) {
            
            ICapDecay decayCap = stack.getCapability(GeoCaps.CAP_DECAY, null);
            decayCap.updateFromNBT(stack.getTagCompound());
            
            if (decayCap.isRot(Geomastery.proxy.getClientWorld())) {
            
                return I18n.format(Lang.ROTTEN) +
                        super.getItemStackDisplayName(stack);
            }
        }
        
        return super.getItemStackDisplayName(stack);
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack,
            NBTTagCompound nbt) {
        
        return new ProviderCapDecay(new DefaultCapDecay(this.shelfLife));
    }

    // Sends the capability data to the client because there is
    // no other way to sync it reliably
    @Override
    public NBTTagCompound getNBTShareTag(ItemStack stack) {

        NBTTagCompound tag = stack.getTagCompound() == null ?
                new NBTTagCompound() : stack.getTagCompound();
        
        if (stack.hasCapability(GeoCaps.CAP_DECAY, null)) {

            tag.setLong("birthTime", stack.getCapability(GeoCaps.CAP_DECAY,
                    null).getBirthTime());
            tag.setInteger("stageSize", stack.getCapability(GeoCaps.CAP_DECAY,
                    null).getStageSize());
        }
        
        return tag;
    }
    
    // Makes this item always show a durability bar.
    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        
        return true;
    }
    
    // Makes this item show a full durability bar unless config otherwise. 
    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        
        if (GeoConfig.foodDurability) {
            
            if (stack.hasCapability(GeoCaps.CAP_DECAY, null)) {
                
                ICapDecay decayCap = stack.getCapability(GeoCaps.CAP_DECAY,
                        null);
                decayCap.updateFromNBT(stack.getTagCompound());
                return 1F - decayCap.getFraction(Geomastery.proxy
                        .getClientWorld());
            }
        }
        
        return 0;
    }
    
    // Makes this item's durability bar colour represent its decay. 
    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        
        if (stack.hasCapability(GeoCaps.CAP_DECAY, null)) {
            
            ICapDecay decayCap = stack.getCapability(GeoCaps.CAP_DECAY, null);
            decayCap.updateFromNBT(stack.getTagCompound());
            float fraction = decayCap.getFraction(Geomastery.proxy
                    .getClientWorld());
            return MathHelper.hsvToRGB(fraction / 3.0F, 1.0F, 1.0F);
        }
        
        return 0;
    }

    // Puts fresh and rotten versions in creative inventory.
    @SideOnly(Side.CLIENT) @Override
    public void getSubItems(Item item, CreativeTabs tab,
            NonNullList<ItemStack> list) {
        
        list.add(ItemSimple.newStack(this, 1,
                Geomastery.proxy.getClientWorld()));    
        list.add(ItemSimple.rottenStack(this, 1));
    }
}
