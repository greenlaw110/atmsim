package org.greenlaw110.atmsim.cmd;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import jline.console.ConsoleReader;

import java.io.IOException;
import java.util.Arrays;

/**
 * Handles "atm" command. When running the "atm" command,
 * user can optionally specify the following parameters:
 * <ul>
 * <li>-20 [number of twenties notes]</li>
 * <li>-50 [number of fifties notes]</li>
 * </ul>
 *
 * Once user has specified the parameters, it means to
 * create an new ATM initialized with the number of twenties
 * and fifties notes
 */
public class AtmCommand extends CommandBase {

    public static AtmCommand INSTANCE = new AtmCommand();

    @Parameter(names = {"-20"}, description = "number of twenties notes", required = false)
    private int twentiesNotes;

    @Parameter(names = {"-50"}, description = "number of fifties notes", required = false)
    private int fiftiesNotes;

    private static final String USAGE = "atm [-20 <number of twenties notes>] " +
            "[-50 <number of fifties notes]: create ATM with notes number specified\n\t" +
            "atm: view ATM state";

    @Override
    public String help() {
        return "create an ATM or view state of current ATM. Usage: \n\t" + USAGE;
    }

    @Override
    public String name() {
        return "atm";
    }

    void createATM(ConsoleReader console, int twenties, int fifties) throws IOException {
        sh().createATM(twenties, fifties);
        echo(console, "new ATM created:\n%s\n", atm());
    }

    @Override
    public void handleEvent(ConsoleReader console, String... args) throws IOException {
        if (args.length == 0) {
            echo(console, sh().atm().toString());
            return;
        }
        try {
            new JCommander(this, args);
        } catch (ParameterException e) {
            error("Invalid arguments: %s\n%s", Arrays.toString(args), USAGE);
        }
        if (twentiesNotes < 0 || fiftiesNotes < 0) {
            error("the notes number must not be negative");
        }
        createATM(console, twentiesNotes, fiftiesNotes);
    }
}
