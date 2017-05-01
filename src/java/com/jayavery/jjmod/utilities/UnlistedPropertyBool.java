package com.jayavery.jjmod.utilities;

import net.minecraftforge.common.property.IUnlistedProperty;

public class UnlistedPropertyBool implements IUnlistedProperty<Boolean> {

    private final String name;
    
    public UnlistedPropertyBool(String name) {
        
        this.name = name;
    }
    
    @Override
    public String getName() {

        return this.name;
    }

    @Override
    public Class<Boolean> getType() {

        return Boolean.class;
    }

    @Override
    public boolean isValid(Boolean value) {

        return value != null;
    }

    @Override
    public String valueToString(Boolean value) {

        return value.toString();
    }

}
