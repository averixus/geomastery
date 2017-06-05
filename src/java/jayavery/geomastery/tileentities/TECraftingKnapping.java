package jayavery.geomastery.tileentities;

import jayavery.geomastery.utilities.EnumSingle;

/** Tile entity for knapping crafting block. */
public class TECraftingKnapping extends TECraftingAbstract<EnumSingle> {

    @Override
    public void update() {}
    
    @Override
    public boolean hasDurability() {
        
        return false;
    }

    @Override
    protected EnumSingle partByOrdinal(int ordinal) {

        return EnumSingle.SINGLE;
    }
}
