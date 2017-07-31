/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.items;

import java.util.List;
import java.util.function.Supplier;
import jayavery.geomastery.blocks.BlockBuildingAbstract;
import jayavery.geomastery.main.GeoConfig;
import jayavery.geomastery.utilities.EBlockWeight;
import jayavery.geomastery.utilities.Lang;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Loose block placing item. */
public class ItemLooseplacing extends ItemPlacing {

    /** Supplier for this item's block. */
    private final Supplier<Block> block;
    
    /** Constructor using non-owned block. */
    public ItemLooseplacing(String name, Supplier<Block> block) {
        
        super(name, 1, CreativeTabs.BUILDING_BLOCKS);
        this.block = block;
    }
    
    /** Constructor using owned block. */
    public ItemLooseplacing(BlockBuildingAbstract<?> block, int stackSize,
            CreativeTabs tab) {
        
        super(block, stackSize, tab);
        this.block = () -> block;
    }

    @Override
    public boolean place(World world, BlockPos targetPos,
            EnumFacing targetSide, EnumFacing placeFacing, ItemStack stack,
            EntityPlayer player) {
        
        BlockPos posPlace = targetPos.offset(targetSide);
        Block blockTarget = world.getBlockState(posPlace).getBlock();
        
        if (!blockTarget.isReplaceable(world, posPlace)) {
            
            message(player, Lang.BUILDFAIL_OBSTACLE);
            return false;
        }
        
        BlockPos posBelow = posPlace.down();
        IBlockState stateBelow = world.getBlockState(posBelow);
        
        if (!EBlockWeight.getWeight(stateBelow)
                .canSupport(EBlockWeight.MEDIUM)) {
            
            message(player, Lang.BUILDFAIL_SUPPORT);
            return false;
        }
        
        for (EnumFacing facing : EnumFacing.HORIZONTALS) {
            
            BlockPos posOffset = posBelow.offset(facing);
            IBlockState stateOffset = world.getBlockState(posOffset);
            
            if (!EBlockWeight.getWeight(stateOffset)
                    .canSupport(EBlockWeight.MEDIUM)) {
                
                message(player, Lang.BUILDFAIL_SUPPORT);
                return false;
            }
        }
        
        world.setBlockState(posPlace, this.block.get().getDefaultState());
        return true;
    }
    
    @Override
    protected SoundType getSoundType() {
    
        return this.block.get().getSoundType();
    }

    // Adds the block's building information to tooltip
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world,
            List<String> tooltip, ITooltipFlag advanced) {
        
        if (GeoConfig.textVisual.buildTooltips) {
            
            tooltip.add(I18n.format(EBlockWeight.getWeight(this.block.get().getDefaultState()).supports()));
            tooltip.add(I18n.format(Lang.BUILDTIP_HEAPING));
        }
    }
}
