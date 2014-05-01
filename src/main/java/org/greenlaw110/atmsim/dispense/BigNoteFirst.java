package org.greenlaw110.atmsim.dispense;

import org.greenlaw110.atmsim.Bucket;
import org.greenlaw110.atmsim.DispenseAlgorithm;

import java.util.Comparator;

/**
 * This algorithm try select bucket of note type with bigger value
 * if the total value of the bucket is sufficient (ie. greater than
 * or equals to the value specified)
 */
public class BigNoteFirst implements DispenseAlgorithm {
    @Override
    public Comparator<Bucket> comparator(final int value) {
        return new Comparator<Bucket>() {
            @Override
            public int compare(Bucket o1, Bucket o2) {
                if (o1.noteTypeValue() > value) return 1;
                return -1 * (o1.type().value() - o2.type().value());
            }
        };
    }
}
