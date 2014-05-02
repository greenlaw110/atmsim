package org.greenlaw110.atmsim.cmd;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import jline.console.ConsoleReader;

import java.io.IOException;
import java.util.Arrays;

/**
 * Handle "create_atm" command
 */
public class CreateATM extends CommandBase {

    public static CreateATM INSTANCE = new CreateATM();

    @Parameter(names = {"-20"}, description = "number of twenties notes. Default value is 10")
    private int twentiesNotes = 10;

    @Parameter(names = {"-50"}, description = "number of fifties notes. Default value is 10")
    private int fiftiesNotes = 10;

    private static final String USAGE = "Usage: create_atm [-20 <number of twenties notes>] " +
            "[-50 <number of fifties notes]";


    @Override
    public String help() {
        return "create an ATM with notes number specified. \n\t" + USAGE;
    }

    @Override
    public String name() {
        return "create_atm";
    }

    @Override
    public void handleEvent(ConsoleReader console, String... args) throws IOException {
        try {
            new JCommander(this, args);
        } catch (ParameterException e) {
            error("Invalid arguments: %s\n%s", Arrays.toString(args), USAGE);
        }
        if (twentiesNotes < 0 || fiftiesNotes < 0) {
            error("the notes number must not be negative");
        }
        sh().createATM(twentiesNotes, fiftiesNotes);
        echo(console, "new ATM created:\n%s\n", atm());
    }
}
