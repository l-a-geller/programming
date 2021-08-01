package tools.commands;

import tools.execution.Script_Executor;

import java.io.File;

public class Execute_Script extends AbstractCommand {
    Execute_Script(){
        super("Execute_Script", "Считать и исполнить скрипт из указанного файла. " +
                "В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме");
    }
    @Override
    public void execute(String data){
        File file = new File(data);
        if (file.exists()){
            Script_Executor se = new Script_Executor(file);
            try {
                se.exec();
            }catch (StackOverflowError e){ System.out.println("Файл-скрипт составлен неверно. Уберите self-calls"); }
        }else { System.out.println("Файл-скрипт с именем " + data + " не обнаружен"); }
    }
}
