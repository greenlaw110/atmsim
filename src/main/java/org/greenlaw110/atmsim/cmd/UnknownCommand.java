package org.greenlaw110.atmsim.cmd;

import jline.console.ConsoleReader;

import java.io.IOException;

/**
 * handles any unknown command
 */
public class UnknownCommand extends CommandBase {

    private String cmd;

    public UnknownCommand(String cmd) {
        this.cmd = cmd;
    }

    @Override
    public String name() {
        return "unknown";
    }

    @Override
    public void handleEvent(ConsoleReader console, String... args) throws IOException {
        try {
            Integer.parseInt(cmd);
            Dispense.INSTANCE.handleEvent(console, cmd);
            return;
        } catch (NumberFormatException e) {

        }
        echo(console, "Unknown command");
    }
}
