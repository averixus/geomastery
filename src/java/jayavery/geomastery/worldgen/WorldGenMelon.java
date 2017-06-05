package jayavery.geomastery.worldgen;

import java.util.Random;
import jayavery.geomastery.blocks.BlockFruit;
import jayavery.geomastery.main.GeoBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** WorldGenerator for Melon crops. */
public class WorldGenMelon extends WorldGenCrop {

    public WorldGenMelon(World world, Random rand) {
        
        super(world, rand, GeoBlocks.melonCrop.getFullgrown(), 6, 4);
    }

    @Override
    protected boolean generateOne(BlockPos crop) {
        
        EnumFacing fruitOffset = EnumFacing.Plane.HORIZONTAL.random(this.rand);
        BlockPos fruit = crop.offset(fruitOffset);
        BlockPos ground = fruit.down();
        
        if (!this.world.isAirBlock(fruit) ||
                this.world.getBlockState(ground).getBlock() != Blocks.GRASS) {
            
            return false;
        }
        
        this.world.setBlockState(crop, this.crop);
        this.world.setBlockState(fruit, GeoBlocks.melonFruit.getDefaultState()
                .withProperty(BlockFruit.FACING, fruitOffset.getOpposite()));
        return true;
    }
}
