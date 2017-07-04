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
import jayavery.geomastery.render.block.RenderWallSingle;
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

/** Rough heaping wall block. */
public class BlockWallHeaping extends BlockWall implements IDoublingBlock {
    
    /** The item this block drops. */
    protected final Supplier<ItemBlockplacer.Doubling<BlockWallHeaping>> item;
    /** Whether this block is double. */
    protected final boolean isDouble;
    
    public BlockWallHeaping(Material material, String name, float hardness,
            ToolType harvestTool, boolean isDouble, int sideAngle,
            Supplier<ItemBlockplacer.Doubling<BlockWallHeaping>> item) {
        
        super(material, name, hardness, harvestTool, sideAngle);
        this.item = item;
        this.isDouble = isDouble;
    }
    
    @Override
    public boolean isValid(World world, BlockPos pos) {
        
        IBlockState state = world.getBlockState(pos.down());
        Block block = state.getBlock();
        
        if (this.isDouble()) {
            
            return super.isValid(world, pos) &&
                    !(block instanceof BlockWallHeaping);
            
        } else {
                        
            if (block instanceof BlockWallHeaping) {
                
                return super.isValid(world, pos) &&
                        ((BlockWallHeaping) block).isDouble();
            }
            
            return super.isValid(world, pos);
        }
    }
    
    @Override
    public BlockWeight getWeight() {
        
        return BlockWeight.MEDIUM;
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
            
            world.setBlockState(pos, this.item.get()
                    .single.get().getDefaultState());
            return false;
            
        } else {
            
            world.setBlockToAir(pos);
            return true;
        }
    }
}
