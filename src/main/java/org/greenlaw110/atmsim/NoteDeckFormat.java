package org.greenlaw110.atmsim;

import org.osgl._;
import org.osgl.exception.NotAppliedException;
import org.osgl.util.C;
import org.osgl.util.S;

import java.util.Collection;

/**
 * Used to print a {@link org.greenlaw110.atmsim.NoteDeck}
 */
public class NoteDeckFormat {

    /**
     * The default implementation
     */
    public static final NoteDeckFormat INSTANCE = new NoteDeckFormat();

    /**
     * Format a deck of notes of specific type
     */
    public String format(NoteDeck notes) {
        return S.fmt("%sth X %s = %s", notes.type().value(), notes.noteCount(), notes.value());
    }

    /**
     * Format a collection of {@link org.greenlaw110.atmsim.NoteDeck}
     */
    public String format(Collection<? extends NoteDeck> notes) {
        return S.join("\n", C.list(notes).map(F.FORMAT));
    }

    /**
     * The function object namespace
     */
    public static enum F {
        ;
        public static _.F1<NoteDeck, String> FORMAT = new _.F1<NoteDeck, String>() {
            @Override
            public String apply(NoteDeck noteDeck) throws NotAppliedException, _.Break {
                return INSTANCE.format(noteDeck);
            }
        };

        /**
         * In case there are other {@code NoteDeckFormat format implementation}
         */
        public static _.F1<NoteDeck, String> format(final NoteDeckFormat fmt) {
            return new _.F1<NoteDeck, String>() {
                @Override
                public String apply(NoteDeck noteDeck) throws NotAppliedException, _.Break {
                    return fmt.format(noteDeck);
                }
            };
        }
    }
}
