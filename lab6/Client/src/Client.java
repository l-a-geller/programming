import java.net.*;
import java.io.*;
import java.io.IOException;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Scanner;
import tools.*;
import data.*;
import java.util.ArrayList;

public class Client {
    private static Command currentCommand;
    private static boolean first = true;
    private static ArrayList<LabWork> labs;
    private static Scanner scanner = new Scanner(System.in);
    private static LabWork lab;
    private static boolean inScriptExecution = false;
    private static boolean canGo = true;

    private static int PORT = 9876;

    private static void getPORT() {
        while (true) {
            try {
                System.out.print("Введите порт, на котором будет работать сервер > ");
                String port = scanner.nextLine();
                PORT = Integer.parseInt(port.trim());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Порт должен быть числом");
            }
        }
    }

    public static void act(String FILENAME) throws IOException, InterruptedException, ClassNotFoundException, ConnectException {
        loadCommand(FILENAME);
        getPORT();
        System.out.println("Please enter a command");
        scanner = new Scanner(System.in);
        while (scanner.hasNext()) {

            String commandName = scanner.nextLine().trim().toUpperCase();
            if (!commandName.equals("")){
                if (isCommand(commandName)) {
                    go(commandName);
                } else {
                    System.out.println("Not a command");
                }
            }
            if (!canGo){
                break;
            }
        }
    }

    public static void go(String commandName) throws IOException, InterruptedException, ClassNotFoundException, ConnectException {
        System.out.println("\nStarting...");
        Socket socket = new Socket("localhost", PORT);
        OutputStream os = socket.getOutputStream();
        InputStream is = socket.getInputStream();
        ObjectOutputStream ous = new ObjectOutputStream(os);
        if (first){
            first = false;
            ous.writeObject(labs);
            ous.flush();
        }
        ous.writeObject(currentCommand);
        ous.flush();

        if (currentCommand.needsScanner){
            ous.writeObject(lab);
            ous.flush();
        }

        SocketAddress a = new InetSocketAddress("localhost", PORT);
        SocketChannel sc = SocketChannel.open(a);
        Answer answer = new Answer(sc);
        answer.start();
        answer.join();

        if (currentCommand.getName().toUpperCase().equals("QUIT")){
            canGo = false;
        }
    }


    public static boolean isCommand(String line) throws IOException, InterruptedException, ClassNotFoundException, ConnectException{

        String[] parts = line.split(" ", 2);
        String COMMAND = parts[0];
        Command command = CommandInvoker.getCommand(COMMAND);

        if (command == null){
            return false;
        }
        if (command.needsScanner){
            Loader loader = new Loader();
            lab = loader.search(scanner);
        }

        try{
            String DATA = parts[1];
            command.data = DATA;
        }catch (ArrayIndexOutOfBoundsException e){
            if (command.hasData){
                System.out.println("Command must have an ARGUMENT");
                return false;
            }
            command.hasData = false;
        }

        if (command.needsExecutor){

            if (inScriptExecution){
                return false;
            }
            inScriptExecution = true;
            System.out.println("Script executing started");
            System.out.println(command.data);
            File file = new File(command.data.toLowerCase());
            if (file.exists()){
                try {
                    Scanner scanner = new Scanner(file);
                    while (scanner.hasNext()) {

                        String l = scanner.nextLine();
                        if (!l.trim().toUpperCase().equals("EXIT")) {
                            if (isCommand(l)) {
                                go(l);
                            }
                        }
                        if (!canGo){
                            break;
                        }
                    }
                }catch (StackOverflowError e){ System.out.println("Invalid script. Please remove self calls."); }
            }else { System.out.println("No such file"); }
            return false;
        }
        currentCommand = command;
        return true;
    }

    public static void loadCommand(String FILENAME) {

        File file = new File(FILENAME);
        XMLScanner xsc = new XMLScanner();
        try{
            xsc.scan(file);
        }catch (IOException e){
            System.out.println("File not found");
        }
        labs = LabworksStorage.getSortedByCoordinates();

    }
}
