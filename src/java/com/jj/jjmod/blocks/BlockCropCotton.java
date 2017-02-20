package com.jj.jjmod.blocks;

import com.jj.jjmod.init.ModItems;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeJungle;
import net.minecraft.world.biome.BiomePlains;

/** Cotton Crop block. */
public class BlockCropCotton extends BlockCropAbstract {
    
    public BlockCropCotton() {
        
        super("cotton", () -> ModItems.cotton, () -> ModItems.cuttingCotton,
                (rand) -> 1, 0.3F, 0.2F, ToolType.SICKLE);
    }

    @Override
    public boolean isPermitted(Biome biome) {

        return biome instanceof BiomePlains || biome == Biomes.BEACH ||
                biome instanceof BiomeJungle;
    }

}
