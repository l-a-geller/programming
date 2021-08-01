package tools.commands;

import tools.commands.commands.*;
import tools.commands.commands.Execute_Script;
import tools.commands.commands.Add;
import tools.commands.commands.Info;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class CommandInvoker{
    private static HashMap<String, AbstractCommand> commands;
    private static List<AbstractCommand> lastCommands = new ArrayList<>();

    static {
        commands = new HashMap<>();
        commands.put("INFO", new Info());
        commands.put("HELP", new Help());
        commands.put("SHOW", new Show());
        commands.put("CLEAR", new Clear());
        commands.put("HISTORY", new History());
        commands.put("UPDATE", new Update());
        commands.put("REMOVE_BY_ID", new Remove_by_id());
        //Command save = commands.put("SAVE", new Save());
        commands.put("ADD", new Add());
        commands.put("REMOVE_LOWER", new Remove_lower());
        commands.put("REMOVE_ANY_BY_MINIMAL_POINT", new Remove_Any_By_Minimal_Point());
        commands.put("MIN_BY_COORDINATES", new Min_By_Coordinates());
        commands.put("FILTER_BY_DISCIPLINE", new Filter_By_Discipline());
        commands.put("EXECUTE_SCRIPT", new Execute_Script());
        commands.put("QUIT", new Quit());

        commands.put("LOGIN", new Login());
        commands.put("REGISTER", new Register());
    }

    public static void registerCommand(String commmandName, Command c){
        commands.put(commmandName, c);
    }

    public static AbstractCommand getCommand(String name){
        Optional<AbstractCommand> command = Optional.ofNullable(commands.get(name.toUpperCase()));
        return command.orElse(null);
    }

    public static HashMap<String, AbstractCommand> getCommands(){
        return commands;
    }

    public static void loadToSavedCommands(AbstractCommand comm){
        if (lastCommands.size() >= 7){ lastCommands = lastCommands.subList(0, 6); }
        lastCommands.add(comm);
    }

    public static String printSavedCommands(){
        String res = "Last commands:\n";
        for (AbstractCommand comm: lastCommands){
            res += comm.getName() + "\n";
        }
        return res;
    }
}
