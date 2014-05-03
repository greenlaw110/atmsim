package org.greenlaw110.atmsim;

import java.util.Comparator;

public interface DispenseStrategy {
    /**
     * Returns a comparator used to sort the buckets in an ATM
     *
     * <p>The comparator sort the buckets in an order that
     * the first bucket should be the one to dispense the notes if
     * the ATM support to dispense the value</p>
     *
     * @return a comparator
     */
    Comparator<Bucket> comparator();

}
