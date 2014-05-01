package org.greenlaw110.atmsim.cmd;

import jline.console.ConsoleReader;

import java.io.IOException;

/**
 * Handle "atm_state" command
 */
public class ATMState extends CommandBase {

    @Override
    public String help() {
        return "report atm state";
    }

    @Override
    public String name() {
        return "atm_state";
    }

    @Override
    public void handleEvent(ConsoleReader console, String... args) throws IOException {
        echo(console, sh().atm().toString());
    }
}
