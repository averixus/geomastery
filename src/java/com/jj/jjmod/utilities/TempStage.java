package com.jj.jjmod.utilities;

import net.minecraft.util.ResourceLocation;

/** Enum defining stages of temperature and associated icons. */
public enum TempStage {
    
    COLD("cold"), COOL("cool"), OK("ok"), WARM("warm"), HOT("hot");
    
    /** Texture of this icon. */
    private ResourceLocation res;
    
    private TempStage(String name) {
        
        this.res = new ResourceLocation("jjmod:textures/gui/temp_" +
                name + ".png");
    }
    
    /** @return The ResourceLocation of the icon for this Stage. */
    public ResourceLocation toResourceLocation() {
        
        return this.res;
    }
}
