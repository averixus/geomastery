/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.crafting;

import java.awt.Color;
import java.util.Map;
import java.util.Map.Entry;
import com.google.common.collect.Maps;
import net.minecraft.item.ItemStack;

/** Compost ingredient manager. */
public class CompostManager {
    
    /** Map of ingredients to types. */
    public final Map<ItemStack, CompostType> recipes;
    
    public CompostManager() {
        
        this.recipes = Maps.newHashMap();
    }
    
    /** Adds an ingredient type. */
    public void addRecipe(ItemStack stack, CompostType type) {
        
        this.recipes.put(stack, type);
    }

    /** Adds a wet type ingredient. */
    public void addWet(ItemStack... stacks) {
        
        for (ItemStack stack : stacks) {
            
            this.addRecipe(stack, CompostType.WET);
        }
    }
    
    /** Adds a dry type ingredient. */
    public void addDry(ItemStack... stacks) {
        
        for (ItemStack stack : stacks) {
            
            this.addRecipe(stack, CompostType.DRY);
        }
    }
    
    /** @return The type of the given ingredient. */
    public CompostType getType(ItemStack stack) {
        
        for (Entry<ItemStack, CompostType> entry : this.recipes.entrySet()) {
            
            if (ItemStack.areItemsEqual(stack, entry.getKey())) {
                
                return entry.getValue();
            }
        }
        
        return null;
    }
    
    /** Types of compost ingredient. */
    public static enum CompostType {
        
        WET("Wet", new Color(0, 120, 0)), DRY("Dry", new Color(120, 70, 20));
        
        private final String name;
        private final Color colour;
        
        private CompostType(String name, Color colour) {
            
            this.name = name;
            this.colour = colour;
        }
        
        public String getName() {
            
            return this.name;
        }
        
        public Color getColour() {
            
            return this.colour;
        }
    }
}
