package com.jj.jjmod.blocks;

import java.util.Random;
import java.util.function.Supplier;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.init.Biomes;
import net.minecraft.init.Items;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeForest;
import net.minecraft.world.biome.BiomePlains;
import net.minecraft.world.biome.BiomeTaiga;

public class BlockCropWheat extends BlockCrop {
    
    public BlockCropWheat() {
        
        super("wheat", () -> Items.WHEAT, new Supplier<Integer>() {
            
            private final Random rand = new Random();
            
            @Override
            public Integer get() {
                
                return this.rand.nextInt(2) + 1;
            }
        }, 0.4F, 0.2F, ToolType.SICKLE);
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
