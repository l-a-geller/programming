package tools.commands;

import tools.io.QuitException;
import tools.io.StreamReadWriter;
import tools.io.Transport;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class CommandInvoker {
    private static HashMap<String, Command> commands;
    private StreamReadWriter ioServer;
    private StreamReadWriter io;
    private Scanner sc;
    private String login;
    private String password;
    private static boolean logged = true;
    private ArrayList<String> ansverArray = new ArrayList<>();
    private List<Command> lastCommands = new ArrayList<Command>();
    private String labString;
    private String labData;

    public CommandInvoker(HashMap<String, Command> commandList, StreamReadWriter ioServer) {
        this.commands = commandList;
        this.ioServer = ioServer;
    }

    public ArrayList<String> getAnsverArray(){
        return ansverArray;
    }

    public void setData(String login, String password){
        this.login = login;
        this.password = password;
    }
    public void setComData(String data){
        this.labData = data;
    }
    public static boolean getLogged(){
        return logged;
    }

    public static Command getCommand(String name){
        Optional<Command> command = Optional.ofNullable(commands.get(name.toUpperCase().trim()));
        return command.orElse(null);
    }

    public void loadToSavedCommands(Command comm){
        if (lastCommands.size() >= 7){ lastCommands = lastCommands.subList(1, 7); }
        lastCommands.add(comm);
    }

    public String printSavedCommands(){
        String res = "Last commands:\n";
        for (Command comm: lastCommands){
            res += comm.getName() + "\n";
        }
        return res;
    }

    public boolean run(String str, StreamReadWriter ioi, Scanner scanner) throws QuitException, IOException {
        io = ioi;
        sc = scanner;

        try {
            if (!str.trim().equals("")) {

                String[] s = str.trim().toUpperCase().split(" ");
                Command command = commands.get(s[0]);

                if (isCommand(str.trim().toUpperCase(), scanner)) {
                    if (!command.getName().trim().toUpperCase().equals("SHOW")){
                        loadToSavedCommands(command);
                    }
                    command.addUserInfo(login, password);                  // putting userParameters into Command object

                    Transport trans = new Transport(command);

                    this.ioServer.writeObj(trans);                        // writing to server
                    long start = System.currentTimeMillis();

                    while (!this.ioServer.ready()) {
                        long finish = System.currentTimeMillis();
                        if (finish - start > 2000L) {
                            throw new QuitException();
                        }
                    }

                    boolean i = true;
                    ansverArray.clear();
                    while (this.ioServer.ready()) {
                        if (i){
                            i = false;
                            io.writeln(" >>> Answer from server >>> ");
                        }

                        String answer = this.ioServer.readLine();
                        if (answer.equals("NOT_LOGGED")){
                            return false;
                        }
                        if (answer.equals("NOT_REG")){
                            return false;
                        }
                        ansverArray.add(answer);

                        io.writeln(answer);              //reading from server
                    }
                }
            }
            return true;
            } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("Unknown Command");
            return true;
        } catch (IllegalArgumentException e) {
            io.writeln("Скрипт составлен неверно");
            return true;
        }
    }

    public void setLabString(String labString){
        this.labString = labString;
    }

    public boolean isCommand(String line, Scanner sc)  {
        String[] parts = line.split(" ", 2);
        String COMMAND = parts[0];
        Command command = CommandInvoker.getCommand(COMMAND);
        if (command == null){
            return false;
        }

        if (command.needsScanner){
            command.lab = labString;
        }

        try {
            if (command.hasData){
                if (command.getName().trim().toUpperCase().equals("REMOVE_BY_ID")) {
                    command.data = labData;
                }else if (command.getName().trim().toUpperCase().equals("UPDATE")){
                    command.data = labData;
                }else {
                    command.data = parts[1];
                }
            }
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Command must have an argument");
            return false;
        }


        if (command.needsExecutor){
            try {

                System.out.println("Script executing started");
                command.data = parts[1];
                System.out.println(command.data);
                File file = new File(command.data.toLowerCase());
                if (file.exists()){
                    try {
                        Scanner scanner1 = new Scanner(file);
                        System.out.println("scanning started");
                        while (scanner1.hasNext()) {

                            String l = scanner1.nextLine();
                            if (!(l.trim().toUpperCase().equals("EXIT") | l.trim().toUpperCase().equals("QUIT") )) {
                                run(l, io, scanner1);
                                }
                            }
                        } catch (QuitException ex) {
                        ex.printStackTrace();
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                catch (StackOverflowError e){ System.out.println("Invalid script. Please remove self calls."); }
                }else { System.out.println("No such file"); }
                return false;
            }catch (ArrayIndexOutOfBoundsException e){
                System.out.println("Command must have an argument");
                return false;
            }
        }
        return true;
    }
}
