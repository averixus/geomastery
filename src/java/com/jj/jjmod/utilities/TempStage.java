package com.jj.jjmod.utilities;

import net.minecraft.util.ResourceLocation;

public enum TempStage {
    
    COLD("cold"), COOL("cool"), OK("ok"), WARM("warm"), HOT("hot");
    
    private ResourceLocation res;
    
    private TempStage(String name) {
        
        this.res = new ResourceLocation("jjmod:textures/gui/temp_" + name + ".png");
    }
    
    public ResourceLocation toResourceLocation() {
        
        return this.res;
    }
}
