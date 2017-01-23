package com.jj.jjmod.utilities;

import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class BlockMaterial extends Material {
    
    public static final BlockMaterial WOOD_FURNITURE = new BlockMaterial(MapColor.WOOD, true, false, true, true, false, true, false);
    public static final BlockMaterial STONE_FURNITURE = new BlockMaterial(MapColor.STONE, true, false, false, true, false, true, false);
    public static final BlockMaterial WATER_PLANT = new BlockMaterial(MapColor.WATER, true, false, false, true, false, true, false);
    public static final BlockMaterial CARCASS = new BlockMaterial(MapColor.BROWN, true, true, true, true, false, false, false);
    public static final BlockMaterial SOIL = new BlockMaterial(MapColor.DIRT, true, false, false, false, false, false, false);
    public static final BlockMaterial TAR = new BlockMaterial(MapColor.BLACK, false, false, false, true, true, false, true);
    
    protected boolean isSolid;
    protected boolean isHandHarvestable;
    protected boolean isFlammable;
    protected boolean isTranslucent;
    protected boolean isReplaceable;
    protected boolean isImmovable;
    protected boolean isLiquid;
    
    public BlockMaterial(MapColor colour, boolean isSolid, boolean isHandHarvestable, boolean isFlammable, boolean isTranslucent, boolean isReplaceable, boolean isImmovable, boolean isLiquid) {
        
        super(colour);
        this.isSolid = isSolid;
        this.isHandHarvestable = isHandHarvestable;
        this.isFlammable = isFlammable;
        this.isTranslucent = isTranslucent;
        this.isReplaceable = isReplaceable;
        this.isImmovable = isImmovable;
        this.isLiquid = isLiquid;
    }
    
    @Override
    public boolean isLiquid() {
        
        return this.isLiquid;
    }
    
    @Override
    public boolean isSolid() {
        
        return this.isSolid;
    }
    
    @Override
    public boolean blocksLight() {
        
        return !this.isTranslucent;
    }
    
    @Override
    public boolean blocksMovement() {
        
        return !this.isReplaceable || this.isImmovable;
    }
    
    @Override
    public boolean getCanBurn() {
        
        return this.isFlammable;
    }
    
    @Override
    public boolean isReplaceable() {
        
        return this.isReplaceable;
    }
    
    @Override
    public boolean isOpaque() {
        
        return !this.isTranslucent;
    }
    
    @Override
    public boolean isToolNotRequired() {
        
        return this.isHandHarvestable;
    }
    
    @Override
    public EnumPushReaction getMobilityFlag() {
        
        return this.isImmovable ? EnumPushReaction.BLOCK :
                (this.isReplaceable ? EnumPushReaction.DESTROY :
                EnumPushReaction.NORMAL);
    }
}
