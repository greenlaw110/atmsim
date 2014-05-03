package org.greenlaw110.atmsim.cmd;

import jline.console.ConsoleReader;
import org.greenlaw110.atmsim.DispenseStrategy;
import org.greenlaw110.atmsim.dispense.BalancedNoteCount;
import org.greenlaw110.atmsim.dispense.BalancedValue;
import org.greenlaw110.atmsim.dispense.BigNoteFirst;
import org.osgl.util.C;

import java.io.IOException;

/**
 * Handle "strategy" command. This command accept a string parameter
 * indicate the new dispense strategy to be set to the atm
 * <p/>
 * <p>If strategy specified not found, then it simply print out the
 * error message and return</p>
 * <p/>
 * <p>If no strategy specified then it print out the current
 * strategy and return</p>
 */
public class Strategy extends CommandBase {

    private static C.List<DispenseStrategy> strategies = C.list(BigNoteFirst.INSTANCE, BalancedNoteCount.INSTANCE, BalancedValue.INSTANCE);

    @Override
    public String help() {
        return "set/view atm dispense strategy. Usage: \n\t" +
                "strategy: show current strategy\n\t" +
                "strategy <strategy>: set strategy. Available strategies:" +
                "\n\t\t1 - " + BigNoteFirst.INSTANCE +
                "\n\t\t2 - " + BalancedNoteCount.INSTANCE +
                "\n\t\t3 - " + BalancedValue.INSTANCE;
    }

    @Override
    public String name() {
        return "strategy";
    }

    @Override
    public void handleEvent(ConsoleReader console, String... args) throws IOException {
        if (args.length < 1) {
            echo(console, "%s", atm().getStrategy());
            return;
        }
        try {
            int value = Integer.parseInt(args[0]);

            switch (value) {
                case 1:
                    atm().setStrategy(BigNoteFirst.INSTANCE);
                    break;
                case 2:
                    atm().setStrategy(BalancedNoteCount.INSTANCE);
                    break;
                case 3:
                    atm().setStrategy(BalancedValue.INSTANCE);
                    break;
                default:
                    error("please choose from 1, 2 and 3.");
            }

            echo(console, "Strategy is now set to %s", atm().getStrategy());
        } catch (NumberFormatException e) {
            error("please choose from 1, 2 and 3.");
        }
    }
}
