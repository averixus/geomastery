/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.main;

import java.util.Map;
import com.google.common.collect.Maps;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeBeach;
import net.minecraft.world.biome.BiomeDesert;
import net.minecraft.world.biome.BiomeEnd;
import net.minecraft.world.biome.BiomeForest;
import net.minecraft.world.biome.BiomeHell;
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
import net.minecraft.world.biome.BiomeVoid;

/** Stores and calculates Geomastery temperatures of vanilla biomes. */
public class GeoBiomes {
    
    /** Map of biome base temperatures */
    private static final Map<Biome, Float> BIOMES = Maps.newHashMap();
    
    /** Sets up biome map. */
    public static void init() {
        
        Geomastery.LOG.info("Creating biome temperature map");
        for (ResourceLocation key : Biome.REGISTRY.getKeys()) {
            
            Biome biome = Biome.REGISTRY.getObject(key);
            BIOMES.put(biome, chooseTemp(biome));
        }
    }
    
    /** @return The base temp for the biome */
    public static float getTemp(Biome biome) {
        
        return BIOMES.get(biome);
    }
    
    /** Assigns a temperature to the biome.
     * @return The base temperature for the biome */
    private static float chooseTemp(Biome biome) {
        
        if (biome == Biomes.MUTATED_ICE_FLATS) {
            
            return -4;
            
        } else if (biome instanceof BiomeSnow) {
            
            return -3;
            
        } else if (biome == Biomes.FROZEN_RIVER) {
            
            return -2;
            
        } else if (biome == Biomes.COLD_TAIGA ||
                biome == Biomes.COLD_TAIGA_HILLS ||
                biome == Biomes.MUTATED_TAIGA_COLD) {
            
            return -1;
            
        } else if (biome == Biomes.COLD_BEACH) {
            
            return -0.5F;
            
        } else if (biome instanceof BiomeHills || biome instanceof BiomeTaiga ||
                biome instanceof BiomeVoid || biome instanceof BiomeEnd) {
            
            return 0;
            
        } else if (biome == Biomes.STONE_BEACH) {
            
            return 0.5F;
            
        } else if (biome == Biomes.BIRCH_FOREST ||
                biome == Biomes.BIRCH_FOREST_HILLS) {
            
            return 1;
            
        } else if (biome instanceof BiomeOcean || biome instanceof BiomeForest
                || biome instanceof BiomeRiver) {
            
            return 2;
            
        } else if (biome instanceof BiomePlains) {
            
            return 2.5F;
            
        } else if (biome instanceof BiomeSwamp || biome instanceof BiomeBeach) {
            
            return 3;
            
        } else if (biome instanceof BiomeMushroomIsland) {

            return 4;
            
        } else if (biome instanceof BiomeJungle) {
            
            return 4.5F;
            
        } else if (biome instanceof BiomeSavanna) {
            
            return 5;
            
        } else if (biome instanceof BiomeDesert || biome instanceof BiomeMesa ||
                biome instanceof BiomeHell) {
            
            return 6;
            
        } else {

            float base = biome.getTemperature();
            float converted = (base - 1) * 10;
            Geomastery.LOG.info("Unsupported biome {} has had its temperature set to {}", biome.getBiomeName(), converted);
            return converted;
        }
    }
}
