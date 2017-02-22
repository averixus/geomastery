package com.jj.jjmod.blocks;

import com.jj.jjmod.init.ModItems;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeForest;
import net.minecraft.world.biome.BiomeJungle;
import net.minecraft.world.biome.BiomePlains;

/** Tomato crop block. */
public class BlockCropHarvestableTomato extends BlockCropHarvestable {
    
    public BlockCropHarvestableTomato() {
        
        super("tomato", 2, () -> ModItems.tomato, (rand) -> 2, 0.4F, 0.2F);
    }

    @Override
    public boolean isPermitted(Biome biome) {

        return biome instanceof BiomeForest || biome == Biomes.RIVER ||
                biome instanceof BiomePlains || biome == Biomes.BEACH ||
                biome instanceof BiomeJungle;
    }
}
