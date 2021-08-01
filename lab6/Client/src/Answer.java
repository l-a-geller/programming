import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.net.*;
import java.io.*;
import java.nio.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;

public class Answer extends Thread {

    private SocketChannel socketChannel;
    public Answer(SocketChannel socketChannel){
        this.socketChannel = socketChannel;
    }

    @Override
    public void run() {

        try {
            Socket socket = socketChannel.socket();
            InputStream is = socket.getInputStream();

            BufferedReader in = new BufferedReader(new InputStreamReader(is, "US-ASCII"));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(": " + line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
