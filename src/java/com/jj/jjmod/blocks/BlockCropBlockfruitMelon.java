package com.jj.jjmod.blocks;

import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.init.ModItems;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeJungle;
import net.minecraft.world.biome.BiomePlains;
import net.minecraft.world.biome.BiomeSavanna;

/** Melon stem crop block. */
public class BlockCropBlockfruitMelon extends BlockCropBlockfruit {
    
    public BlockCropBlockfruitMelon() {
        
        super("melon_crop", 0.4F, 0.2F, () -> ModBlocks.melonFruit,
                () -> ModItems.seedMelon);
    }

    @Override
    public boolean isPermitted(Biome biome) {

        return biome instanceof BiomePlains || biome == Biomes.BEACH ||
                biome instanceof BiomeJungle || biome instanceof BiomeSavanna;
    }
}
