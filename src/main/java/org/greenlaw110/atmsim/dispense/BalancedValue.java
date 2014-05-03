package org.greenlaw110.atmsim.dispense;

import org.greenlaw110.atmsim.Bucket;
import org.greenlaw110.atmsim.DispenseStrategy;
import org.osgl._;

import java.util.Comparator;

/**
 * This strategy try to keep the notes of different buckets
 * be balanced by value
 */
public class BalancedValue implements DispenseStrategy {

    public static BalancedValue INSTANCE = new BalancedValue();

    private static _.Comparator<Bucket> VALUE_DESC = new _.Comparator<Bucket>() {
        @Override
        public int compare(Bucket o1, Bucket o2) {
            return o2.value() - o1.value();
        }
    };

    @Override
    public Comparator<Bucket> comparator() {
        return VALUE_DESC;
    }

    @Override
    public String toString() {
        return "Balanced value: keep balance between buckets by value";
    }
}
