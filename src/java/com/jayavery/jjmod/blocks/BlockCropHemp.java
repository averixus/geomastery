package com.jayavery.jjmod.blocks;

import com.jayavery.jjmod.init.ModItems;
import com.jayavery.jjmod.utilities.ToolType;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeForest;
import net.minecraft.world.biome.BiomeJungle;
import net.minecraft.world.biome.BiomeOcean;
import net.minecraft.world.biome.BiomePlains;
import net.minecraft.world.biome.BiomeSavanna;

/** Hemp crop block. */
public class BlockCropHemp extends BlockCropAbstract {
    
    public BlockCropHemp() {
        
        super("hemp", () -> ModItems.twineHemp, () -> ModItems.cuttingHemp,
                (rand) -> 1, 0.4F, 0.2F, ToolType.SICKLE);
    }

    @Override
    public boolean isPermitted(Biome biome) {

        return biome == Biomes.RIVER || biome instanceof BiomeOcean ||
                biome instanceof BiomeForest ||
                biome instanceof BiomePlains ||
                biome instanceof BiomeJungle ||
                biome instanceof BiomeSavanna;
    }
}
