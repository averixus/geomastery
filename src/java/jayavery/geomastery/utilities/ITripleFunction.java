/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.utilities;

/** Functional interface which takes three inputs to produce one output. */
@FunctionalInterface
public interface ITripleFunction<A, B, C, T> {
    
    public T apply(A a, B b, C c);
}
