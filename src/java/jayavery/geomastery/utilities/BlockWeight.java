/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.utilities;

import java.util.Map;
import com.google.common.collect.Maps;
import jayavery.geomastery.blocks.BlockBuilding;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
    
/** Building block weight categories. */
public enum BlockWeight {
        
    HEAVY, MEDIUM, LIGHT, NONE;
    
    /** Map of vanilla block weights. */
    public static Map<Block, BlockWeight> BLOCK_WEIGHTS = Maps.newHashMap();
    static {
        
        for (Block block : new Block[] {Blocks.BEDROCK, Blocks.COBBLESTONE,
                Blocks.MOSSY_COBBLESTONE, Blocks.SANDSTONE,
                Blocks.RED_SANDSTONE, Blocks.OBSIDIAN, Blocks.STONE,
                Blocks.STONEBRICK, Blocks.PRISMARINE, Blocks.QUARTZ_BLOCK,
                Blocks.BRICK_BLOCK, Blocks.COAL_ORE, Blocks.DIAMOND_ORE,
                Blocks.EMERALD_ORE, Blocks.GOLD_ORE, Blocks.IRON_ORE,
                Blocks.LAPIS_ORE, Blocks.REDSTONE_ORE}) {
            
            BLOCK_WEIGHTS.put(block, BlockWeight.HEAVY);
        }
        
        for (Block block : new Block[] {Blocks.CLAY, Blocks.DIRT,
                Blocks.GRASS, Blocks.GRAVEL, Blocks.ICE,
                Blocks.PACKED_ICE, Blocks.SAND,
                Blocks.HARDENED_CLAY, Blocks.STAINED_HARDENED_CLAY}) {
            
            BLOCK_WEIGHTS.put(block, BlockWeight.MEDIUM);
        }
    }
    
    /** @return Whether this weight can support the other. */
    public boolean canSupport(BlockWeight other) {
        
        return this != NONE && this.compareTo(other) <= 0;
    }
    
    /** @return The weight of the given block. */
    public static BlockWeight getWeight(Block block) {
        
        if (block instanceof BlockBuilding) {
            
            return ((BlockBuilding) block).getWeight();
            
        } else if (BLOCK_WEIGHTS.containsKey(block)) {
            
            return BLOCK_WEIGHTS.get(block);
            
        } else {
            
            return NONE;
        }
    }
}
