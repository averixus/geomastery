/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.blocks;

import java.util.List;
import javax.annotation.Nullable;
import jayavery.geomastery.items.ItemPlacing;
import jayavery.geomastery.main.GeoConfig;
import jayavery.geomastery.utilities.BlockMaterial;
import jayavery.geomastery.utilities.EBlockWeight;
import jayavery.geomastery.utilities.EToolType;
import jayavery.geomastery.utilities.Lang;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Fence wall block. */
public class BlockWallFence extends BlockWallThin {

    public BlockWallFence() {
        
        super(BlockMaterial.WOOD_FURNITURE, "fence", 2F, 90, 1);
    }
    
    @Override
    public ItemPlacing.Building createItem(int stackSize) {
        
        return new ItemPlacing.Building(this, stackSize);
    }
    
    @Override
    public EBlockWeight getWeight(IBlockState state) {
        
        return EBlockWeight.NONE;
    }

    @Override
    public boolean isValid(World world, BlockPos pos, ItemStack stack,
            boolean alreadyPresent, IBlockState setState, EntityPlayer player) {
        
        if (!alreadyPresent && !world.getBlockState(pos).getBlock()
                .isReplaceable(world, pos)) {
            
            message(player, Lang.BUILDFAIL_OBSTACLE);
            return false;
        }
        
        IBlockState stateBelow = world.getBlockState(pos.down());
        Block blockBelow = stateBelow.getBlock();
        EBlockWeight weightBelow = EBlockWeight.getWeight(stateBelow);
        
        if (!weightBelow.canSupport(this.getWeight(this.getDefaultState())) &&
                !(blockBelow instanceof BlockWallFence)) {
            
            message(player, Lang.BUILDFAIL_SUPPORT);
            return false;
        }
        
        return true;
    }

    @SideOnly(Side.CLIENT) @Override
    public void addInformation(ItemStack stack, EntityPlayer player,
            List<String> tooltip, boolean advanced) {
        
        if (GeoConfig.buildTooltips) {
            
            tooltip.add(I18n.format(EBlockWeight.NONE.requires()));
            tooltip.add(I18n.format(Lang.BUILDTIP_FENCE));
        }
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World world,
            BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> list,
            @Nullable Entity entity, boolean unused) {
        
        state = this.getExtendedState(state, world, pos);
        
        if (!(state instanceof IExtendedBlockState)) {
            
            return;
        }
        
        IExtendedBlockState extState = (IExtendedBlockState) state; 
        addCollisionBoxToList(pos, entityBox, list, CENTRE_POST_THIN_TALL);
        
        if (extState.getValue(NORTH) != null) {
            
            addCollisionBoxToList(pos, entityBox, list, BRANCH_NORTH_THIN_TALL);
        }
        
        if (extState.getValue(EAST) != null) {
            
            addCollisionBoxToList(pos, entityBox, list, BRANCH_EAST_THIN_TALL);
        }
        
        if (extState.getValue(SOUTH) != null) {
            
            addCollisionBoxToList(pos, entityBox, list, BRANCH_SOUTH_THIN_TALL);
        }
        
        if (extState.getValue(WEST) != null) {
            
            addCollisionBoxToList(pos, entityBox, list, BRANCH_WEST_THIN_TALL);
        }
    }
}
