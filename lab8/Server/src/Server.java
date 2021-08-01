import IO.IOClient;
import IO.IOinterface;
import data.*;
import tools.*;
import tools.DataBase.DataBaseConnector;
import tools.commands.AbstractCommand;
import tools.commands.Command;
import tools.commands.CommandInvoker;
import tools.connector.ClientHandler;
import tools.io.Transport;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.net.BindException;
import java.net.ConnectException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Server {
   private static Scanner sc = new Scanner(System.in);
   private static ClientHandler clientHandler;
   private static int PORT;

   public static boolean sent;
   private static ExecutorService ftp = Executors.newFixedThreadPool(2);
   private static ForkJoinPool fjp = new ForkJoinPool();
   private static ReadWriteLock rrl = new ReentrantReadWriteLock();

   private static void getPORT() throws IOException {
      while (true) {
         try {
            System.out.print("Введите порт, на котором будет работать сервер > ");
            String port = sc.nextLine();
            PORT = Integer.parseInt(port.trim());
            clientHandler = new ClientHandler(PORT);
            break;
         } catch (NumberFormatException e) {
            System.out.println("Порт должен быть числом");
         } catch (IllegalArgumentException e) {
            System.out.println("Порт вне допустимого диапазона");
         }
      }
   }

   private static void loginDB() {
      String host, dbName, user, pass;
      while (true) {
         try {
            System.out.print("Введите хост для доступа к БД > ");
            host = sc.nextLine();

            System.out.print("Введите название БД > ");
            dbName = sc.nextLine();

            System.out.print("Введите логин для доступа к БД > ");
            user = sc.nextLine();

            //Console console = System.console();
            //char[] passwordArray = console.readPassword("Введите пароль для доступа к БД > \n");
            //console.printf("Password entered was: %s%n", new String(passwordArray));
            //pass = new String(passwordArray);

            System.out.print("Введите пароль для доступа к БД > ");
            pass = sc.nextLine();

            DataBaseConnector.init(host, dbName, user, pass);
            break;

         } catch (SQLException e) {
            System.out.println("Не удалось установить подключение к БД. Проверьте данные и попробуйте еще раз");
         } catch (ClassNotFoundException e) {
            System.out.println("Драйвер не обнаружен");
         }
      }
   }

    /**
     * Is a loop handling incoming requests with help of Selector
     */
   public static void main(String[] args) throws IOException {

      getPORT();
      loginDB();
      loadLabs();
      System.out.println("Сервер успешно запущен");
      try {
         IOinterface ioClient = null;

         Transport collectionSender = new Transport("map");
         HashMap<String, AbstractCommand> l = CommandInvoker.getCommands();
         collectionSender.putObject(l);
         Command comm = null;

         while(true) {
            clientHandler.getSelector().select();
            Iterator iter = clientHandler.getSelector().selectedKeys().iterator();

            while(iter.hasNext()) {
               SelectionKey selKey = (SelectionKey)iter.next();
               iter.remove();

               try {
                  if (selKey.isValid()) {
                     if (selKey.isAcceptable()) { clientHandler.acceptConnect(); sent = false; }

                     if (selKey.isWritable()) {
                        if (!sent) {
                           ioClient = new IOClient((SocketChannel)selKey.channel(), true);
                           ioClient.writeObj(collectionSender); sent = true;
                        } else {
                           Command finalComm = comm;
                           IOinterface finalIoClient = ioClient;
                           ftp.submit(() -> {
                              try {
                                 rrl.writeLock().lock();
                                 processCommand(finalComm, finalIoClient);
                              } catch (IOException e) {
                              } finally { rrl.writeLock().unlock(); }
                           });
                        }
                        selKey.interestOps(1);
                     }

                     if (selKey.isReadable()) {
                         ioClient = new IOClient((SocketChannel)selKey.channel(), true);
                         IOinterface finalIoClient1 = ioClient;
                         Future<Command> future = ftp.submit(() -> (Command)((Transport) finalIoClient1.readObj()).getObject());

                         comm = future.get();
                         selKey.interestOps(4);
                     }
                  }
               } catch (ConnectException e) { sent = false;
               } catch (InterruptedException | ExecutionException e) {
               }
            }
         }
      } catch (IndexOutOfBoundsException ex) {
         System.out.println("Please provide PORT");
         System.exit(0);
      }catch (BindException e){ System.out.println("Address & port already in use"); }
   }

    /**
     * Executes a command, puts it into saved commands and sends result to client
     * @param comm A command to be executed
     * @param ioClient Interface for communicating with Client
     * @throws IOException
     */
   private static void processCommand(Command comm, IOinterface ioClient) throws IOException {
      String res = "";
      if (!comm.getName().equals("QUIT")){
         if (comm.needsScanner){
            String labWork = comm.lab;
            DataBaseConnector.writeLab(labWork, comm.login);
         }
         CommandInvoker.loadToSavedCommands(comm);
         if (comm.hasData) comm.execute(comm.data);
         else comm.execute();
         res = comm.getAnswer();
      }
      ioClient.writeln(res);
   }

    /**
     * Loads labs from DB
     */
   private static void loadLabs(){
      TreeSet<LabWork> labs = new TreeSet<>();
      ArrayList<LabWork> labWorks = DataBaseConnector.readLab();
      if (null != labWorks) for (LabWork labWork: labWorks){ labs.add(labWork); }
      LabworksStorage.setData(labs);
      LabworksStorage.setData(LabworksStorage.getSortedByCoordinates());
   }
}
