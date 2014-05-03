package org.greenlaw110.atmsim;

import org.osgl._;
import org.osgl.exception.NotAppliedException;
import org.osgl.util.E;

/**
 * A readonly view of a bucket
 */
public class BucketView implements NoteDeck {

    private Bucket bucket;

    /**
     * Construct a bucket view of a bucket
     *
     * @param bucket the bucket that back this view
     */
    public BucketView(Bucket bucket) {
        E.NPE(bucket);
        this.bucket = bucket;
    }

    /**
     * Returns the type of notes that could be kept in the
     * bucket backed this view
     *
     * @see org.greenlaw110.atmsim.Bucket#type()
     */
    public NoteType type() {
        return bucket.type();
    }

    /**
     * Returns the number of notes been kept in the
     * bucket backed this view
     *
     * @see org.greenlaw110.atmsim.Bucket#noteCount()
     */
    public int noteCount() {
        return bucket.noteCount();
    }

    /**
     * Returns the value of the bucket backed this view
     *
     * @see org.greenlaw110.atmsim.Bucket#value()
     */
    public int value() {
        return bucket.value();
    }

    @Override
    public String toString() {
        return NoteDeckFormat.INSTANCE.format(this);
    }

    public static BucketView of(Bucket bucket) {
        return new BucketView(bucket);
    }

    /**
     * The function object namespace
     */
    public static enum F {
        ;
        public static final _.F1<Bucket, BucketView> CONSTRUCTOR = new _.F1<Bucket, BucketView>() {
            @Override
            public BucketView apply(Bucket bucket) throws NotAppliedException, _.Break {
                return new BucketView(bucket);
            }
        };
    }
}
