package org.greenlaw110.atmsim.dispense;

import org.greenlaw110.atmsim.Bucket;
import org.greenlaw110.atmsim.DispenseStrategy;

import java.util.Comparator;

/**
 * This strategy choose bucket with larger denomination notes
 */
public class BigNoteFirst implements DispenseStrategy {

    private static final Comparator<Bucket> CMP = new Comparator<Bucket>() {
        @Override
        public int compare(Bucket o1, Bucket o2) {
            return -1 * (o1.type().value() - o2.type().value());
        }
    };

    @Override
    public Comparator<Bucket> comparator() {
        return CMP;
    }
}
