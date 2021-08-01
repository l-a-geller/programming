package tools.commands;

public class History extends AbstractCommand {
    public History(){
        super("History", "Вывести последние 7 команд (без их аргументов)");
    }
    @Override
    public void execute(){
        CommandStorage.printSavedCommands();
    }
}
