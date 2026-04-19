import java.io.*;
import java.net.*;
import java.util.*;

class mutual_client1 {
    public static void main(String[] args) throws Exception {
        Socket s = new Socket("localhost", 7000);
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);

        ServerSocket ss = new ServerSocket(7001);
        Socket next = ss.accept();
        BufferedReader in = new BufferedReader(
            new InputStreamReader(next.getInputStream()));
        PrintWriter outNext = new PrintWriter(next.getOutputStream(), true);

        Scanner sc = new Scanner(System.in);
        String token = "Token";

        while (true) {
            if (token.equals("Token")) {
                System.out.print("Send data? (yes/no): ");
                if (sc.nextLine().equalsIgnoreCase("yes")) {
                    System.out.print("Enter data: ");
                    out.println(sc.nextLine());
                }
                outNext.println("Token");
                token = "";
            }
            System.out.println("Waiting for token...");
            token = in.readLine();
        }
    }
}
