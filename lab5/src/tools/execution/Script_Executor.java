package tools.execution;

import tools.commands.AbstractCommand;
import tools.commands.CommandStorage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Script_Executor {
    private File file;
    public Script_Executor(File f){
        this.file = f;
    }
    public void exec(){
        try {
            Scanner scanner = new Scanner(this.file);
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                if (!line.trim().toUpperCase().equals("EXIT")) {

                    String[] parts = line.split(" ", 2);
                    String COMMAND = parts[0];

                    AbstractCommand comm = CommandStorage.getCommand(COMMAND);
                    if(comm != null){
                        CommandStorage.updateHistory(comm);

                        boolean hasArgument = false;
                        String ARG = null;
                        try {
                            ARG = parts[1];
                            hasArgument = true;
                            comm.setSc(scanner);
                        }catch (ArrayIndexOutOfBoundsException e){ }
                        if (hasArgument){
                            try {
                                comm.execute(ARG);
                            }catch (ArrayIndexOutOfBoundsException e){
                                System.out.println("No parameters expected for this command.");
                            }
                        }else {
                            try{
                                if (comm.doesNeedScanner()){
                                    try {
                                        comm.execute(scanner);
                                    }catch (NoSuchElementException e){
                                        System.out.println("Ошибка при исполнении команды, требующей ввода полей с информацией о персонаже. Проверьте правильность ввода полей в скрипте");
                                    }
                                }else {
                                    comm.execute();
                                }
                            }catch (ArrayIndexOutOfBoundsException e){
                                System.out.println("Parameter expected for this command.");
                            }
                        }
                    }
                    else System.out.println("Команда не распознана.");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        }catch (NullPointerException e){
            System.out.println("invalid file");
        }
    }
}
