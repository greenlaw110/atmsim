package org.greenlaw110.atmsim;

import org.osgl._;
import org.osgl.exception.NotAppliedException;
import org.osgl.util.E;

/**
 * Bucket can keep certain number of notes of a specific {@link NoteType}
 */
public class Bucket implements NoteDeck {

    /**
     * Specify the type of note that could be
     * kept in this bucket
     */
    private final NoteType type;

    /**
     * The number of notes in this bucket
     */
    private int noteCount;

    /**
     * Construct a bucket with type and noteCount specified
     * @param type the {@link org.greenlaw110.atmsim.NoteType}
     * @param noteCount the initial number of notes to be put into this bucket
     */
    Bucket(NoteType type, int noteCount) {
        E.NPE(type);
        E.illegalArgumentIf(noteCount < 0, "note count in bucket cannot be negative number");
        this.type = type;
        this.noteCount = noteCount;
    }

    /**
     * Construct a bucket instance with an existing bucket
     *
     * <p>After constructed, the {@link #type} and {@link #noteCount}
     * should be exactly the same with those of the bucket
     * supplied</p>
     */
    Bucket(Bucket bucket) {
        this(bucket.type, bucket.noteCount);
        bucket.noteCount = 0;
    }

    /**
     * Transfer all notes from bucket specified to this bucket.
     * The note type of specified bucket must match the type
     * of this bucket
     *
     * @param bucket the bucket in which notes will be
     *        transfer to this bucket
     * @return this bucket instance
     * @throws java.lang.IllegalArgumentException if type of
     *         the bucket supplied doesn't match the type of
     *         this bucket
     */
    public Bucket transferFrom(Bucket bucket) {
        E.illegalArgumentIf((bucket.type != type), "Bucket type doesn't match");
        noteCount += bucket.noteCount;
        bucket.noteCount = 0;
        return this;
    }

    /**
     * Transfer certain noteCount of notes from bucket specified to this bucket
     *
     * @param bucket the bucket in which notes will be transfer to this bucket
     * @param noteCount the number of notes to be transferred
     * @return this bucket instance
     * @throws java.lang.IllegalArgumentException if type of
     *         the bucket supplied doesn't match the type of
     *         this bucket
     */
    public Bucket transferFrom(Bucket bucket, int noteCount) {
        E.illegalArgumentIf((bucket.type != type), "Bucket type doesn't match");
        E.illegalArgumentIf(noteCount < 0, "notes number cannot be negative");
        E.illegalArgumentIf(noteCount > bucket.noteCount, "notes number cannot exceed the supplying bucket");
        this.noteCount += noteCount;
        bucket.noteCount -= noteCount;
        return this;
    }

    /**
     * Returns the type of this bucket
     */
    public NoteType type() {
        return type;
    }

    /**
     * Returns the total value in this bucket
     */
    public int value() {
        return type.value() * noteCount;
    }

    /**
     * Returns the number of notes in this bucket
     */
    public int noteCount() {
        return noteCount;
    }

    /**
     * Returns the value of the {@link #type() note type} of this bucket
     */
    public int noteTypeValue() {
        return type().value();
    }

    public static Bucket of(NoteType type) {
        return new Bucket(type, 0);
    }

    public static Bucket of(NoteType type, int noteCount) {
        return new Bucket(type, noteCount);
    }

    /**
     * The function object namespace
     */
    public static enum F {
        ;
        public static _.F1<Bucket, Integer> VALUE_OF = new _.F1<Bucket, Integer>() {
            @Override
            public Integer apply(Bucket bucket) throws NotAppliedException, _.Break {
                return bucket.value();
            }
        };

    }
}
