package com.jayavery.jjmod.tileentities;

import com.jayavery.jjmod.utilities.EnumPartSingle;

/** Tile entity for knapping crafting block. */
public class TECraftingKnapping extends TECraftingAbstract<EnumPartSingle> {

    @Override
    public void update() {}
    
    @Override
    public boolean hasDurability() {
        
        return false;
    }

    @Override
    protected EnumPartSingle partByOrdinal(int ordinal) {

        return EnumPartSingle.SINGLE;
    }
}
