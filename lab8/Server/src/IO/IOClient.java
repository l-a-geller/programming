package IO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class IOClient implements IOinterface {
   private boolean interactive;
   SocketChannel socCh;

   public IOClient(SocketChannel socCh, boolean interactive) throws IOException {
      this.interactive = interactive;
      this.socCh = socCh;
   }

   public void write(String str) throws IOException {
      ByteBuffer bb = ByteBuffer.wrap(str.getBytes());
      this.socCh.write(bb);
   }

   public void writeln(String str) throws IOException {
      Runnable r = () -> {
         try {
            this.write(str + "\n");
         } catch (IOException e) {}
      };
      Thread thread = new Thread(r);
      thread.start();
   }

   public String readLine() throws IOException {
      return null;
   }

   public boolean hasNextLine() throws IOException {
      return false;
   }

   public boolean ready() {
      return false;
   }

   public boolean isInteractive() {
      return false;
   }

   public void writeObj(Object obj) throws IOException {
      Runnable r = () -> {
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         ObjectOutputStream oos = null;
         try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.flush();
            this.socCh.write(ByteBuffer.wrap(baos.toByteArray()));
         } catch (IOException e) { }
      };
      Thread thread = new Thread(r);
      thread.start();
   }

   public Object readObj() throws IOException, ClassNotFoundException {
      ByteBuffer bb = ByteBuffer.allocate(5120);

      try {
         this.socCh.read(bb);
         return (new ObjectInputStream(new ByteArrayInputStream(bb.array()))).readObject();
      } catch (IOException e) {
         this.socCh.close();
         throw new ConnectException("Connection aborted");
      }
   }

   public Object read() {
      return null;
   }

   public void close() throws IOException {
   }
}
