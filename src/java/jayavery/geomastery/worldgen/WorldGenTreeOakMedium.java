/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.worldgen;

import java.util.ArrayList;
import java.util.Random;
import com.google.common.collect.Lists;
import jayavery.geomastery.blocks.BlockLeaves;
import jayavery.geomastery.blocks.BlockSeedling;
import jayavery.geomastery.main.GeoBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

// TEST
public class WorldGenTreeOakMedium extends WorldGenTreeAbstract {

    public WorldGenTreeOakMedium(World world, Random rand, boolean isSapling) {
        
        super(world, rand, isSapling, 1, 1, null);
    }

    @Override
    public boolean generateTree(BlockPos pos) {

    //    switch (this.rand.nextInt(2)) {
//            case 0:
           //     return this.generateTreeA(pos);
  //          case 1: default:
                return this.generateTreeB(pos);
     //   }
    }
    
    private boolean generateTreeA(BlockPos stump) {
        
        int trunkCount = 3 + this.rand.nextInt(2);
        ArrayList<BlockPos> trunks = Lists.newArrayList();
                
        for (int i = 0; i < trunkCount; i++) {
            
            trunks.add(stump.up(i + 1));
        }
        
        Block stumpFound = this.world.getBlockState(stump).getBlock();
        
        if (!(stumpFound instanceof BlockSapling) &&
                !stumpFound.isReplaceable(this.world, stump)) {
            
            return false;
        }
        
        for (BlockPos trunk : trunks) {
            
            Block found = this.world.getBlockState(trunk).getBlock();
            
            if (!(found instanceof BlockSapling) &&
                    !found.isReplaceable(this.world, trunk)) {
                
                return false;
            }
        }
        
        this.setBlock(stump, GeoBlocks.STUMP_MEDIUM.getDefaultState());
        
        for (BlockPos trunk : trunks) {
            
            this.setBlock(trunk, GeoBlocks.TREE_MEDIUM.getDefaultState());
        }
        
        ArrayList<BlockPos> leaves = Lists.newArrayList();

        //make leaves
        int layer = 1;
        
        if (trunkCount == 4) {
            
            leaves.add(stump.up(layer));
            
            for (EnumFacing facing : EnumFacing.HORIZONTALS) {
                
                leaves.add(stump.up(layer).offset(facing));
                
                if (this.rand.nextInt(2) == 0) {
                    
                    leaves.add(stump.up(layer).offset(facing).offset(facing.rotateY()));
                }
            }
            
            layer++;
        }
        
        for (int x = -2; x <= 2; x++) {
            
            for (int z = -2; z <= 2; z++) {
                
                if ((Math.abs(x) == 2 && Math.abs(z) == 1) ||
                        (Math.abs(z) == 2 && Math.abs(x) == 1)) {
                    
                    if (this.rand.nextInt(3) != 0) {
                        
                        leaves.add(stump.add(x, layer, z));
                    }
                    
                } else if (Math.abs(x) == 2 && Math.abs(z) == 2) {
                    
                    int adjX = x > 0 ? 1 : -1;
                    int adjZ = z > 0 ? 1 : -1;
                    
                    if (this.rand.nextInt(3) == 0 &&
                            (leaves.contains(stump.add(adjX, layer, z)) ||
                            leaves.contains(stump.add(x, layer, adjZ)))) {
                        
                        leaves.add(stump.add(x, layer, z));
                    }
                    
                } else {
                    
                    leaves.add(stump.add(x, layer, z));
                }
            }
        }
        
        layer++;

        for (int x = -2; x <= 2; x++) {
            
            for (int z = -2; z <= 2; z++) {
                
                if (Math.abs(x) == 2 && Math.abs(z) == 2) {
                    
                } else if ((Math.abs(x) == 2 && z != 0) ||
                        (Math.abs(z) == 2 && x != 0)) {
                    
                    if (this.rand.nextInt(4) != 0 &&
                            leaves.contains(new BlockPos(x, layer - 1, z))) {
                        
                        leaves.add(stump.add(x, layer, z));
                    }
                    
                } else {
                    
                    leaves.add(stump.add(x, layer, z));
                }
            }
        }
        
        layer++;

        for (int x = -2; x <= 2; x++) {
            
            for (int z = -2; z <= 2; z++) {
                
                if ((Math.abs(x) == 2 && z == 0) ||
                        (Math.abs(z) == 2 && x == 0)) {
                    
                    if (this.rand.nextInt(2) == 0) {
                        
                        leaves.add(stump.add(x, layer, z));
                    }
                    
                } else if (Math.abs(x) == 2 || Math.abs(z) == 2) {
                    
                } else {
                    
                    leaves.add(stump.add(x, layer, z));
                }
            }
        }
        
        layer++;
        
        leaves.add(stump.up(layer));
        
        for (EnumFacing facing : EnumFacing.HORIZONTALS) {
            
            leaves.add(stump.up(layer).offset(facing));
        }
        
        for (BlockPos leaf : leaves) {
            
            if (this.world.getBlockState(leaf).getBlock()
                    .isReplaceable(this.world, leaf)) {
            
                this.setBlock(leaf, GeoBlocks.LEAF.getDefaultState().withProperty(BlockLeaves.NODE, false));
            }
        }
        
        return true;
    }
    
    private boolean generateTreeB(BlockPos stump) {
        
        EnumFacing treeFacing = EnumFacing.HORIZONTALS[this.rand.nextInt(EnumFacing.HORIZONTALS.length)];
        EnumFacing sideFacing = treeFacing.rotateY();
        
        int trunkCount = 3 + this.rand.nextInt(2);
        ArrayList<BlockPos> trunks = Lists.newArrayList();
                
        for (int i = 0; i < trunkCount; i++) {
            
            trunks.add(stump.up(i + 1));
        }
        
        Block stumpFound = this.world.getBlockState(stump).getBlock();
        
        if (!(stumpFound instanceof BlockSapling) &&
                !stumpFound.isReplaceable(this.world, stump)) {
            
            return false;
        }
        
        for (BlockPos trunk : trunks) {
            
            Block found = this.world.getBlockState(trunk).getBlock();
            
            if (!(found instanceof BlockSapling) &&
                    !found.isReplaceable(this.world, trunk)) {
                
                return false;
            }
        }
        
        this.setBlock(stump, GeoBlocks.STUMP_MEDIUM.getDefaultState());
        
        for (BlockPos trunk : trunks) {
            
            this.setBlock(trunk, GeoBlocks.TREE_MEDIUM.getDefaultState());
        }
        
        ArrayList<BlockPos> leaves = Lists.newArrayList();

        //make leaves
        int layer = 1;
        
        if (trunkCount == 4) {
            
            leaves.add(stump.up(layer));
            
            for (EnumFacing facing : EnumFacing.HORIZONTALS) {
                
                leaves.add(stump.up(layer).offset(facing));
                
                if (this.rand.nextInt(2) == 0) {
                    
                    leaves.add(stump.up(layer).offset(facing).offset(facing.rotateY()));
                }
            }
            
            layer++;
        }
        
        for (int front = -2; front <= 2; front++) {
            
            for (int side = -2; side <= 2; side++) {
                
                if (Math.abs(front) == 2 && Math.abs(side) == 2) {
                    
                } else if ((front == 2 && side == 1) ||
                        (front == 1 && side == 2)) {
                    
                    if (this.rand.nextInt(2) == 0) {
                        
                        leaves.add(stump.up(layer).offset(treeFacing, front)
                                .offset(sideFacing, side));
                    }
                    
                } else {
                    
                    leaves.add(stump.up(layer).offset(treeFacing, front)
                            .offset(sideFacing, side));
                }
            }
        }
        
        layer++;
        
        for (int front = -2; front <= 2; front++) {
            
            for (int side = -2; side <= 2; side++) {
                
                if (Math.abs(front) == 2 && Math.abs(side) == 2) {
                    
                } else if ((front < 0 || side < 0) &&
                        (Math.abs(front) == 2 || Math.abs(side) == 2)) {
                    
                    if (this.rand.nextInt(2) == 0) {
                        
                        leaves.add(stump.up(layer).offset(treeFacing, front)
                                .offset(sideFacing, side));
                    }
                    
                } else {
                    
                    leaves.add(stump.up(layer).offset(treeFacing, front)
                            .offset(sideFacing, side));
                }
            }
        }
        
        layer++;
        
        for (int front = -1; front <= 2; front++) {
            
            for (int side = -1; side <= 2; side++) {
                
                if ((front == 2 && side == 2) || (front == 2 && side == 1) ||
                        (front == 1 && side == 2)) {
                    
                } else if (front == 2 || front == -1 ||
                        side == 2 || side == -1) {
                    
                    if (this.rand.nextInt(3) == 0) {
                        
                        leaves.add(stump.up(layer).offset(treeFacing, front)
                                .offset(sideFacing, side));
                    }
                    
                } else {
                    
                    leaves.add(stump.up(layer).offset(treeFacing, front)
                            .offset(sideFacing, side));
                }
            }
        }
        
        layer++;
        
        leaves.add(stump.up(layer));
        leaves.add(stump.up(layer).offset(treeFacing));
        leaves.add(stump.up(layer).offset(sideFacing));
        leaves.add(stump.up(layer).offset(treeFacing).offset(sideFacing));
        
        for (BlockPos leaf : leaves) {
            
            if (this.world.getBlockState(leaf).getBlock()
                    .isReplaceable(this.world, leaf)) {
            
                this.setBlock(leaf, GeoBlocks.LEAF.getDefaultState().withProperty(BlockLeaves.NODE, false));
            }
        }
        
        return true;
    }
}
