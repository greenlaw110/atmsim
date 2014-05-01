package org.greenlaw110.atmsim.cmd;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import jline.console.ConsoleReader;

import java.io.IOException;

/**
 * Handle "create_atm" command
 */
public class CreateATM extends CommandBase {

    @Parameter(names = {"-20"}, description = "number of twenties notes. Default value is 10")
    private int twentiesNotes = 10;

    @Parameter(names = {"-50"}, description = "number of fifties notes. Default value is 10")
    private int fiftiesNotes = 10;

    @Override
    public String help() {
        return "create an ATM with notes number specified. \n\t\t" +
                "Usage: create_atm [-20 <number of twenties notes>] " +
                "[-50 <number of fifties notes]";
    }

    @Override
    public String name() {
        return "create_atm";
    }

    @Override
    public void handleEvent(ConsoleReader console, String... args) throws IOException {
        new JCommander(this, args);
        sh().createATM(twentiesNotes, fiftiesNotes);
        echo(console, "new ATM created:\n%s\n", atm());
    }
}
