/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.worldgen;

import java.util.Random;
import jayavery.geomastery.blocks.BlockTar;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Abstract superclass for WorldGen objects. */
public abstract class WorldGenAbstract {
    
    /** The world to generate in. */
    protected World world;
    /** Randomiser. */
    protected Random rand;
    
    public WorldGenAbstract(World world, Random rand) {
        
        this.world = world;
        this.rand = rand;
    }
    
    /** Executes this WorldGen at the given chunk position. */
    public abstract void generateChunk(int xFromChunk, int zFromChunk);
    
    /** Finds an air block with adjacent log block,
     * at the given x and z co-ordinates.
     * @return The y co-ordinate of the valid position, -1 if there is none. */
    protected int findValidLog(int x, int z) {
        
        BlockPos midPos = new BlockPos(x, 256, z);
        
        BlockPos southPos = midPos.south();
        IBlockState southState = this.world.getBlockState(southPos);
        Block southBlock = southState.getBlock();
        
        BlockPos northPos = midPos.north();
        IBlockState northState = this.world.getBlockState(northPos);
        Block northBlock = northState.getBlock();
        
        BlockPos westPos = midPos.west();
        IBlockState westState = this.world.getBlockState(westPos);
        Block westBlock = westState.getBlock();
        
        BlockPos eastPos = midPos.east();
        IBlockState eastState = this.world.getBlockState(eastPos);
        Block eastBlock = eastState.getBlock();
        
        while (!(this.world.isAirBlock(midPos) &&
                (southBlock instanceof BlockLog ||
                 northBlock instanceof BlockLog ||
                 westBlock instanceof BlockLog ||
                 eastBlock instanceof BlockLog)) && midPos.getY() > 0) {
            
            midPos = midPos.down();
            
            southPos = southPos.down();
            southState = this.world.getBlockState(southPos);
            southBlock = southState.getBlock();
            
            northPos = northPos.down();
            northState = this.world.getBlockState(northPos);
            northBlock = northState.getBlock();
            
            westPos = westPos.down();
            westState = this.world.getBlockState(westPos);
            westBlock = westState.getBlock();
            
            eastPos = eastPos.down();
            eastState = this.world.getBlockState(eastPos);
            eastBlock = eastState.getBlock();
        }

        if (midPos.getY() <= 0) {
            
            return -1;
        }
        
        return midPos.getY();
    }
        
    /** Finds an air block with solid surface beneath,
     * at the given x and z co-ordinates.
     * @return The y co-ordinate of the valid position, -1 if none. */
    protected int findValidSurface(int x, int z) {
        
        BlockPos checkPos = new BlockPos(x, 256, z);
        IBlockState checkState = this.world.getBlockState(checkPos);
        Block checkBlock = checkState.getBlock();
        
        while (!checkBlock.isSideSolid(checkState, this.world,
                checkPos, EnumFacing.UP) && checkPos.getY() > 0) {
            
            checkPos = checkPos.down();
            checkState = this.world.getBlockState(checkPos);
            checkBlock = checkState.getBlock();
        }
        
        BlockPos spacePos = checkPos.up();
        boolean validSpace = this.world.isAirBlock(spacePos);
        
        if (checkBlock.isSideSolid(checkState, this.world,
                checkPos, EnumFacing.UP) && validSpace) {
            
            return checkPos.getY() + 1;
        }
        
        return -1;
    }
    
    /** Finds an air block with dirt beneath,
     * at the given x and z co-ordinates.
     * @return The y co-ordinate of the valid position, -1 if none. */
    public int findValidDirt(int x, int z) {
        
        int surface = this.findValidSurface(x, z);
        
        BlockPos surfacePos = new BlockPos(x, surface - 1, z);
        Block surfaceBlock = this.world.getBlockState(surfacePos).getBlock();
        
        if (surfaceBlock == Blocks.GRASS || surfaceBlock == Blocks.DIRT) {
            
            return surface;
        }
        
        return -1;
    }
    
    /** Finds an air block with grass beneath,
     * at the given x and z co-ordinates.
     * @return The y co-ordinate of the valid position, -1 if none. */
    protected int findValidGrass(int x, int z) {
        
        int surface = this.findValidSurface(x, z);
        
        BlockPos surfacePos = new BlockPos(x, surface - 1, z);
        Block surfaceBlock = this.world.getBlockState(surfacePos).getBlock();
        
        if (surfaceBlock == Blocks.GRASS) {
            
            return surface;
        }
        
        return -1;
    }
}
