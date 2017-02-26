package com.jayavery.jjmod.blocks;

import com.jayavery.jjmod.worldgen.WorldGenTreeBanana;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeJungle;
import net.minecraft.world.biome.BiomeMushroomIsland;
import net.minecraft.world.biome.BiomeSwamp;

/** Banana seedling block. */
public class BlockSeedlingBanana extends BlockSeedling {
    
    public BlockSeedlingBanana() {
        
        super("seedling_banana", WorldGenTreeBanana::new, 0.2F);
    }

    @Override
    public boolean isPermitted(Biome biome) {

        return biome instanceof BiomeSwamp ||
                biome instanceof BiomeMushroomIsland ||
                biome instanceof BiomeJungle;
    }
}
