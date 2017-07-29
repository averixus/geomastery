/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.utilities;

import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

/** Custom block materials. */
public class BlockMaterial extends Material {
    
    /** Wooden furniture material harvestable by hand. */
    public static final BlockMaterial WOOD_FURNITURE =          new BlockMaterial(MapColor.WOOD, true, true, true, true, false, true, false);
    /** Stone furniture material harvestable by hand. */
    public static final BlockMaterial STONE_FURNITURE =         new BlockMaterial(MapColor.STONE, true, true, false, true, false, true, false);
    /** Carcass material. */
    public static final BlockMaterial CARCASS =                 new BlockMaterial(MapColor.BROWN, true, true, true, true, false, false, false);
    /** Soil material. */
    public static final BlockMaterial SOIL =                    new BlockMaterial(MapColor.DIRT, true, true, false, false, false, false, false);
    /** Blockfruit material. */
    public static final BlockMaterial FRUIT =                   new BlockMaterial(MapColor.GREEN, false, true, true, true, false, false, false);
    /** Tree material. */
    public static final BlockMaterial TREES =                   new BlockMaterial(MapColor.WOOD, true, false, true, true, false, true, false);
    
    /** Whether this material is solid. */
    private final boolean isSolid;
    /** Whether this material can be harvested by hand. */
    private final boolean isHandHarvestable;
    /** Whether this material is flammable. */
    private final boolean isFlammable;
    /** Whether this material is translucent. */
    private final boolean isTranslucent;
    /** Whether this material can be replaced by other blocks. */
    private final boolean isReplaceable;
    /** Whether this material blocks pistons. */
    private final boolean isImmovable;
    /** Whether this material is liquid. */
    private final boolean isLiquid;
    
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
