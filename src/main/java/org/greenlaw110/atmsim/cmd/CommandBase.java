package org.greenlaw110.atmsim.cmd;

import jline.console.ConsoleReader;
import org.greenlaw110.atmsim.ATM;
import org.osgl.util.S;

import java.io.IOException;

/**
 * Created by luog on 1/05/2014.
 */
public abstract class CommandBase implements Command {

    protected CommandBase() {
    }

    @Override
    public String help() {
        return null;
    }

    protected final void error(String msg, Object... args) {
        throw new ErrorMsg(msg, args);
    }

    protected final void echo(ConsoleReader console, String msg, Object... args) throws IOException {
        console.print(S.fmt(msg, args));
        console.print("\n");
    }

    protected final Shell sh() {
        return Shell.INSTANCE;
    }

    protected final ATM atm() {
        return sh().atm();
    }

    @Override
    public String toString() {
        String help = help();
        if (S.notEmpty(help)) {
            return S.fmt("%s: %s", name(), help);
        } else {
            return name();
        }
    }

    @Override
    public int hashCode() {
        return name().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Command) {
            Command that = (Command)obj;
            return S.eq(that.name(), name());
        }
        return false;
    }

}
