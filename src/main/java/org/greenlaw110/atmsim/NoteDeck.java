package org.greenlaw110.atmsim;

/**
 * Defines a data structure that represent a deck of a
 * certain {@link org.greenlaw110.atmsim.NoteType type of note}
 */
public interface NoteDeck {
    /**
     * Returns the {@link org.greenlaw110.atmsim.NoteType}
     */
    NoteType type();

    /**
     * Returns the number of notes in the deck
     */
    int noteCount();

    /**
     * Returns the total value of the note deck. The value of value must be
     * equivalent to {@link #noteCount()} times of
     * {@link org.greenlaw110.atmsim.NoteType#value} of the {@link #type()}
     *
     * @return the total value of this note deck
     */
    int value();
}
