/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.utilities;

import java.util.Map;
import com.google.common.collect.Maps;
import jayavery.geomastery.blocks.BlockBuildingAbstract;
import jayavery.geomastery.main.GeoBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
    
/** Building block weight categories. */
public enum EBlockWeight {
        
    HEAVY(Lang.BUILDTIP_REQ_HEAVY, Lang.BUILDTIP_SUP_HEAVY),
    MEDIUM(Lang.BUILDTIP_REQ_MEDIUM, Lang.BUILDTIP_SUP_MEDIUM),
    LIGHT(Lang.BUILDTIP_REQ_LIGHT, Lang.BUILDTIP_SUP_LIGHT),
    NONE(Lang.BUILDTIP_REQ_NONE, Lang.BUILDTIP_SUP_NONE);
    
    /** Unlocalised string for required supports tooltip. */
    private final String requires;
    /** Unlocalised string for provided supports tooltip. */
    private final String supports;
    
    private EBlockWeight(String requires, String supports) {
        
        this.requires = requires;
        this.supports = supports;
    }
   
    /** @return Unlocalised string for required supports tooltip. */
    public String requires() {
        
        return this.requires;
    }
    
    /** @return Unlocalised string for provided supports tooltip. */
    public String supports() {
        
        return this.supports;
    }
    
    /** @return Whether this weight can supports the other. */
    public boolean canSupport(EBlockWeight other) {
        
        return this != NONE && this.compareTo(other) <= 0;
    }
    
    
    /** Map of block weights for non BlockBuildingAbstract blocks. */
    private static final Map<Block, EBlockWeight> BLOCK_WEIGHTS =
            Maps.newHashMap();
    
    /** Sets up non BlockBuildingAbstract weights. */
    public static void preInit() {
        
        for (Block block : new Block[] {Blocks.BEDROCK, Blocks.COBBLESTONE,
                Blocks.MOSSY_COBBLESTONE, Blocks.SANDSTONE,
                Blocks.RED_SANDSTONE, Blocks.OBSIDIAN, Blocks.STONE,
                Blocks.STONEBRICK, Blocks.PRISMARINE, Blocks.QUARTZ_BLOCK,
                Blocks.BRICK_BLOCK, Blocks.COAL_ORE, Blocks.DIAMOND_ORE,
                Blocks.EMERALD_ORE, Blocks.GOLD_ORE, Blocks.IRON_ORE,
                Blocks.LAPIS_ORE, Blocks.REDSTONE_ORE, GeoBlocks.ORE_TIN,
                GeoBlocks.ORE_SILVER, GeoBlocks.ORE_COPPER, GeoBlocks.RUBBLE,
                GeoBlocks.LODE_SAPPHIRE, GeoBlocks.LODE_RUBY,
                GeoBlocks.LODE_FIREOPAL, GeoBlocks.LODE_AMETHYST}) {
            
            BLOCK_WEIGHTS.put(block, EBlockWeight.HEAVY);
        }
        
        for (Block block : new Block[] {Blocks.CLAY, Blocks.DIRT, Blocks.GRASS,
                Blocks.GRAVEL, Blocks.ICE, Blocks.PACKED_ICE, Blocks.SAND,
                Blocks.HARDENED_CLAY, Blocks.STAINED_HARDENED_CLAY,
                GeoBlocks.PEAT, GeoBlocks.CHALK, GeoBlocks.SALT}) {
            
            BLOCK_WEIGHTS.put(block, EBlockWeight.MEDIUM);
        }
    }
    
    /** @return The weight of the given blockstate. */
    public static EBlockWeight getWeight(IBlockState state) {
        
        Block block = state.getBlock();
        
        if (block instanceof BlockBuildingAbstract) {
            
            return ((BlockBuildingAbstract<?>) block).getWeight(state);
            
        } else if (BLOCK_WEIGHTS.containsKey(block)) {
            
            return BLOCK_WEIGHTS.get(block);
            
        } else {
            
            return NONE;
        }
    }
}
