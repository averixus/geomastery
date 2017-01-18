package com.jj.jjmod.blocks;

import com.jj.jjmod.worldgen.WorldGenTreeBanana;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeJungle;
import net.minecraft.world.biome.BiomeMushroomIsland;
import net.minecraft.world.biome.BiomeSwamp;

public class BlockSeedlingBanana extends BlockSeedling {
    
    public BlockSeedlingBanana() {
        
        super("seedling_banana",
                WorldGenTreeBanana::new, 0.2F);
    }

    @Override
    public boolean isPermitted(Biome biome) {

        return biome instanceof BiomeSwamp ||
                biome instanceof BiomeMushroomIsland ||
                biome instanceof BiomeJungle;
    }

}
