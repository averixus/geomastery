package com.jj.jjmod.blocks;

import com.jj.jjmod.init.ModItems;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeForest;
import net.minecraft.world.biome.BiomePlains;
import net.minecraft.world.biome.BiomeTaiga;

/** Wheat Crop block. */
public class BlockCropWheat extends BlockCropAbstract {
    
    public BlockCropWheat() {
        
        super("wheat", () -> ModItems.wheat, (rand) -> 1,
                0.4F, 0.2F, ToolType.SICKLE);
    }

    @Override
    public boolean isPermitted(Biome biome) {

        return (biome instanceof BiomeTaiga && biome != Biomes.COLD_TAIGA &&
                biome != Biomes.COLD_TAIGA_HILLS &&
                biome != Biomes.MUTATED_TAIGA_COLD) ||
                biome instanceof BiomeForest || biome instanceof BiomePlains ||
                biome == Biomes.BEACH;
    }
}
