package com.jj.jjmod.blocks;

import com.jj.jjmod.init.ModFluids;
import com.jj.jjmod.utilities.BlockMaterial;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;

public class BlockTar extends BlockFluidClassic {

    public BlockTar() {
        
        super(ModFluids.tarFluid, BlockMaterial.TAR);
        BlockNew.setupBlock(this, "tar", null, -1, null);
        this.setQuantaPerBlock(3);
    }
    
    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos,
            IBlockState state, Entity entity) {
        
        entity.motionX *= 0.1D;
        entity.motionY *= 0.1D;
    }

}
