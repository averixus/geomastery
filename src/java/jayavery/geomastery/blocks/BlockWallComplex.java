/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.blocks;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import jayavery.geomastery.items.ItemBlockplacer;
import jayavery.geomastery.render.block.RenderWallAbstract;
import jayavery.geomastery.render.block.RenderWallComplex;
import jayavery.geomastery.utilities.BlockWeight;
import jayavery.geomastery.utilities.IDoublingBlock;
import jayavery.geomastery.utilities.ToolType;
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

/** Adaptive wall building block. */
public class BlockWallComplex extends BlockWall implements IDoublingBlock {
    
    /** The item this block drops. */
    protected final Supplier<ItemBlockplacer.Doubling<BlockWallComplex>> item;
    /** Whether this block is double. */
    protected final boolean isDouble;
    
    public BlockWallComplex(Material material, String name, float hardness,
            ToolType harvestTool, boolean isDouble,
            Supplier<ItemBlockplacer.Doubling<BlockWallComplex>> item) {
        
        super(material, name, hardness, harvestTool, 0);
        this.item = item;
        this.isDouble = isDouble;
    }
    
    @Override
    public BlockWeight getWeight() {

        return BlockWeight.HEAVY;
    }
    
    @Override
    public boolean shouldDouble(IBlockState state, EnumFacing side) {
        
        return side != EnumFacing.UP;
    }
    
    @Override
    public boolean isDouble() {
        
        return this.isDouble;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        if (this.isDouble()) {
            
            return FULL_BLOCK_AABB;
            
        } else {
            
            return CENTRE_POST;
        }
    }
    
    @Override
    public IBlockState getActualState(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        Block below = world.getBlockState(pos.down()).getBlock();
        boolean isBottom = below instanceof BlockWallComplex ?
                ((BlockWallComplex) below).isDouble() != this.isDouble() :
                true;
        Block above = world.getBlockState(pos.up()).getBlock();
        boolean isTop = this.isDouble() ? above != this :
                !(above instanceof BlockBuilding);
        
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
        
        if (this.isDouble()) {
            
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
    
    /** Drops handled manually for double->single breaking. */
    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos,
            IBlockState state, int fortune) {
        
        return Collections.emptyList();
    }
    
    @Override
    public boolean removedByPlayer(IBlockState state, World world,
            BlockPos pos, EntityPlayer player, boolean willHarvest) {
    
        spawnAsEntity(world, pos, new ItemStack(this.item.get()));
        
        if (this.isDouble()) {
            
            world.setBlockState(pos, this.item.get().single.getDefaultState());
            return false;
            
        } else {
            
            world.setBlockToAir(pos);
            return true;
        }
    }
}
