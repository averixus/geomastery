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
    
    @Override
    public String toString() {
        
        return this.NAME;
    }
}
