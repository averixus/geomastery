package com.jj.jjmod.utilities;

import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

/** Custom Block Materials. */
public class BlockMaterial extends Material {
    
    public static final BlockMaterial WOOD_FURNITURE =
            new BlockMaterial(MapColor.WOOD, true, false,
            true, true, false, true, false);
    public static final BlockMaterial WOOD_HANDHARVESTABLE =
            new BlockMaterial(MapColor.WOOD, true, true,
            true, true, false, true, false);
    public static final BlockMaterial STONE_FURNITURE =
            new BlockMaterial(MapColor.STONE, true, false,
            false, true, false, true, false);
    public static final BlockMaterial STONE_HANDHARVESTABLE =
            new BlockMaterial(MapColor.STONE, true, true,
            false, true, false, true, false);
    public static final BlockMaterial WATER_PLANT =
            new BlockMaterial(MapColor.WATER, true, false,
            false, true, false, true, false);
    public static final BlockMaterial CARCASS =
            new BlockMaterial(MapColor.BROWN, true, true,
            true, true, false, false, false);
    public static final BlockMaterial SOIL =
            new BlockMaterial(MapColor.DIRT, true, false,
            false, false, false, false, false);
    public static final BlockMaterial TAR =
            new BlockMaterial(MapColor.BLACK, false, false,
            false, true, true, false, true);
    public static final BlockMaterial FRUIT =
            new BlockMaterial(MapColor.GREEN, true, true,
            true, true, false, false, false);
    
    private boolean isSolid;
    private boolean isHandHarvestable;
    private boolean isFlammable;
    private boolean isTranslucent;
    private boolean isReplaceable;
    private boolean isImmovable;
    private boolean isLiquid;
    
    public BlockMaterial(MapColor colour, boolean isSolid,
            boolean isHandHarvestable, boolean isFlammable,
            boolean isTranslucent, boolean isReplaceable,
            boolean isImmovable, boolean isLiquid) {
        
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
