package com.jj.jjmod.utilities;

/** Implemented for building blocks to compare against each other. */
public interface IBuildingBlock {
    
    /** @return Whether this block is classed as Light. */
    public boolean isLight();
    /** @return Whether this block is classed as Heavy. */
    public boolean isHeavy();
    /** @return Whether this block is classed as Double */
    public boolean isDouble();
    /** @return Whether this block can support Beams. */
    public boolean supportsBeam();
}
