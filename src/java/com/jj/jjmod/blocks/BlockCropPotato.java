package com.jj.jjmod.blocks;

import java.util.Random;
import java.util.function.Supplier;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.init.Biomes;
import net.minecraft.init.Items;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeForest;
import net.minecraft.world.biome.BiomeHills;
import net.minecraft.world.biome.BiomePlains;
import net.minecraft.world.biome.BiomeTaiga;

/** Potato Crop block. */
public class BlockCropPotato extends BlockCrop {
    
    public BlockCropPotato() {
        
        super("potato", () -> Items.POTATO, (rand) -> rand.nextInt(3) + 1,
                0.3F, 0.2F, ToolType.SICKLE);
    }
    
    @Override
    public boolean isPermitted(Biome biome) {

        return biome instanceof BiomeTaiga || biome instanceof BiomeHills ||
                biome == Biomes.STONE_BEACH || biome instanceof BiomeForest ||
                biome == Biomes.RIVER || biome instanceof BiomePlains;
    }
}
