/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.blocks;

import java.util.List;
import java.util.function.Supplier;
import com.google.common.collect.Lists;
import jayavery.geomastery.items.ItemBlockplacer;
import jayavery.geomastery.utilities.BlockMaterial;
import jayavery.geomastery.utilities.BlockWeight;
import jayavery.geomastery.utilities.IDoublingBlock;
import jayavery.geomastery.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Paving slab block. */
public class BlockSlab extends BlockBuilding implements IDoublingBlock {
    
    /** Whether this is a double block. */
    private final boolean isDouble;
    /** The item dropped by this block. */
    private final Supplier<ItemBlockplacer.Doubling<BlockSlab>> item;

    public BlockSlab(String name, boolean isDouble,
            Supplier<ItemBlockplacer.Doubling<BlockSlab>> item) {
        
        super(BlockMaterial.STONE_FURNITURE, name, null, 2F, ToolType.PICKAXE);
        this.isDouble = isDouble;
        this.item = item;
    }

    @Override
    public BlockWeight getWeight() {

        return BlockWeight.LIGHT;
    }
    
    @Override
    public boolean isValid(World world, BlockPos pos) {
        
        Block blockBelow = world.getBlockState(pos.down()).getBlock();
        BlockWeight weightBelow = BlockWeight.getWeight(blockBelow);
        return weightBelow.canSupport(BlockWeight.MEDIUM);
    }
    
    @Override
    public boolean shouldDouble(IBlockState state, EnumFacing side) {
        
        return side == EnumFacing.UP;
    }
    
    @Override
    public boolean isDouble() {
        
        return this.isDouble;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world,
            BlockPos pos) {

        return state;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world,
            BlockPos pos) {

        return this.isDouble() ? FULL_BLOCK_AABB : EIGHT;
    }

    /** Drops handled manually for double->single breaking. */
    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos,
            IBlockState state, int fortune) {
        
        return Lists.newArrayList(new ItemStack(this.item.get(),
                this.isDouble() ? 2 : 1));
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

    @Override
    public BlockStateContainer createBlockState() {

        return new BlockStateContainer(this);
    }
}
