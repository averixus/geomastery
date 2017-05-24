package com.jayavery.jjmod.utilities;

import com.jayavery.jjmod.blocks.BlockWall;
import net.minecraftforge.common.property.IUnlistedProperty;

public class UnlistedPropertyWall implements IUnlistedProperty<BlockWall> {

    private final String name;
    
    public UnlistedPropertyWall(String name) {
        
        this.name = name;
    }
    @Override
    public String getName() {

        return this.name;
    }

    @Override
    public boolean isValid(BlockWall value) {

        return true;
    }

    @Override
    public Class<BlockWall> getType() {

        return BlockWall.class;
    }

    @Override
    public String valueToString(BlockWall value) {

        return value.getRegistryName().toString();
    } 
}
