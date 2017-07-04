/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.items;

import java.util.function.Supplier;
import jayavery.geomastery.container.ContainerInventory;
import jayavery.geomastery.utilities.FoodType;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
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

/** Decayable food item which plants a crop. */
public class ItemEdibleSeedDecayable extends ItemEdibleDecayable
        implements IPlantable {
    
    /** This item's crop block. */
    private final Supplier<Block> crop;
    /** This item's plant type. */
    private final EnumPlantType plantType;

    @SafeVarargs
    public ItemEdibleSeedDecayable(String name, int hunger, float saturation,
            int stackSize, Supplier<Block> crop, EnumPlantType plantType,
            FoodType foodType, int shelfLife,
            Class<? extends EntityAnimal>... animalEaters) {

        super(name, hunger, saturation, stackSize, foodType,
                shelfLife, animalEaters);
        this.crop = crop;
        this.plantType = plantType;
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
        
        return this.plantType;
    }
    
    /** Gets plant state for block interactions. */
    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
        
        return this.crop.get().getDefaultState();
    }
}
