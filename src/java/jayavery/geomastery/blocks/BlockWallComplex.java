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
import jayavery.geomastery.utilities.EBlockWeight;
import jayavery.geomastery.utilities.IDoublingBlock;
import jayavery.geomastery.utilities.EToolType;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IExtendedBlockState;

/** Fully adaptive wall building block. */
public class BlockWallComplex extends BlockWall implements IDoublingBlock {
        
    public BlockWallComplex(Material material, String name, float hardness) {
        
        super(material, name, hardness, 0, 2);
    }
    
    @Override
    public ItemPlacing.Building createItem(int stackSize) {
        
        return new ItemPlacing.Building(this, stackSize);
    }
    
    @Override
    public EBlockWeight getWeight(IBlockState state) {

        return EBlockWeight.HEAVY;
    }
    
    @Override
    public boolean shouldDouble(IBlockState state, EnumFacing side) {
        
        return !state.getValue(DOUBLE) && side != EnumFacing.UP;
    }
    
    @Override
    public boolean place(World world, BlockPos targetPos,
            EnumFacing targetSide, EnumFacing placeFacing,
            ItemStack stack, EntityPlayer player) {
        
        IBlockState targetState = world.getBlockState(targetPos);
        Block targetBlock = targetState.getBlock();

        if (targetBlock == this && this.shouldDouble(targetState
                .getActualState(world, targetPos), targetSide)) {
            
            world.setBlockState(targetPos,
                    targetState.withProperty(DOUBLE, true));
       
        } else {
            
            targetPos = targetPos.offset(targetSide);
            targetState = world.getBlockState(targetPos);
            targetBlock = targetState.getBlock();
            
            if (!this.isValid(world, targetPos, stack, false, this.getDefaultState(), player)) {
                
                return false;
            }
            
            world.setBlockState(targetPos, this.getDefaultState()
                    .withProperty(DOUBLE, false));
        }
        
        return true;
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
    public IBlockState getActualState(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        IBlockState stateBelow = world.getBlockState(pos.down());
        Block blockBelow = stateBelow.getBlock();
        boolean isBottom = blockBelow instanceof BlockWallComplex ?
                ((BlockWallComplex) blockBelow).isDouble(stateBelow) !=
                this.isDouble(state) : true;
        IBlockState stateAbove = world.getBlockState(pos.up());
        Block blockAbove = stateAbove.getBlock();
        boolean isTop = this.isDouble(state) ? blockAbove != this :
                !(blockAbove instanceof BlockBuildingAbstract);
        
        state = state.withProperty(TOP, isTop);
        state = state.withProperty(BOTTOM, isBottom);
        
        return state;
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
        
        if (this.isDouble(state)) {
            
            addCollisionBoxToList(pos, entityBox, list, FULL_BLOCK_AABB);
            return;
        }
                
        if (extState.getValue(TOP)) {
        
            addCollisionBoxToList(pos, entityBox, list, CENTRE_POST_LOW);
            
            if (extState.getValue(NORTH) != null) {
                
                addCollisionBoxToList(pos, entityBox, list, BRANCH_NORTH_LOW);
            }
            
            if (extState.getValue(EAST) != null) {
                
                addCollisionBoxToList(pos, entityBox, list, BRANCH_EAST_LOW);
            }
            
            if (extState.getValue(SOUTH) != null) {
                
                addCollisionBoxToList(pos, entityBox, list, BRANCH_SOUTH_LOW);
            }
            
            if (extState.getValue(WEST) != null) {
                
                addCollisionBoxToList(pos, entityBox, list, BRANCH_WEST_LOW);
            }
            
        } else {
            
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
}
