package com.jj.jjmod.capabilities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

/** Capability to make ItemStacks decay over time. */
public interface ICapDecay extends INBTSerializable<NBTTagCompound> {

    /** Update the tick counter.
     * @return Whether the stack will turn into rot. */
//    public boolean updateAndRot();
    
    /** Get the effective damage level for rendering the durability bar.
     * @return A float between 0 (full freshness) and 1 (to rot). */
    public float getRenderFraction();

    /** @return The current age in ticks. */
    public long getBirthTime();
    
    public int getStageSize();
    public void setStageSize(int stageSize);
    
    /** Sets the current age in ticks to the given value. */
    public void setBirthTime(long age);

    public boolean isRot();
}
