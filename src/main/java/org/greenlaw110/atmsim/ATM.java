package org.greenlaw110.atmsim;

import org.greenlaw110.atmsim.dispense.BigNoteFirst;
import org.osgl._;
import org.osgl.util.C;
import org.osgl.util.E;
import org.osgl.util.N;

import java.util.Collection;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;

/**
 * Simulate the ATM note dispense logic
 */
public class ATM {

    /**
     * Map the {@link Bucket buckets} in this ATM to the {@link org.greenlaw110.atmsim.NoteType types}
     * <p/>
     * <p>That says we have one bucket per note type in this ATM</p>
     */
    private EnumMap<NoteType, Bucket> buckets = new EnumMap<NoteType, Bucket>(NoteType.class);

    /**
     * Organize all bucket instances in a list so that we can easily iterate through them
     */
    private C.List<Bucket> bucketList;

    /**
     * The dispense strategy. Default value is
     * {@link org.greenlaw110.atmsim.dispense.BigNoteFirst}
     */
    private DispenseStrategy strategy;

    /**
     * The format that help to print out the state
     * of this ATM. Default value is
     * {@link org.greenlaw110.atmsim.NoteDeckFormat#INSTANCE}
     */
    private NoteDeckFormat fmt = NoteDeckFormat.INSTANCE;

    /**
     * Keep track of the sum of all buckets values
     */
    private int totalValue;

    /**
     * Construct an empty ATM without any notes
     */
    public ATM() {
        this(new BigNoteFirst());
    }

    /**
     * Construct an empty ATM with {@link DispenseStrategy} specified
     */
    public ATM(DispenseStrategy strategy) {
        this(C.emptyListOf(Bucket.class), strategy);
    }

    /**
     * Construct an ATM with a list of buckets in which the notes will
     * be transferred to this ATM
     *
     * @param buckets a list of buckets contains notes
     */
    public ATM(List<Bucket> buckets) {
        this(buckets, new BigNoteFirst());
    }

    /**
     * Construct an ATM with a list of buckets in which the notes will
     * be transferred to this ATM and the dispense strategy specified
     *
     * @param buckets   a list of buckets contains notes
     * @param strategy the note dispense strategy
     */
    public ATM(List<Bucket> buckets, DispenseStrategy strategy) {
        init(strategy, buckets);
    }

    /**
     * Set notes dispense strategy to this ATM
     *
     * @param strategy the dispense strategy
     * @return the ATM instance
     */
    public ATM setStrategy(DispenseStrategy strategy) {
        E.NPE(strategy);
        this.strategy = strategy;
        return this;
    }

    /**
     * Set the {@link org.greenlaw110.atmsim.NoteDeckFormat format}
     * @param format
     * @return this ATM instance
     */
    public ATM setFormat(NoteDeckFormat format) {
        E.NPE(format);
        fmt = format;
        return this;
    }

    /**
     * fill this ATM with a list of buckets
     *
     * @param buckets the buckets in which all notes to be
     *                transferred to buckets in this ATM
     */
    private void fill(List<Bucket> buckets) {
        for (Bucket bucket : buckets) {
            totalValue += bucket.value();
            bucketByType(bucket.type()).transferFrom(bucket);
        }
    }

    /**
     * Initialize the bucket instances in this ATM
     *
     * @param algorithm the dispense strategy
     * @param buckets   a list of buckets contains notes to be filled
     *                  into buckets of this ATM
     * @return this ATM instance
     */
    private void init(DispenseStrategy algorithm, List<Bucket> buckets) {
        setStrategy(algorithm);
        for (NoteType type : NoteType.values()) {
            this.buckets.put(type, Bucket.of(type));
        }
        // this will be an readonly immutable list
        bucketList = C.list(this.buckets.values());
        fill(buckets);
    }


    /**
     * Returns a {@link org.greenlaw110.atmsim.Bucket bucket} by
     * {@link org.greenlaw110.atmsim.NoteType specified}
     *
     * @param type the note type
     * @return the bucket corresponding to the note
     */
    private Bucket bucketByType(NoteType type) {
        return buckets.get(type);
    }

    /**
     * Returns a read only view to all buckets of this ATM
     *
     * @return a list of {@link org.greenlaw110.atmsim.BucketView} of all
     * buckets in this ATM
     */
    public List<BucketView> buckets() {
        return bucketList.map(BucketView.F.CONSTRUCTOR);
    }

    // revert the dispense operation from
    // a collection of buckets
    private void revert(Collection<Bucket> buckets) {
        for (Bucket bucket : buckets) {
            totalValue += bucket.value();
            bucketByType(bucket.type()).transferFrom(bucket);
        }
    }

    /**
     * Dispense notes that add up to the value specified. At the end of
     * the operation this method returns a list of Bucket represents the
     * notes been dispensed from this ATM.
     *
     * @param value the money value to be dispensed from the ATM
     * @return a list of buckets contains notes been dispensed from the ATM
     * @throws org.greenlaw110.atmsim.NoteDispenseException if the ATM failed
     *                                                      to dispense the required money value
     * @see DispenseStrategy#comparator(int)
     */
    public List<Bucket> dispense(int value) throws NoteDispenseException {
        E.illegalArgumentIf(value < 0, "oops, can't dispense notes for negative value");
        if (value > totalValue || value % NoteType.GCD_VALUE != 0) {
            throw new NoteDispenseException(value);
        }
        C.List<Bucket> notes = C.newList();
        int originalValue = value;
        try {
            while (value > 0) {
                // sort/filter available buckets for notes dispense
                Comparator<Bucket> cmp = strategy.comparator(value);
                C.List<Bucket> l = bucketList.sort(cmp).filter(F.filter(value));
                if (l.isEmpty()) {
                    throw new NoteDispenseException(originalValue);
                }

                // calculate at most how many notes we need to dispense
                // from the available bucket
                Bucket atmBucket = l.first();
                NoteType noteType = atmBucket.type();
                int noteValue = noteType.value();
                int remainder = value % noteValue;
                int dispenseValue = value - remainder;
                value = remainder;
                int noteCount = dispenseValue / noteValue;

                // check if there are enough notes in our bucket
                int transferCount = Math.min(noteCount, atmBucket.noteCount());
                if (transferCount < noteCount) {
                    dispenseValue = transferCount * noteValue;
                    value += (noteCount - transferCount) * noteValue;
                }

                // prepare the dispense bucket and commit notes transfer
                Bucket bucket = Bucket.of(noteType);
                bucket.transferFrom(atmBucket, transferCount);
                totalValue -= dispenseValue;
                notes.add(bucket);
            }
            return notes;
        } catch (NoteDispenseException e) {
            revert(notes);
            throw e;
        } catch (RuntimeException e) {
            revert(notes);
            throw e;
        }
    }

    /**
     * Returns the total value of all notes in this ATM
     */
    public int value() {
        return bucketList.reduce(0, N.F.adder(Bucket.F.VALUE_OF, Integer.class));
    }

    @Override
    public String toString() {
        return "ATM state\n" + fmt.format(bucketList);
    }

    /**
     * The function object namespace
     */
    private enum F {
        ;

        /**
         * Returns a filter function that test the whether a bucket support
         * dispense notes for the specified value. A bucket is considered to
         * be able to dispense if:
         * <ul>
         * <li>there are notes in the bucket</li>
         * <li>the note type value is lesser than the value to be dispensed</li>
         * </ul>
         *
         * @param value the value (sum of notes) needs to be dispensed
         * @return a predicate to test the bucket
         */
        static final _.Predicate<Bucket> filter(final int value) {
            return new _.Predicate<Bucket>() {
                @Override
                public boolean test(Bucket bucket) {
                    return bucket.noteCount() > 0 && bucket.noteTypeValue() <= value;
                }
            };
        }
    }

}
