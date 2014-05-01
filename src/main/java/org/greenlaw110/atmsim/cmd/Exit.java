package org.greenlaw110.atmsim.cmd;

import jline.console.ConsoleReader;

import java.io.IOException;

/**
 * This command handle "exit" directive and exit
 * the program
 */
public class Exit extends CommandBase {
    @Override
    public String name() {
        return "exit";
    }

    @Override
    public String help() {
        return "exit this atm simulator program.";
    }

    @Override
    public void handleEvent(ConsoleReader console, String... args) throws IOException {
        throw new ExitSignal();
    }
}
