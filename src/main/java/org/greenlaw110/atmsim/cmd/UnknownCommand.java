package org.greenlaw110.atmsim.cmd;

import jline.console.ConsoleReader;

import java.io.IOException;

/**
 * handles any unknown command
 */
public class UnknownCommand extends CommandBase {

    static final UnknownCommand INSTANCE = new UnknownCommand();

    @Override
    public String name() {
        return "unknown";
    }

    @Override
    public void handleEvent(ConsoleReader console, String... args) throws IOException {
        echo(console, "Unknown command");
    }
}
