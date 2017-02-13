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

public class ItemCarcassDecayable extends ItemNew {

    private final BlockCarcass block;
    
    public ItemCarcassDecayable(String name, BlockCarcass block) {
        
        super(name, 1, CreativeTabs.FOOD);
        this.block = block;
    }
    
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack,
            NBTTagCompound nbt) {
        
        return new ProviderCapDecay(new
                DefaultCapDecay(this.block.getShelfLife()));
    }
    
    @Override
    public boolean onEntityItemUpdate(EntityItem entity) {
        
        if (!entity.world.isRemote && entity.getEntityItem()
                .getCapability(ModCapabilities.CAP_DECAY, null)
                .updateAndRot()) {
            
            entity.setEntityItemStack(new ItemStack(ModItems.rot));
        }
        
        return false;
    }
    
    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        
        return true;
    }
    
    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        
        return 0;
    }
    
    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        
        float fraction = stack.getCapability(ModCapabilities.CAP_DECAY, null)
                .getRenderFraction();
        return MathHelper.hsvToRGB(fraction / 3.0F, 1.0F, 1.0F);
    }
    
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world,
            BlockPos pos, EnumHand hand, EnumFacing facing,
            float hitX, float hitY, float hitZ) {
        
        if (world.isRemote) {
            
            return EnumActionResult.SUCCESS;
        }
        
        pos = pos.offset(facing);
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
            
        if (!block.isReplaceable(world, pos)) {
            
            return EnumActionResult.FAIL;
        }

        ItemStack stack = player.getHeldItem(hand);
        ICapDecay cap = stack.getCapability(ModCapabilities.CAP_DECAY, null);
        
        IBlockState placeState = this.block.getDefaultState();
        world.setBlockState(pos, placeState);
        
        ((TECarcass) world.getTileEntity(pos)).setAge(cap.getAge());
        world.playSound(null, pos, SoundType.CLOTH.getPlaceSound(),
                SoundCategory.BLOCKS, (SoundType.CLOTH.getVolume() + 1.0F)
                / 2.0F,  SoundType.CLOTH.getPitch() * 0.8F);
        
        if (!player.capabilities.isCreativeMode) {
            
            stack.shrink(1);
            ((ContainerInventory) player.inventoryContainer).sendUpdateHighlight();
            ((ContainerInventory) player.inventoryContainer).sendUpdateOffhand();
        }
        
        return EnumActionResult.SUCCESS;
    }
}
