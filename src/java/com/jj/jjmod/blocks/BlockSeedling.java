package com.jj.jjmod.blocks;

import java.util.Random;
import com.jj.jjmod.utilities.IBiomeCheck;
import com.jj.jjmod.utilities.ITreeGenFactory;
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

/** Abstract superclass for seedling blocks. */
public abstract class BlockSeedling extends BlockBush implements IBiomeCheck {
    
    /** WorldGenerator factory for this tree. */
    private ITreeGenFactory treeGenFactory;
    /** Chanc of growth per update tick. */
    private float growthChance;
    /** Chance of death per update tick if invalid position. */
    private float deathChance = 0.5F;

    public BlockSeedling(String name, ITreeGenFactory treeGenFactory,
            float growthChance) {
        
        BlockNew.setupBlock(this, name, CreativeTabs.DECORATIONS,
                1F, ToolType.SHOVEL);
        this.treeGenFactory = treeGenFactory;
        this.growthChance = growthChance;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        return BlockNew.CENTRE_SIXTEEN;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        return NULL_AABB;
    }
    
    /** Grow or die according to chance. */
    @Override
    public void updateTick(World world, BlockPos pos,
            IBlockState state, Random rand) {

        super.updateTick(world, pos, state, rand);

        if (world.isRemote) {
            
            return;
        }
                
        if (!this.isPermitted(world.getBiome(pos)) &&
                rand.nextFloat() <= this.deathChance) {

            world.setBlockState(pos, Blocks.DEADBUSH.getDefaultState());
            return;
        }

        if (rand.nextFloat() <= this.growthChance) {
            
            this.treeGenFactory.makeTreeGen(world, rand, true)
                    .generateTree(pos);
        }
    }
}
