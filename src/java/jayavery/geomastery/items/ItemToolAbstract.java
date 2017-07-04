/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.items;

import java.util.Set;
import jayavery.geomastery.container.ContainerInventory;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Abstract superclass for Tool items, functionality added
 * to update the player container when durability is used. */
public abstract class ItemToolAbstract extends net.minecraft.item.ItemTool {

    public ItemToolAbstract(float attackDamageIn, float attackSpeedIn,
            ToolMaterial materialIn, Set<Block> effectiveBlocksIn) {
        
        super(attackDamageIn, attackSpeedIn, materialIn, effectiveBlocksIn);
    }
    
    /** Updates container when hit entity. */
    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target,
            EntityLivingBase attacker) {
        
        super.hitEntity(stack, target, attacker);
        
        if (attacker instanceof EntityPlayer) {
            
            EntityPlayer player = (EntityPlayer) attacker;
            ContainerInventory.updateHand(player, player.getActiveHand());
        }
        
        return true;
    }
    
    /** Updates container when block broken. */
    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn,
            IBlockState state, BlockPos pos, EntityLivingBase entity) {
        
        boolean result = super.onBlockDestroyed(stack,
                worldIn, state, pos, entity);
        
        if (entity instanceof EntityPlayer) {
            
            EntityPlayer player = (EntityPlayer) entity;
            ContainerInventory.updateHand(player, player.getActiveHand());
        }
        
        return result;
    }
}
