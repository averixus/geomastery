/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.items;

import java.util.List;
import java.util.function.Supplier;
import jayavery.geomastery.main.GeoConfig;
import jayavery.geomastery.utilities.EFoodType;
import jayavery.geomastery.utilities.Lang;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Food item which plants a crop. */
public class ItemEdibleSeed extends ItemEdible implements IPlantable {

    /** This item's crop block. */
    private final Supplier<Block> crop;

    public ItemEdibleSeed(String name, int hunger, float saturation,
            int stackSize, Supplier<Block> crop, EFoodType foodType) {

        super(name, hunger, saturation, stackSize, foodType);
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
            }
            
            return EnumActionResult.SUCCESS;
            
        } else {
            
            return EnumActionResult.FAIL;
        }
    }
    
    /** Adds this item's food type and valid biomes to the tooltip if config. */
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player,
            List<String> tooltip, boolean advanced) {
        
        if (GeoConfig.foodTooltips) {
            
            tooltip.add(I18n.format(this.type.tip()));
        }
        
        if (GeoConfig.cropTooltips) {

            tooltip.add(I18n.format(this.getUnlocalizedName() +
                    Lang.BIOMES));
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
}
