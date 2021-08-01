package tools.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class CommandStorage {
    private static HashMap<String, AbstractCommand> commands;
    private static List<AbstractCommand> lastCommands = new ArrayList<>();

    static {
        commands = new HashMap<>();
        AbstractCommand info = commands.put("INFO", new Info());
        AbstractCommand help = commands.put("HELP", new Help());
        AbstractCommand show = commands.put("SHOW", new Show());
        AbstractCommand clear = commands.put("CLEAR", new Clear());
        AbstractCommand history = commands.put("HISTORY", new History());
        AbstractCommand update = commands.put("UPDATE", new Update());
        AbstractCommand remove_by_id = commands.put("REMOVE_BY_ID", new Remove_by_id());
        AbstractCommand save = commands.put("SAVE", new Save());
        AbstractCommand add = commands.put("ADD", new Add());
        AbstractCommand sort = commands.put("SORT", new Sort());
        AbstractCommand remove_greater = commands.put("REMOVE_GREATER", new Remove_greater());
        AbstractCommand insert_at_index = commands.put("INSERT_AT_INDEX", new Insert_at_index());
        AbstractCommand remove_any_by_impact_speed = commands.put("REMOVE_ANY_BY_IMPACT_SPEED", new Remove_any_by_impact_speed());
        AbstractCommand max_by_weapon_type = commands.put("MAX_BY_WEAPON_TYPE", new Max_by_weapon_type());
        AbstractCommand filter_less_than_weapon_type  = commands.put("FILTER_LESS_THAN_WEAPON_TYPE", new Filter_less_than_weapon_type());
        AbstractCommand execute_script = commands.put("EXECUTE_SCRIPT", new Execute_Script());
    }

    public static AbstractCommand getCommand(String name){
        Optional<AbstractCommand> command = Optional.ofNullable(commands.get(name.toUpperCase()));
        return command.orElse(null);
    }

    public static HashMap<String, AbstractCommand> getCommands(){
        return commands;
    }

    public static void updateHistory (AbstractCommand comm) {
        if (lastCommands.size() >= 7) lastCommands = lastCommands.subList(0, 6);
        lastCommands.add(comm);
    }

    public static void printSavedCommands(){
        System.out.println("Последние 7 команд:");
        for (AbstractCommand comm: lastCommands){
            System.out.println(comm.getName());
        }
    }
}
