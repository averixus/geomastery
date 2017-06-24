/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.capabilities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;

/** Capability to make ItemStacks decay over time. */
public interface ICapDecay extends INBTSerializable<NBTTagCompound> {

    /** Get the effective damage level for rendering the durability bar.
     * @return A float between 0 (full freshness) and 1 (rot). */
    public float getFraction(World world);

    /** @return The current age in ticks. */
    public long getBirthTime();
    
    /** @return The size of one age stage in ticks. */
    public int getStageSize();
    
    /** Sets the size of one age stage in ticks to the given value. */
    public void setStageSize(int stageSize);
    
    /** Sets the birth time to approximately the given value,
     * rounded down according to shelflife. */
    public void setBirthTime(long birthTime);

    /** @return Whether the item is older than its shelf life. */
    public boolean isRot(World world);
    
    /** Updates the data using tags passed to {@link net.minecraft.item
     * .Item#getNBTShareTag(ItemStack stack) Item#getNBTShareTag}
     * (required to keep the client synced with a physical server
     * due to Forge ItemStack syncing limitations). */
    public void updateFromNBT(NBTTagCompound nbt);
}
