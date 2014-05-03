package org.greenlaw110.atmsim.cmd;

import jline.console.ConsoleReader;
import org.osgl._;
import org.osgl.util.S;

import java.io.IOException;

/**
 * Display help information
 */
public class Help extends CommandBase {
    @Override
    public String help() {
        return "Show this help message";
    }

    @Override
    public String name() {
        return "help";
    }

    @Override
    public void handleEvent(ConsoleReader console, String... args) throws IOException {
        if (args.length > 0) {
            String nm = args[0];
            Command cmd = sh().findCommand(nm);
            if (cmd instanceof UnknownCommand) {
                error("Unknown command: %s", nm);
            }
            echo(console, cmd.help());
            return;
        }
        String help = S.join("\n", sh().commands().map(_.F.asString()));
        echo(console, help);
    }
}
