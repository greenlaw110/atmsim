package org.greenlaw110.atmsim;

import org.osgl._;

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

    public static enum F {
        ;
        public static final _.F2<NoteDeck, NoteDeck, Boolean> IS_SAME = new _.F2<NoteDeck, NoteDeck, Boolean>() {
            @Override
            public Boolean apply(NoteDeck deck1, NoteDeck deck2) {
                return deck2.noteCount() == deck2.noteCount() && deck1.type() == deck2.type();
            }
        };
        public static <T extends NoteDeck> _.Predicate<T> isSame(final NoteDeck deck) {
            return new _.Predicate<T>() {
                @Override
                public boolean test(T t) {
                    return t.type() == deck.type() && t.noteCount() == deck.noteCount();
                }
            };
        }

    }
}
