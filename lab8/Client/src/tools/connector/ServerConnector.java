package tools.connector;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerConnector {
   Socket soc;

   public void connect(String host, Integer port) throws IOException, UnknownHostException, ConnectException {
      this.soc = new Socket(host, port);
      System.out.println("Connection successful");
   }

   public InputStream getInputStream() throws IOException {
      return this.soc.getInputStream();
   }

   public OutputStream getOutputStream() throws IOException {
      return this.soc.getOutputStream();
   }

   public void close() throws IOException {
      this.soc.close();
   }
}
