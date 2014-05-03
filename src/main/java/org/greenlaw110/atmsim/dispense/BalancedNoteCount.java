package org.greenlaw110.atmsim.dispense;

import org.greenlaw110.atmsim.Bucket;
import org.greenlaw110.atmsim.DispenseStrategy;
import org.osgl._;

import java.util.Comparator;

/**
 * This strategy try to keep the notes of different buckets
 * be balanced by value
 */
public class BalancedNoteCount implements DispenseStrategy {

    public static BalancedNoteCount INSTANCE = new BalancedNoteCount();

    /**
     * Ascending order based on bucket notes count
     */
    private static _.Comparator<Bucket> NOTES_COUNT_DESC = new _.Comparator<Bucket>() {
        @Override
        public int compare(Bucket o1, Bucket o2) {
            int n = o2.noteCount() - o1.noteCount();
            return n != 0 ? n : o2.noteTypeValue() - o1.noteTypeValue();
        }
    };


    @Override
    public Comparator<Bucket> comparator() {
        return NOTES_COUNT_DESC;
    }

    @Override
    public String toString() {
        return "Balanced note count: keep balance between buckets by note count";
    }
}
