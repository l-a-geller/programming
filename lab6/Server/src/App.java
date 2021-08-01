import data.LabWork;
import data.LabworksStorage;
import tools.Command;
import tools.CommandInvoker;
import tools.Java2XML;

import java.io.*;
import java.net.*;
import java.nio.channels.ServerSocketChannel;
import java.util.ArrayList;
import java.util.Scanner;

public class App {

    private static final Scanner scanner = new Scanner(System.in);
    private static int PORT = 9876;

    private static ServerSocket serverSocket;
    private static ServerSocketChannel serverSocketChannel;

    private static Socket socket;
    private static boolean first = true;
    private static Command command;

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

    private static void init() {
        while (true) {
            try {
                getPORT();
                serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.configureBlocking(false);
                serverSocket = serverSocketChannel.socket();
                InetSocketAddress inetSocketAddress = new InetSocketAddress(PORT);
                serverSocket.bind(inetSocketAddress);
                serverSocketChannel.configureBlocking(true);
                System.out.println("Сервер успешно запущен");
                break;
            } catch (IOException e) {
                System.out.println("ADDRESS & PORT already in use");
            }
        }
    }

    private static void startCommandLoop() throws IOException, ClassNotFoundException, InterruptedException {
        while (true) {

            socket = serverSocket.accept();
            InputStream is = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            if (first){
                first = false;
                ArrayList<LabWork> arr = (ArrayList<LabWork>) ois.readObject();
                for (LabWork labWork: arr){
                    LabworksStorage.put(labWork);
                }
                System.out.println("\n");
                System.out.println(LabworksStorage.getData());

            }
            try{
                command = (Command) ois.readObject();
            }catch (ClassCastException er){
                command = (Command) ois.readObject();
            }

            if (command.getName().toUpperCase().equals("QUIT") || command.getName().toUpperCase().equals("SAVE")){
                saveData();
            }
            if (command.needsScanner){
                LabWork labWork = (LabWork) ois.readObject();
                command.res += LabworksStorage.put(labWork);
            }
            CommandInvoker.loadToSavedCommands(command);
            if (command.hasData){
                command.execute(command.data);
            }else {
                command.execute();
            }

            serverSocketChannel.configureBlocking(true);
            Answer answer = new Answer(serverSocketChannel, command.getAnswer());
            answer.start();
            answer.join();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        init();
        startCommandLoop();
    }

    private static void saveData(){
        Java2XML j2 = new Java2XML();
        j2.writeXML();
    }
}
