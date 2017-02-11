package com.jj.jjmod.utilities;

/** Enum defining all Tool Types. */
public enum ToolType {
    
    PICKAXE("pickaxe"),
    AXE("axe"),
    KNIFE("knife"),
    SICKLE("sickle"),
    MACHETE("machete"),
    SHOVEL("shovel");
    
    private String name;
    
    private ToolType(String name) {
        
        this.name = name;
    }
    
    @Override
    public String toString() {
        
        return this.name;
    }
}
