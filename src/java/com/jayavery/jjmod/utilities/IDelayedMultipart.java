package com.jayavery.jjmod.utilities;

import net.minecraftforge.client.model.ICustomModelLoader;

/** Interface to define blocks which are modelled as delayed multiparts. */
public interface IDelayedMultipart {
    
    /** @return A loader for this block's model. */
    public abstract ICustomModelLoader getLoader();
}
