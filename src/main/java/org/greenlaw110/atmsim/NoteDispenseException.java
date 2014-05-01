package org.greenlaw110.atmsim;

import org.osgl.util.S;

/**
 * The exception indicate a failure of dispense notes from an ATM
 */
public class NoteDispenseException extends Exception {
    public NoteDispenseException(int value) {
        super(S.fmt("The ATM failed to dispense the value requested: %s", value));
    }
}
