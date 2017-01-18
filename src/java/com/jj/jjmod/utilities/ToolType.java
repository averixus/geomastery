package com.jj.jjmod.utilities;


public enum ToolType {
    
    PICKAXE("pickaxe"),
    AXE("axe"),
    KNIFE("knife"),
    SICKLE("sickle"),
    MACHETE("machete"),
    SHOVEL("shovel"),
    NONE("none");
    
    private String NAME;
    
    private ToolType(String name) {
        
        this.NAME = name;
    }
    
    public String toString() {
        
        return this.NAME;
    }
}
