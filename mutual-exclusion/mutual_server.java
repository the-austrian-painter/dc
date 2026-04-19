import java.io.*;
import java.net.*;

class mutual_server {
    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(7000);
        System.out.println("Server Started");

        while (true) {
            Socket s = ss.accept();
            new Thread(() -> {
                try {
                    BufferedReader in = new BufferedReader(
                        new InputStreamReader(s.getInputStream()));
                    String msg;
                    while ((msg = in.readLine()) != null)
                        System.out.println(msg);
                } catch (Exception e) {}
            }).start();
        }
    }
}
