package jayavery.geomastery.worldgen;

import java.util.Random;
import jayavery.geomastery.blocks.BlockFruit;
import jayavery.geomastery.main.GeoBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** WorldGenerator for pumpkin crops. */
public class WorldGenPumpkin extends WorldGenCrop {

    public WorldGenPumpkin(World world, Random rand) {
        
        super(world, rand, GeoBlocks.pumpkinCrop.getFullgrown(), 4, 3);
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
        this.world.setBlockState(fruit, GeoBlocks.pumpkinFruit.getDefaultState()
                .withProperty(BlockFruit.FACING, fruitOffset.getOpposite()));
        return true;
    }
}
