package jayavery.geomastery.worldgen;

import java.util.ArrayList;
import java.util.Random;
import jayavery.geomastery.main.GeoBlocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** WorldGenerator for pear trees. */
public class WorldGenTreePear extends WorldGenTree {
    
    public WorldGenTreePear(World world, Random rand, boolean isSapling) {
        
        super(world, rand, isSapling, 30, 3, GeoBlocks.SEEDLING_PEAR);
    }
    
    @Override
    public boolean generateTree(BlockPos pos) {
        
        ArrayList<BlockPos> trunks = new ArrayList<BlockPos>();
        
        trunks.add(pos);
        trunks.add(pos.up());
        trunks.add(pos.up(2));
        trunks.add(pos.up(3));
        
        for (BlockPos trunk : trunks) {
            
            if (!this.world.getBlockState(trunk).getBlock()
                    .isReplaceable(this.world, trunk)) {
                
                return false;
            }
        }
        
        for (BlockPos trunk : trunks) {
            
            this.setBlock(trunk, GeoBlocks.WOOD_PEAR.getDefaultState());
        }
        
        ArrayList<BlockPos> leaves = new ArrayList<BlockPos>();
        
        BlockPos top = pos.up(3);
        
        leaves.add(top.up());
        leaves.add(top.north());
        leaves.add(top.north().up());
        leaves.add(top.north().east());
        leaves.add(top.north().west());
        leaves.add(top.north().down());
        leaves.add(top.north().down().east());
        leaves.add(top.north().down().west());
        leaves.add(top.south());
        leaves.add(top.south().up());
        leaves.add(top.south().east());
        leaves.add(top.south().west());
        leaves.add(top.south().down());
        leaves.add(top.south().down().east());
        leaves.add(top.south().down().west());
        leaves.add(top.west());
        leaves.add(top.west().up());
        leaves.add(top.west().down());
        leaves.add(top.east());
        leaves.add(top.east().up());
        leaves.add(top.east().down());
        
        for (BlockPos leaf : leaves) {
            
            if (this.world.getBlockState(leaf).getBlock()
                    .isReplaceable(this.world, leaf)) {
            
                this.setBlock(leaf, GeoBlocks.LEAF_PEAR.getDefaultState());
            }
        }
        
        return true;
    }
}
