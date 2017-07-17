/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.blocks;

import java.util.Random;
import jayavery.geomastery.items.ItemLooseplacing;
import jayavery.geomastery.utilities.BlockMaterial;
import jayavery.geomastery.utilities.EBlockWeight;
import jayavery.geomastery.utilities.EToolType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Falling rubble block. */
public class BlockRubble extends BlockBuildingAbstract<ItemLooseplacing> {

    public BlockRubble() {
        
        super(BlockMaterial.SOIL, "rubble",
                CreativeTabs.BUILDING_BLOCKS, 1F, 1);
    }
    
    @Override
    public ItemLooseplacing createItem(int stackSize) {
        
        return new ItemLooseplacing(this, stackSize,
                CreativeTabs.BUILDING_BLOCKS);
    }
    
    @Override
    public EBlockWeight getWeight(IBlockState state) {
        
        return EBlockWeight.HEAVY;
    }
    
    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        
        this.tryFall(world, pos);
    }
    
    @Override
    public void neighborChanged(IBlockState state, World world,
            BlockPos pos, Block block, BlockPos source) {
        
        this.tryFall(world, pos);
    }
    
    @Override
    public void updateTick(World world, BlockPos pos,
            IBlockState state, Random rand) {
        
        this.tryFall(world, pos);
    }
    
    /** Makes the block fall if needed. */
    public void tryFall(World world, BlockPos pos) {
        
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
