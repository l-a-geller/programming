package tools.io;

import java.io.*;
import java.util.Scanner;

public class StreamReadWriter {
   private Writer output;
   private Scanner scan;
   private InputStream in;
   private OutputStream out;
   private boolean interactive;
   private BufferedReader bin;

   public StreamReadWriter(InputStream in, OutputStream out, boolean interactive) throws IOException {
      this.interactive = interactive;
      this.in = in;
      this.out = out;
      try{
         this.output = new OutputStreamWriter(out);
      }catch (Exception e){
         //
      }
      this.scan = new Scanner(in);
      this.bin = new BufferedReader(new InputStreamReader(in));
   }

   public void write(String str) {}

   public void writeln(String str) throws IOException{
      if (!str.trim().toUpperCase().equals("QUIT")){
         this.write(" : " + str + "\n");
      }else {
         throw new IOException();
      }
   }

   public String readLine() throws IOException {
      return this.bin.readLine();
   }

   public boolean hasNextLine() throws IOException {
      return this.bin.ready();
   }

   public boolean hasNext() {
      return this.scan.hasNext();
   }

   public boolean ready() throws IOException {
      return this.bin.ready();
   }

   public boolean isInteractive() {
      return this.interactive;
   }

   public void writeObj(Object obj) throws IOException {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ObjectOutputStream oos = new ObjectOutputStream(baos);
      oos.writeObject(obj);
      oos.flush();
      this.out.write(baos.toByteArray());
   }

   public Object readObj() throws IOException, ClassNotFoundException {
      ObjectInputStream ois = new ObjectInputStream(this.in);
      Object obj = ois.readObject();
      return obj;
   }

   public Object read() {
      return this.scan.next();
   }

   public void close() throws IOException {
      this.output.close();
   }
}
