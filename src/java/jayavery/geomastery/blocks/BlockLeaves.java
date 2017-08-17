/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.blocks;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import com.google.common.collect.Sets;
import jayavery.geomastery.utilities.EToolType;
import jayavery.geomastery.utilities.ETreeType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

// TEST
public class BlockLeaves extends BlockNew {

    public static final PropertyBool NODE = PropertyBool.create("node");
    public static final PropertyEnum<ETreeType> TYPE = PropertyEnum.create("type", ETreeType.class);
    
    public BlockLeaves(String name) {
        
        super(Material.LEAVES, name, CreativeTabs.DECORATIONS,
                0.2F, EToolType.MACHETE);
        this.setTickRandomly(true);
        this.setLightOpacity(1);
    }
    
    @Override
    public void updateTick(World world, BlockPos pos,
            IBlockState state, Random rand) {
        
        this.tryFall(world, pos, state);
    }

    @Override
    public void neighborChanged(IBlockState state, World world,
            BlockPos pos, Block block, BlockPos source) {
        
        this.tryFall(world, pos, state);
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        
        this.tryFall(world, pos, state);
    }
    
    @Override
    public void onEntityWalk(World world, BlockPos pos, Entity entity) {
        
        entity.motionX *= 0.2;
        entity.motionZ *= 0.2;
    }
    
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        
        return Blocks.LEAVES.isOpaqueCube(state);
    }
    
    @Override @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        
        return Blocks.LEAVES.getBlockLayer();
    }
    
    @Override
    public BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this, NODE, TYPE);
    }
    
    @Override
    public int getMetaFromState(IBlockState state) {
        
        int meta = state.getValue(NODE) ? 8 : 0;
        meta += state.getValue(TYPE).ordinal();
        return meta;
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta) {
        
        IBlockState state = this.getDefaultState();
        state = state.withProperty(NODE, (meta & 8) > 0);
        state = state.withProperty(TYPE, ETreeType.values()[meta % 8]);
        return state;
    }

    private void tryFall(World world, BlockPos pos, IBlockState state) {
        
        if (this.shouldFall(world, pos, state)) {
            
            this.fall(world, pos, state);
        }
    }
    
    private boolean shouldFall(World world, BlockPos pos, IBlockState state) {
        
        Set<BlockPos> checked = Sets.newHashSet();
        Queue<BlockPos> checkQueue = new LinkedList<BlockPos>();
        checkQueue.add(pos);
        
        if (state.getValue(NODE)) {
            
            while (!checkQueue.isEmpty()) {
                
                BlockPos nextPos = checkQueue.remove();
                IBlockState nextState = world.getBlockState(nextPos);
                Block nextBlock = nextState.getBlock();
                checked.add(nextPos);
                
                if (nextBlock instanceof BlockTree) {
                    
                    return false;
                    
                } else if (nextBlock == this && nextState.getValue(NODE)) {
                    
                    for (EnumFacing facing : EnumFacing.VALUES) {
                        
                        BlockPos toAdd = nextPos.offset(facing);
                        
                        if (!checked.contains(toAdd) && !checkQueue.contains(toAdd) && Math.sqrt(pos.distanceSq(toAdd)) < 8) {
                            
                            checkQueue.add(toAdd);
                        }
                    }
                }  
            }
            
            world.setBlockState(pos, state.withProperty(NODE, false));
            return false;
            
        } else {
            
            while (!checkQueue.isEmpty()) {
                
                BlockPos nextPos = checkQueue.remove();
                IBlockState nextState = world.getBlockState(nextPos);
                Block nextBlock = nextState.getBlock();
                checked.add(nextPos);
                
                if ((nextBlock == this && nextState.getValue(NODE)) ||
                        (nextBlock instanceof BlockTree)) {
                    
                    return false;
                    
                } else if (nextState.getBlock() == this) {
                    
                    for (EnumFacing facing : EnumFacing.VALUES) {
                        
                        BlockPos toAdd = nextPos.offset(facing);
                        
                        if (!checked.contains(toAdd) && !checkQueue.contains(toAdd) && Math.sqrt(pos.distanceSq(toAdd)) < 4) {
                            
                            checkQueue.add(toAdd);
                        }
                    }
                }
            }
            
            return true;
        }
    }
    
    private void fall(World world, BlockPos pos, IBlockState state) {
        
        if (world.isRemote) {
            
            return;
        }
        
        if (canFallThrough(world, pos.down())) {
            
            if (!BlockFalling.fallInstantly && world
                    .isAreaLoaded(pos.add(-32,-32,-32), pos.add(32,32,32))) {

                    EntityFallingBlock fall = new EntityFallingBlock(world,
                            pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D,
                            world.getBlockState(pos));
                    world.spawnEntity(fall);
                    
            } else {
                
                IBlockState current = world.getBlockState(pos);
                world.setBlockToAir(pos);
                BlockPos check;
                
                for (check = pos.down(); canFallThrough(world, check) &&
                        check.getY() > 0; check = check.down()) {
                    ;
                }

                if (check.getY() > 0) {
                    
                    world.setBlockState(check.up(), current);
                }
            }
        }
    }
    
    /** @return Whether the material at this position can be fallen through. */
    private static boolean canFallThrough(World world, BlockPos pos) {
        
        Material below = world.getBlockState(pos).getMaterial();
        return below == Material.AIR || below == Material.WATER ||
                below == Material.LAVA || below == Material.FIRE;
    }
}
