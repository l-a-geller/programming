import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Answer extends Thread {

    private String data;
    private Selector selector;
    private ServerSocket serverSocket;
    private SocketChannel channel;
    private ByteBuffer byteBuffer;
    private Socket socket;

    public Answer(ServerSocketChannel serverSocketChannel, String s){
        try {
            selector = Selector.open();
            serverSocketChannel.configureBlocking(true);
            serverSocket = serverSocketChannel.socket();
            byteBuffer = ByteBuffer.allocateDirect(4096);
            data = s;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            socket = serverSocket.accept();
            System.out.println("Connection from: " + socket);
            channel = socket.getChannel();
        } catch (IOException e) {
            System.err.println("Unable to accept channel");
            e.printStackTrace();
        }
        if (channel != null) {
            try {
                channel.configureBlocking(false);
                channel.register(selector, SelectionKey.OP_READ);
            } catch (IOException e) {
                System.err.println("Unable to use channel");
                e.printStackTrace();
            }
        }

        SocketChannel socketChannel = (SocketChannel) socket.getChannel();
        byteBuffer.clear();
        try {
            byteBuffer = ByteBuffer.wrap(data.getBytes());

            while (byteBuffer.hasRemaining()) {
                socketChannel.write(byteBuffer);
            }
            byteBuffer.clear();
        } catch (IOException e) {
            System.err.println("Error writing back bytes");
            e.printStackTrace();
        }
        try {
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
