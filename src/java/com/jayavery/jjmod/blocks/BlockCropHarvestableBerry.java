package com.jayavery.jjmod.blocks;

import com.jayavery.jjmod.init.ModItems;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeForest;
import net.minecraft.world.biome.BiomeJungle;
import net.minecraft.world.biome.BiomeOcean;
import net.minecraft.world.biome.BiomePlains;
import net.minecraft.world.biome.BiomeSavanna;

/** Berry crop block. */
public class BlockCropHarvestableBerry extends BlockCropHarvestableAbstract {
    
    public BlockCropHarvestableBerry() {
        
        super("berry", 2, () -> ModItems.berry, (rand) -> 8, 0.4F, 0.2F);
    }

    @Override
    public boolean isPermitted(Biome biome) {

        return biome instanceof BiomeForest || biome == Biomes.RIVER ||
                biome instanceof BiomeOcean || biome instanceof BiomePlains ||
                biome instanceof BiomeJungle || biome instanceof BiomeSavanna;
    }
}
