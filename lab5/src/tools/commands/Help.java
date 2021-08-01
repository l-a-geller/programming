package tools.commands;

public class Help extends AbstractCommand {
    public Help(){
        super("Help","Вывести справку по доступным командам");
    }
    @Override
    public void execute(){
        System.out.println("Список доступных команд:");
        for (AbstractCommand comm : CommandStorage.getCommands().values()){
            System.out.println(comm.getName() + ": " + comm.getDescription());
        }
    }
}
