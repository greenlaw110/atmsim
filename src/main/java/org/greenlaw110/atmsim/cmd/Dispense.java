package org.greenlaw110.atmsim.cmd;

import jline.console.ConsoleReader;
import org.greenlaw110.atmsim.ATM;
import org.greenlaw110.atmsim.Bucket;
import org.greenlaw110.atmsim.NoteDeckFormat;
import org.greenlaw110.atmsim.NoteDispenseException;

import java.io.IOException;
import java.util.List;

/**
 * Handle "dispense" command. This command accept a positive integer
 * as the value to be dispensed from the ATM. After notes
 * dispensed from ATM successfully, this command will print out the
 * note deck been dispensed and then followed by the ATM state.
 *
 * <p>If ATM failed to dispense the amount request, it simply
 * print out the error message and return</p>
 */
public class Dispense extends CommandBase {

    @Override
    public String help() {
        return "dispense notes from ATM. Usage: \n\t" +
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
            ATM atm = atm();
            List<Bucket> buckets = atm.dispense(value);
            echo(console, "Successfully dispensed from ATM: \n%s", NoteDeckFormat.INSTANCE.format(buckets));
            echo(console, "\n%s", atm);
        } catch (NumberFormatException e) {
            error("please specify value in digits");
        } catch (NoteDispenseException e) {
            error(e.getMessage());
        }
    }
}
