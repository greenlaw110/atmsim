package org.greenlaw110.atmsim.cmd;

import jline.console.ConsoleReader;

import java.io.IOException;

/**
 * Defines a command that handles a specific shell instruction
 */
public interface Command {
    /**
     * Returns name of the command
     */
    String name();

    /**
     * Returns help message for this command
     */
    String help();

    /**
     * Handle shell instruction with optional arguments
     * @param console The object that can be used by the command
     *                event handler to interactive with the console
     * @param args the optional command arguments
     * @throws IOException if there are IO exception with the console interaction
     */
    void handleEvent(ConsoleReader console, String ... args) throws IOException;
}
