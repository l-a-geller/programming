import java.io.IOException;
import java.util.Scanner;
import java.net.*;

public class App {
    private static String FILENAME;
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Client client = new Client();
        try{
            FILENAME = args[0];
            client.act(FILENAME);
        }catch (ConnectException e){
            reviwe(client);
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Please provide a filename, EXAMPLE: java App data.xml");
        }
    }
    private static void reviwe(Client client) throws IOException, InterruptedException, ClassNotFoundException{
        String message = "Server is not working... Quit (Enter Q) or TRY AGAIN (Enter A or ANY other key)\n";
        System.out.println(message);
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()){
            String comm = sc.nextLine();
            if (comm.trim().toUpperCase().equals("Q")){
                break;
            }
            try {
                client.act(FILENAME);
                break;
            }catch (ConnectException e){
                System.out.println(message);
                continue;
            }
        }
    }
}