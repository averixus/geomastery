package com.jayavery.jjmod.utilities;

import net.minecraftforge.client.model.IModel;

/** Pair object for IModel with rotation. */
public class Modeller {
    
    /** The model. */
    private final IModel model;
    /** The model's y-axis rotation. */
    private final int rot;

    public Modeller(IModel model, int rot) {
        
        this.model = model;
        this.rot = rot;
    }
    
    public IModel model() {
        
        return this.model;
    }
    
    public int rot() {
        
        return this.rot;
    }
}
