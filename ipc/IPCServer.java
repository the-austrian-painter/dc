import java.net.*;
import java.io.*;

class IPCServer {
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(1200);
            System.out.println("Server is waiting for the client...");

            Socket s = ss.accept();
            System.out.println("Client connected!");

            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            int a = in.readInt();
            int b = in.readInt();
            int c = a + b;

            System.out.println("Client says: " + a + " " + b);
            out.writeInt(c);

            s.close();
            ss.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
