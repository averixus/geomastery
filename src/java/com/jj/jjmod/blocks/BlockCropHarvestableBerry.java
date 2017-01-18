package com.jj.jjmod.blocks;

import com.jj.jjmod.init.ModItems;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeForest;
import net.minecraft.world.biome.BiomeJungle;
import net.minecraft.world.biome.BiomeOcean;
import net.minecraft.world.biome.BiomePlains;
import net.minecraft.world.biome.BiomeSavanna;

public class BlockCropHarvestableBerry extends BlockCropHarvestable {
    
    public BlockCropHarvestableBerry() {
        
        super("berry", 2,
                () -> ModItems.berry, 9, 0.2F);
        this.growthChance = 0.4F;
    }

    @Override
    public boolean isPermitted(Biome biome) {

        return biome instanceof BiomeForest || biome == Biomes.RIVER ||
                biome instanceof BiomeOcean || biome instanceof BiomePlains ||
                biome instanceof BiomeJungle || biome instanceof BiomeSavanna;
    }

}
