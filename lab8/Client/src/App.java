import front.LogRegManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tools.commands.Command;
import tools.commands.CommandInvoker;
import tools.connector.ServerConnector;
import tools.io.StreamReadWriter;
import tools.io.Transport;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Scanner;

public class App extends Application {
    private static CommandInvoker commandInvoker;
    private static StreamReadWriter ioClient;
    private static ServerConnector serverConnector;
    private static String HOST;
    private static int PORT = 2222;
    private static Scanner scanner = new Scanner(System.in);

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Client");
        LogRegManager lrm = new LogRegManager(commandInvoker, ioClient, primaryStage);
        primaryStage.setScene(new Scene(lrm.getRoot()));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    private static void getHostAndPort() throws IOException {
        while (true) {
            try {
                System.out.print("Введите хост > ");
                HOST = scanner.nextLine();
                System.out.print("Введите порт > ");
                String port = scanner.nextLine();
                PORT = Integer.parseInt(port.trim());
                serverConnector.connect(HOST, PORT);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Порт должен быть числом");
            } catch (IllegalArgumentException e) {
                System.out.println("Порт вне допустимого диапазона");
            } catch (UnknownHostException | SocketException e) {
                System.out.println("Не удолось подключиться к " + HOST + ":" + PORT + "\nПроверьте хост и порт и попробуйте еще раз");
            }
        }
    }

    public static void main(String... args) throws IOException, ClassNotFoundException {

        ioClient = new StreamReadWriter(System.in, null, true);
        serverConnector = new ServerConnector();
        getHostAndPort();
        StreamReadWriter ioServer = new StreamReadWriter(serverConnector.getInputStream(), serverConnector.getOutputStream(), true); //provides Input / Output Streams

        while(!ioServer.ready()) {}
        Transport fromServer = (Transport)ioServer.readObj(); // reading from ObjectInputStream
        HashMap<String, Command> commandMap = (HashMap)fromServer.getObject();    // reading collection from server

        commandInvoker = new CommandInvoker(commandMap, ioServer);
        launch(args);
    }
}
