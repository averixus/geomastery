package com.jayavery.jjmod.utilities;

import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

/** Custom Block Materials. */
public class BlockMaterial extends Material {
    
    /** Wooden furniture material. */
    public static final BlockMaterial WOOD_FURNITURE =
            new BlockMaterial(MapColor.WOOD, true, false,
            true, true, false, true, false);
    /** Wooden furniture material harvestable by hand. */
    public static final BlockMaterial WOOD_HANDHARVESTABLE =
            new BlockMaterial(MapColor.WOOD, true, true,
            true, true, false, true, false);
    /** Stone furniture material. */
    public static final BlockMaterial STONE_FURNITURE =
            new BlockMaterial(MapColor.STONE, true, false,
            false, true, false, true, false);
    /** Stone furniture material harvestable by hand. */
    public static final BlockMaterial STONE_HANDHARVESTABLE =
            new BlockMaterial(MapColor.STONE, true, true,
            false, true, false, true, false);
    /** Water plant material. */
    public static final BlockMaterial WATER_PLANT =
            new BlockMaterial(MapColor.WATER, false, true,
            false, true, false, true, false);
    /** Carcass material. */
    public static final BlockMaterial CARCASS =
            new BlockMaterial(MapColor.BROWN, true, true,
            true, true, false, false, false);
    /** Soild material. */
    public static final BlockMaterial SOIL =
            new BlockMaterial(MapColor.DIRT, true, true,
            false, false, false, false, false);
    /** Tar material. */
    public static final BlockMaterial TAR =
            new BlockMaterial(MapColor.BLACK, false, false,
            false, true, true, false, true);
    /** Blockfruit material. */
    public static final BlockMaterial FRUIT =
            new BlockMaterial(MapColor.GREEN, false, true,
            true, true, false, false, false);
    
    /** Whether this material is solid. */
    private boolean isSolid;
    /** Whether this material can be harvested by hand. */
    private boolean isHandHarvestable;
    /** Whether this material is flammable. */
    private boolean isFlammable;
    /** Whether this material is translucent. */
    private boolean isTranslucent;
    /** Whether this material can be replaced by other blocks. */
    private boolean isReplaceable;
    /** Whether this material blocks pistons. */
    private boolean isImmovable;
    /** Whether this material is liquid. */
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
