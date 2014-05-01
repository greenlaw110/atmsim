package org.greenlaw110.atmsim.cmd;


import org.osgl.exception.FastRuntimeException;

/**
 * Used to pass through exit signal to {@link Shell}
 * from within a
 * {@link org.greenlaw110.atmsim.cmd.Command#handleEvent(jline.console.ConsoleReader, String...) event handler}
 */
public class ExitSignal extends FastRuntimeException {

}
