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
    
    @SideOnly(Side.CLIENT) @Override
    public void addInformation(ItemStack stack, EntityPlayer player,
            List<String> tooltip, boolean advanced) {
        
        if (GeoConfig.buildTooltips) {
        
            tooltip.add(I18n.format(Lang.BUILDTIP_DOUBLING));
            tooltip.add(I18n.format(this.getWeight(this.getDefaultState()).requires()));
            tooltip.add(I18n.format(this.getWeight(this.getDefaultState()).supports()));
            tooltip.add(I18n.format(Lang.BUILDTIP_HEAPWALL));
        }
    }
    
    @Override
    public boolean isValid(World world, BlockPos pos, ItemStack stack,
            boolean alreadyPresent, IBlockState setState, EntityPlayer player) {
        
        IBlockState stateCurrent = world.getBlockState(pos);
        Block blockCurrent = stateCurrent.getBlock();
        
        if (!alreadyPresent && blockCurrent != this &&
                !blockCurrent.isReplaceable(world, pos)) {
            
            message(player, Lang.BUILDFAIL_OBSTACLE);
            return false;
        }
        
        IBlockState stateBelow = world.getBlockState(pos.down());
        Block blockBelow = stateBelow.getBlock();
        
        if (blockBelow == this) {
        
            if (alreadyPresent) {
                
                if (!stateBelow.getValue(DOUBLE)) {
                    
                    return false;
                }
                
            } else {
    
                if (blockCurrent == this) {
                    
                    message(player, Lang.BUILDFAIL_HEAPWALL);
                    return false;
                    
                } else if (!stateBelow.getValue(DOUBLE)) {
                    
                    message(player, Lang.BUILDFAIL_HEAPWALL);
                    return false;
                }
            }
            
        } else {
        
            EBlockWeight weightBelow = EBlockWeight.getWeight(stateBelow);
            
            if (!weightBelow.canSupport(this
                    .getWeight(this.getDefaultState()))) {
                
                message(player, Lang.BUILDFAIL_SUPPORT);
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public boolean place(World world, BlockPos targetPos,
            EnumFacing targetSide, EnumFacing placeFacing,
            ItemStack stack, EntityPlayer player) {
        
        IBlockState targetState = world.getBlockState(targetPos);
        Block targetBlock = targetState.getBlock();

        if (targetBlock == this && this.shouldDouble(targetState
                .getActualState(world, targetPos), targetSide)) {
            
            if (!this.isValid(world, targetPos, stack, false, this.getDefaultState(), player)) {
                
                return false;
            }
            
            world.setBlockState(targetPos,
                    targetState.withProperty(DOUBLE, true)); 
       
        } else {
            
            targetPos = targetPos.offset(targetSide);
            targetState = world.getBlockState(targetPos);
            targetBlock = targetState.getBlock();
            
            if (!this.isValid(world, targetPos, stack, false, this.getDefaultState(), player)) {
                
                return false;
            }
            
            world.setBlockState(targetPos,
                    this.getDefaultState().withProperty(DOUBLE, false));
        }
        
        return true;
    }
    
    @Override
    public EBlockWeight getWeight(IBlockState state) {
        
        return EBlockWeight.MEDIUM;
    }
    
    @Override
    public boolean shouldDouble(IBlockState state, EnumFacing side) {
        
        return !state.getValue(DOUBLE) && side != EnumFacing.UP;
    }
    
    @Override
    public boolean isDouble(IBlockState state) {
        
        return state.getValue(DOUBLE);
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        if (state.getValue(DOUBLE)) {
            
            return FULL_BLOCK_AABB;
            
        } else {
            
            return CENTRE_POST;
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
    
    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos,
            IBlockState state, int fortune, TileEntity te,
            ItemStack tool, EntityPlayer player) {

        return Lists.newArrayList(new ItemStack(this.item,
                state.getValue(DOUBLE) && player == null ? 2 : 1));
    }
    
    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos,
            IBlockState state, @Nullable TileEntity te, ItemStack tool) {
            
        if (state.getValue(DOUBLE)) {
            
            world.setBlockState(pos, state.withProperty(DOUBLE, false));
            
        } else {
            
            world.setBlockToAir(pos);
        }
        
        this.doHarvest(world, pos, state, player, te, tool);
    }
}
