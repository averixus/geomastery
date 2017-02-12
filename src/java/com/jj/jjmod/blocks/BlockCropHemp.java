package com.jj.jjmod.blocks;

import com.jj.jjmod.init.ModItems;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeForest;
import net.minecraft.world.biome.BiomeJungle;
import net.minecraft.world.biome.BiomeOcean;
import net.minecraft.world.biome.BiomePlains;
import net.minecraft.world.biome.BiomeSavanna;

/** Hemp Crop block. */
public class BlockCropHemp extends BlockCrop {
    
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
