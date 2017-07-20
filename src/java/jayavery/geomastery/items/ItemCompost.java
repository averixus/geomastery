/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.items;

import java.util.ArrayList;
import jayavery.geomastery.blocks.BlockCrop;
import jayavery.geomastery.container.ContainerInventory;
import jayavery.geomastery.tileentities.TECrop;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Compost fertiliser item. */
public class ItemCompost extends ItemSimple {
    
    public ItemCompost() {
        
        super("compost", 12);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }
    
    // Applies the stack's fertiliser level to right-clicked crop
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world,
            BlockPos targetPos, EnumHand hand, EnumFacing targetSide,
            float hitX, float hitY, float hitZ) {
        
        if (world.isRemote) {
            
            return EnumActionResult.SUCCESS;
        }
        
        ItemStack stack = player.getHeldItem(hand);
        Block targetBlock = world.getBlockState(targetPos).getBlock();
        TileEntity tileEntity = world.getTileEntity(targetPos);
        
        if (targetBlock instanceof BlockCrop && tileEntity instanceof TECrop) {
            
            TECrop tileCrop = (TECrop) tileEntity;
            
            if (tileCrop.applyFertiliser(stack.getMetadata())) {
                
                world.playEvent(2005, targetPos, 0);
                
                if (!player.capabilities.isCreativeMode) {
                    
                    stack.shrink(1);
                }
                
                return EnumActionResult.SUCCESS;
            }
        }
        
        return EnumActionResult.FAIL;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {

        return super.getUnlocalizedName() + "_grade_" + stack.getMetadata();
    }
    
    // Puts variants in creative inventory
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item item, CreativeTabs tab,
            NonNullList<ItemStack> subItems) {
        
        for (int i = 1; i < 6; i++) {
            
            subItems.add(new ItemStack(item, 1, i));
        }
    }
}
