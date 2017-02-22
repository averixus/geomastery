package com.jj.jjmod.utilities;

/** Implemented for enums which define multipart structures. */
public interface IMultipart {
    
    /** @return Whether this part of the sturcture should drop its item. */
    public boolean shouldDrop();
}
