package com.jayavery.jjmod.items;

import com.jayavery.jjmod.blocks.BlockCarcassAbstract;
import com.jayavery.jjmod.capabilities.DefaultCapDecay;
import com.jayavery.jjmod.capabilities.ICapDecay;
import com.jayavery.jjmod.capabilities.ProviderCapDecay;
import com.jayavery.jjmod.container.ContainerInventory;
import com.jayavery.jjmod.init.ModCaps;
import com.jayavery.jjmod.tileentities.TECarcass;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Decayable carcass item. */
public class ItemCarcassDecayable extends ItemJj {

    /** The block for this item. */
    private final BlockCarcassAbstract block;
    
    public ItemCarcassDecayable(String name, BlockCarcassAbstract block) {
        
        super(name, 1, CreativeTabs.FOOD);
        this.block = block;
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
            
        if (!block.isReplaceable(world, pos) ||
                !this.block.canPlaceBlockAt(world, pos)) {
            
            return EnumActionResult.FAIL;
        }
        
        // Set up block and TE
        IBlockState placeState = this.block.getDefaultState();
        world.setBlockState(pos, placeState);
        ICapDecay cap = stack.getCapability(ModCaps.CAP_DECAY, null);
        ((TECarcass) world.getTileEntity(pos))
                .setData(cap.getBirthTime(), cap.getStageSize());
        
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
    
    /** Makes this item named rotten according to capability. */
    @SideOnly(Side.CLIENT)
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        
        if (stack.hasCapability(ModCaps.CAP_DECAY, null) &&
                stack.getCapability(ModCaps.CAP_DECAY, null)
                .isRot(Minecraft.getMinecraft().world)) {
            
            return "Rotten " + super.getItemStackDisplayName(stack);
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
        
        float fraction = stack.getCapability(ModCaps.CAP_DECAY, null)
                .getRenderFraction();
        return MathHelper.hsvToRGB(fraction / 3.0F, 1.0F, 1.0F);
    }
}
