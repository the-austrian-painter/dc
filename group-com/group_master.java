import java.io.*;
import java.net.*;
import java.util.*;

class group_master {
    public static void main(String[] args) throws Exception {
        Socket s = new Socket("localhost", 9001);

        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        out.println(name);

        new Thread(() -> {
            try {
                String line;
                while ((line = in.readLine()) != null) {
                   if (!line.startsWith(name + ":") && !line.equals(name + " joined"))
                        System.out.println(line);
                }
            } catch (Exception e) {}
        }).start();

        while (true) {
            System.out.print("Enter message: ");
            String msg = sc.nextLine();
            System.out.println("You: " + msg);
            out.println(msg);
        }
    }
}
