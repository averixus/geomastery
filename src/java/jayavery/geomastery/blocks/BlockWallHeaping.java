/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.blocks;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import com.google.common.collect.Lists;
import jayavery.geomastery.items.ItemPlacing;
import jayavery.geomastery.main.GeoConfig;
import jayavery.geomastery.utilities.EBlockWeight;
import jayavery.geomastery.utilities.IDoublingBlock;
import jayavery.geomastery.utilities.Lang;
import jayavery.geomastery.utilities.EToolType;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Rough heaping wall block. */
public class BlockWallHeaping extends BlockWall implements IDoublingBlock {
        
    public BlockWallHeaping(Material material, String name, float hardness,
            int sideAngle) {
        
        super(material, name, hardness, sideAngle, 2);
    }
    
    @Override
    public ItemPlacing.Building createItem(int stackSize) {
        
        return new ItemPlacing.Building(this, stackSize);
    }
    
    @Override
    public EBlockWeight getWeight(IBlockState state) {
        
        return EBlockWeight.MEDIUM;
    }

    @Override
    public boolean shouldDouble(IBlockState state, EnumFacing side) {
        
        return !this.isDouble(state) && side != EnumFacing.UP;
    }

    @Override
    public boolean place(World world, BlockPos targetPos,
            EnumFacing targetSide, EnumFacing placeFacing,
            ItemStack stack, EntityPlayer player) {
        
        IBlockState targetState = world.getBlockState(targetPos);
        Block targetBlock = targetState.getBlock();
    
        if (targetBlock == this && this.shouldDouble(targetState, targetSide)) {
            
            IBlockState state = this.getDefaultState()
                    .withProperty(DOUBLE, true);
            
            if (!this.isValid(world, targetPos, stack, false, state, player)) {
                
                return false;
            }
            
            world.setBlockState(targetPos, state); 
    
        } else {
            
            targetPos = targetPos.offset(targetSide);
            targetState = world.getBlockState(targetPos);
            targetBlock = targetState.getBlock();
            
            IBlockState state = this.getDefaultState()
                    .withProperty(DOUBLE, false);
            
            if (!this.isValid(world, targetPos, stack, false, state, player)) {
                
                return false;
            }
            
            world.setBlockState(targetPos, state);
        }
        
        return true;
    }

    @Override
    public boolean isValid(World world, BlockPos pos, ItemStack stack,
            boolean alreadyPresent, IBlockState setState, EntityPlayer player) {
        
        IBlockState stateCurrent = world.getBlockState(pos);
        Block blockCurrent = stateCurrent.getBlock();
        
        if (!alreadyPresent && !this.isDouble(setState) &&
                !blockCurrent.isReplaceable(world, pos)) {
            
            message(player, Lang.BUILDFAIL_OBSTACLE);
            return false;
        }
        
        IBlockState stateBelow = world.getBlockState(pos.down());
        Block blockBelow = stateBelow.getBlock();
        
        if (blockBelow == this) {
    
            if (setState.getValue(DOUBLE) || !stateBelow.getValue(DOUBLE)) {
                
                message(player, Lang.BUILDFAIL_HEAPWALL);
                return false;
            }
            
        } else {
        
            EBlockWeight weightBelow = EBlockWeight.getWeight(stateBelow);
            
            if (!weightBelow.canSupport(this.getWeight(setState))) {
                
                message(player, Lang.BUILDFAIL_SUPPORT);
                return false;
            }
        }
        
        return true;
    }

    @SideOnly(Side.CLIENT) @Override
    public void addInformation(ItemStack stack, EntityPlayer player,
            List<String> tooltip, boolean advanced) {
        
        if (GeoConfig.textVisual.buildTooltips) {
        
            tooltip.add(I18n.format(Lang.BUILDTIP_DOUBLING));
            tooltip.add(I18n.format(this.getWeight(this
                    .getDefaultState()).requires()));
            tooltip.add(I18n.format(this.getWeight(this
                    .getDefaultState()).supports()));
            tooltip.add(I18n.format(Lang.BUILDTIP_HEAPWALL));
        }
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        return this.isDouble(state) ? FULL_BLOCK_AABB : CENTRE_POST;
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
        
        if (state.getValue(DOUBLE)) {
            
            addCollisionBoxToList(pos, entityBox, list, FULL_BLOCK_AABB);
            return;
        }
            
        addCollisionBoxToList(pos, entityBox, list, CENTRE_POST);
        
        if (extState.getValue(NORTH) != null) {
            
            addCollisionBoxToList(pos, entityBox, list, BRANCH_NORTH);
        }
        
        if (extState.getValue(EAST) != null) {
            
            addCollisionBoxToList(pos, entityBox, list, BRANCH_EAST);
        }
        
        if (extState.getValue(SOUTH) != null) {
            
            addCollisionBoxToList(pos, entityBox, list, BRANCH_SOUTH);
        }
        
        if (extState.getValue(WEST) != null) {
            
            addCollisionBoxToList(pos, entityBox, list, BRANCH_WEST);
        }
    }
}
