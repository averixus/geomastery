package com.jayavery.jjmod.items;

import javax.annotation.Nullable;
import com.jayavery.jjmod.blocks.BlockCarcass;
import com.jayavery.jjmod.capabilities.DefaultCapDecay;
import com.jayavery.jjmod.capabilities.ICapDecay;
import com.jayavery.jjmod.capabilities.ProviderCapDecay;
import com.jayavery.jjmod.container.ContainerInventory;
import com.jayavery.jjmod.init.ModCaps;
import com.jayavery.jjmod.main.Jjmod;
import com.jayavery.jjmod.tileentities.TECarcass;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Decayable carcass item. */
public class ItemCarcassDecayable extends ItemBlockplacer {

    /** The block for this item. */
    private final BlockCarcass block;
    
    public ItemCarcassDecayable(String name, BlockCarcass block) {
        
        super(name, 1, CreativeTabs.FOOD, SoundType.CLOTH);
        this.block = block;
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
    
    /** Sends the capability data for syncing to
     * the client (needed because of Forge syncing limitations). */
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
    
    /** Gives this item an ICapDecay of its block's shelf life. */
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack,
            NBTTagCompound nbt) {
        
        return new ProviderCapDecay(new
                DefaultCapDecay(this.block.getShelfLife()));

    }
    
    /** Attempts to place this item's carcass block. */
    @Override
    protected boolean place(World world, BlockPos targetPos,
            EnumFacing targetSide, EnumFacing placeFacing, ItemStack stack) {

        BlockPos placePos = targetPos.offset(targetSide);
        IBlockState state = world.getBlockState(placePos);
        Block block = state.getBlock();
            
        if (!block.isReplaceable(world, placePos) ||
                !this.block.isValid(world, placePos)) {
            
            return false;
        }
        
        // Set up block and TE

        ICapDecay decayCap = stack.getCapability(ModCaps.CAP_DECAY, null);
        decayCap.updateFromNBT(stack.getTagCompound());
        
        if (decayCap.isRot(world)) {
            
            return false;
        }
        
        IBlockState placeState = this.block.getDefaultState();
        world.setBlockState(placePos, placeState);
        ((TECarcass) world.getTileEntity(placePos))
                .setData(decayCap.getBirthTime(), decayCap.getStageSize());
        
        return true;
    }
    
    /** Makes this item named rotten according to capability. */
    @SideOnly(Side.CLIENT)
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        
        if (stack.hasCapability(ModCaps.CAP_DECAY, null)) {
            
            ICapDecay decayCap = stack.getCapability(ModCaps.CAP_DECAY, null);
            decayCap.updateFromNBT(stack.getTagCompound());
            
            if (decayCap.isRot(Jjmod.proxy.getClientWorld())) {
            
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
            float fraction = decayCap.getFraction(Jjmod.proxy.getClientWorld());
            return MathHelper.hsvToRGB(fraction / 3.0F, 1.0F, 1.0F);
        }
        
        return 0;
    }
}
