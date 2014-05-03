package org.greenlaw110.atmsim.cmd;

import jline.console.ConsoleReader;
import jline.console.completer.StringsCompleter;
import org.greenlaw110.atmsim.ATM;
import org.greenlaw110.atmsim.Bucket;
import org.greenlaw110.atmsim.NoteType;
import org.osgl._;
import org.osgl.exception.NotAppliedException;
import org.osgl.util.C;
import org.osgl.util.S;

import java.util.List;

/**
 * A command line user interface to play with the ATM simulator
 */
public class Shell {

    /**
     * The Singleton shell instance
     */
    public static final Shell INSTANCE = new Shell();

    private ATM atm;

    // keep track of all supported commands
    private C.List<Command> commands = C.newList();

    private Shell() {
        register(new Help(), new Exit(), AtmCommand.INSTANCE, new Dispense(), new Strategy());
    }

    ATM atm() {
        return atm;
    }

    /**
     * Create an new ATM with specified number of twenties and fifties 
     * notes. The old ATM instance is disposed if there is after
     * running this method
     * 
     * @param twentiesNotes the number of twenties notes in the ATM
     * @param fiftiesNotes the number of fifties notes in the ATM
     */
    void createATM(int twentiesNotes, int fiftiesNotes) {
        Bucket tw = Bucket.of(NoteType.twentieth, twentiesNotes);
        Bucket fi = Bucket.of(NoteType.fiftieth, fiftiesNotes);
        atm = new ATM(C.list(tw, fi));
    }

    /**
     * Register a command to the shell
     * @param cmds the commands to be registered
     */
    private void register(Command... cmds) {
        C.listOf(cmds).forEach(C.F.addTo(commands));
    }

    /**
     * Returns an read only view to the command registry
     */
    C.List<Command> commands() {
        return C.list(commands);
    }

    Command findCommand(String name) {
        return commands.findFirst(F.CMD_NAME.andThen(_.F.eq().curry(name))).orElse(new UnknownCommand(name));
    }

    private List<String> completeWords() {
        return commands.map(F.CMD_NAME);
    }

    private void run() throws Exception {

        System.setProperty("jline.shutdownhook", "true");
        ConsoleReader console = new ConsoleReader();
        console.setPrompt("atm>");
        console.addCompleter(new StringsCompleter(completeWords()));

        if (null == atm()) {
            console.print("no atm found. prepare to create default atm:");
            AtmCommand.INSTANCE.createATM(console, 100, 40);
        }

        String line;
        while ((line = console.readLine()) != null) {
            try {
                if (S.empty(line)) {
                    console.print("");
                    continue;
                }
                String[] sa = line.split("[\\s]+");
                String cmd = sa[0];
                Command c = findCommand(cmd);
                if (sa.length > 1) {
                    String[] opts = new String[sa.length - 1];
                    System.arraycopy(sa, 1, opts, 0, sa.length - 1);
                    c.handleEvent(console, opts);
                } else {
                    c.handleEvent(console);
                }
                console.print("\n");
            } catch (ExitSignal exit) {
                return;
            } catch (ErrorMsg errorMsg) {
                console.print(errorMsg.getMessage());
                console.print("\n\n");
            }
        }
    }

    /**
     * The function object namespace
     */
    public static enum F {
        ;
        public static _.F1<Command,String> CMD_NAME = new _.F1<Command, String>() {
            @Override
            public String apply(Command command) throws NotAppliedException, _.Break {
                return command.name();
            }
        };

    }

    public static void main(String[] args) throws Exception {
        new Shell().run();
    }
}
