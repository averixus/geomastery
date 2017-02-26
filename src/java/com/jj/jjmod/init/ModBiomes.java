package com.jj.jjmod.init;

import java.util.Map;
import com.google.common.collect.Maps;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeBeach;
import net.minecraft.world.biome.BiomeDesert;
import net.minecraft.world.biome.BiomeForest;
import net.minecraft.world.biome.BiomeHills;
import net.minecraft.world.biome.BiomeJungle;
import net.minecraft.world.biome.BiomeMesa;
import net.minecraft.world.biome.BiomeMushroomIsland;
import net.minecraft.world.biome.BiomeOcean;
import net.minecraft.world.biome.BiomePlains;
import net.minecraft.world.biome.BiomeRiver;
import net.minecraft.world.biome.BiomeSavanna;
import net.minecraft.world.biome.BiomeSnow;
import net.minecraft.world.biome.BiomeSwamp;
import net.minecraft.world.biome.BiomeTaiga;

public class ModBiomes {
    
    /** Map of biome base temperatures */
    private static final Map<Biome, Integer> BIOMES = Maps.newHashMap();
    
    public static void init() {
        
        for (ResourceLocation key : Biome.REGISTRY.getKeys()) {
            
            Biome biome = Biome.REGISTRY.getObject(key);
            BIOMES.put(biome, chooseTemp(biome));
        }
    }
    
    /** @return The base temp for the biome */
    public static int getTemp(Biome biome) {
        
        return BIOMES.get(biome);
    }
    
    /** Assigns a temperature to the biome.
     * @return The base temperature for the biome */
    private static int chooseTemp(Biome biome) {
        
        if (biome == Biomes.MUTATED_ICE_FLATS) {
            
            return -4;
        }
        
        if (biome instanceof BiomeSnow) {
            
            return -3;
        }
        
        if (biome == Biomes.FROZEN_RIVER) {
            
            return -2;
        }
        
        if (biome == Biomes.COLD_TAIGA || biome == Biomes.COLD_TAIGA_HILLS ||
                biome == Biomes.MUTATED_TAIGA_COLD ||
                biome == Biomes.COLD_BEACH) {
            
            return -1;
        }
        
        if (biome instanceof BiomeHills || biome == Biomes.STONE_BEACH ||
                biome instanceof BiomeTaiga) {
            
            return 0;
        }
        
        if (biome == Biomes.BIRCH_FOREST ||
                biome == Biomes.BIRCH_FOREST_HILLS ||
                biome instanceof BiomeRiver) {
            
            return 1;
        }
        
        if (biome instanceof BiomeOcean || biome instanceof BiomeForest) {
            
            return 2;
        }
        
        if (biome instanceof BiomePlains || biome instanceof BiomeSwamp ||
                biome instanceof BiomeBeach) {
            
            return 3;
        }
        
        if (biome instanceof BiomeMushroomIsland ||
                biome instanceof BiomeJungle) {

            return 4;
        }
        
        if (biome instanceof BiomeSavanna) {
            
            return 5;
        }
        
        if (biome instanceof BiomeDesert || biome instanceof BiomeMesa) {
            
            return 6;
        }

        return 1;
    }
}
