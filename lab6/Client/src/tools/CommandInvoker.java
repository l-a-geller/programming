package tools;

import tools.commands.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class CommandInvoker{
    private static HashMap<String, Command> commands;
    private static List<Command> lastCommands = new ArrayList<>();

    static {
        commands = new HashMap<>();
        commands.put("INFO", new Info());
        commands.put("HELP", new Help());
        commands.put("SHOW", new Show());
        commands.put("CLEAR", new Clear());
        commands.put("HISTORY", new History());
        commands.put("UPDATE", new Update());
        commands.put("REMOVE_BY_ID", new Remove_by_id());
        //commands.put("SAVE", new Save());
        commands.put("ADD", new Add());
        commands.put("REMOVE_LOWER", new Remove_lower());
        commands.put("REMOVE_ANY_BY_MINIMAL_POINT", new Remove_Any_By_Minimal_Point());
        commands.put("MIN_BY_COORDINATES", new Min_By_Coordinates());
        commands.put("FILTER_BY_DISCIPLINE", new Filter_By_Discipline());
        commands.put("EXECUTE_SCRIPT", new Execute_Script());
        commands.put("QUIT", new Quit());
    }

    public static Command getCommand(String name){
        Optional<Command> command = Optional.ofNullable(commands.get(name.toUpperCase()));
        return command.orElse(null);
    }

    public static HashMap<String, Command> getCommands(){
        return commands;
    }

    public static void loadToSavedCommands(Command comm){
        if (lastCommands.size() >= 7){ lastCommands = lastCommands.subList(0, 6); }
        lastCommands.add(comm);
    }

    public static String printSavedCommands(){
        String res = "Last commands:\n";
        for (Command comm: lastCommands){
            res += comm.getName() + "\n";
        }
        return res;
    }
}
