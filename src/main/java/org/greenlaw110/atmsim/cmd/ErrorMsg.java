package org.greenlaw110.atmsim.cmd;

import org.osgl.exception.FastRuntimeException;
import org.osgl.util.S;

/**
 * Used to pass an error message to {@link Shell}
 * from within a
 * {@link org.greenlaw110.atmsim.cmd.Command#handleEvent(jline.console.ConsoleReader, String...) event handler}
 */
public class ErrorMsg extends FastRuntimeException {
    public ErrorMsg(String desc, Object... args) {
        super(S.fmt(desc, args));
    }
}
