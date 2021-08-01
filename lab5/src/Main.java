import data.HumanBeing;
import data.HumanBeingStorage;
import tools.csvConverting.HumanBeingsLoader;
import tools.commands.AbstractCommand;
import tools.commands.CommandStorage;

import java.util.Scanner;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        Vector<HumanBeing> humanBeings = HumanBeingsLoader.loadHumanBeings();
        HumanBeingStorage.setHumanBeings(humanBeings);
        CommandStorage.getCommand("help").execute();
        System.out.println("\nПожалуйста введите команду:");
        startCommandLoop();
    }

    private static void startCommandLoop() {
        Scanner sc = new Scanner(System.in);
        String currentCommand;
        while (sc.hasNext()){
            String line = sc.nextLine().trim();

            if (!line.equals("")) {
                if (line.toUpperCase().equals("EXIT")) break;
                else {

                    String[] parts = line.split(" ", 2);
                    currentCommand = parts[0];

                    AbstractCommand comm = CommandStorage.getCommand(currentCommand);
                    if (comm == null) {
                        System.out.println("Команда не распознана.");
                        CommandStorage.getCommand("help").execute();
                    }
                    else {
                        CommandStorage.updateHistory(comm);

                        boolean hasArgument = false;
                        String ARG = null;
                        try {
                            ARG = parts[1];
                            hasArgument = true;
                            comm.setSc(sc);
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
                                    comm.execute(sc);
                                }else {
                                    comm.execute();
                                }
                            }catch (ArrayIndexOutOfBoundsException e){
                                System.out.println("Parameter expected for this command.");
                            }
                        }
                    }
                }
            }
        }
    }
}
