package tools.connector;

import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ClientHandler {
   ServerSocketChannel ssch = ServerSocketChannel.open();
   Selector selector;
   SocketChannel channel;

   public ClientHandler(int port) throws IOException {
      this.ssch.configureBlocking(false);
      this.ssch.socket().bind(new InetSocketAddress(port));
      this.selector = Selector.open();
      this.ssch.register(this.selector, SelectionKey.OP_ACCEPT);
   }

   public Selector getSelector() {
      return this.selector;
   }

   public void acceptConnect() throws IOException {
      this.channel = this.ssch.accept();
      this.channel.configureBlocking(false);
      this.channel.register(this.selector, SelectionKey.OP_WRITE);
   }

   public SocketChannel getSocketChan() {
      return this.channel;
   }
}
