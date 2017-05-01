package com.jayavery.jjmod.utilities;

import net.minecraftforge.common.property.IUnlistedProperty;

public class UnlistedPropertyEnum<E extends Enum<E>>
        implements IUnlistedProperty<E> {

    private final String name;
    private final Class<E> enumClass;
    
    public UnlistedPropertyEnum(String name, Class<E> enumClass) {
        
        this.name = name;
        this.enumClass = enumClass;
    }
    
    @Override
    public String getName() {

        return this.name;
    }

    @Override
    public Class<E> getType() {

        return this.enumClass;
    }

    @Override
    public boolean isValid(E value) {

        return true;
    }

    @Override
    public String valueToString(E value) {

        return value.toString();
    }
}
