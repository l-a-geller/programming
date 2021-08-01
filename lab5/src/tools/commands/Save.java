package tools.commands;

import tools.csvConverting.HumanBeingsWriter;

public class Save extends AbstractCommand {
    public Save(){
        super("Save", "Сохранить коллекцию в файл");
    }
    @Override
    public void execute(){
        HumanBeingsWriter humanBeingsWriter = new HumanBeingsWriter();
        humanBeingsWriter.writeCSV();
    }
}
