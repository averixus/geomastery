/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.crafting;

import java.awt.Color;
import java.util.Set;
import com.google.common.collect.Sets;
import jayavery.geomastery.utilities.Lang;
import net.minecraft.item.ItemStack;

/** Compost ingredient manager. */
public class CompostManager {
    
    /** Set of wet ingredients. */
    public final Set<ItemStack> wet;
    /** Set of dry ingredients. */
    public final Set<ItemStack> dry;
    
    public CompostManager() {
        
        this.wet = Sets.newHashSet();
        this.dry = Sets.newHashSet();
    }

    /** Adds a wet type ingredient. */
    public void addWet(ItemStack... stacks) {
        
        for (ItemStack stack : stacks) {
            
            this.wet.add(stack);
        }
    }
    
    /** Adds a dry type ingredient. */
    public void addDry(ItemStack... stacks) {
        
        for (ItemStack stack : stacks) {
            
            this.dry.add(stack);
        }
    }
    
    /** @return The type of the given ingredient. */
    public CompostType getType(ItemStack stack) {
        
        for (ItemStack wet : this.wet) {
            
            if (ItemStack.areItemsEqual(stack, wet)) {
                
                return CompostType.WET;
            }
        }
        
        for (ItemStack dry : this.dry) {
            
            if (ItemStack.areItemsEqual(stack, dry)) {
                
                return CompostType.DRY;
            }
        }
        
        return null;
    }
    
    /** Types of compost ingredient. */
    public static enum CompostType {
        
        WET(new Color(0, 120, 0), Lang.COMPOST_WET),
        DRY(new Color(120, 70, 20), Lang.COMPOST_DRY);

        /** Colour for GUI uses. */
        private final Color colour;
        /** Unlocalised name of this type. */
        private final String name;
        
        private CompostType(Color colour, String name) {
            
            this.colour = colour;
            this.name = name;
        }
        
        public String getName() {
            
            return this.name;
        }
        
        public Color getColour() {
            
            return this.colour;
        }
    }
}
