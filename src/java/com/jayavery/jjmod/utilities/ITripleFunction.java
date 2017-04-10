package com.jayavery.jjmod.utilities;

/** Functional interface which takes three inputs to produce one output. */
@FunctionalInterface
public interface ITripleFunction<A, B, C, T> {
    
    public T apply(A a, B b, C c);
}
