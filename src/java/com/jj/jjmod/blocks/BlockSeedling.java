package com.jj.jjmod.blocks;

import java.util.Random;
import com.jj.jjmod.utilities.IBiomeCheck;
import com.jj.jjmod.utilities.ITreeGenRef;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.BlockBush;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

/** Abstract superclass for Seedling blocks. */
public abstract class BlockSeedling extends BlockBush implements IBiomeCheck {

    protected static final AxisAlignedBB BOUNDS =
            new AxisAlignedBB(0.09999999403953552D, 0.0D,
                    0.09999999403953552D, 0.8999999761581421D,
                    0.800000011920929D, 0.8999999761581421D);
    
    /** WorldGenerator factory for this tree. */
    private ITreeGenRef treeGen;
    /** Chanc of growth per update tick. */
    private float growthChance;
    /** Chance of death per update tick if invalid position. */
    private float deathChance = 0.5F;

    public BlockSeedling(String name, ITreeGenRef treeGen, float growthChance) {
        
        super();
        BlockNew.setupBlock(this, name, CreativeTabs.DECORATIONS,
                1F, ToolType.SHOVEL);
        this.treeGen = treeGen;
        this.growthChance = growthChance;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        return BOUNDS;
    }
    
    @Override
    public void updateTick(World world, BlockPos pos,
            IBlockState state, Random rand) {

        if (world.isRemote) {
            
            return;
        }
        
        super.updateTick(world, pos, state, rand);
        
        if (!this.isPermitted(world.getBiome(pos)) &&
                rand.nextFloat() <= this.deathChance) {

            world.setBlockState(pos, Blocks.DEADBUSH.getDefaultState());
            return;
        }

        if (rand.nextFloat() <= this.growthChance) {
            
            this.treeGen.makeTreeGen(world, rand, true).generateTree(pos);
        }
    }
}
