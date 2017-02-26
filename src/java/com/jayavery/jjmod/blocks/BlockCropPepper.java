package com.jayavery.jjmod.blocks;

import com.jayavery.jjmod.init.ModItems;
import com.jayavery.jjmod.utilities.ToolType;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeBeach;
import net.minecraft.world.biome.BiomeForest;
import net.minecraft.world.biome.BiomeJungle;
import net.minecraft.world.biome.BiomeOcean;
import net.minecraft.world.biome.BiomePlains;

/** Pepper crop block. */
public class BlockCropPepper extends BlockCropAbstract {
    
    public BlockCropPepper() {
        
        super("pepper", () -> ModItems.pepper, (rand) -> 3,
                0.3F, 0.2F, ToolType.SICKLE);
    }

    @Override
    public boolean isPermitted(Biome biome) {

        return biome instanceof BiomeOcean || biome == Biomes.RIVER ||
                biome instanceof BiomeBeach || biome instanceof BiomeForest ||
                biome instanceof BiomePlains ||
                biome instanceof BiomeJungle;
    }
}
