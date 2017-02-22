package com.jj.jjmod.blocks;

import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.init.ModItems;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeBeach;
import net.minecraft.world.biome.BiomeForest;
import net.minecraft.world.biome.BiomeHills;
import net.minecraft.world.biome.BiomeJungle;
import net.minecraft.world.biome.BiomePlains;
import net.minecraft.world.biome.BiomeTaiga;

/** Pumpkin stem crop block. */
public class BlockCropBlockfruitPumpkin extends BlockCropBlockfruit {
    
    public BlockCropBlockfruitPumpkin() {
        
        super("pumpkin_crop", 0.3F, 0.2F, () -> ModBlocks.pumpkinFruit,
                () -> ModItems.seedPumpkin);
    }

    @Override
    public boolean isPermitted(Biome biome) {

        return biome instanceof BiomeTaiga || biome instanceof BiomeHills ||
                biome instanceof BiomeBeach || biome instanceof BiomeForest ||
                biome instanceof BiomePlains || biome == Biomes.RIVER ||
                biome instanceof BiomeJungle;
    }
}
