package com.jj.jjmod.blocks;

import com.jj.jjmod.worldgen.WorldGenTreePear;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeForest;
import net.minecraft.world.biome.BiomeJungle;
import net.minecraft.world.biome.BiomeOcean;
import net.minecraft.world.biome.BiomePlains;
import net.minecraft.world.biome.BiomeSavanna;

/** Pear seedling block. */
public class BlockSeedlingPear extends BlockSeedling {
    
    public BlockSeedlingPear() {
        
        super("seedling_pear", WorldGenTreePear::new, 0.1F);
    }

    @Override
    public boolean isPermitted(Biome biome) {

        return biome instanceof BiomeForest || biome instanceof BiomeOcean ||
                biome instanceof BiomePlains || biome == Biomes.RIVER ||
                biome instanceof BiomeJungle || biome instanceof BiomeSavanna;
    }
}
