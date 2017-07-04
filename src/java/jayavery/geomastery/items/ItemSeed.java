/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.items;

import java.util.Set;
import java.util.function.Supplier;
import com.google.common.collect.Sets;
import jayavery.geomastery.container.ContainerInventory;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

/** Seed item. */
public class ItemSeed extends ItemSimple implements IPlantable {
    
    /** Set of animals which can be bred with this item. */
    private final Set<Class<? extends EntityAnimal>> animalEaters;
    /** This item's crop block. */
    private final Supplier<Block> crop;

    @SafeVarargs
    public ItemSeed(String name, int stackSize, Supplier<Block> crop,
            Class<? extends EntityAnimal>... animalEaters) {

        super(name, stackSize, CreativeTabs.MATERIALS);
        this.animalEaters = Sets.newHashSet(animalEaters);
        this.crop = crop;
    }
    
    /** Attempts to plant this item's crop. */
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world,
            BlockPos pos, EnumHand hand, EnumFacing side,
            float x, float y, float z) {
        
        ItemStack stack = player.getHeldItem(hand);
        IBlockState state = world.getBlockState(pos);
        
        if (side == EnumFacing.UP &&
                state.getBlock().canSustainPlant(state, world,
                pos, EnumFacing.UP, this) && world.isAirBlock(pos.up())) {
            
            world.setBlockState(pos.up(), this.crop.get().getDefaultState());
            
            if (!player.capabilities.isCreativeMode) {
                
                stack.shrink(1);
                ContainerInventory.updateHand(player, hand);
            }
            
            return EnumActionResult.SUCCESS;
            
        } else {
            
            return EnumActionResult.FAIL;
        }
    }
    
    /** Gets plant type for block interactions. */
    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        
        return EnumPlantType.Crop;
    }
    
    /** Gets plant state for block interactions. */
    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
        
        return this.crop.get().getDefaultState();
    }
    
    /** Breeds or grows the right-clicked animal if applicable. */
    @Override
    public boolean itemInteractionForEntity(ItemStack stack,
            EntityPlayer player, EntityLivingBase entity, EnumHand hand) {
        
        if (entity.world.isRemote) {
            
            return true;
        }
        
        if (this.animalEaters.contains(entity.getClass())) {
            
            EntityAnimal animal = (EntityAnimal) entity;
            
            if (animal.getGrowingAge() == 0 && !animal.isInLove()) {
                
                if (!player.capabilities.isCreativeMode) {
                    
                    stack.shrink(1);
                    ContainerInventory.updateHand(player, hand);
                }
                
                animal.setInLove(player);
                return true;
            }
            
            if (animal.isChild()) {
                
                if (!player.capabilities.isCreativeMode) {
                    
                    stack.shrink(1);
                    ContainerInventory.updateHand(player, hand);
                }
                
                animal.ageUp((int)(((float)-animal.getGrowingAge() / 20) *
                        0.1F), true);
                return true;
            }
        }
        
        return false;
    }
}
