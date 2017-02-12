package com.jj.jjmod.blocks;

import com.jj.jjmod.worldgen.WorldGenTreeOrange;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDesert;
import net.minecraft.world.biome.BiomeJungle;
import net.minecraft.world.biome.BiomeMesa;
import net.minecraft.world.biome.BiomeSavanna;

/** Orange seedling block. */
public class BlockSeedlingOrange extends BlockSeedling {
    
    public BlockSeedlingOrange() {
        
        super("seedling_orange",  WorldGenTreeOrange::new, 0.15F);
    }

    @Override
    public boolean isPermitted(Biome biome) {

        return biome instanceof BiomeJungle || biome instanceof BiomeSavanna ||
                biome instanceof BiomeDesert || biome instanceof BiomeMesa;
    }
}
