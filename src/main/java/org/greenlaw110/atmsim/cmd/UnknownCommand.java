package org.greenlaw110.atmsim.cmd;

import jline.console.ConsoleReader;

import java.io.IOException;

/**
 * Created by luog on 2/05/2014.
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
