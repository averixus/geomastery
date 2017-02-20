package com.jj.jjmod.items;

import java.util.Set;
import com.google.common.collect.Sets;
import com.jj.jjmod.container.ContainerInventory;
import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Hoe tool item. */
public class ItemHoe extends ItemToolAbstract {

    /** Set of vanilla blocks to harvest. */
    private static final Set<Block> EFFECTIVE_ON =
            Sets.newHashSet( new Block[] {Blocks.DIRT, Blocks.GRASS});

    public ItemHoe(String name, ToolMaterial material) {

        super(2, -3.1F, material, EFFECTIVE_ON);
        ItemJj.setupItem(this, name, 1, CreativeTabs.TOOLS);
        this.setHarvestLevel(ToolType.HOE.toString(), 1);
        this.efficiencyOnProperMaterial = 0.25F;
    }
    
    /** Turns broken dirt/grass into farmland. */
    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos,
            EntityPlayer player) {

        if (!player.world.isRemote) {
            
            stack.damageItem(1, player);
            ContainerInventory.updateHand(player, player.getActiveHand());
            
            Block block = player.world.getBlockState(pos).getBlock();
            
            if (block == Blocks.DIRT || block == Blocks.GRASS) {
                
                player.world.setBlockState(pos,
                        Blocks.FARMLAND.getDefaultState());
                return true;
            }
        }
        
        return false;
    }
}