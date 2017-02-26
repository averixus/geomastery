package com.jayavery.jjmod.blocks;

import com.jayavery.jjmod.worldgen.WorldGenTreeApple;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeForest;
import net.minecraft.world.biome.BiomePlains;

/** Apple seedling block. */
public class BlockSeedlingApple extends BlockSeedling {
    
    public BlockSeedlingApple() {
        
        super("seedling_apple", WorldGenTreeApple::new, 0.1F);
    }

    @Override
    public boolean isPermitted(Biome biome) {

        return biome instanceof BiomeForest || biome == Biomes.RIVER ||
                biome instanceof BiomePlains || biome == Biomes.BEACH;
    }
}
