package com.jj.jjmod.items;

import com.jj.jjmod.blocks.BlockCarcass;
import com.jj.jjmod.capabilities.DefaultCapDecay;
import com.jj.jjmod.capabilities.ICapDecay;
import com.jj.jjmod.capabilities.ProviderCapDecay;
import com.jj.jjmod.container.ContainerInventory;
import com.jj.jjmod.init.ModCapabilities;
import com.jj.jjmod.init.ModItems;
import com.jj.jjmod.tileentities.TECarcass;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

/** Decayable carcass item. */
public class ItemCarcassDecayable extends ItemJj {

    /** The block for this item. */
    private final BlockCarcass block;
    
    public ItemCarcassDecayable(String name, BlockCarcass block) {
        
        super(name, 1, CreativeTabs.FOOD);
        this.block = block;
    }
    
    /** Gives this item an ICapDecay of its block's shelf life. */
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack,
            NBTTagCompound nbt) {
        
        return new ProviderCapDecay(new DefaultCapDecay(this.block.getShelfLife()));

    }
    
    /** Ticks the ICapDecay while this item is an entity. */
    @Override
    public boolean onEntityItemUpdate(EntityItem entity) {
      /*  
        if (!entity.world.isRemote && entity.getEntityItem()
                .getCapability(ModCapabilities.CAP_DECAY, null)
                .updateAndRot()) {
            
            entity.setEntityItemStack(new ItemStack(ModItems.rot));
        }
        */
        return false;
    }
    
    /** Attempts to place this item's carcass block. */
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world,
            BlockPos pos, EnumHand hand, EnumFacing side,
            float hitX, float hitY, float hitZ) {
        
        if (world.isRemote) {
            
            return EnumActionResult.SUCCESS;
        }
        
        ItemStack stack = player.getHeldItem(hand);
        
        pos = pos.offset(side);
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
            
        if (!block.isReplaceable(world, pos)) {
            
            return EnumActionResult.FAIL;
        }
        
        // Set up block and TE
        IBlockState placeState = this.block.getDefaultState();
        world.setBlockState(pos, placeState);
        ICapDecay cap = stack.getCapability(ModCapabilities.CAP_DECAY, null);
        ((TECarcass) world.getTileEntity(pos)).setData(cap.getBirthTime(), cap.getStageSize());
        
        // Use item
        world.playSound(null, pos, SoundType.CLOTH.getPlaceSound(),
                SoundCategory.BLOCKS, (SoundType.CLOTH.getVolume() + 1.0F)
                / 2.0F,  SoundType.CLOTH.getPitch() * 0.8F);
        
        if (!player.capabilities.isCreativeMode) {
            
            stack.shrink(1);
            ContainerInventory.updateHand(player, hand);
        }
        
        return EnumActionResult.SUCCESS;
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
        
        float fraction = stack.getCapability(ModCapabilities.CAP_DECAY, null)
                .getRenderFraction();
        return MathHelper.hsvToRGB(fraction / 3.0F, 1.0F, 1.0F);
    }
}
