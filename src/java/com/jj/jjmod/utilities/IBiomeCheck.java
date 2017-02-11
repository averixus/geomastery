package com.jj.jjmod.utilities;

import net.minecraft.world.biome.Biome;

/** Implemented when a block is restricted to certain Biomes. */
public interface IBiomeCheck {
    
    /** @return Whether this block is allowed in the given Biome. */
    public boolean isPermitted(Biome biome);
}
