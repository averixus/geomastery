package com.jayavery.jjmod.utilities;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.jayavery.jjmod.entities.FallingTreeBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;

/** Utility class for fellings trees and leaves. */
public class TreeFallUtils {
    
    /** Array of all possible offset BlockPos for tree trunks. */
    public static final BlockPos[] TRUNK_OFFSETS = {new BlockPos(-1, 0, -1),
            new BlockPos(-1, 1, -1), new BlockPos(-1, 0, 0),
            new BlockPos(-1, 1, 0), new BlockPos(-1, 0, 1),
            new BlockPos(-1, 1, 1), new BlockPos(0, 0, -1),
            new BlockPos(0, 1, -1), new BlockPos(1, 1, 1),
            new BlockPos(0, 1, 0), new BlockPos(0, 0, 1),
            new BlockPos(0, 1, 1), new BlockPos(1, 0, -1),
            new BlockPos(1, 1, -1), new BlockPos(1, 0, 0),
            new BlockPos(1, 1, 0), new BlockPos(1, 0, 1)};
    
    /** Surroundings array for leaf decay checking. */
    private static int[] surroundings;

    /** Utility class: do not create instances. */
    private TreeFallUtils() {}

    /** Fells a tree with trunk at origin position in a random direction. */
    public static void fellTree(World world, BlockPos origin) {

        fellTree(world, origin, EnumFacing.HORIZONTALS[world.rand.nextInt(4)]);
    }

    /** Fells a tree with trunk at origin position in given direction. */
    public static void fellTree(World world, BlockPos origin,
            EnumFacing direction) {

        List<FallingTreeBlock> toFall = Lists.newArrayList();
        Set<BlockPos> checked = Sets.newHashSet();
        Queue<BlockPos> trunkQueue = new LinkedList<BlockPos>();
        Queue<BlockPos> leafQueue = new LinkedList<BlockPos>();

        if (world.getBlockState(origin).getBlock() instanceof BlockLog) {

            trunkQueue.add(origin);

        } else {

            leafQueue.add(origin);
        }

        while (!trunkQueue.isEmpty()) {

            BlockPos nextPos = trunkQueue.remove();
            IBlockState nextState = world.getBlockState(nextPos);
            Block nextBlock = nextState.getBlock();
            checked.add(nextPos);

            if (nextBlock instanceof BlockLog) {

                toFall.add(new FallingTreeBlock.Trunk(world, nextPos, direction,
                        nextState, nextPos.getY() - origin.getY()));

                for (BlockPos offset : TRUNK_OFFSETS) {

                    BlockPos toAdd = nextPos.add(offset);

                    if (!checked.contains(toAdd)) {

                        trunkQueue.add(toAdd);
                    }
                }

            } else if (nextBlock instanceof BlockLeaves) {

                leafQueue.add(nextPos);
            }
        }

        int max = toFall.size() * 10;

        while (!leafQueue.isEmpty() && toFall.size() <= max) {

            BlockPos nextPos = leafQueue.remove();
            IBlockState nextState = world.getBlockState(nextPos);
            Block nextBlock = nextState.getBlock();
            checked.add(nextPos);

            if (nextBlock instanceof BlockLeaves) {

                toFall.add(new FallingTreeBlock.Leaves(world, nextPos, direction,
                        nextState, nextPos.getY() - origin.getY()));

                for (EnumFacing facing : EnumFacing.VALUES) {

                    BlockPos toAdd = nextPos.offset(facing);

                    if (!checked.contains(toAdd)) {

                        leafQueue.add(toAdd);
                    }
                }
            }
        }

        for (FallingTreeBlock fall : toFall) {

            world.spawnEntity(fall);
        }
    }
    
    /** Checks and fells leaves at the given position. */
    public static void checkLeavesFall(World world, BlockPos pos) {
        
        IBlockState state = world.getBlockState(pos);
        
        if (TreeFallUtils.shouldLeavesFall(world, pos, state)
                && world.isAirBlock(pos.down())) {
            
            world.spawnEntity(new FallingTreeBlock.Leaves(world, pos,
                    EnumFacing.HORIZONTALS[world.rand.nextInt(4)],
                    state, 1));
        }
    }
    
    /** Checks and fells tree starting at given position. */
    public static void checkTreeFall(World world, BlockPos pos) {
        
        boolean logFall = world.isAirBlock(pos.down());
        
        for (BlockPos offset : TreeFallUtils.TRUNK_OFFSETS) {
            
            if (world.getBlockState(pos.subtract(offset)).getBlock()
                    instanceof BlockLog) {
                
                logFall = false;
                break;
            }
        }
        
        if (logFall) {

            fellTree(world, pos);
        }
    }

    /** Copied from vanilla.
     * @return Whether leaves should decay. */
    private static boolean shouldLeavesFall(World world, BlockPos pos,
            IBlockState state) {

        if (state.getValue(BlockLeaves.CHECK_DECAY) && state.getValue(
                BlockLeaves.DECAYABLE)) {
            
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();

            if (surroundings == null) {
                
                surroundings = new int[32768];
            }

            if (world.isAreaLoaded(new BlockPos(x - 5, y - 5, z - 5),
                    new BlockPos(x + 5, y + 5, z + 5))) {
                
                MutableBlockPos mutablePos = new MutableBlockPos();

                for (int i = -4; i <= 4; ++i) {
                    
                    for (int j = -4; j <= 4; ++j) {
                        
                        for (int k = -4; k <= 4; ++k) {
                            
                            IBlockState blockState = world
                                    .getBlockState(mutablePos.setPos(x + i,
                                    y + j, z + k));
                            Block block = blockState.getBlock();

                            if (!block.canSustainLeaves(blockState, world,
                                    mutablePos.setPos(x + i,
                                    y + j, z + k))) {
                                
                                if (block.isLeaves(blockState, world,
                                        mutablePos.setPos(x + i,
                                        y + j, z + k))) {
                                    
                                    surroundings[(i + 16) * 1024 +
                                            (j + 16) * 32 + k + 16] = -2;
                                } else {
                                    
                                    surroundings[(i + 16) * 1024 +
                                            (j + 16) * 32 + k + 16] = -1;
                                }
                                
                            } else {
                                
                                surroundings[(i + 16) * 1024 +
                                        (j + 16) * 32 + k + 16] = 0;
                            }
                        }
                    }
                }

                for (int i = 1; i <= 4; ++i) {
                    
                    for (int j = -4; j <= 4; ++j) {
                        
                        for (int k = -4; k <= 4; ++k) {
                            
                            for (int l = -4; l <= 4; ++l) {
                                
                                if (surroundings[(j + 16) * 1024 + (k + 16)
                                        * 32 + l + 16] == i - 1) {
                                    
                                    if (surroundings[(j + 16 - 1) * 1024 +
                                            (k + 16) * 32 + l + 16] == -2) {
                                        
                                        surroundings[(j + 16 - 1) * 1024 + 
                                                (k + 16) * 32 + l + 16] = i;
                                    }

                                    if (surroundings[(j + 16 + 1) * 1024 +
                                            (k + 16) * 32 + l + 16] == -2) {
                                        
                                        surroundings[(j + 16 + 1) * 1024 +
                                                (k + 16) * 32 + l + 16] = i;
                                    }

                                    if (surroundings[(j + 16) * 1024 +
                                            (k + 16 - 1) * 32 + l + 16] == -2) {
                                        
                                        surroundings[(j + 16) * 1024 +
                                                (k + 16 - 1) * 32 + l + 16] = i;
                                    }

                                    if (surroundings[(j + 16) * 1024 +
                                            (k + 16 + 1) * 32 + l + 16] == -2) {
                                        surroundings[(j + 16) * 1024 +
                                                (k + 16 + 1) * 32 + l + 16] = i;
                                    }

                                    if (surroundings[(j + 16) * 1024 + (k + 16)
                                             * 32 + (l + 16 - 1)] == -2) {
                                        
                                        surroundings[(j + 16) * 1024 + (k + 16)
                                                * 32 + (l + 16 - 1)] = i;
                                    }

                                    if (surroundings[(j + 16) * 1024 + (k + 16)
                                             * 32 + l + 16 + 1] == -2) {
                                        
                                        surroundings[(j + 16) * 1024 + (k + 16)
                                                * 32 + l + 16 + 1] = i;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            return surroundings[16912] < 0;
        }

        return false;
    }
}
