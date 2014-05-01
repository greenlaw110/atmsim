package org.greenlaw110.atmsim.cmd;

import jline.console.ConsoleReader;
import org.greenlaw110.atmsim.Bucket;
import org.greenlaw110.atmsim.NoteDeckFormat;
import org.greenlaw110.atmsim.NoteDispenseException;

import java.io.IOException;
import java.util.List;

/**
 * Handle "dispense" command
 */
public class Dispense extends CommandBase {

    @Override
    public String help() {
        return "dispense notes from ATM. Usage:\n\t\t" +
                "dispense <value>";
    }

    @Override
    public String name() {
        return "dispense";
    }

    @Override
    public void handleEvent(ConsoleReader console, String... args) throws IOException {
        if (args.length < 1) {
            error("dispense command needs a value argument");
        }
        try {
            int value = Integer.parseInt(args[0]);
            if (value < 0) {
                error("please specify non-negative number for value");
            }
            List<Bucket> buckets = atm().dispense(value);
            echo(console, "Successfully dispensed from ATM: \n%s", NoteDeckFormat.INSTANCE.format(buckets));
            echo(console, "\n%s", atm());
        } catch (NumberFormatException e) {
            error("please specify value in digits");
        } catch (NoteDispenseException e) {
            error(e.getMessage());
        }
    }
}
