import java.io.*;
import java.net.*;
import java.util.*;

class group_server {
    static Vector<PrintWriter> clients = new Vector<>();

    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(9001);
        System.out.println("Server running...");

        while (true)
            new Handler(ss.accept()).start();
    }

    static class Handler extends Thread {
        Socket s;
        Handler(Socket s) { this.s = s; }

        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                clients.add(out);

                String name = in.readLine();
                broadcast(name + " joined");

                String msg;
                while ((msg = in.readLine()) != null)
                    broadcast(name + ": " + msg);

            } catch (Exception e) {}
        }

        void broadcast(String msg) {
            for (PrintWriter p : clients) p.println(msg);
        }
    }
}
