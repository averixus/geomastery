package com.jayavery.jjmod.utilities;

/** Implemented for building blocks to compare against each other. */
public interface IBuildingBlock {
    
    /** @return Whether this block is classed as light. */
    public boolean isLight();
    /** @return Whether this block is classed as heavy. */
    public boolean isHeavy();
    /** @return Whether this block is classed as double */
    public boolean isDouble();
    /** @return Whether this block can support beams. */
    public boolean supportsBeam();
    /** @return Whether this block gives full rain shelter. */
    public boolean isShelter();
}
